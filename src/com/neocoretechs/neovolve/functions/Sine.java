package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;

import java.io.*;

/**
 * The sine function. Allowed to be of type float or double. Its
 * child is the angle in radians, and must be of the same type as this node.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Sine.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class Sine extends Function implements Serializable {

  public Sine(Class type) {
    super(1,type);
  }

  public String getName() {
    return "SIN";
  }

  public float execute_float(Chromosome c, int n, Object[] args) {
    float f = c.execute_float(n, 0, args);
    // clip to -10000 -> 10000
    return (float)Math.sin(Math.max(-10000.0f,Math.min(f,10000.0f)));
  }

  public double execute_double(Chromosome c, int n, Object[] args) {
    double f = c.execute_double(n, 0, args);
    // clip to -10000 -> 10000
    return Math.sin(Math.max(-10000.0,Math.min(f,10000.0)));
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    return ((Compatible)c.execute_object(n, 0, args)).execute_sin();
  }

  public Class getChildType(int i) {
    return returnType;
  }

  public static interface Compatible {
    public Object execute_sin();
  }
}