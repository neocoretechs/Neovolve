package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;

import java.util.*;
import java.io.*;

/**
 * Represents an argument on a chromosome. World-creators should not use this
 * class. Instances are generated automatically by chromosomes.
 *
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Argument.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class Argument extends Function implements Serializable {

  //static Hashtable[] arguments = null;
  //static Stack stack = new Stack();
  //static Object[] args = null;
  int argnum;

  public Argument(int num /*, int chromosomeNum */, Class type) {
    super(0,type);
    argnum = num;
    // arguments[chromosomeNum].put(new Integer(argnum), this);
  }

  /**
   * Creates an instance of an Argument.
   * If an Argument of that name already exists, that is returned.
   * Otherwise a new instance is created, and it
   * is placed into the static hashtable for later retrieval by name via {@link #get get} or
   * {@link #getArgument getArgument}.
   *
   * @param num the number of the Argument to create
   * @param chromosomeNum the number of chromosome this Argument belongs to
   * @param the type of this Argument
   *
   * @since 1.0.1
   */

	 /*
  public static Argument create(int num, int chromosomeNum, Type type) {
    Argument arg;

    if (arguments==null || chromosomeNum>=arguments.length) { // need to create more hashtables
      Hashtable[] newtables = new Hashtable[chromosomeNum+1];
      if (arguments!=null)
        System.arraycopy(arguments, 0, newtables, 0, arguments.length);
      for (int i=(arguments==null ? 0 : arguments.length); i<newtables.length; i++)
        newtables[i] = new Hashtable();
      arguments = newtables;
    }

    if ( (arg=getArgument(num, chromosomeNum))!=null )
      return arg;
    return new Argument(num, chromosomeNum, type);
  }
	*/

  public String getName() {
    return "ARG"+argnum;
  }

  /**
   * Creates a new stack frame consisting of a number of arguments. Saves the original
   * set of arguments and creates a new set of arguments. Used when calling chromosomes,
   * where the previous argument values must be saved before the argument values are set.
   *
   * @param n the number of arguments the new frame should contain
   *
   * @return a reference to the new frame for convenience
   *
   * @since 1.0.1
   */
	 /*
  public static Object[] newFrame(int n) {
    stack.push(args);
    args = new Object[n];
    return args;
  }
	*/

  /**
   * Restores the previous stack frame. Used when returning from chromosomes, where the
   * previous argument values must be restored.
   *
   * @since 1.0.1
   */
	 /*
  public static void restoreFrame() {
    args = (Object[])stack.pop();
  }
	*/

  /**
   * Sets an argument without having to obtain a reference to the argument.
   *
   * @param num the number of the argument to set
   * @param value the value to set the argument with
   *
   * @since 1.0.1
   */
	 /*
  public static void set(int num, Object value) {
    args[num] = value;
  }*/

  /**
   * Gets the value of an argument without having to obtain a reference to
   * the argument.
   *
   * @param num the number of the argument to get
   * @return an Object representing the value of the argument
   *
   * @since 1.0.1
   */
	 /*
  public static Object get(int num) {
    return args[num];
  }*/

  /**
   * Gets the one instance of a numbered argument.
   *
   * @param num the number of the argument to get
   * @param chromosomeNum the number of the chromosome from which to get the argument
   *
   * @return the argument, or null if that number wasn't found.
   *
   * @since 1.0.1
   */
	 /*
  public static Argument getArgument(int num, int chromosomeNum) {
    return (Argument)arguments[chromosomeNum].get(new Integer(num));
  }*/

  /**
   * Sets the value of this argument.
   *
   * @param value the value to set this argument with
   *
   * @since 1.0.1
   */
	 /*
  public void set(Object value) {
    args[argnum] = value;
  }*/

  /**
   * Gets the value of this argument.
   *
   * @return an Object representing the value of this argument, or null if this
   * argument has not yet been set.
   *
   * @since 1.0.1
   */
	 /*
  public Object get() {
    return args[argnum];
  }*/

  public boolean execute_boolean(Chromosome c, int n, Object[] args) {
    return ((Boolean)args[argnum]).booleanValue();
  }

  public int execute_int(Chromosome c, int n, Object[] args) {
    return ((Integer)args[argnum]).intValue();
  }

  public long execute_long(Chromosome c, int n, Object[] args) {
    return ((Long)args[argnum]).longValue();
  }

  public float execute_float(Chromosome c, int n, Object[] args) {
    return ((Float)args[argnum]).floatValue();
  }

  public double execute_double(Chromosome c, int n, Object[] args) {
    return ((Double)args[argnum]).doubleValue();
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    return args[argnum];
  }

  public Class getChildType(int num) {
    return null;
  }

  public boolean isConstant() {
    return false;
  }
}
