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

public class Matrix3x3 implements Add.Compatible, Subtract.Compatible, Serializable {

	public int[][] data;
	public int N;
	public static Class matrixClass = Matrix3x3.class;

  public Matrix3x3(int[][] data) {
		this.N = 3;
		this.data = data;
  }

	public Object execute_add(Object obj) {
		int[][] sum = new int[N][N];

		Matrix3x3 m = (Matrix3x3)obj;

		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++)
				if (i>=m.N || j>=m.N)
					sum[i][j] = data[i][j];
				else
					sum[i][j] = data[i][j] + m.data[i][j];

		return new Matrix3x3(sum);
	}

	public Object execute_subtract(Object obj) {
		int[][] sum = new int[N][N];

		Matrix3x3 m = (Matrix3x3)obj;

		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++)
				if (i>=m.N || j>=m.N)
					sum[i][j] = data[i][j];
				else
					sum[i][j] = data[i][j] - m.data[i][j];

		return new Matrix3x3(sum);
	}


}