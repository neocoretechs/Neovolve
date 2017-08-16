package com.neocoretechs.neovolve.functions;

import com.neocoretechs.neovolve.*;
import java.io.*;
import com.neocoretechs.neovolve.objects.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class Convolve extends Function implements Serializable {

  public Convolve() {
		super(3, MatrixNxN.matrixClass);
  }

  public Class getChildType(int i) {
    return i==0 ? MatrixNxN.matrixClass :
					 i==1 ? Matrix2x2.matrixClass :
					 integerClass;
  }

  public String getName() {
    return "convolve";
  }

	public Object execute_object(Chromosome c, int n, Object[] args) {
		Matrix2x2 filter = (Matrix2x2)c.execute_object(n, 1, args);
		MatrixNxN matrix = (MatrixNxN)c.execute_object(n, 0, args);
		int offset = c.execute_int(n, 2, args);
		int data[][] = new int[matrix.N][matrix.N];

		for (int i=0; i<matrix.N; i++)
			for (int j=0; j<matrix.N; j++) {
				int sum = offset;

				sum += filter.data[0][0]*matrix.data[i][j];
				if (j+1<matrix.N)
				  sum += filter.data[0][1]*matrix.data[i][j+1];
				if (i+1<matrix.N)
				  sum += filter.data[1][0]*matrix.data[i+1][j];
				if (i+1<matrix.N && j+1<matrix.N)
				  sum += filter.data[1][1]*matrix.data[i+1][j+1];

				data[i][j] = sum;
			}

		return new MatrixNxN(matrix.N, data);
	}
}