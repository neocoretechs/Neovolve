package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import com.neocoretechs.neovolve.objects.Strings;
import java.io.*;

/**
 * substring function
 * @author Groff 1/2003
 */
public class Substring extends Function implements Serializable {

  /**
   * Creates a substring node of the given type
   *
   * @param type the type of the node
   *
   * @since 1.0
   */
  public Substring() {
    super(3,Strings.stringClass);
  }

  public String getName() {
    return "SUBS";
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    int start = c.execute_int(n, 1, args);
    int end = c.execute_int(n, 2, args);
    return ((Compatible)c.execute_object(n, 0, args)).execute_substring( start, end);
  }

  public Class getChildType(int i) {
        if( i == 0 )
                return returnType;
        return Function.integerClass;
  }

  public static interface Compatible {
    public Object execute_substring( int start, int end);
  }

}
