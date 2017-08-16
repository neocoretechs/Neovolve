package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * The addition function. Allowed to be of type int, long, float, or double. Its
 * two children are the addends, and must be of the same type as this node.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Add.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class Add extends Function implements Serializable {

  /**
   * Creates an addition node of the given type
   *
   * @param type the type of the node
   *
   * @since 1.0
   */
  public Add(Class type) {
    super(2,type);
  }

  public String getName() {
    return "+";
  }

  public int execute_int(Chromosome c, int n, Object[] args) {
    return c.execute_int(n, 0, args) + c.execute_int(n, 1, args);
  }

  public long execute_long(Chromosome c, int n, Object[] args) {
    return c.execute_long(n, 0, args) + c.execute_long(n, 1, args);
  }

  public float execute_float(Chromosome c, int n, Object[] args) {
    return c.execute_float(n, 0, args) + c.execute_float(n, 1, args);
  }

  public double execute_double(Chromosome c, int n, Object[] args) {
    return c.execute_double(n, 0, args) + c.execute_double(n, 1, args);
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    return ((Compatible)c.execute_object(n, 0, args)).execute_add(c.execute_object(n, 1, args));
  }

  public Class getChildType(int i) {
    return returnType;
  }

  public static interface Compatible {
    public Object execute_add(Object o);
  }

}
