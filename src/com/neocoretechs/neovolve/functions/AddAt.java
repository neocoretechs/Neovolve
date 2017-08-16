package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * AddAt function, to add elements to a colleciton at an index
 * @author Groff 2/2003
 */
public class AddAt extends Function implements Serializable {
   Class childArg2;
  /**
   * Creates an AddAt node of the given type, always returning void
   * ADDAT(collection thing index)
   * @param childArg1 The type of first arg to function, because we're generic
   * @param childArg2        "    second   "
   */
  public AddAt(Class childArg1, Class childArg2) {
    super(3,childArg1);
    this.childArg2 = childArg2;
  }

  public String getName() {
    return "ADDAT";
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    Object targ = c.execute_object(n, 1, args);
    int pos = c.execute_int(n, 2, args);
    return ((Compatible)c.execute_object(n, 0, args)).execute_addAt(targ, pos);
  }

  public Class getChildType(int i) {
        if( i == 0 )
                return returnType;
        else {
                if( i == 1)
                        return childArg2;
                else
                        return Function.integerClass;
        }
  }

  public static interface Compatible {
    public Object execute_addAt(Object t, int pos);
  }

}
