package com.neocoretechs.neovolve.objects;

import java.io.*;
import com.neocoretechs.neovolve.functions.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class MatrixNxN implements Add.Compatible, Subtract.Compatible, Serializable {

	public int[][] data;
	public int N;
	public static Class matrixClass = MatrixNxN.class;

  public MatrixNxN(int N, int[][] data) {
		this.N = N;
		this.data = data;
  }

	public Object execute_add(Object obj) {
		int[][] sum = new int[N][N];

		MatrixNxN m = (MatrixNxN)obj;

		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++)
				if (i>=m.N || j>=m.N)
					sum[i][j] = data[i][j];
				else
					sum[i][j] = data[i][j] + m.data[i][j];

		return new MatrixNxN(N, sum);
	}

	public Object execute_subtract(Object obj) {
		int[][] sum = new int[N][N];

		MatrixNxN m = (MatrixNxN)obj;

		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++)
				if (i>=m.N || j>=m.N)
					sum[i][j] = data[i][j];
				else
					sum[i][j] = data[i][j] - m.data[i][j];

		return new MatrixNxN(N, sum);
	}

}