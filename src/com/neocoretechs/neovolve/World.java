package com.neocoretechs.neovolve;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.zip.*;
import javax.swing.event.*;
import com.neocoretechs.powerspaces.PowerSpace;
import com.neocoretechs.powerspaces.Packet;
import com.neocoretechs.powerspaces.server.CustomerConnectionPanel;
import com.neocoretechs.powerspaces.server.handler.PKParallel;
/**
 * World was the main engine class of the previous incarnation of this
 * system as Groovy Java Genetic Programming system.  In this system, Neovolve,
 * an abstract method has been added to reduce fitness at the users option, when
 * an exception is thrown from computeRawFitness.<br>
 * This class is abstract and should be subclassed so that you can provide specifics
 * about your own genetic programming problem (or "world").
 * <p>
 * In the constructor of your subclass, you should set the values of the various
 * genetic programming runtime parameters. The defaults are:
 * <p>
 * <ul>
 *   <li>selectionMethod: FitnessProportionateSelection
 *   <li>crossoverProb: 0.9
 *   <li>reproductionProb: 0.1
 *   <li>maxCrossoverDepth: 17
 *   <li>maxInitDepth: 6
 *   <li>random: Random() (default Random constructor)
 * </ul>
 * <p>
 * Once you have determined your world's parameters, you must provide a function which
 * evaluates the absolute fitness of a given {@link Individual Individual}.
 * The adjusted fitness of an individual is a value between 0 and 1 inclusive, with
 * larger values indicating fitter individuals. Koza[1992] uses the adjusted
 * fitness formula 1/(1+s),
 * where s is the standardized fitness whose value is between 0 and infinity,
 * with smaller values indicating fitter individuals, and 0 indicating a perfect
 * individual. While the use of the Koza formula is not required, it does have the
 * advantage of exaggerating the difference between highly and closely fit individuals.
 * If you do not use the Koza formula, some other fitness function must be used such
 * that higher values indicate fitter individuals.
 * <p>
 * The next step is to {@link #create create} the world. You will need to know the population size,
 * the number of {@link Chromosome chromosome}s per individual, the return type of each chromosome,
 * the number of arguments and their types for each chromosome, and the types of
 * nodes (functions and terminals) each chromosome is allowed to have.
 * <p>
 * Check the function directory for available functions and the objects dir for available objects
 * <p>
 * Automatically Defined function:ADF could be considered a terminal if it refers to a 
 * chromosome which takes
 * no arguments, but in general it's a function.
 * <p>
 * Creating the world will generate the initial population according to the
 * ramped half-and-half method described in Koza[1992]. The create method
 * evaluates each individual by calling your fitness method.
 * <p>
 * After create returns, you can evolve the population by calling
 * {@link #nextGeneration nextGeneration}.
 * This method performs the reproduce and crossover operations described in Koza[1992].
 * The individuals chosen for these operations are selected using the
 * {@link #selectionMethod selectionMethod} you chose. The default selection method is
 * fitness-proportionate.
 * <p>
 * You can call nextGeneration as many times as you want. A new run can be started
 * by calling the create method again.
 * <p>
 * Visit the <a href=http://www.neocoretechs.com>
 * NeoCoreTechs Home Page</a> for information about other projects.
 * <P>
 * Original Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @author Jon groff 
 * @version $Id: World.java,v 1.4 2000/10/12 15:22:55 groovyjava Exp $
 */

public abstract class World implements Serializable {
  Population population;
  float totalFitness;
  public boolean DEBUG = false; // Gets set true for duration of best of generation
  public boolean BREAKBEST = false; // Set true to stop when fitness = 0.0
  int ClusterNodes = 0;
  transient CustomerConnectionPanel CustomerCP;
  transient Integer PowerplantLeg;
  /**
   * The method of choosing an individual to perform an evolution operation on.
   * The default value is {@link FitnessProportionateSelection FitnessProportionateSelection}.
   *
   * @since 1.0
   */
  protected static SelectionMethod selectionMethod = new FitnessProportionateSelection();

  protected static CrossMethod crossMethod = new BranchTypingCross();

  /**
   * The probability that a crossover operation is chosen during evolution. Must be
   * between 0.0f and 1.0f, inclusive. The default value is 0.9f.
   *
   * @since 1.0
   */
  protected static float crossoverProb = 0.9f;

  /**
   * The probability that a reproduction operation is chosen during evolution. Must be
   * between 0.0f and 1.0f, inclusive. The default value is 0.1f. In the current version
   * of Neovolve, crossoverProb + reproductionProb must equal 1.0f. Other operations may
   * be added in the future.
   *
   * @since 1.0
   */
  protected static float reproductionProb = 0.1f;

  /**
   * The maximum depth of an individual resulting from crossover. The default value
   * is 17.
   *
   * @since 1.0
   */
  protected static int maxCrossoverDepth = 17;

  /**
   * The maximum depth of an individual when the world is created. The default value
   * is 6.
   *
   * @since 1.0
   */
  protected static int maxInitDepth = 6;

  protected static int maxChromosomes = 4;
  protected static int minChromosomes = 1;
  protected static int maxSize = 500;

  /**
   * The random number generator used by every random event. The default value is
   * Random (the default constructor which bases the seed on the current time).
   *
   * @since 1.0
   */
  public static Random random = new Random();

  public static Object[] noargs = new Object[0];

  EventListenerList GPListeners = new EventListenerList();

  /**
   * Constructs a world.
   *
   * @since 1.0
   */
  public World() throws IOException {

    // Make a compute thread for this host
/*
    (new ComputeWorker(57800)).start();
    try {
	    Thread.sleep(1000);
    } catch (InterruptedException ex) {
    }
*/
  }

  /**
   * Creates the initial population for the world and computes the fitnesses
   * of all the individuals in the initial population.
   * <p>
   * Implementation note: the arguments of a chromosome, if any, are treated as
   * {@link com.neocoretechs.neovolve.functions.Variable Variable}s of name "ARG"+argnum
   * (ARG0, ARG1, etc). These variables
   * are automatically saved, loaded before a call to the chromosome (via
   * {@link com.groovyj.jgprog.functions.ADF ADF}),
   * and restored after the call.
   *
   * @param popSize the number of individuals in the population
   * @param types the type of each chromosome, the length is
   * the number of chromosomes
   * @param argTypes the types of the arguments to each chromosome, must be an array
   * of arrays, the first dimension of which is the number of chromosomes and the
   * second dimension of which is the number of arguments to the chromosome.
   * @param nodeSets the nodes which are allowed to be used by each chromosome, must
   * be an array of arrays, the first dimension of which is the number of chromosomes
   * and the second dimension of which is the number of nodes. Note that it is not necessary
   * to include the arguments of a chromosome as terminals in the chromosome's node set.
   * This is done automatically for you.
   *
   * @since 1.0
   */
  public void create(int popSize, Class[] types, Class[][] argTypes, Function[][] nodeSets) {
    System.gc();
    System.out.println("Memory before create: " + (Runtime.getRuntime().totalMemory()/1024/1024) + "M");
    Object[] listeners = GPListeners.getListenerList();
    for (int i=listeners.length-1; i>=0; i-=2)
      ((GPListener)listeners[i]).setPopulationSize(popSize);
    System.out.println("Creating initial population");
    population = new Population(popSize);
    population.create(types, argTypes, nodeSets);
    System.gc();
    System.out.println("Memory before computing initial fitnesses: " + (Runtime.getRuntime().totalMemory()/1024/1024) + "M");
    System.out.println("Computing fitnesses");
    computeAll();
  }

   /**
   * All-important create method - creates the world
   */
   public abstract void create();

  /**
   * Computes the adjusted fitness of the given individual. The subclass must provide
   * an implementation of this method.
   *
   * @param individual the Individual to evaulate
   * @return the adjusted fitness, where bigger numbers are better. Does not necessarily
   * have to have a maximum value.
   *
   * @since 1.0
   */
  public abstract float computeFitness(Individual individual);
  public abstract float computeRawFitness(Individual individual);
  public abstract float computeExecutionErrorFitness(Exception ex);

  static class FitnessComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      if (! (o1 instanceof Individual) || ! (o2 instanceof Individual))
        throw new ClassCastException("FitnessComparator must operate on Individuals");
      float f1 = ((Individual)o1).getFitness();
      float f2 = ((Individual)o2).getFitness();
      return f1>f2 ? 1 : (f1==f2 ? 0 : -1);
    }
  }

  /**
  * set cluster params if in use
  */
  public void setCluster(int clusterNodes, Integer plantLeg, CustomerConnectionPanel ccp) {
        ClusterNodes = clusterNodes;
        PowerplantLeg = plantLeg;
        CustomerCP = ccp;
  }

  void computeAll() {
    if( ClusterNodes > 0 ) {
        try {
            // send the worlds, this runs from inside main world
            // and calls out to kernel node worlds
            long st = System.currentTimeMillis();
            PKParallel.PowerKernel_BroadcastAllNoSynch(PowerplantLeg, CustomerCP,
                new Packet("com.neocoretechs.neovolve.NeovolveKernel","Neovolve", 
                		new Packet(this, new Integer(ClusterNodes))));
            Packet ppp = null;
            NeovolveKernel.NeovolveRoot(this, new Integer(ClusterNodes));
            for(int n = 0; n < ClusterNodes-1; n++) {
                ppp = (Packet)(PKParallel.PowerKernel_Collect(PowerplantLeg, CustomerCP));
                //System.out.println(ppp);
                Vector v = new Vector();
                PowerSpace.unwindPacket(ppp, v);
                for(int i = 0; i < v.size(); i++) {
                        Object[] o = (Object[])(v.elementAt(i));
                        int[] fromto = (int[])o[0];
                        float[] fitnesses = (float[])o[1];
                        int k = 0;
                        for (int j = fromto[0]; j < fromto[1]; j++) {
                                Individual ind = population.getIndividual(j);
                                ind.setFitness(fitnesses[k]);
                                ind.population = population;
                                ind.sequence = j;
                                totalFitness += fitnesses[k++];
                        }
                }
            }
            System.out.println("Cluster evolution time "+String.valueOf(System.currentTimeMillis()-st)+" ms. "+ClusterNodes+" nodes");
            System.out.println("Total fitness = " + totalFitness);
            // Order population with most fit last
            population.sort(new FitnessComparator());
            return;
        } catch (Exception ex) {
                System.out.println("Cluster compute fault: " + ex);
                System.exit(1);
        } 
    }
    computeSome(0, population.getSize());
    population.sort(new FitnessComparator());
  }

  long computeSome(int start, int end) {

    Individual ind;
    totalFitness = 0.0f;

    System.out.println("Computing some individuals...");

    Object[] listeners = GPListeners.getListenerList();
    for (int i=listeners.length-1; i>=0; i-=2)
      ((GPListener)listeners[i]).resetEvaluationProgress();

    long t = System.currentTimeMillis();

    for (int i = start; i < end; i++) {
      if ((i%500)==0) {
      	System.out.print(".");
       	System.out.flush();
      }
      ind = population.getIndividual(i);
      ind.sequence = i;
      ind.population = population;
      if (ind.getFitness()==-1.0f) { // it wasn't reproduced from the previous generation
        try {
          ind.setFitness(computeFitness(ind));
        }
        catch(UnsupportedOperationException uoe) {
                System.out.println("Possible function problem : "+uoe);
                System.out.println(ind);
                ind.setFitness(0.0F);
        }
        catch (Exception ex) {
                // Groff: Attempt to disqualify wrong types causing class cast ex
                /*
					System.out.println("Exception in computing this individual:");
					System.out.println(ind.toString());
                */
                //                        System.out.println(ex+" "+ind.toString());
                /*
					ex.printStackTrace();
					System.exit(1);
                */
                ind.setFitness(computeExecutionErrorFitness(ex));
        }
      }

      //if( ind.getFitness() != 0.0F ) System.out.println(ind.toString());

      totalFitness += ind.getFitness();

      for (int j=listeners.length-1; j>=0; j-=2)
        ((GPListener)listeners[j]).bumpEvaluationProgress();
    }

    t = System.currentTimeMillis() - t;
    for (int j=listeners.length-1; j>=0; j-=2)
      ((GPListener)listeners[j]).setEvaluationTime(t);
    System.out.println("Evaluation complete: " + t + " msec");
    return t;
  }

  /**
   * Gets the sum of the adjusted fitnesses of all individuals in the population.
   * Used by selection methods as a convenience.
   *
   * @return the sum of the population's adjusted fitnesses.
   *
   * @since 1.0
   */
  public float getTotalFitness() {
    return totalFitness;
  }

  /**
   * Gets the population.
   *
   * @return the population
   *
   * @since 1.0
   */
  public Population getPopulation() {
    return population;
  }

  /**
   * Evolve the population by one generation. Probabilistically reproduces
   * and crosses individuals into a new population which then overwrites the
   * original population. Computes the population's fitnesses before returning.
   *
   * @since 1.0
   */
  public void nextGeneration() {
    Population newPopulation = new Population(population.getSize());
    float val;

    System.out.println("Evolving next generation");

    Object[] listeners = GPListeners.getListenerList();
    for (int i=listeners.length-1; i>=0; i-=2)
      ((GPListener)listeners[i]).resetEvolutionProgress();

    for (int i=0; i<population.getSize(); i++) {
      val = random.nextFloat();

      // Note that if we only have one slot left to fill, we don't do
      // crossover, but fall through to reproduction.

      if (i<population.getSize()-1 && val<crossoverProb) {
        Individual i1 = selectionMethod.select(this);
        Individual i2 = selectionMethod.select(this);
        Individual[] newIndividuals = crossMethod.cross(i1, i2);
        newPopulation.setIndividual(i++, newIndividuals[0]);
        newPopulation.setIndividual(i, newIndividuals[1]);
        for (int j=listeners.length-1; j>=0; j-=2) {
          ((GPListener)listeners[j]).bumpEvolutionProgress();
          ((GPListener)listeners[j]).bumpEvolutionProgress();
        }
      } else if (val<crossoverProb + reproductionProb) {
        newPopulation.setIndividual(i, selectionMethod.select(this));
        for (int j=listeners.length-1; j>=0; j-=2)
          ((GPListener)listeners[j]).bumpEvolutionProgress();
      }
    }
    population = newPopulation;
    System.out.println("Computing fitnesses");
    computeAll();
  }

  /**
   * Gets the most fit individual from the current population.
   *
   * @return the most fit individual
   *
   * @since 1.0
   */
  public Individual getBestIndividual() {
    return population.getIndividual(population.getSize()-1);
  }

  /**
   * Gets the least fit individual from the current population.
   *
   * @return the least fit individual
   *
   * @since 1.0
   */
  public Individual getWorstIndividual() {
    return population.getIndividual(0);
  }

	public static int getMaxChromosomes() {
		return maxChromosomes;
	}

	/**
	 * This should print the results of an individual's execution
	 */

	public abstract void output(Individual ind);

  public void run(int numGenerations) {
    Individual best;
    Object[] listeners = GPListeners.getListenerList();

    for (int j=listeners.length-1; j>=0; j-=2)
      ((GPListener)listeners[j]).setMaxGenerations(numGenerations);
		System.gc();
		System.out.println("Memory: " + (Runtime.getRuntime().totalMemory()/1024/1024) + "M");
    System.out.println("Best of generation 0:");
    best = getBestIndividual();
    for (int j=listeners.length-1; j>=0; j-=2)
      ((GPListener)listeners[j]).setBestFitness(best.fitness);
    System.out.println(best);
    // Groff: see if the best is the best of whats left
    DEBUG = true;
    try {
        System.out.println("Raw Fitness = " + computeRawFitness(best));
    } catch(Exception e) {
        System.out.println("Sorry, best failed due to "+e);
        e.printStackTrace();
    }
    DEBUG = false;
    output(best);
    System.out.println("Avg Fitness = " +
      (getTotalFitness()/population.getSize()));
		System.gc();
		System.out.println("Memory: " + (Runtime.getRuntime().totalMemory()/1024/1024) + "M");
    for (int i=1; i<numGenerations; i++) {
			for (int j=listeners.length-1; j>=0; j-=2)
				((GPListener)listeners[j]).setGeneration(i);
      nextGeneration();
      System.out.println("Best of generation " + i + ":");
      best = getBestIndividual();
			for (int j=listeners.length-1; j>=0; j-=2)
				((GPListener)listeners[j]).setBestFitness(best.fitness);
      System.out.println(best);
      DEBUG = true;
      float bestRawFitness = -1.0F;
      try {
        bestRawFitness =  computeRawFitness(best);
        System.out.println("Raw Fitness = " + bestRawFitness);
      } catch(Exception e) {
                System.out.println("Sorry, best failed due to "+e);
                e.printStackTrace();
      }
      DEBUG = false;
      output(best);
      System.out.println("Avg Fitness = " +
        (getTotalFitness()/population.getSize()));
			System.gc();
			System.out.println("Memory: " + (Runtime.getRuntime().totalMemory()/1024/1024) + "M");
      if( bestRawFitness == 0.0F && BREAKBEST )
        break;
    }
  }

}
