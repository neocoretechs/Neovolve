package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * indexOf function
 * @author Groff 1/2003
 */
public class IndexOf extends Function implements Serializable {
   Class childArg1, childArg2;
  /**
   * Creates an indexOf node of the given type, always returning integer index
   * INDX(mainthing thinglookingfor)
   * @param childArg1 The type of first arg to function, because we're generic indexOf
   * @param childArg2        "    second   "
   */
  public IndexOf(Class childArg1, Class childArg2) {
    super(2,Function.integerClass);
    this.childArg1 = childArg1;
    this.childArg2 = childArg2;
  }

  public String getName() {
    return "INDX";
  }
  // incorrect
  //public Object execute_object(Chromosome c, int n, Object[] args) {
  //  Strings targ = (Strings)(c.execute_object(n, 1, args));
  //  return ((Compatible)c.execute_object(n, 0, args)).execute_indexOf(targ);
  //}

  public int execute_int(Chromosome c, int n, Object[] args) {
    Object targ = c.execute_object(n, 1, args);
    return ((Compatible)c.execute_object(n, 0, args)).execute_indexOf(targ);
  }

  public Class getChildType(int i) {
        if( i == 0 )
                return childArg1;
        else
                return childArg2;
  }

  public static interface Compatible {
    public int execute_indexOf(Object t);
  }

}
