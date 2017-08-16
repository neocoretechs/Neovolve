package com.neocoretechs.neovolve;

import java.util.*;
import java.io.*;

/**
 * Abstract class representing a method of selecting individuals for evolutionary operations.
 * Classes extending this class must implement the {@link #select select} method.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: SelectionMethod.java,v 1.2 2000/10/12 15:22:55 groovyjava Exp $
 */
public abstract class SelectionMethod implements Serializable {

  /**
   * Select an individual based on some method.
   *
   * @param random the random number generator to use
   * @param world the World for the run
   * @return the individual chosen from the world's population
   *
   * @since 1.0
   */
  public abstract Individual select(World world);
}