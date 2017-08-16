package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * The true boolean terminal.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: True.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class True extends Function implements Serializable {

  public True() {
    super(0, booleanClass);
  }

  public String getName() {
    return "T";
  }

  public boolean execute_boolean(Chromosome c, int n, Object[] args) {
    return true;
  }

  public Class getChildType(int parm1) {
    return null;
  }
}
