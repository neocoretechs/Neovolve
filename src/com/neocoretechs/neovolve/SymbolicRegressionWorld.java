package com.neocoretechs.neovolve;

import com.neocoretechs.neovolve.functions.*;
import java.io.*;

/**
 * The symbolic regression problem of Koza[1992], to find the formula x^4+x^3+x^2+x
 * given 20 random points for x in the range [-1,1]. Uses a population size of 500
 * with fitness-proportionate selection, and runs for 50 generations.
 * <p>
 * Note: this is where I found that I had to protect the EXP and LN functions against extremely
 * large values, since they would return infinity and then other functions operating on that
 * value (especially SIN and COS) would return Not-a-Number, which would totally throw everything
 * off.
 * <p>
 * Probably a better solution would have been to protect functions against infinity arguments.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: SymbolicRegressionWorld.java,v 1.4 2000/10/12 15:22:55 groovyjava Exp $
 */
public class SymbolicRegressionWorld extends World {

  Float[] x = new Float[20];
  float[] y = new float[20];
  Variable vx;

  public SymbolicRegressionWorld() throws IOException {
    // selectionMethod = new GreedyOverselection();
    for (int i=0; i<20; i++) {
      float f = 2.0f*(random.nextFloat()-0.5f);
      x[i] = new Float(f);
      y[i] = f*f*f*f+f*f*f+f*f+f;
    }
  }

  public void create() {

    Class[] types = { Function.floatClass };
    Class[][] argTypes = { {} };
    Function[][] nodeSets = {
      {
        vx=Variable.create("X", Function.floatClass),
        new Add(Function.floatClass),
        new Subtract(Function.floatClass),
        new Multiply(Function.floatClass),
        new Divide(Function.floatClass),
        new Sine(Function.floatClass),
        new Cosine(Function.floatClass),
        new Exponential(Function.floatClass),
        new NaturalLogarithm(Function.floatClass)
      }
    };

    create(500, types, argTypes, nodeSets);
  }

  public float computeFitness(Individual ind) {
    return 1.0f/(1.0f+computeRawFitness(ind));
  }

  public float computeRawFitness(Individual ind) {
    float error = 0.0f;

    for (int i=0; i<20; i++) {
      vx.set(x[i]);
      try {
        float result = ind.execute_float(0, World.noargs);
        error += Math.abs(result-y[i]);
      } catch (ArithmeticException ex) {
        System.out.println("x="+x[i].floatValue());
        System.out.println(ind);
        throw ex;
      }
    }
    return error;
  }

	/*
  public int run(int numGenerations) {
    Individual best;
    long t;

    System.out.println("Best of generation 0:");
    best = getBestIndividual();
    System.out.println(best);
    System.out.println("Raw Fitness = " + computeRawFitness(best));
    System.out.println("Avg Fitness = " +
      (getTotalFitness()/population.getSize()));
    if (computeRawFitness(best)<0.01)
      return 0;
    for (int i=1; i<numGenerations; i++) {
      t = System.currentTimeMillis();
      nextGeneration();
      long t2 = System.currentTimeMillis()-t;
      System.out.println((((float)t2)/1000)+" sec");
      System.out.println("Best of generation " + i + ":");
      best = getBestIndividual();
      System.out.println(best);
      System.out.println("Raw Fitness = " + computeRawFitness(best));
      System.out.println("Avg Fitness = " +
        (getTotalFitness()/population.getSize()));
      if (computeRawFitness(best)<0.01)
        return i;
    }
    return -1;
  }

  public void runProb(int numGenerations, int numRuns) {

    int[] wins = new int[numGenerations];

    for (int i=0; i<numRuns; i++) {
        int gen;

        System.out.println("+++\n+++Run " + i + "\n+++");
        create();
        gen = run(numGenerations);
        if (gen>=0)
            wins[gen]++;
    }

    int total = 0;

    for (int i=0; i<numGenerations; i++) {
        total += wins[i];
        System.out.println(i + "," + ( (float)total/(float)numRuns ));
    }
}

  public static void main(String[] args) throws IOException {
    SymbolicRegressionWorld world = new SymbolicRegressionWorld();

    world.create();
		if (args.length == 0) {
			System.out.println("Usage: SymbolicRegressionWorld <population> <#runs>");
			System.exit(0);
		} else {
			world.runProb(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		}
  }
	*/

	public void output(Individual ind) {
	}
  public float computeExecutionErrorFitness(Exception ex) { return 0.0F; }

  public static void main(String[] args) {

		try {
			World world = new SymbolicRegressionWorld();

			world.create();
			world.run(50);
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
  }
}
