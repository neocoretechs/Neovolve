package com.neocoretechs.neovolve.objects;

import com.neocoretechs.neovolve.functions.*;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class Matrix2x2 implements Serializable, Add.Compatible, Subtract.Compatible {

	public int[][] data = {{0,0},{0,0}};
	public static Class matrixClass = Matrix2x2.class;

  public Matrix2x2(int[][] data) {
		this.data = data;
  }

	public Object execute_add(Object obj) {
		int[][] sum = new int[2][2];

		Matrix2x2 m = (Matrix2x2)obj;

		sum[0][0] = data[0][0] + m.data[0][0];
		sum[0][1] = data[0][1] + m.data[0][1];
		sum[1][0] = data[1][0] + m.data[1][0];
		sum[1][1] = data[1][1] + m.data[1][1];

		return new Matrix2x2(sum);
	}

	public Object execute_subtract(Object obj) {
		int[][] sum = new int[2][2];

		Matrix2x2 m = (Matrix2x2)obj;

		sum[0][0] = data[0][0] - m.data[0][0];
		sum[0][1] = data[0][1] - m.data[0][1];
		sum[1][0] = data[1][0] - m.data[1][0];
		sum[1][1] = data[1][1] - m.data[1][1];

		return new Matrix2x2(sum);
	}


}