package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;

import java.io.*;

/**
 * A node representing a named variable that is a container element.  The
 * node is constructed with the type thats in the container, then set with
 * the actual container; an object implementing ContainerAccess.  From then on
 * a "Get" on container sets the index of the container object this variable returns<p>
 * Since objects are immutable in this system, modifications in functions returning deep 
 * copies, somewhat consistent results will come from this variable.  It appears objects
 * have to be immutable for the optimization effect to function properly
 * <p>
 * A named variable is created by constructing an instance of the Variable, giving
 * it a name and type. From that point on, all references to that particular variable
 * should be through either the instance initially created, or looked up via the
 * {@link #getVariable getVariable} method.
 * <p>
 * @author J. Groff
 */
public class ContainedVariable extends Variable implements Serializable {
	private static final long serialVersionUID = -7819097507621469084L;

	private ContainedVariable(String name, Class type) {
		super(name, type);
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
		return new ContainedVariable(name, type);
	}

  /**
   * Sets a named variable without having to obtain a reference to the named variable.
   *
   * @param name the name of the variable to set
   * @param value the value to set the variable with, container
   *
   * @since 1.0
   */
	public static void set(String name, ContainerAccess value) {
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
		return ((ContainerAccess)((Variable)vars.get(name)).value).getCurrent();
	}


	public Object execute_object(Chromosome c, int n, Object[] args) {
		return ((ContainerAccess)value).getCurrent();
	}

	public static interface ContainerAccess {
        public Object getCurrent();
	}
}
