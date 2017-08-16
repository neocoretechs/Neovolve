package com.neocoretechs.neovolve;

import java.util.*;
import java.io.*;

/** Represents a population of individuals.
 * <P>
 * Copyright:    Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Population.java,v 1.4 2000/10/12 15:22:55 groovyjava Exp $
 */

public class Population implements Serializable {
  Individual[] individuals;
  transient float[] fitnessRank;

  /**
   * Constructs a population of the given size, with empty individuals.
   * <p>
   * @param size the number of individuals in the population
   */
  public Population(int size) {
    individuals = new Individual[size];
    fitnessRank = new float[size];
  }

  /**
   * Creates a population using the ramped half-and-half method.
   * <p>
   * @param types the type of each chromosome, the length
   * is the number of chromosomes
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

  public void create(Class[] types, Class[][] argTypes, Function[][] nodeSets) {
    for (int i=0; i<individuals.length; i++) {
      // maxInitDepth the maximum depth of the initial individuals
      int depth = 2+(World.maxInitDepth-1)*i/individuals.length;
      // Groff: attempt to remove nonviable individuals
      try {
      individuals[i] = new Individual(types.length);
      if ((i%2)==0)
        individuals[i].grow(depth, types, argTypes, nodeSets);
      else
        individuals[i].full(depth, types, argTypes, nodeSets);
      } catch(Exception e) {
        System.out.println(e);
        --i;
      }
      if( (i%100)==0 )System.out.print(i+"\r");
    }
    System.out.println();
  }

  /**
   * Gets the number of individuals in this population.
   * <p>
   * @return the number of individuals in this population
   *
   * @since 1.0
   */
  public int getSize() {
    return individuals.length;
  }

  /**
   * Gets a particular individual.
   * <p>
   * @param i the individual number, 0 to population size-1.
   * @return the individual
   *
   * @since 1.0
   */
  public Individual getIndividual(int i) {
    return individuals[i];
  }

  /**
   * Sets a particular individual.
   * <p>
   * @param i the individual number, 0 to population size-1
   * @param ind the individual
   *
   * @since 1.0
   */
  public void setIndividual(int i, Individual ind) {
    individuals[i] = ind;
  }

  /**
   * Sorts the population into "ascending" order using some criterion for "ascending".
   * A Comparator is given which will compare two individuals, and if one individual
   * compares as lower than another individual, the first individual will appear
   * in the population before the second individual.
   * <p>
   * @param c the Comparator to use
   */
  public void sort(Comparator c) {
    Arrays.sort(individuals, c);
    float f = 0;
    for (int i=0; i<individuals.length; i++) {
      fitnessRank[i] = f;
      f += individuals[i].getFitness();
    }
  }

	/**
	 * Execute a chromosome of an individual as a void.
	 *
	 * @param n the individual number
	 * @param c the chromosome number
	 * @param args the arguments to the chromosome
	 */
  public void execute_void(int n, int c, Object[] args) {
      individuals[n].execute_void(c, args);
  }

  public boolean execute_boolean(int n, int c, Object[] args) {
      return individuals[n].execute_boolean(c, args);
  }

  public int execute_int(int n, int c, Object[] args) {
      return individuals[n].execute_int(c, args);
  }

  public long execute_long(int n, int c, Object[] args) {
      return individuals[n].execute_long(c, args);
  }

  public float execute_float(int n, int c, Object[] args) {
      return individuals[n].execute_float(c, args);
  }

  public double execute_double(int n, int c, Object[] args) {
      return individuals[n].execute_double(c, args);
  }

  public Object execute_object(int n, int c, Object[] args) {
      return individuals[n].execute_object(c, args);
  }
}
