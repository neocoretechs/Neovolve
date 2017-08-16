package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * Clear function, to clear elements from a collection
 * @author Groff 2/2003
 */
public class Clear extends Function implements Serializable {
  /**
   * Creates a clear node of the given type, always returning void
   * CLEAR(collection)
   * @param childArg1 The type of first arg to function, because we're generic
   */
  public Clear(Class childArg1) {
    super(1,childArg1);
  }

  public String getName() {
    return "CLEAR";
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    return ((Compatible)c.execute_object(n, 0, args)).execute_clear();
  }

  public Class getChildType(int i) {
        return returnType;
  }

  public static interface Compatible {
    public Object execute_clear();
  }

}
