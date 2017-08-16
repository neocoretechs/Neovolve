package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import com.neocoretechs.neovolve.objects.Strings;
import java.io.*;

/**
 * length function
 * @author Groff 1/2003
 */
public class Length extends Function implements Serializable {
   Class childArg1;
  /**
   * Creates a length node of the given type
   *
   * @param childArg1 the type of the argument to the node
   *
   * @since 1.0
   */
  public Length(Class childArg1) {
    super(1,Function.integerClass);
    this.childArg1 = childArg1;
  }

  public String getName() {
    return "LEN";
  }

  public int execute_int(Chromosome c, int n, Object[] args) {
    return ((Compatible)c.execute_object(n, 0, args)).execute_length();
  }

  public Class getChildType(int i) {
        return childArg1;
  }

  public static interface Compatible {
    public int execute_length();
  }

}
