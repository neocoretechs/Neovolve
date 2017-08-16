package com.neocoretechs.neovolve.distributed;

import java.io.*;
import java.net.*;

public class HostInfoMessage extends Message {

  public Announcer.WorldAddress address;

  public HostInfoMessage(Announcer.WorldAddress address) {
    this.address = address;
  }

  protected void encode(DataOutputStream out) throws IOException {
    out.writeUTF(address.address.getHostName());
    out.writeInt(address.port);
  }

  public static Message decode(DataInputStream in) throws IOException {
    Announcer.WorldAddress addr = new Announcer.WorldAddress();

    String name = in.readUTF();
    System.out.println("name is " + name);
    addr.address = InetAddress.getByName(name);
    addr.port = in.readInt();

    return new HostInfoMessage(addr);
  }
}