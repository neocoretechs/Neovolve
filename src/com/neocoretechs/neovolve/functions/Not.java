package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * The logical-not function. Allowed to be of type boolean. Its
 * one child must be of type boolean.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Not.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class Not extends Function implements Serializable {

  /**
   * Creates a logical-not node
   *
   * @since 1.0
   */
  public Not() {
    super(1, booleanClass);
  }

  public String getName() {
    return "!";
  }

  public boolean execute_boolean(Chromosome c, int n, Object[] args) {
    return !c.execute_boolean(n, 0, args);
  }

  public Class getChildType(int i) {
    return booleanClass;
  }
}