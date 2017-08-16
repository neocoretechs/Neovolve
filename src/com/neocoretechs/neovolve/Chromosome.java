package com.neocoretechs.neovolve;

import java.util.*;
import java.io.*;
import com.neocoretechs.neovolve.functions.*;

/** Represents a single chromosome.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Chromosome.java,v 1.4 2000/10/12 15:22:55 groovyjava Exp $
 */
public class Chromosome implements Serializable, Cloneable {

	/**
	 * Array to hold the nodes in this Chromosome.
	 */
  Function[] functions = null;

  /**
   * The allowable function/terminal list.
   */

  Function[] functionSet = null;

	/**
	 * Array to hold the depths of each node.
	 */
  int[] depth = null;

	/**
	 * Array to hold the indices of the children of each node. We fix the
	 * maximum number of children that a node can have to save on having
	 * differently-sized arrays for each node.
	 */
  // int[][] children = null;

	/**
	 * Array to hold the types of the arguments to this Chromosome.
	 */
  Class[] argTypes = null;

  transient int index;
  transient int maxDepth;

  /**
   * Perform a somewhat deep copy of a chromosome. All of the arrays are completely
	 * separate entities, but their contents are equal.
   *
   * @since 1.2.0beta2
   */
  public Object clone() throws CloneNotSupportedException {

		Chromosome c = new Chromosome(true);

    c.argTypes = (Class[])argTypes.clone();
    c.functions = (Function[])functions.clone();
    c.functionSet = (Function[])functionSet.clone();
    c.depth = (int[])depth.clone();
    /*c.children = new int[4][functions.length];
    c.children[0] = (int[])children[0].clone();
    c.children[1] = (int[])children[1].clone();
    c.children[2] = (int[])children[2].clone();
    c.children[3] = (int[])children[3].clone(); */

		return c;
  }

  /**
   * Construct an empty chromosome
   *
   * @since 1.0
   */
  public Chromosome() {
    functions = new Function[World.maxSize];
    depth = new int[World.maxSize];
    /*children = new int[4][World.maxSize];
    children[0] = new int[World.maxSize];
    children[1] = new int[World.maxSize];
    children[2] = new int[World.maxSize];
    children[3] = new int[World.maxSize]; */
  }

  /**
   * Construct an empty chromosome
   *
   * @since 1.0
   */
  public Chromosome(int size, Function[] functionSet, Class[] argTypes) {
		this.functionSet = functionSet;
		this.argTypes = argTypes;
    functions = new Function[size];
    depth = new int[size];
    /* children = new int[4][size];
    children[0] = new int[size];
    children[1] = new int[size];
    children[2] = new int[size];
    children[3] = new int[size]; */
  }

  /**
   * Construct a VERY empty chromosome
   *
   * @since 1.2.0beta2
   */
  public Chromosome(boolean empty) {
  }

  /**
   * Determines whether there exists a function or terminal in the given node set with the
   * given type.
   *
   * @param type the type to look for
   * @param nodeSet the array of nodes to look through
   * @param function true to look for a function, false to look for a terminal
   *
   * @return true if such a node exists, false otherwise
   *
   * @since 1.0
   */
  public static boolean isPossible(Class type, Function[] nodeSet, boolean function) {

    for (int i=0; i<nodeSet.length; i++)
      if (nodeSet[i].getReturnType()==type && (nodeSet[i].getArity()!=0)==function)
        return true;
    return false;
  }

  public boolean isPossible(Function f) {
    for (int i=0; i<functionSet.length; i++)
        if (functionSet[i] == f)
            return true;
    return false;
  }

  /**
   * Randomly chooses a node from the node set.
   *
   * @param type the type of node to choose
   * @param nodeSet the array of nodes to choose from
   * @param function true to choose a function, false to choose a terminal
   * @param growing true to ignore the function parameter, false otherwise
   * @return the node chosen
   *
   * @since 1.0
   */
  public static Function selectNode(Class type, Function[] functionSet,
    boolean function, boolean growing) {

    if (!isPossible(type, functionSet, function))
      throw new IllegalArgumentException("Chromosome requires a " +
        (function ? ("function"+(growing ? " or terminal" : "")) : "terminal") + " of type " +
        type + " but there is no such node available");

    Function n = null;
    int index;

    while (n==null) {
      index = World.random.nextInt(functionSet.length);
      if (functionSet[index].getReturnType()==type) {
        if ( functionSet[index].getArity()==0 && (!function || growing) )
          n = functionSet[index];
        if ( functionSet[index].getArity()!=0 && function )
          n = functionSet[index];
      }
    }

    return n;
  }

  /**
   * Create a tree of nodes using the full method.
   *
   * @param depth the depth of the tree to create
   * @param type the type of node to start with
   * @param nodeSet the set of nodes valid to pick from
   * @return a node which is the root of the generated tree
   *
   * @since 1.0
   */
  void fullNode(int depth, Class type, Function[] functionSet) {

    // Generate the node.

    Function n = selectNode(type, functionSet, depth>1, false);
    this.depth[index] = maxDepth - depth;
    functions[index++] = n;

    if (depth>1)
      for (int i=0; i<n.getArity(); i++)
        fullNode(depth-1, n.getChildType(i), functionSet);
  }

  /**
   * Create a tree of nodes using the grow method.
   *
   * @param depth the maximum depth of the tree to create
   * @param type the type of node to start with
   * @param nodeSet the set of nodes valid to pick from
   * @return a node which is the root of the generated tree
   *
   * @since 1.0
   */
  void growNode(int depth, Class type, Function[] functionSet) {

    // Generate the node.
    // Groff: Attempt to remove non-viable functions, this does not work
    // due to 0-elem tree getting nullpointer in redepth
    Function n = null;
    //try {
        n = selectNode(type, functionSet, depth>1, true);
    //} catch(Exception e) {
    //    System.out.println("Bad node "+type+" @ depth "+depth);
    //    return;
    //}
    this.depth[index] = maxDepth - depth;
    functions[index++] = n;

    if (depth>1)
      for (int i=0; i<n.getArity(); i++)
        growNode(depth-1, n.getChildType(i), functionSet);
  }

  // Initializing chromosomes involves adding in Arguments of the appropriate
  // type. Exercise for the student: Make this more efficient by having Argument
	// have a cache of Arguments.

  /**
   * Initialize this chromosome using the full method.
   *
   * @param num the number of this chromosome
   * @param depth the depth of the chromosome to create
   * @param type the type of the chromosome to create
   * @param argTypes the array of types of arguments for this chromosome
   * @param functionSetSet the set of nodes valid to pick from
   *
   * @since 1.0
   */
  public void full(int num, int depth, Class type, Class[] argTypes, Function[] functionSet) {

    this.argTypes = argTypes;

    this.functionSet = new Function[functionSet.length + argTypes.length];

    System.arraycopy(functionSet, 0, this.functionSet, 0, functionSet.length);
    for (int i=0; i<argTypes.length; i++)
      this.functionSet[functionSet.length + i] = new Argument(i, argTypes[i]);

    index = 0;
    maxDepth = depth;
    fullNode(depth, type, this.functionSet);
    redepth();
  }

  /**
   * Initialize this chromosome using the grow method.
   *
   * @param num the chromosome number of this chromosome
   * @param depth the maximum depth of the chromosome to create
   * @param type the type of the chromosome to create
   * @param argTypes the array of types of arguments for this chromosome
   * @param functionSet the set of nodes valid to pick from
   *
   * @since 1.0
   */
  public void grow(int num, int depth, Class type, Class[] argTypes, Function[] functionSet) {

    this.argTypes = argTypes;

    this.functionSet = new Function[functionSet.length + argTypes.length];

    System.arraycopy(functionSet, 0, this.functionSet, 0, functionSet.length);
    for (int i=0; i<argTypes.length; i++)
      this.functionSet[functionSet.length + i] = new Argument(i, argTypes[i]);

    index = 0;
    maxDepth = depth;
    growNode(depth, type, this.functionSet);
    redepth();
  }

  protected String toString(int n) {
    if (functions[n].getArity()==0)
      return functions[n].getName() + " ";

    String str = new String();
    str += functions[n].getName() + " ( ";
    for (int i=0; i<functions[n].getArity(); i++)
      str += toString(getChild(n, i));
    str += ") ";
    return str;
  }

  /**
   * Returns the string representing this chromosome.
   *
   * @return the string representing this chromosome.
   *
   * @since 1.0
   */
  public String toString() {
    /* String str = toString(0) + "\n";
    for (int i=0; functions[i]!=null; i++)
        str += functions[i].getName() + " ";
    str += "\n";
    for (int i=0; functions[i]!=null; i++)
        str += depth[i] + " ";
    str += "\n";
    for (int i=0; functions[i]!=null; i++)
        str += "( " + children[0][i] + " " +
            children[1][i] + " " +
            children[2][i] + " " +
            children[3][i] + ") ";
    str += "\n";
    return str;
    */
    return toString(0);
  }

	/**
	 * Recalculate the depths of each node.
	 */

  public void redepth() {
    depth[0] = 0;
    redepth(0);
  }

	/**
	 * Calculate the depth of the next node and the indices of the children
	 * of the current node.
	 *
	 * The depth of the next node is
	 * just one plus the depth of the current node. The index of the first
	 * child is always the next node. The index of the second child is found
	 * by recursively calling this method on the tree starting with the first
	 * child.
	 *
	 * @returns the index of the next node of the same depth as the
	 * current node (i.e. the next sibling node)
	 */
  protected int redepth(int n) {

    int num = n+1;
    int arity = getNode(n).getArity();

    for (int i=0; i<arity; i++) {
        depth[num] = depth[n]+1;
        // children[i][n] = num;
        num = redepth(num);
    }
		/*
    for (int i=arity; i<4; i++)
        children[i][n] = -1; */

    return num;
  }

  /**
   * Counts the number of terminals in this chromosome.
   *
   * @return the number of terminals in this chromosome.
   *
   * @since 1.0
   */
  public int numTerminals() {
    int count = 0;

    for (int i=0; i<functions.length && functions[i]!=null; i++)
      if (functions[i].getArity()==0)
        count++;
    return count;
  }

  /**
   * Counts the number of functions in this chromosome.
   *
   * @return the number of functions in this chromosome.
   *
   * @since 1.0
   */
  public int numFunctions() {
    int count = 0;

    for (int i=0; i<functions.length && functions[i]!=null; i++)
      if (functions[i].getArity()!=0)
        count++;
    return count;
  }

  /**
   * Counts the number of terminals of the given type in this chromosome.
   *
   * @param type the type of terminal to count
   * @return the number of terminals in this chromosome.
   *
   * @since 1.0
   */
  public int numTerminals(Class type) {
    int count = 0;

    for (int i=0; i<functions.length && functions[i]!=null; i++)
      if (functions[i].getArity()==0 && functions[i].getReturnType()==type)
        count++;
    return count;
  }

  /**
   * Counts the number of functions of the given type in this chromosome.
   *
   * @param type the type of function to count
   * @return the number of functions in this chromosome.
   *
   * @since 1.0
   */
  public int numFunctions(Class type) {
    int count = 0;

    for (int i=0; i<functions.length && functions[i]!=null; i++)
      if (functions[i].getArity()!=0 && functions[i].getReturnType()==type)
        count++;
    return count;
  }

  /**
   * Gets the i'th node in this chromosome. The nodes are counted in a depth-first
   * manner, with node 0 being the root of this chromosome.
   *
   * @param i the node number to get
   * @return the node
   *
   * @since 1.0
   */
  public Function getNode(int i) {
    if (i>=functions.length || functions[i]==null)
        return null;
    return functions[i];
  }

  /**
   * Gets the child'th child of the n'th node in this chromosome. This is the same
   * as the child'th node whose depth is one more than the depth of the n'th node.
   *
   * @param n the node number of the parent
   * @param child the child number (starting from 0) of the parent
   * @returns the node number of the child, or -1 if not found
   *
   * @since 1.2.0
   */

  public int getChild(int n, int child) {

    for (int i=n+1; i<functions.length; i++) {
        if (depth[i] <= depth[n])
            return -1;
        if (depth[i] == depth[n]+1)
            if (--child < 0)
                return i;
    }
    return -1;

    // return children[child][n];
  }

  /**
   * Gets the i'th node of the given type in this chromosome. The nodes are counted in a depth-first
   * manner, with node 0 being the first node of the given type in this chromosome.
   *
   * @param i the node number to get
   * @param type the type of node to get
   * @return the node
   *
   * @since 1.0
   */
  public int getNode(int i, Class type) {
    for (int j=0; j<functions.length && functions[j]!=null; j++)
      if (functions[j].getReturnType()==type)
        if (--i < 0)
            return j;
    return -1;
  }

  /**
   * Gets the i'th terminal in this chromosome. The nodes are counted in a depth-first
   * manner, with node 0 being the first terminal in this chromosome.
   *
   * @param i the terminal number to get
   * @return the terminal
   *
   * @since 1.0
   */
  public int getTerminal(int i) {
    for (int j=0; j<functions.length && functions[j]!=null; j++)
      if (functions[j].getArity()==0)
        if (--i < 0)
            return j;
    return -1;
  }

  /**
   * Gets the i'th function in this chromosome. The nodes are counted in a depth-first
   * manner, with node 0 being the first function in this chromosome.
   *
   * @param i the function number to get
   * @return the function
   *
   * @since 1.0
   */
  public int getFunction(int i) {
    for (int j=0; j<functions.length && functions[j]!=null; j++)
      if (functions[j].getArity()!=0)
        if (--i < 0)
            return j;
    return -1;
  }

  /**
   * Gets the i'th terminal of the given type in this chromosome. The nodes are counted in a depth-first
   * manner, with node 0 being the first terminal of the given type in this chromosome.
   *
   * @param i the terminal number to get
   * @param type the type of terminal to get
   * @return the terminal
   *
   * @since 1.0
   */
  public int getTerminal(int i, Class type) {
    for (int j=0; j<functions.length && functions[j]!=null; j++)
      if (functions[j].getReturnType()==type && functions[j].getArity()==0)
        if (--i < 0)
            return j;
    return -1;
  }

  /**
   * Gets the i'th function of the given type in this chromosome. The nodes are counted in a depth-first
   * manner, with node 0 being the first function of the given type in this chromosome.
   *
   * @param i the function number to get
   * @param type the type of function to get
   * @return the function
   *
   * @since 1.0
   */
  public int getFunction(int i, Class type) {
    for (int j=0; j<functions.length && functions[j]!=null; j++)
      if (functions[j].getReturnType()==type && functions[j].getArity()!=0)
        if (--i < 0)
            return j;
    return -1;
  }

	/**
	 * Gets the number of nodes in the branch starting at the n'th node.
	 *
	 * @param n the index of the node at which to start counting.
	 * @returns the number of nodes in the branch starting at the n'th node.
	 */
  public int getSize(int n) {
    int i;

    // Get the node at which the depth is <= depth[n].

    for (i=n+1; i<functions.length && functions[i]!=null; i++)
        if (depth[i] <= depth[n])
            break;
    return i-n;
  }

	/**
	 * Gets the depth of the branch starting at the n'th node.
	 *
	 * @param n the index of the node at which to check the depth.
	 * @returns the depth of the branch starting at the n'th node.
	 */
  public int getDepth(int n) {
    int i, maxdepth = depth[n];

    for (i=n+1; i<functions.length && functions[i]!=null; i++) {
        if (depth[i] <= depth[n])
            break;
        if (depth[i] > maxdepth)
            maxdepth = depth[i];
    }
    return maxdepth - depth[n];
  }

  /**
   * Gets the node which is the parent of the given node in this chromosome. If the child is at
   * depth d then the parent is the first function at depth d-1 when iterating backwards through
   * the function list starting from the child.
   *
   * @param child the child node
   * @return the parent node, or null if the child is the root node
   *
   * @since 1.0
   */
  public int getParentNode(int child) {
    if (child >= functions.length || functions[child]==null)
        return -1;
    for (int i=child-1; i>=0; i--)
        if (depth[i] == depth[child]-1)
            return i;
    return -1;
  }


  /**
   * Executes this node as a boolean.
   *
   * @return the boolean return value of this node
   * @throws UnsupportedOperationException if the type of this node is not boolean
   *
   * @since 1.0
   */
  public boolean execute_boolean(Object[] args) {
    return functions[0].execute_boolean(this, 0, args);
  }

  /**
   * Executes this node as a boolean.
   *
   * @param n the index of the parent node
   * @param child the child number of the node to execute
   * @return the boolean return value of this node
   * @throws UnsupportedOperationException if the type of this node is not boolean
   */
  public boolean execute_boolean(int n, int child, Object[] args) {
    if (child == 0)
        return functions[n+1].execute_boolean(this, n+1, args);
    int other = getChild(n, child);
    return functions[other].execute_boolean(this, other, args);
  }

  /**
   * Executes this node, returning nothing.
   *
   * @throws UnsupportedOperationException if the type of this node is not void
   *
   * @since 1.0
   */
  public void execute_void(Object[] args) {
    functions[0].execute_void(this, 0, args);
  }

  public void execute_void(int n, int child, Object[] args) {
    if (child == 0)
        functions[n+1].execute_void(this, n+1, args);
    int other = getChild(n, child);
    functions[other].execute_void(this, other, args);
  }

  /**
   * Executes this node as an integer.
   *
   * @return the integer return value of this node
   * @throws UnsupportedOperationException if the type of this node is not integer
   *
   * @since 1.0
   */
  public int execute_int(Object[] args) {
    return functions[0].execute_int(this, 0, args);
  }

  public int execute_int(int n, int child, Object[] args) {
    if (child == 0)
        return functions[n+1].execute_int(this, n+1, args);
    int other = getChild(n, child);
    return functions[other].execute_int(this, other, args);
  }

  /**
   * Executes this node as a long.
   *
   * @return the long return value of this node
   * @throws UnsupportedOperationException if the type of this node is not long
   *
   * @since 1.0
   */
  public long execute_long(Object[] args) {
    return functions[0].execute_long(this, 0, args);
  }

  public long execute_long(int n, int child, Object[] args) {
    if (child == 0)
        return functions[n+1].execute_long(this, n+1, args);
    int other = getChild(n, child);
    return functions[other].execute_long(this, other, args);
  }

  /**
   * Executes this node as a float.
   *
   * @return the float return value of this node
   * @throws UnsupportedOperationException if the type of this node is not float
   *
   * @since 1.0
   */
  public float execute_float(Object[] args) {
    return functions[0].execute_float(this, 0, args);
  }

  public float execute_float(int n, int child, Object[] args) {
    if (child == 0)
        return functions[n+1].execute_float(this, n+1, args);
    int other = getChild(n, child);
    return functions[other].execute_float(this, other, args);
  }

  /**
   * Executes this node as a double.
   *
   * @return the double return value of this node
   * @throws UnsupportedOperationException if the type of this node is not double
   *
   * @since 1.0
   */
  public double execute_double(Object[] args) {
    return functions[0].execute_double(this, 0, args);
  }

  public double execute_double(int n, int child, Object[] args) {
    if (child == 0)
        return functions[n+1].execute_double(this, n+1, args);
    int other = getChild(n, child);
    return functions[other].execute_double(this, other, args);
  }

  /**
   * Executes this node as an object.
   *
   * @return the object return value of this node
   * @throws UnsupportedOperationException if the type of this node is not object
   *
   * @since 1.0
   */
  public Object execute_object(Object[] args) {
    return functions[0].execute_object(this, 0, args);
  }

  public Object execute_object(int n, int child, Object[] args) {
    if (child == 0)
        return functions[n+1].execute_object(this, n+1, args);
    int other = getChild(n, child);
    return functions[other].execute_object(this, other, args);
  }

  /**
   * Executes this node without knowing its return type.
   *
   * @return the Object which wraps the return value of this node, or null
   * if the return type is null or unknown.
   *
   * @since 1.0
   */
  public Object execute(Object[] args) {
		return functions[0].execute_object(this, 0, args);
  }

  public Object execute(int n, int child, Object[] args) {
		return execute_object(n, child, args);
  }

	/**
         * Get the argument type this Chromosome accepts.
	 *
         * @returns an argument type.
	 */

	public Class getArgType(int i) {
		return argTypes[i];
	}

	/**
	 * Get the number of arguments this Chromosome accepts.
	 *
	 * @returns the number of arguments.
	 */
	public int getArity() {
		return argTypes.length;
	}

	/**
	 * A quick test which creates two Chromosomes and does a
	 * branch-typing cross on them.
	 */
  public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Usage: Chromosome <full|grow> <full|grow>");
			System.out.println("  full uses the 'full' method to create a Chromosome");
			System.out.println("  grow uses the 'grow' method to create a Chromosome");
			System.exit(0);
		}

    Class[] argTypes = { };
    Function[] functionSet = {
        new Add(Function.integerClass),
        new Multiply(Function.integerClass),
        new One(Function.integerClass)
    };

    Chromosome[] c = new Chromosome[4];

    if (args[0].equals("full")) {
        c[0] = new Chromosome();
        c[0].full(0, 4, Function.integerClass, argTypes, functionSet);
        System.out.println(c[0]);
    } else if (args[0].equals("grow")) {
        c[0] = new Chromosome();
        c[0].grow(0, 4, Function.integerClass, argTypes, functionSet);
        System.out.println(c[0]);
    }

    System.out.print("c[0]: ");
    for (int i=0; c[0].functions[i]!=null; i++)
        System.out.print(c[0].functions[i].getName() + " ");
    System.out.println();

    System.out.print("c[0]: ");
    for (int i=0; c[0].functions[i]!=null; i++)
        System.out.print(c[0].depth[i] + " ");
    System.out.println();

    if (args[0].equals("full")) {
        c[1] = new Chromosome();
        c[1].full(0, 4, Function.integerClass, argTypes, functionSet);
        System.out.println(c[1]);
    } else if (args[0].equals("grow")) {
        c[1] = new Chromosome();
        c[1].grow(0, 4, Function.integerClass, argTypes, functionSet);
        System.out.println(c[1]);
    }

    System.out.print("c[1]: ");
    for (int i=0; c[1].functions[i]!=null; i++)
        System.out.print(c[1].functions[i].getName() + " ");
    System.out.println();

    System.out.print("c[1]: ");
    for (int i=0; c[1].functions[i]!=null; i++)
        System.out.print(c[1].depth[i] + " ");
    System.out.println();

    Chromosome[] n = BranchTypingCross.doCross(c[0], c[1]);

		System.out.println("After crossing:");

    System.out.println(n[0]);
    System.out.println(n[1]);
  }
}
