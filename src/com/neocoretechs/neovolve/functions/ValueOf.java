package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import com.neocoretechs.neovolve.objects.Strings;
import java.io.*;

/**
 * valueOf function, as in String.valueOf(type)
 * @author Groff 1/2003
 */
public class ValueOf extends Function implements Serializable {
   Class childArg1;
  /**
   * Creates an valueOf node of the given type, always returning string rep
   * VALU(mainthing thinglookingfor)
   * @param childArg1 The type of first arg to function, because we're generic indexOf
   */
  public ValueOf(Class childArg1) {
    super(1,Strings.stringClass);
    this.childArg1 = childArg1;
  }

  public String getName() {
    return "VALU";
  }
  /**
  * Ever wondered about overloading in this system, it aint pretty
  */
  public Object execute_object(Chromosome c, int n, Object[] args) {
  
    if (childArg1==booleanClass)
        return new Strings(String.valueOf(c.execute_boolean(n, 0, args)));
    if (childArg1==integerClass)
        return new Strings(String.valueOf(c.execute_int(n, 0, args)));
    if (childArg1==longClass)
        return new Strings(String.valueOf(c.execute_long(n, 0, args)));
    if (childArg1==floatClass)
        return new Strings(String.valueOf(c.execute_float(n, 0, args)));
    if (childArg1==doubleClass)
        return new Strings(String.valueOf(c.execute_double(n, 0, args)));
    //if (childArg1==voidClass)
    //  execute_void(c, n, args);
    else
        //System.out.println("val="+String.valueOf(c.execute_object(n, 0, args)));
        return new Strings(String.valueOf(c.execute_object(n, 0, args)));
  }

  public Class getChildType(int i) {
    return childArg1;
  }

}
