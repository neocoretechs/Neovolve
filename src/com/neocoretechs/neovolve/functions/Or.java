package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * The logical-or function. Allowed to be of type boolean. Its
 * two children are the arguments, and must be of type boolean.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Or.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class Or extends Function implements Serializable {

  /**
   * Creates a logical-or node
   *
   * @since 1.0
   */
  public Or() {
    super(2, booleanClass);
  }

  public String getName() {
    return "|";
  }

  public boolean execute_boolean(Chromosome c, int n, Object[] args) {
    return c.execute_boolean(n, 0, args) || c.execute_boolean(n, 1, args);
  }

  public Class getChildType(int i) {
    return booleanClass;
  }
}