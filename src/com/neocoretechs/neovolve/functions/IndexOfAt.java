package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * indexOf two arg function
 * @author Groff 1/2003
 */
public class IndexOfAt extends Function implements Serializable {
   Class childArg1, childArg2;
  /**
   * Creates an indexOf node of the given type, always returning integer index
   * INDXA(mainthing thinglookingfor start)
   * @param childArg1 The type of first arg to function, because we're generic indexOf
   * @param childArg2        "    second   "
   * Third is always int
   */
  public IndexOfAt(Class childArg1, Class childArg2) {
    super(3,Function.integerClass);
    this.childArg1 = childArg1;
    this.childArg2 = childArg2;
  }

  public String getName() {
    return "INDXA";
  }

  public int execute_int(Chromosome c, int n, Object[] args) {
    Object targ = c.execute_object(n, 1, args);
    int strt = c.execute_int(n, 2, args);
    return ((Compatible)c.execute_object(n, 0, args)).execute_indexOfAt(targ, strt);
  }

  public Class getChildType(int i) {
        if( i == 0 )
                return childArg1;
        else {
                if( i == 1 )
                        return childArg2;
                else
                        return integerClass;
        }
  }

  public static interface Compatible {
    public int execute_indexOfAt(Object t, int strt);
  }

}
