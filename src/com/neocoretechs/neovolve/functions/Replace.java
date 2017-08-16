package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * Replace function
 * @author Groff 1/2003
 */
public class Replace extends Function implements Serializable {
   Class childType1, childType2;
  /**
   * replaces this with that in something of the given type
   *
   * @param type the type of the node
   *
   * @since 1.0
   */
  public Replace(Class mainType, Class childType1, Class childType2) {
    super(3,mainType);
    this.childType1 = childType1;
    this.childType2 = childType2;
  }

  public String getName() {
    return "REPL";
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    Object replThis = c.execute_object(n, 1, args);
    Object withThat = c.execute_object(n, 2, args);
    return ((Compatible)c.execute_object(n, 0, args)).execute_replace( replThis, withThat);
  }

  public Class getChildType(int i) {
        if( i == 0 )
                return returnType; // super ctor sets arg2 to return type
        else {
                if( i == 1 )
                        return childType1;
                else
                        return childType2;
        }                
  }     

  public static interface Compatible {
    public Object execute_replace( Object replThis, Object withThat);
  }

}
