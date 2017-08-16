package com.neocoretechs.neovolve.functions;

import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * Do two things.  This function's
 * two children are the things to execute, and must be of void type.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: TwoThings.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */

public class TwoThings extends Function implements Serializable {

  public TwoThings() {
    super(2, voidClass);
  }

  public String getName() {
    return "do2";
  }

  public Class getChildType(int n) {
    return voidClass;
  }

  public void execute_void(Chromosome c, int n, Object[] args) {
    c.execute_void(n, 0, args);
    c.execute_void(n, 1, args);
  }
}