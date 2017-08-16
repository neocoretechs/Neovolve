package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * The if function. Allowed to be of any type. It has three children,
 * the first of which must be boolean. The other two children must be of the
 * same type as this node. If the first child evaluates to true, the return value
 * of the first child is used as the return value of this node, otherwise the
 * second child is used.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: If.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class If extends Function implements Serializable {

  /**
   * Creates an if node
   *
   * @since 1.0
   */
  public If(Class type) {
    super(3,type);
  }

  public String getName() {
    return "IF";
  }

  public boolean execute_boolean(Chromosome c, int n, Object[] args) {
    return c.execute_boolean(n, 0, args) ? c.execute_boolean(n, 1, args) :
      c.execute_boolean(n, 2, args);
  }

  public void execute_void(Chromosome c, int n, Object[] args) {
    if (c.execute_boolean(n, 0, args))
      c.execute_void(n, 1, args);
    else
      c.execute_void(n, 2, args);
  }

  public int execute_int(Chromosome c, int n, Object[] args) {
    return c.execute_boolean(n, 0, args) ? c.execute_int(n, 1, args) :
      c.execute_int(n, 2, args);
  }

  public long execute_long(Chromosome c, int n, Object[] args) {
    return c.execute_boolean(n, 0, args) ? c.execute_long(n, 1, args) :
      c.execute_long(n, 2, args);
  }

  public float execute_float(Chromosome c, int n, Object[] args) {
    return c.execute_boolean(n, 0, args) ? c.execute_float(n, 1, args) :
      c.execute_float(n, 2, args);
  }

  public double execute_double(Chromosome c, int n, Object[] args) {
    return c.execute_boolean(n, 0, args) ? c.execute_double(n, 1, args) :
      c.execute_double(n, 2, args);
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    return c.execute_boolean(n, 0, args) ? c.execute_object(n, 1, args) :
      c.execute_object(n, 2, args);
  }

  public Class getChildType(int i) {
    if (i==0)
      return booleanClass;
    else
      return returnType;
  }
}