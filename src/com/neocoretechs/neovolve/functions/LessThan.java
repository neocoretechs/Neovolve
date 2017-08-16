package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

public class LessThan extends Function implements Serializable {

  Class childType;

  public LessThan(Class type) {
    super(2, booleanClass);
    childType = type;
  }

  public String getName() {
    return "<";
  }

  public boolean execute_boolean(Chromosome c, int n, Object[] args) {
    if (childType.equals(integerClass))
      return c.execute_int(n, 0, args) < c.execute_int(n, 1, args);
    else if (childType.equals(longClass))
      return c.execute_long(n, 0, args) < c.execute_long(n, 1, args);
    else if (childType.equals(floatClass))
      return c.execute_float(n, 0, args) < c.execute_float(n, 1, args);
    else if (childType.equals(doubleClass))
      return c.execute_double(n, 0, args) < c.execute_double(n, 1, args);
    else if (!childType.equals(voidClass))
      return ((Compatible)c.execute_object(n, 0, args)).less_than(c.execute_object(n, 1, args));
    throw new UnsupportedOperationException(
    	"LessThan may not take a void argument");
  }

  public Class getChildType(int parm1) {
    return childType;
  }

  public static interface Compatible {
    public boolean less_than(Object o);
  }
}