package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * isEmpty function for collections
 * @author Groff 2/2003
 */
public class IsEmpty extends Function implements Serializable {
   Class childArg1;
  /**
   * Creates an isEmpty node of the given type, always returning boolean
   * EMPTY(mainthing thinglookingfor)
   * @param childArg1 The type of first arg to function, because we're generic indexOf
   */
  public IsEmpty(Class childArg1) {
    super(1,Function.booleanClass);
    this.childArg1 = childArg1;
  }

  public String getName() {
    return "EMPTY";
  }

  public boolean execute_boolean(Chromosome c, int n, Object[] args) {
    return ((Compatible)c.execute_object(n, 0, args)).execute_isEmpty();
  }

  public Class getChildType(int i) {
                return childArg1;
  }

  public static interface Compatible {
    public boolean execute_isEmpty();
  }

}
