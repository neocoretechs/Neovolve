package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;

import java.io.*;

/**
 * The protected natural exponential function. Allowed to be of type float or double. Its
 * child is the exponent and must be of the same type as this node.
 * <p>
 * For float exponents, if the result is greater than 1e10 then the result is set to
 * 1e10.
 * <p>
 * For double exponents, if the result is greater than 1e100 then the result is set to 1e100.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Exponential.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class Exponential extends Function implements Serializable {

  public Exponential(Class type) {
    super(1,type);
  }

  public String getName() {
    return "EXP";
  }

  public float execute_float(Chromosome c, int n, Object[] args) {
    float f = c.execute_float(n, 0, args);
    // clip to -10000 -> 20
    return (float)Math.exp(Math.max(-10000.0f,Math.min(f,20.0f)));
  }

  public double execute_double(Chromosome c, int n, Object[] args) {
    double f = c.execute_double(n, 0, args);
    // clip to -10000 -> 20
    return Math.exp(Math.max(-10000.0,Math.min(f,20.0)));
  }

  public Class getChildType(int i) {
    return returnType;
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    return ((Compatible)c.execute_object(n, 0, args)).execute_exp();
  }

  public static interface Compatible {
    public Object execute_exp();
  }
}