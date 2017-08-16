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

public class Convolve3x3 extends Function implements Serializable {

  public Convolve3x3() {
		super(3, MatrixNxN.matrixClass);
  }

  public Class getChildType(int i) {
    return i==0 ? MatrixNxN.matrixClass :
					 i==1 ? Matrix3x3.matrixClass :
					 integerClass;
  }

  public String getName() {
    return "conv3x3";
  }

	public Object execute_object(Chromosome c, int n, Object[] args) {
		try {
		Matrix3x3 filter = (Matrix3x3)c.execute_object(n, 1, args);
		MatrixNxN matrix = (MatrixNxN)c.execute_object(n, 0, args);
		int offset = c.execute_int(n, 2, args);
		int data[][] = new int[matrix.N][matrix.N];

		for (int i=0; i<matrix.N; i++)
			for (int j=0; j<matrix.N; j++) {

				int sum = offset;

				for (int x=0; x<3; x++)
					for (int y=0; y<3; y++)
						if (i+x-1<matrix.N && i+x-1>0 &&
							  j+y-1<matrix.N && j+y-1>0)
							sum += filter.data[x][y]*matrix.data[i+x-1][j+y-1];

				data[i][j] = sum;
			}

		return new MatrixNxN(matrix.N, data);
		} catch (Exception ex) {
			Object obj = c.execute_object(n, 1, args);
			System.exit(0);
			return null;
		}
	}
}
