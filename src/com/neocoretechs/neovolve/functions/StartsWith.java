package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * startsWith function
 * @author Groff 1/2003
 */
public class StartsWith extends Function implements Serializable {
   Class childArg1, childArg2;
  /**
   * Creates a startswith node of the given type, always returning boolean
   * STRTS(mainthing thinglookingfor)
   * @param childArg1 The type of first arg to function, because we're generic indexOf
   * @param childArg2        "    second   "
   */
  public StartsWith(Class childArg1, Class childArg2) {
    super(2,Function.booleanClass);
    this.childArg1 = childArg1;
    this.childArg2 = childArg2;
  }

  public String getName() {
    return "STRTS";
  }

  public boolean execute_boolean(Chromosome c, int n, Object[] args) {
    Object targ = c.execute_object(n, 1, args);
    return ((Compatible)c.execute_object(n, 0, args)).execute_startsWith(targ);
  }

  public Class getChildType(int i) {
        if( i == 0 )
                return childArg1;
        else
                return childArg2;
  }

  public static interface Compatible {
    public boolean execute_startsWith(Object t);
  }

}
