package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;

import java.io.*;

/**
 * The protected divide function. Allowed to be of type int, long, float, or double. Its
 * first child is the numerator and its second child is the denominator.
 * Both children must be of the same type as this node. If the denominator is zero, the
 * node returns 1.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Divide.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class Divide extends Function implements Serializable {

  /**
   * Creates a divide node of the given type
   *
   * @param type the type of the node
   *
   * @since 1.0
   */
  public Divide(Class type) {
    super(2,type);
  }

  public String getName() {
    return "/";
  }

  public int execute_int(Chromosome c, int n, Object[] args) {
    int d = c.execute_int(n, 1, args);
    return d==0 ? 1 : (c.execute_int(n, 0, args)/d);
  }

  public long execute_long(Chromosome c, int n, Object[] args) {
    long d = c.execute_long(n, 1, args);
    return d==0 ? 1 : (c.execute_long(n, 0, args)/d);
  }

  public float execute_float(Chromosome c, int n, Object[] args) {
    float d = c.execute_float(n, 1, args);
    return d==0 ? 1 : (c.execute_float(n, 0, args)/d);
  }

  public double execute_double(Chromosome c, int n, Object[] args) {
    double d = c.execute_double(n, 1, args);
    return d==0 ? 1 : (c.execute_double(n, 0, args)/d);
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    return ((Compatible)c.execute_object(n, 0, args)).execute_divide(c.execute_object(n, 1, args));
  }

  public Class getChildType(int i) {
    return returnType;
  }

  public static interface Compatible {
    public Object execute_divide(Object o);
  }
}