package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;

import java.io.*;

/**
 * The constant 1. Allowed to be of type int, long, float or double.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: One.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class One extends Function implements Serializable {

  public One(Class type) {
    super(0,type);
  }

  public String getName() {
    return "1";
  }

  public int execute_int(Chromosome c, int n, Object[] args) {
    return 1;
  }

  public long execute_long(Chromosome c, int n, Object[] args) {
    return 1;
  }

  public float execute_float(Chromosome c, int n, Object[] args) {
    return 1.0f;
  }

  public double execute_double(Chromosome c, int n, Object[] args) {
    return 1.0;
  }

  public Class getChildType(int i) {
    return null;
  }

}