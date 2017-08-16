package com.neocoretechs.neovolve;

import com.neocoretechs.neovolve.functions.*;
import com.neocoretechs.neovolve.objects.*;
import com.neocoretechs.neovolve.instruments.*;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ImagerWorld extends World {

  Variable[] vars = new Variable[8];
	int[][] imagedata1 = {
		{0,0,0,0,0},
		{0,0,0,0,0},
		{0,0,1,0,0},
		{0,0,0,0,0},
		{0,0,0,0,0}
	};
	int[][] imagedata2 = {
		{0,0,0,0,0},
		{0,0,0,0,0},
		{0,0,0,0,0},
		{0,0,0,0,0},
		{0,0,0,0,0}
	};
	MatrixNxN image1 = new MatrixNxN(5, imagedata1);
	MatrixNxN image2 = new MatrixNxN(5, imagedata2);

  public ImagerWorld() throws IOException {

    selectionMethod = new GreedyOverselection();
		maxCrossoverDepth = 30;
		World.maxChromosomes = 4;

  }

  public float computeFitness(Individual ind) {
    return 1.0f/(1.0f+computeRawFitness(ind));
  }

  public float computeRawFitness(Individual ind) {
		int sum = 0;
		MatrixNxN result;

		vars[0].set(image1);
		result = (MatrixNxN)ind.execute_object(0, World.noargs);
		for (int i=0; i<5; i++)
			for (int j=0; j<5; j++)
				if (i>0 || j>0)
					sum += Math.abs(result.data[i][j]);
				else
					sum += 200*Math.abs(5-result.data[i][j]);

		vars[0].set(image2);
		result = (MatrixNxN)ind.execute_object(0, World.noargs);
		for (int i=0; i<5; i++)
			for (int j=0; j<5; j++)
				if (i>0 || j>0)
					sum += Math.abs(result.data[i][j]);
				else
					sum += 200*Math.abs(-5-result.data[i][j]);

		return (float)sum;
  }

  public void create() {
    Class[] types = { MatrixNxN.matrixClass, MatrixNxN.matrixClass, MatrixNxN.matrixClass, MatrixNxN.matrixClass };
    Class[][] argTypes = {
			{ },
			{MatrixNxN.matrixClass, Matrix3x3.matrixClass, Matrix3x3.matrixClass},
			{MatrixNxN.matrixClass, Matrix3x3.matrixClass, Matrix3x3.matrixClass},
			{MatrixNxN.matrixClass, MatrixNxN.matrixClass, Matrix3x3.matrixClass}
		};
    Function[][] nodeSets = {
      {
        vars[0]=Variable.create("IMG", MatrixNxN.matrixClass),
        vars[1]=Variable.create("F_", Matrix3x3.matrixClass),
        vars[2]=Variable.create("F-", Matrix3x3.matrixClass),
        vars[3]=Variable.create("F^", Matrix3x3.matrixClass),
        vars[4]=Variable.create("F[", Matrix3x3.matrixClass),
        vars[5]=Variable.create("F|", Matrix3x3.matrixClass),
        vars[6]=Variable.create("F]", Matrix3x3.matrixClass),
        vars[7]=Variable.create("F0", Matrix3x3.matrixClass),
        new Add(Matrix3x3.matrixClass),
        new Subtract(Matrix3x3.matrixClass),
        new Add(MatrixNxN.matrixClass),
        new Subtract(MatrixNxN.matrixClass),
				new Add(Function.integerClass),
				new Subtract(Function.integerClass),
				new One(Function.integerClass),
        new Convolve3x3(),
				ADF.ADFs[1],
				ADF.ADFs[2],
				ADF.ADFs[3]
      },
      {
        vars[0],
        vars[1],
        vars[2],
        vars[3],
        vars[4],
        vars[5],
        vars[6],
        vars[7],
        new Add(Matrix3x3.matrixClass),
        new Subtract(Matrix3x3.matrixClass),
        new Add(MatrixNxN.matrixClass),
        new Subtract(MatrixNxN.matrixClass),
				new Add(Function.integerClass),
				new Subtract(Function.integerClass),
				new One(Function.integerClass),
        new Convolve3x3(),
				ADF.ADFs[2],
				ADF.ADFs[3]
      },
      {
        vars[0],
        vars[1],
        vars[2],
        vars[3],
        vars[4],
        vars[5],
        vars[6],
        vars[7],
        new Add(Matrix3x3.matrixClass),
        new Subtract(Matrix3x3.matrixClass),
        new Add(MatrixNxN.matrixClass),
        new Subtract(MatrixNxN.matrixClass),
				new Add(Function.integerClass),
				new Subtract(Function.integerClass),
				new One(Function.integerClass),
        new Convolve3x3(),
				ADF.ADFs[3]
      },
      {
        vars[0],
        vars[1],
        vars[2],
        vars[3],
        vars[4],
        vars[5],
        vars[6],
        vars[7],
        new Add(Matrix3x3.matrixClass),
        new Subtract(Matrix3x3.matrixClass),
        new Add(MatrixNxN.matrixClass),
        new Subtract(MatrixNxN.matrixClass),
				new Add(Function.integerClass),
				new Subtract(Function.integerClass),
				new One(Function.integerClass),
        new Convolve3x3()
      }

    };

		int[][] f1 = {{0,0,0},{0,0,0},{1,1,1}};
		int[][] f2 = {{0,0,0},{1,1,1},{0,0,0}};
		int[][] f3 = {{1,1,1},{0,0,0},{0,0,0}};
		int[][] f4 = {{1,0,0},{1,0,0},{1,0,0}};
		int[][] f5 = {{0,1,0},{0,1,0},{0,1,0}};
		int[][] f6 = {{0,0,1},{0,0,1},{0,0,1}};
		int[][] f0 = {{0,0,0},{0,0,0},{0,0,0}};

		vars[1].set(new Matrix3x3(f1));
		vars[2].set(new Matrix3x3(f2));
		vars[3].set(new Matrix3x3(f3));
		vars[4].set(new Matrix3x3(f4));
		vars[5].set(new Matrix3x3(f5));
		vars[6].set(new Matrix3x3(f6));
		vars[7].set(new Matrix3x3(f0));

    create(5000, types, argTypes, nodeSets);
  }

	public void output(Individual ind) {
		int sum = 0;
		MatrixNxN result1, result2;

		vars[0].set(image1);
		result1 = (MatrixNxN)ind.execute_object(0, World.noargs);

		vars[0].set(image2);
		result2 = (MatrixNxN)ind.execute_object(0, World.noargs);

		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++)
				System.out.print(result1.data[i][j] + " ");
			System.out.println();
		}
		System.out.println();
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++)
				System.out.print(result2.data[i][j] + " ");
			System.out.println();
		}
	}

        public float computeExecutionErrorFitness(Exception ex) { return 0.0F; }

	public static void main(String[] args) throws Exception {
		try {
			World world = new ImagerWorld();
			//SimpleWatcher watcher = new SimpleWatcher("ImagerWorld");

			//world.addGPListener(watcher);

			world.create();
			world.run(50);
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
	}
}
