package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

public class Equals extends Function implements Serializable {

  Class childType;

  public Equals(Class type) {
    super(2, booleanClass);
    childType = type;
  }

  public String getName() {
    return "=";
  }

  public boolean execute_boolean(Chromosome c, int n, Object[] args) {
    if (childType.equals(integerClass))
      return c.execute_int(n, 0, args) == c.execute_int(n, 1, args);
    else if (childType.equals(longClass))
      return c.execute_long(n, 0, args) == c.execute_long(n, 1, args);
    else if (childType.equals(floatClass))
      return c.execute_float(n, 0, args) == c.execute_float(n, 1, args);
    else if (childType.equals(doubleClass))
      return c.execute_double(n, 0, args) == c.execute_double(n, 1, args);
    else if (childType.equals(booleanClass))
      return c.execute_boolean(n, 0, args) == c.execute_boolean(n, 1, args);
    else if (!childType.equals(voidClass))
      return c.execute_object(n, 0, args).equals(c.execute_object(n, 1, args));
    throw new UnsupportedOperationException(
      "Equals may only take boolean, int, long, float, double, or object");
  }

  public Class getChildType(int parm1) {
    return childType;
  }

}
