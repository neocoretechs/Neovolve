package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * Remove function, to remove an element from a colleciton
 * @author Groff 2/2003
 */
public class Remove extends Function implements Serializable {
  /**
   * Creates an remove node of the given type, always returning object
   * REMO(collection index)
   * @param childArg1 The type of first arg to function, because we're generic
   */
  public Remove(Class childArg1) {
    super(2,childArg1); // return type same as collection, new collection with elem removed
  }

  public String getName() {
    return "REMO";
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    int pos = c.execute_int(n, 1, args);
    return ((Compatible)c.execute_object(n, 0, args)).execute_remove(pos);
  }

  public Class getChildType(int i) {
        if( i ==0 )
                return returnType;
        else
                return Function.integerClass;
  }

  public static interface Compatible {
    public Object execute_remove(int pos);
  }

}
