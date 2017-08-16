package com.neocoretechs.neovolve.functions;

import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * Do something a number of times.  This function's first child
 * is an integer which tells how many times to execute the second (void)
 * child. The number of times is limited to between 0 and 10 inclusive.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Loop.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */

public class Loop extends Function implements Serializable {

  public Loop() {
    super(2, voidClass);
  }

  public String getName() {
    return "loop";
  }

  public Class getChildType(int n) {
    return n==0 ? integerClass : voidClass;
  }

  public void execute_void(Chromosome c, int n, Object[] args) {
    int m = Math.min(c.execute_int(n, 0, args), 10);
    for (int i=0; i<m; i++)
    	c.execute_void(n, 1, args);
  }
}