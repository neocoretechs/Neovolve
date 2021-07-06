package com.neocoretechs.neovolve;

import java.util.*;
import java.io.*;
import com.neocoretechs.neovolve.functions.ADF;

/** Represents a single individual.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: Individual.java,v 1.4 2000/10/12 15:22:55 groovyjava Exp $
 */
public class Individual implements Serializable {
	private static final long serialVersionUID = -1113771679241955321L;
	Chromosome[] chromosomes;
  float fitness = -1.0f; // Adjusted Fitness
  transient int sequence;
  transient Population population;

  /**
   * Constructs an empty individual with a number of chromosomes.
   * <p>
   * @param numChromosomes the number of chromosomes this individual has
   *
   * @since 1.0
   */
  public Individual(int numChromosomes) {
    chromosomes = new Chromosome[numChromosomes];
  }

  /**
   * Gets the given chromosome.
   * <p>
   * @param num the chromosome number, 0 to number of chromosomes-1
   * @return the chromosome
   *
   * @since 1.0
   */
  public Chromosome getChromosome(int num) {
    return chromosomes[num];
  }

  /**
   * Initializes all chromosomes in this individual using the grow method.
   * <p>
   * @param types the type of each chromosome, must be an array of the same length
   * as the number of chromosomes
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
  public void grow(int depth, Class[] types, Class[][] argTypes, Function[][] nodeSets) {

		Function.setIndividual(this);

    for (int i=0; i<chromosomes.length; i++) {

      chromosomes[i] = new Chromosome();

      chromosomes[i].argTypes = argTypes[i];

      // If there are any ADF's in the nodeSet, then set its type
      // according to the chromosome it references

      for (int j=0; j<nodeSets[i].length; j++)
        if (nodeSets[i][j] instanceof ADF)
          ((ADF)nodeSets[i][j]).setReturnType(
            types[((ADF)nodeSets[i][j]).getChromosomeNum()]);

		}

    for (int i=0; i<chromosomes.length; i++)
      chromosomes[i].grow(i, depth, types[i], argTypes[i], nodeSets[i]);
  }

  /**
   * Initializes all chromosomes in this individual using the full method.
   * <p>
   * @param types the type of each chromosome, must be an array of the same length
   * as the number of chromosomes
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
   public void full(int depth, Class[] types, Class[][] argTypes, Function[][] nodeSets) {

	Function.setIndividual(this);

    for (int i=0; i<chromosomes.length; i++) {

      chromosomes[i] = new Chromosome();

	  chromosomes[i].argTypes = argTypes[i];

      // If there are any ADF's in the nodeSet, then set its type
      // according to the chromosome it references

      for (int j=0; j<nodeSets[i].length; j++)
        if (nodeSets[i][j] instanceof ADF)
          ((ADF)nodeSets[i][j]).setReturnType(
            types[((ADF)nodeSets[i][j]).getChromosomeNum()]);

    }

    for (int i=0; i<chromosomes.length; i++)
      chromosomes[i].full(i, depth, types[i], argTypes[i], nodeSets[i]);
  }

  /**
   * Causes a chromosome to be duplicated in a new individual. If duplicating
   * a chromosome would cause the maximum number of chromosomes to be exceeded,
   * the individual returned will be the same as the original.
   * <p>
   * @param i1 the individual in which to duplicate the chromosome
   * @return the new individual with a duplicated chromosome
   *
   */

  public static Individual dupChromosome(Individual i1) {

    if (i1.chromosomes.length==World.maxChromosomes)
    	return i1;

    // Choose a random chromosome in the individual

    int chromosome = World.random.nextInt(i1.chromosomes.length);

    // Create a new individuals with N+1 chromosomes

    Individual newIndividual = new Individual(i1.chromosomes.length+1);

    // Copy the N chromosomes from the original individual to the new individual. If
    // one of the chromosomes is allowed to call the chromosome we chose above, then
    // add the ability to call the new chromosome.

    for (int i=0; i<i1.chromosomes.length; i++) {

			Chromosome c = i1.chromosomes[i];

	try {
    	  newIndividual.chromosomes[i] = (Chromosome)c.clone();
	} catch (CloneNotSupportedException ex) {
				// never thrown
	}

    for (int j=0; j<c.functionSet.length; j++)

          if (c.functionSet[j] instanceof ADF)

              if ( ((ADF)c.functionSet[j]).chromosomeNum == chromosome ) {

                  Function[] functionSet = new Function[c.functionSet.length+1];
                  System.arraycopy(c.functionSet, 0,
                      functionSet, 0, c.functionSet.length);
                  functionSet[functionSet.length-1] = ADF.ADFs[i1.chromosomes.length];
                  newIndividual.chromosomes[i].functionSet = functionSet;
                  break;

              }
    }

    // Create the new chromosome as a copy of the chosen chromosome

	try {
      newIndividual.chromosomes[i1.chromosomes.length] = (Chromosome)i1.chromosomes[chromosome].clone();
	} catch (CloneNotSupportedException ex) {
			// never thrown
	}

    // If any of the N chromosomes in the new individual calls the chosen chromosome, then
    // replace each such call (with probability 0.5) with a call to the new chromosome.

    for (int i=0; i<i1.chromosomes.length; i++)
        for (int j=newIndividual.chromosomes[i].getSize(0)-1; j>=0; j--)
            if (newIndividual.chromosomes[i].functions[j] instanceof ADF)
                if ( ((ADF)newIndividual.chromosomes[i].functions[j]).chromosomeNum == chromosome)
                    if (World.random.nextFloat() < 0.5f)
                        newIndividual.chromosomes[i].functions[j] = ADF.ADFs[i1.chromosomes.length];

    return newIndividual;
  }

  /**
   * Causes a chromosome to be deleted in a new individual. If deleting a
   * chromosome would cause the minimum number of chromosomes to be violated,
   * the individual returned will be the same as the original. None of the
   * "minimally required" chromosomes can be deleted. Any other chromosome which
   * calls the deleted chromosome will have its calls replaced with a random terminal
   * or function of the appropriate return type. Any chromosome which calls chromosomes
   * past the deleted one will have its calls replaced with the correct chromosome
   * number in the new individual (so that a call to chromosome N in the old individual
   * will correctly call the same chromosome, now chromosome N-1, in the new individual).
   */

  public static Individual delChromosome(Individual i1) {

    if (i1.chromosomes.length==World.minChromosomes)
    	return i1;

    int chromosome = World.minChromosomes +
    	World.random.nextInt(i1.chromosomes.length-World.minChromosomes);

    Individual newIndividual = new Individual(i1.chromosomes.length-1);

	try {
		for (int i=0; i<chromosome; i++)
				newIndividual.chromosomes[i] = (Chromosome)i1.chromosomes[i].clone();
		for (int i=chromosome; i<i1.chromosomes.length-1; i++)
				newIndividual.chromosomes[i] = (Chromosome)i1.chromosomes[i+1].clone();
	} catch (CloneNotSupportedException ex) {
			// never thrown
	}

	// Adjust each new chromosome's allowed function set so that it does not
	// refer to the last chromosome.

	for  (int i=0; i<newIndividual.chromosomes.length; i++) {

			Chromosome c = newIndividual.chromosomes[i];
			Function[] functionSet = null;

			// If the function set refers to the deleted chromosome, then
			// remove that reference

			for (int j=0; j<c.functionSet.length; j++)
				if (c.functionSet[j] instanceof ADF)
					if ( ((ADF)c.functionSet[j]).chromosomeNum == chromosome) {

						functionSet = new Function[c.functionSet.length-1];
						System.arraycopy(c.functionSet, 0,
							functionSet, 0, j);
						System.arraycopy(c.functionSet, j+1,
							functionSet, j, functionSet.length-j);
						break;

					}

			if (functionSet==null) {
				functionSet = new Function[c.functionSet.length];
				System.arraycopy(c.functionSet, 0, functionSet, 0, functionSet.length);
			}

			// If the function set refers to an ADF out past the deleted one, then replace
			// the reference with the correct one.

			for (int j=0; j<functionSet.length; j++) {

				if (functionSet[j] instanceof ADF)

					if ( ((ADF)functionSet[j]).chromosomeNum > chromosome) {

						ADF oldADF = (ADF)functionSet[j];

						functionSet[j] = ADF.ADFs[oldADF.chromosomeNum-1];

					}

			}

			c.functionSet = functionSet;

			// If the functions refer to the delete chromosome, replace it with a
			// random terminal or function.

			// If the functions refer to an ADF out past the deleted one, then replace
			// the reference with the correct one.

			for (int j=c.getSize(0)-1; j>=0; j--)

				if (c.functions[j] instanceof ADF)

					if ( ((ADF)c.functions[j]).chromosomeNum > chromosome)

						c.functions[j] = ADF.ADFs[((ADF)c.functions[j]).chromosomeNum-1];

	}

	return newIndividual;
 }


  /**
   * Executes the given chromosome as a boolean function.
   *
   * @param chromosomeNum the chromosome to execute
   * @return the boolean return value
   *
   * @since 1.0
   */
  public boolean execute_boolean(int chromosomeNum, Object[] args) {
    Function.setIndividual(this);
    return chromosomes[chromosomeNum].execute_boolean(args);
  }

  /**
   * Executes the given chromosome which returns nothing.
   *
   * @param chromosomeNum the chromosome to execute
   *
   * @since 1.0
   */
  public void execute_void(int chromosomeNum, Object[] args) {
    Function.setIndividual(this);
    chromosomes[chromosomeNum].execute_void(args);
  }

  /**
   * Executes the given chromosome as an integer function.
   *
   * @param chromosomeNum the chromosome to execute
   * @return the integer return value
   *
   * @since 1.0
   */
  public int execute_int(int chromosomeNum, Object[] args) {
    Function.setIndividual(this);
    return chromosomes[chromosomeNum].execute_int(args);
  }

  /**
   * Executes the given chromosome as an object function.
   *
   * @param chromosomeNum the chromosome to execute
   * @return the object return value
   *
   * @since 1.0
   */
  public Object execute_object(int chromosomeNum, Object[] args) {
    Function.setIndividual(this);
    return chromosomes[chromosomeNum].execute_object(args);
  }

  /**
   * Executes the given chromosome as a long function.
   *
   * @param chromosomeNum the chromosome to execute
   * @return the long return value
   *
   * @since 1.0
   */
  public long execute_long(int chromosomeNum, Object[] args) {
    Function.setIndividual(this);
    return chromosomes[chromosomeNum].execute_long(args);
  }

  /**
   * Executes the given chromosome as a float function.
   *
   * @param chromosomeNum the chromosome to execute
   * @return the float return value
   *
   * @since 1.0
   */
  public float execute_float(int chromosomeNum, Object[] args) {
    Function.setIndividual(this);
    return chromosomes[chromosomeNum].execute_float(args);
  }

  /**
   * Executes the given chromosome as a double function.
   *
   * @param chromosomeNum the chromosome to execute
   * @return the boolean double value
   *
   * @since 1.0
   */
  public double execute_double(int chromosomeNum, Object[] args) {
    Function.setIndividual(this);
    return chromosomes[chromosomeNum].execute_double(args);
  }

  /**
   * Gets the adjusted fitness of this individual. The fitness must have
   * already been evaluated by a {@link World World}.
   *
   * @return the adjusted fitness
   *
   * @since 1.0
   */
  public float getFitness() {
    return fitness;
  }

  /**
   * Sets the adjusted fitness of this individual.
   *
   * @param f the adjusted fitness
   *
   * @since 1.0
   */
  public void setFitness(float f) {
    fitness = f;
  }

  /**
   * Returns a string representing this individual. The format of the string is
   * a set of lines, each line representing one chromosome. The format of each
   * line is [chromosomeNum] (string representation of the chromosome).
   *
   * @return the string representing this individual
   *
   * @since 1.0
   */
  public String toString() {
    String str = new String();

    for (int i=0; i<chromosomes.length; i++)
      str += "[" + i + "] " + chromosomes[i] + "\n";
    return str;
  }

  public int getSequence() {
    return sequence;
  }
}
