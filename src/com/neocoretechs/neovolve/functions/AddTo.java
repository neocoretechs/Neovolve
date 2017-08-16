package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * AddTo function, to add elements to a colleciton
 * @author Groff 2/2003
 */
public class AddTo extends Function implements Serializable {
   Class childArg2;
  /**
   * Creates an AddTo node of the given type, always returning void
   * ADDTO(collection thing)
   * @param childArg1 The type of first arg to function, because we're generic
   * @param childArg2        "    second   "
   */
  public AddTo(Class childArg1, Class childArg2) {
    super(2,childArg1);
    this.childArg2 = childArg2;
  }

  public String getName() {
    return "ADDTO";
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    Object targ = c.execute_object(n, 1, args);
    return ((Compatible)c.execute_object(n, 0, args)).execute_addTo(targ);
  }

  public Class getChildType(int i) {
        if( i == 0 )
                return returnType;
        else
                return childArg2;
  }

  public static interface Compatible {
    public Object execute_addTo(Object t);
  }

}
