package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * Get function, to get elements from a colleciton
 * @author Groff 2/2003
 */
public class Get extends Function implements Serializable {
   Class childArg1;
  /**
   * Creates an Get node of the given type, always returning object
   * GET(collection index)
   * @param childArg1 The type of first arg to function, because we're generic
   * @param returnArg The type returned from collection
   */
  public Get(Class childArg1, Class returnArg) {
    super(2,returnArg);
    this.childArg1 = childArg1;
  }

  public String getName() {
    return "GET";
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    int pos = c.execute_int(n, 1, args);
    return ((Compatible)c.execute_object(n, 0, args)).execute_get(pos);
  }

  public Class getChildType(int i) {
        if( i ==0 )
                return childArg1;
        else
                return Function.integerClass;
  }

  public static interface Compatible {
    public Object execute_get(int pos);
  }

}
