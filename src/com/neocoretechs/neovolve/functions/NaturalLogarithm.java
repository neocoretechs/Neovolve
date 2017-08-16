package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;

import java.io.*;

/**
 * The protected natural logarithm function. Allowed to be of type float or double. Its
 * child is the argument and must be of the same type as this node.
 * <p>
 * If the argument is 0, the result is 0.
 * <p>
 * For float exponents, if the absolute result is greater than 1e10 then the result is clipped to
 * +/-1e10.
 * <p>
 * For double exponents, if the absolute result is greater than 1e100 then the result is clipped
 * to +/-1e100.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: NaturalLogarithm.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class NaturalLogarithm extends Function implements Serializable {

  public NaturalLogarithm(Class type) {
    super(1,type);
  }

  public String getName() {
    return "LN";
  }

  public float execute_float(Chromosome c, int n, Object[] args) {
    float f = c.execute_float(n, 0, args);
    if (f==0.0f)
      return 0.0f;
    f = (float)Math.log(Math.abs(f));
    if (f>1.0e10f)
      f=1.0e10f;
    else if (f<-1.0e10f)
      f=-1.0e10f;
    return f;
  }

  public double execute_double(Chromosome c, int n, Object[] args) {
    double d = c.execute_double(n, 0, args);
    if (d==0.0)
      return 0.0;
    d = Math.log(Math.abs(d));
    if (d>1.0e100)
      d=1.0e100;
    else if (d<-1.0e100)
      d=-1.0e100;
    return d;
  }

  public Class getChildType(int i) {
    return returnType;
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    return ((Compatible)c.execute_object(n, 0, args)).execute_ln();
  }

  public static interface Compatible {
    public Object execute_ln();
  }

}