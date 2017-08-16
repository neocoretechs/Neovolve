package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

import java.io.*;

/**
 * Do nothing.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Nop.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */

public class Nop extends Function implements Serializable {

  public Nop() {
    super(0, voidClass);
  }

  public String getName() {
    return "nop";
  }

  public Class getChildType(int parm1) {
    return null;
  }

  public void execute_void(Chromosome c, int n, Object[] args) {
  }
}