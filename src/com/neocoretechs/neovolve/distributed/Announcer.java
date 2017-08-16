package com.neocoretechs.neovolve.distributed;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Announces to the network that this world is joining, and handles replies and
 * other announcements from the network.
 */
public class Announcer extends Thread {

  static Random random = new Random();

  /**
   * Represents an internet address plus a port number
   */
  static class WorldAddress {
    public InetAddress address;
    public int port;

    public boolean equals(WorldAddress p) {
      return address==p.address && port==p.port;
    }
  }

  /**
   * The address of this world's server socket.
   */
  static WorldAddress myAddress = new WorldAddress();

  ServerSocket server;

  /**
   * Stores all the ports this world knows about. There are at most 100. If there
   * are 100 worlds connected in a network, then they are, in theory, fully connected.
   * That is, each world knows about every other world.
   * <p>
   * Now, if one world wants to join the network, the network becomes no longer fully
   * connected. We would want the new world to know about all 100 other worlds, but all those
   * other worlds have to get rid of one entry, preferably a different one for each world.
   *
   */
  static WorldAddress[] addresses = new WorldAddress[100];
  static int numAddresses = 0;

  public Announcer(int myPort, InetAddress anAddress, int aPort) throws UnknownHostException {
    this.myAddress.port = myPort;
    this.myAddress.address = InetAddress.getLocalHost();

    System.out.println("My address is " + this.myAddress.address.getHostName() +
      "(" + this.myAddress.address.getHostAddress() +
      "):" + myPort);

    if (anAddress!=null) {
      addresses[0] = new WorldAddress();
      addresses[0].address = anAddress;
      addresses[0].port = aPort;
      System.out.println("The other address is " + anAddress.getHostName() +
        ":" + aPort);
      numAddresses++;
    }
  }

  /**
   * Run the Announcer thread
   */
  public void run() {

    try {
      server = new ServerSocket(myAddress.port);

      // Send a join request to the only other world this world knows about

      if (numAddresses>0) {
        System.out.println("Sending initial host info to " +
          addresses[0].address.getHostName() + "(" +
          addresses[0].address.getHostAddress() + "):" + addresses[0].port);
        Socket connection = new Socket(addresses[0].address, addresses[0].port);
        OutputStream out = connection.getOutputStream();

        HostInfoMessage msg = new HostInfoMessage(myAddress);
        msg.send(out);
        connection.close();
      }

      // Now we can wait for host information messages.

      while (true) {
        System.out.println("Waiting for a connection");
        Socket connection = server.accept();

        // fork off a thread to handle the message. The thread is responsible
        // for closing the connection.

        System.out.println("Connection: starting a handler");
        MessageHandler handler = new MessageHandler(connection);
        handler.start();
      }

    } catch (IOException ex) {
      System.out.println(ex);
    }

  }

  public static void main(String[] args) throws Exception {

    if (args.length==1) {
      Announcer ann = new Announcer(Integer.parseInt(args[0]), null, 0);
      ann.run();
    } else if (args.length==3) {
      Announcer ann = new Announcer(Integer.parseInt(args[0]),
        InetAddress.getByName(args[1]), Integer.parseInt(args[2]));
      ann.run();
    } else {
      System.out.println("Usage: Announcer <myport> [ <other hostname> <other port> ]");
    }
  }

}