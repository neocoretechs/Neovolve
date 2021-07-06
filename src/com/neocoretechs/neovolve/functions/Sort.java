package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * Sort function, to sort elements in a collection
 * @author Groff 2/2003
 */
public class Sort extends Function implements Serializable {
  /**
   * Creates a sort node of the given type, always returning the type
   * SORT(collection)
   * @param childArg1 The type of first arg to function, because we're generic
   */
  public Sort(Class childArg1) {
    super(1,childArg1);
  }

  public String getName() {
    return "SORT";
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    return ((Compatible)c.execute_object(n, 0, args)).execute_sort();
  }

  public Class getChildType(int i) {
        return returnType;
  }

  public static interface Compatible {
    public Object execute_sort();
  }

}
