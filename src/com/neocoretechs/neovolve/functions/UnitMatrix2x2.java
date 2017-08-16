package com.neocoretechs.neovolve.functions;

import com.neocoretechs.neovolve.*;
import com.neocoretechs.neovolve.*;
import com.neocoretechs.neovolve.objects.Matrix2x2;

import java.io.*;

public class UnitMatrix2x2 extends Function implements Serializable {

	private final static int[][] data = {{1,1},{1,1}};
	private final static Matrix2x2 matrix = new Matrix2x2(data);

  public UnitMatrix2x2() {
		super(0, Matrix2x2.matrixClass);
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {
    return matrix;
  }

  public Class getChildType(int i) {
    return null;
  }

  public String getName() {
    return "2x2:1";
  }

}