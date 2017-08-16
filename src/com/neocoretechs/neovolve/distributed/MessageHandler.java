package com.neocoretechs.neovolve.distributed;

import java.net.*;
import java.io.*;

public class MessageHandler extends Thread {

  private Socket connection;

  public MessageHandler(Socket conn) {
    connection = conn;
  }

  public void run() {
    try {
      InputStream in = connection.getInputStream();

      // Receive the message.

      Message msg = Message.receive(in);

      connection.close();

      System.out.println("Message read");

      // This block is synchronized -- don't want to mess with Announcer if
      // some other thread is messing with it.

      synchronized (Announcer.addresses) {

        if (msg instanceof HostInfoMessage) {

          HostInfoMessage info = (HostInfoMessage)msg;

          // extract the host information.

          Announcer.WorldAddress address = info.address;

          System.out.println("Message is host info " + address.address.getHostName() +
            ":" + address.port);

          // if this address is already known to us, or if it is our own address,
          // ignore the message.

          if (address.equals(Announcer.myAddress)) {
            System.out.println("...which is me. Ignoring.");
            return;
          }

          boolean known = false;

          for (int i=0; i<Announcer.numAddresses; i++)
            if (address.equals(Announcer.addresses[i])) {
              known = true;
              break;
            }

          if (known) {
            System.out.println("...about which I already know. Ignoring.");
            return;
          }

          System.out.println("...adding to my address table");

          // if this port is unknown to us, and we have room in our port table...

          if (Announcer.numAddresses<Announcer.addresses.length) {

            // add it to the port table

            Announcer.addresses[Announcer.numAddresses] = new Announcer.WorldAddress();
            Announcer.addresses[Announcer.numAddresses++] = address;

            // propagate the info message to all other ports

            for (int i=0; i<Announcer.numAddresses-1; i++) {
              System.out.println("...propagating to " +
                Announcer.addresses[i].address.getHostName() + ":" +
                Announcer.addresses[i].port);
              connection = new Socket(Announcer.addresses[i].address, Announcer.addresses[i].port);
              msg.send(connection.getOutputStream());
              connection.close();
            }

          } else {

          // if, on the other hand, this address is unknown to us and we don't have
          // room in our address table...

            // Choose a random address to get rid of

            int a = Announcer.random.nextInt(Announcer.numAddresses);

            Announcer.addresses[a] = address;

            // propagate the info message to all other ports

            for (int i=0; i<Announcer.numAddresses; i++) {
              if (i!=a) {
                System.out.println("...propagating to " +
                  Announcer.addresses[i].address.getHostName() + ":" +
                  Announcer.addresses[i].port);
                connection = new Socket(Announcer.addresses[i].address, Announcer.addresses[i].port);
                msg.send(connection.getOutputStream());
                connection.close();
              }
            }
          }

        }
      }

      System.out.println("...message handled.");
    } catch (IOException ex) {
      System.out.println("Exception in message handler: " + ex);
      throw new RuntimeException();
    }

  }
}