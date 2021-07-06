package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * Set function, to set element in a collection. This is part of the Collection and ContainedVariable framework.
 * @author Groff 2/2003
 */
public class Set extends Function implements Serializable {
	private static final long serialVersionUID = 5648056207979774530L;
	Class childArg2;
  /**
   * Creates a set node of the given type, always returning object
   * SET(collection object index)
   * @param childArg1 The type of first arg to function, because we're generic
   * @param childArg2 The type to add to collection
   */
  public Set(Class childArg1, Class childArg2) {
    super(3,childArg1);
    this.childArg2 = childArg2;
  }

  public String getName() {
    return "SET";
  }
  /**
  * @return The object formerly know as position
  */
  public Object execute_object(Chromosome c, int n, Object[] args) {
    Object targ = c.execute_object(n, 1, args);
    int pos = c.execute_int(n, 2, args);
    return ((Compatible)c.execute_object(n, 0, args)).execute_set(pos, targ);
  }
  /**
  * Type of set object must be same as return type
  */
  public Class getChildType(int i) {
        if( i ==0 )
                return returnType;
        else {
                if( i == 1)
                        return childArg2;
                else
                        return Function.integerClass;
        }
  }

  public static interface Compatible {
    public Object execute_set(int pos, Object targ);
  }

}
