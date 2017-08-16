package com.neocoretechs.neovolve;

import java.io.*;


/** The base class of all terminals and functions. A generic Function consists of
 * zero or more children Functions and a return type. If a Function has zero children
 * then it is a terminal. If a Function has more than zero children then it is a
 * function.
 * <P>
 * Executing a Function requires knowing some context. For example, the Chromosome in which
 * this Function is executing and the index number of the Function into the Chromosome's function
 * list is required so that the Function can execute its children if necessary. Since knowledge of
 * a Function's children is kept in the Chromosome for efficiency, the above context is necessary.
 * <P>
 * Part of the context is the current Chromosome's argument list, if the Chromosome was called from
 * another Chromosome. The argument list isn't kept in the Chromosome because a Chromsome can be called
 * recursively. Thus, the argument list needs to be kept on the stack.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Function.java,v 1.1 2000/10/12 15:22:55 groovyjava Exp $
 */
public abstract class Function implements Serializable {

	public final static Class booleanClass = Boolean.class;
	public final static Class integerClass = Integer.class;
	public final static Class longClass = Long.class;
	public final static Class floatClass = Float.class;
	public final static Class doubleClass = Double.class;
	public final static Class voidClass = Void.class;


  /**
   * The return type of this node.
   *
   * @since 1.0
   */
  protected Class returnType;

  protected int arity;

  /**
   * The individual currently being evaluated. This is here for nodes (such as ADF)
   * whose execution depends on the individual. It is automatically set by the
   * {@link World World} when it evaluates an individual.
   *
   * @since 1.0
   */
  transient protected static Individual individual = null;

  /**
   * Create a node with the given arity (i.e. number of children) and the given type.
   *
   * @param arity the number of children
   * @param type the return type for this node
   *
   * @since 1.0
   */
  protected Function(int arity, Class type) {
    /*if (arity!=0)
      children = new Node[arity]; */
    returnType = type;
    this.arity = arity;
  }

  /**
   * Make a deep copy of this node. A deep copy of a terminal is this node itself --
   * the node is not cloned.
   *
   * @return the copy of the node
   *
   * @since 1.0
   */
  /*public Node deepCopy() {
    if (children==null)
      return this;
    Node n = (Node)clone();
    if (children!=null)
      for (int i=0; i<children.length; i++)
        n.children[i] = children[i].deepCopy();
    return n;
  }*/

  /**
   * Clone this node. This is used when we need to make a copy of a node and need
   * to retain the node's class. The resulting copy has the same arity and number of children,
   * but the children are blank (not set).
   * <p>
   * If you create a class extending from this class, you should be aware that the members
   * of that class will be shallow-copied on a cloning. Thus if there are members you do
   * not want shallow-copied (such as instance-specific arrays), you should override
   * the clone method to perform the cloning operation you want.
   *
   * @return the copy of the node
   *
   * @since 1.0
   */
  /*public Object clone() {
    Node o;

    try {
      o = (Node)super.clone();
    } catch (CloneNotSupportedException ex) {
      // never happens
      return null;
    }
    if (o.children!=null)
      o.children = new Node[children.length];
    return o;
  }*/

  /**
   * Sets the individual currently being evaluated.
   *
   * @param individual the individual currently being evaluated
   *
   * @since 1.0
   */
  public static void setIndividual(Individual individual) {
    Function.individual = individual;
  }

  /**
   * Gets the given child node, between 0 and number of children-1
   *
   * @param i the child number
   *
   * @return the child node
   *
   * @since 1.0
   */
  /*public Node getChild(int i) {
    return children[i];
  }*/


  /**
   * Gets the arity (or number of children) of this node.
   *
   * @return the arity of this node
   *
   * @since 1.0
   */
  public int getArity() {
    return arity;
  }

  /**
   * Sets the given child to the given node.
   *
   * @param i the child number to set, between 0 and number of children-1
   * @param n the node to set the child to
   *
   * @since 1.0
   */
  /*public void setChild(int i, Node n) {
    children[i] = n;
  }*/

  /**
   * Gets the return type of this node
   *
   * @return the return type of this node
   *
   * @since 1.0
   */
  public Class getReturnType() {
    return returnType;
  }


  /**
   * Sets the return type of this node
   *
   * @param type the type to set the return type to
   *
   * @since 1.0
   */

  public void setReturnType(Class type) {
    returnType = type;
  }

  /**
   * Executes this node as a boolean.
   *
   * @param c the current Chromosome which is executing
   * @param n the index of the Function in the Chromosome's Function array which is executing
   * @param args the arguments to the current Chromosome which is executing
   * @return the boolean return value of this node
   * @throws UnsupportedOperationException if the type of this node is not boolean
   *
   * @since 1.0
   */
  public boolean execute_boolean(Chromosome c, int n, Object[] args) {
    throw new UnsupportedOperationException(getName() +
      " cannot return boolean");
  }

  /**
   * Executes this node, returning nothing.
   *
   * @throws UnsupportedOperationException if the type of this node is not void
   *
   * @since 1.0
   */
  public void execute_void(Chromosome c, int n, Object[] args) {
    throw new UnsupportedOperationException(getName() +
      " cannot return void");
  }

  /**
   * Executes this node as an integer.
   *
   * @return the integer return value of this node
   * @throws UnsupportedOperationException if the type of this node is not integer
   *
   * @since 1.0
   */
  public int execute_int(Chromosome c, int n, Object[] args) {
    throw new UnsupportedOperationException(getName() +
      " cannot return int");
  }

  /**
   * Executes this node as a long.
   *
   * @return the long return value of this node
   * @throws UnsupportedOperationException if the type of this node is not long
   *
   * @since 1.0
   */
  public long execute_long(Chromosome c, int n, Object[] args) {
    throw new UnsupportedOperationException(getName() +
      " cannot return long");
  }

  /**
   * Executes this node as a float.
   *
   * @return the float return value of this node
   * @throws UnsupportedOperationException if the type of this node is not float
   *
   * @since 1.0
   */
  public float execute_float(Chromosome c, int n, Object[] args) {
    throw new UnsupportedOperationException(getName() +
      " cannot return float");
  }

  /**
   * Executes this node as a double.
   *
   * @return the double return value of this node
   * @throws UnsupportedOperationException if the type of this node is not double
   *
   * @since 1.0
   */
  public double execute_double(Chromosome c, int n, Object[] args) {
    throw new UnsupportedOperationException(getName() +
      " cannot return double");
  }

  /**
   * Executes this node as an object.
   *
   * @return the object return value of this node
   * @throws UnsupportedOperationException if the type of this node is not object
   *
   * @since 1.0
   */
  public Object execute_object(Chromosome c, int n, Object[] args) {
    throw new UnsupportedOperationException(getName() +
      " cannot return Object");
  }

  /**
   * Executes this node without knowing its return type.
   *
   * @return the Object which wraps the return value of this node, or null
   * if the return type is null or unknown.
   *
   * @since 1.0
   */
  public Object execute(Chromosome c, int n, Object[] args) {
    if (returnType==booleanClass)
      return new Boolean(execute_boolean(c, n, args));
    if (returnType==integerClass)
      return new Integer(execute_int(c, n, args));
    if (returnType==longClass)
      return new Long(execute_long(c, n, args));
    if (returnType==floatClass)
      return new Float(execute_float(c, n, args));
    if (returnType==doubleClass)
      return new Double(execute_double(c, n, args));
    if (returnType==voidClass)
      execute_void(c, n, args);
    else
      return execute_object(c, n, args);
    return null;
  }

  /**
   * Gets the number of nodes in the tree rooted at this node.
   *
   * @return the number of nodes
   *
   * @since 1.0
   */
  /*public int getSize() {
    int size = 1;

    for (int i=0; i<getArity(); i++)
      size += children[i].getSize();

    return size;
  }*/

  /**
   * Gets the number of nodes of the given type in the tree rooted at this node.
   *
   * @return the number of nodes
   *
   * @since 1.0
   */
  /*public int getSize(Type type) {
    int size = (type==returnType ? 1 : 0);

    for (int i=0; i<getArity(); i++)
      size += children[i].getSize(type);

    return size;
  }*/

  /**
   * Gets the depth of the tree rooted at this node.
   *
   * @return the depth of the tree
   *
   * @since 1.0
   */
  /*public int getDepth() {
    int depth = 1;

    for (int i=0; i<getArity(); i++)
      depth = Math.max(depth, 1+children[i].getDepth());

    return depth;
  }*/

  /**
   * Returns the string representing the tree rooted at this node.
   *
   * @return the string representing the tree rooted at this node
   *
   * @since 1.0
   */
  /*public String toString() {
    if (getArity()==0)
      return getName() + " ";

    String str = new String();
    str += getName() + " ( ";
    for (int i=0; i<getArity(); i++)
      str += children[i];
    str += ") ";
    return str;
  }*/

  /**
   * Gets the name of this node. Must be overridden in subclasses.
   *
   * @return the name of this node.
   *
   * @since 1.0
   */
  public abstract String getName();

  /**
   * Gets the type of node allowed form the given child number. Must be overridden
   * in subclasses.
   *
   * @param i the child number
   * @return the type of node allowed for that child
   *
   * @since 1.0
   */
  public abstract Class getChildType(int i);

  /**
   * Determine whether this node has constant value. A node has constant
   * value if it returns a constant value when its children return constant
   * values. By default, terminals are constants unless a terminal overrides
   * this method. Functions can also override this method if it doesn't
   * return a constant value when its children return constant values.
   */
  /*public boolean isConstant() {
    for (int i=0; i<getArity(); i++)
      if (!children[i].isConstant())
        return false;
    return true;
  }*/

}
