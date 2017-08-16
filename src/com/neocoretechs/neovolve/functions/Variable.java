package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;

import java.util.*;
import java.io.*;

/**
 * A node representing a named variable. Allowed to be of type boolean,
 * int, long, float, or double.
 * <p>
 * A named variable is created by constructing an instance of the Variable, giving
 * it a name and type. From that point on, all references to that particular variable
 * should be through either the instance initially created, or looked up via the
 * {@link #getVariable getVariable} method.
 * <p>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Variable.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class Variable extends Function implements Serializable {

  static Hashtable vars = new Hashtable();
  String name;
  public Object value;

  protected Variable(String name, Class type) {
    super(0, type);
    this.name = name;
    vars.put(name, this);
  }

  /**
   * Creates an instance of a Variable.
   * If a Variable of that name already exists, that is returned.
   * Otherwise a new instance is created, its value is initialized to null, and it
   * is placed into the static hashtable for later retrieval by name via {@link #get get} or
   * {@link #getVariable getVariable}.
   *
   * @param name the name of the Variable to create
   * @param type the type of the Variable to create
   *
   * @since 1.0
   */

  public static Variable create(String name, Class type) {
    Variable var;

    if ( (var=getVariable(name))!=null )
      return var;
    return new Variable(name, type);
  }

  /**
   * Sets a named variable without having to obtain a reference to the named variable.
   *
   * @param name the name of the variable to set
   * @param value the value to set the variable with
   *
   * @since 1.0
   */
  public static void set(String name, Object value) {
    ((Variable)vars.get(name)).value = value;
  }

  /**
   * Gets the value of a named variable without having to obtain a reference to
   * the named variable.
   *
   * @param name the name of the variable to get
   * @return an Object representing the value of the named variable, or null
   * if that name wasn't found.
   *
   * @since 1.0
   */
  public static Object get(String name) {
    return ((Variable)vars.get(name)).value;
  }

  /**
   * Gets the one instance of a named variable.
   *
   * @param name the name of the variable to get
   * @return the named variable, or null if that name wasn't found.
   *
   * @since 1.0
   */
  public static Variable getVariable(String name) {
    return (Variable)vars.get(name);
  }

  /**
   * Sets the value of this named variable.
   *
   * @param value the value to set this variable with
   *
   * @since 1.0
   */
  public void set(Object value) {
    this.value = value;
  }

  /**
   * Gets the value of this named variable.
   *
   * @return an Object representing the value of this variable, or null if this
   * variable has not yet been set.
   *
   * @since 1.0
   */
  public Object get() {
    return value;
  }

  public String getName() {
    return name;
  }

  public boolean execute_boolean(Chromosome c, int n, Object[] args) {
    return ((Boolean)value).booleanValue();
  }

  public int execute_int(Chromosome c, int n, Object[] args) {
    return ((Integer)value).intValue();
  }

  public long execute_long(Chromosome c, int n, Object[] args) {
    return ((Long)value).longValue();
  }

  public float execute_float(Chromosome c, int n, Object[] args) {
    return ((Float)value).floatValue();
  }

  public double execute_double(Chromosome c, int n, Object[] args) {
    return ((Double)value).doubleValue();
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    return value;
  }

  public Class getChildType(int i) {
    return returnType;
  }

  public boolean isConstant() {
    return false;
  }
}
