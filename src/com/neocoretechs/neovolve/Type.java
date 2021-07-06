package com.neocoretechs.neovolve;

import java.io.*;

/**
 * The type of a node. I use Type.X instead of X.class because, at least in
 * Sun's 1.3 compiler, X.class is implemented as Class.forName("X"), which is
 * hideously expensive.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 * <p>
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Type.java,v 1.2 2000/10/12 15:22:55 groovyjava Exp $
 */
public class Type extends Object implements Serializable {
  private static final long serialVersionUID = 2763509811283151968L;

  private static int maxtype = 0;

  public static Type Boolean = new Type("boolean");
  public static Type Integer = new Type("integer");
  public static Type Long = new Type("long");
  public static Type Float = new Type("float");
  public static Type Double = new Type("double");
  public static Type Void = new Type("void");
  public static Type Object = new Type("object");

  private int type;
  private String name;

  private Type(String n) {
    type = maxtype++;
    name = n;
  }

  public String getName() {
    return name;
  }

  public boolean equals(Type t) {
    return type==t.type;
  }
}
