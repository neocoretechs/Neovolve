package com.neocoretechs.neovolve;

import java.io.*;
import java.net.*;
import java.util.zip.*;

/**
 * A thread which accepts a connection on its socket, downloads a World,
 * computes the fitnesses of some portion of it, and returns those fitnesses.
 */

public class ComputeWorker extends Thread {

	ServerSocket server;
  int startIndex;
  int numInd;

  public ComputeWorker(int port) throws IOException {
    server = new ServerSocket(port, 0);
  }

  public void run() {
    try {
      while (true) {
	      System.out.println("Worker waiting for connection");

		    Socket socket = server.accept();

	      System.out.println("Worker got connection");

		    // We'll be getting a GZIP stream which should be fed to an Object stream.

		    ObjectInputStream in = new ObjectInputStream(
		    	new GZIPInputStream(socket.getInputStream()));

//				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		    // Get the start index and number of individuals to process

		    startIndex = in.readInt();
		    numInd = in.readInt();

      	System.out.println("Worker will compute " + numInd +
       		" individuals starting from " + startIndex);

		    // Get the World

		    World world = (World)in.readObject();

		    // Process the individuals

		    long t = world.computeSome(startIndex, numInd);

		    // Send the results back up... probably not worth it to GZIP.

		    ObjectOutputStream out = new ObjectOutputStream(
		    	socket.getOutputStream());

		    // The time it took to process

		    out.writeLong(t);

		    // The fitnesses;

		    float[] fitnesses = new float[numInd];
		    for (int i=0; i<numInd; i++)
		    	fitnesses[i] = world.getPopulation().getIndividual(startIndex+i).getFitness();

		    out.writeObject(fitnesses);
      	out.flush();

        // Wait for the boss to close the connection

        try {
          if (socket.getInputStream().read()==-1)
          	System.out.println("End of stream");
        } catch (IOException ex) {
          System.out.println("Connection closed");
        }

		    // That's it... close the socket and wait for another connection.

		    socket.close();
      }

    } catch (Exception ex) {
      System.out.println(ex);
    }

  }

}