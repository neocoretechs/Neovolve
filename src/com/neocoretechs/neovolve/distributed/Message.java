package com.neocoretechs.neovolve.distributed;
import java.io.*;
import java.util.*;

public abstract class Message {

  /**
   * The version of the message protocol.
   */
  protected final static int version = 1;

  private final static Hashtable messageTypes = new Hashtable();
  private final static Hashtable messageClasses = new Hashtable();

  private final static void register(int type, Class c) {
    messageTypes.put(new Integer(0), c);
    messageClasses.put(c, new Integer(0));
  }

  static {
    register(0, HostInfoMessage.class);
  }

  protected Message() {
  }

  public final static int getVersion() {
    return version;
  }

  public final void send(OutputStream out) throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    DataOutputStream encoded = new DataOutputStream(bytes);

    encoded.writeInt(version);
    encoded.writeInt( ((Integer)messageClasses.get(getClass())).intValue() );
    encode(encoded);
    encoded.flush();

    DataOutputStream dout = new DataOutputStream(out);
    byte[] array = bytes.toByteArray();
    System.out.println("Message is " + array.length + " bytes");
    dout.writeInt(array.length);
    dout.write(array);
    dout.flush();
  }

  protected abstract void encode(DataOutputStream out) throws IOException;

  public final static Message receive(InputStream in) throws IOException {
    DataInputStream din = new DataInputStream(in);

    int num = din.readInt();
    System.out.println("Message received is " + num + " bytes");
    if (num>1024)
      throw new IOException("Wacky length received");
    byte[] array = new byte[num];
    din.readFully(array);
    ByteArrayInputStream bytes = new ByteArrayInputStream(array);
    DataInputStream decoded = new DataInputStream(bytes);

    if (decoded.readInt()!=version)
      return null;

    int type = decoded.readInt();

    System.out.println("Type " + type);

    Class messageClass = (Class)messageTypes.get(new Integer(type));

    if (messageClass==null)
      return null;

    System.out.println("...class " + messageClass.getName());

    java.lang.reflect.Method[] ms = messageClass.getMethods();
    for (int i=0; i<ms.length; i++)
      System.out.println(ms[i]);


    try {
      return (Message)messageClass.getMethod("decode",
        new Class[] {DataInputStream.class}).invoke(null, new Object[] {decoded});
    } catch (Exception ex) {
      System.out.println("Exception in calling decode: " + ex);
      return null;
    }
  }

  public static Message decode(DataInputStream in) throws IOException { return null; }
}