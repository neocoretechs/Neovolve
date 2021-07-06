package com.neocoretechs.neovolve.functions;
import com.neocoretechs.neovolve.*;
import java.io.*;

/**
 * An automatically defined function (ADF) is a chromosome which takes
 * a number of arguments. Calling an ADF results in setting the arguments
 * and computing the value of the chromosome.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: ADF.java,v 1.1 2000/10/12 15:19:39 groovyjava Exp $
 */
public class ADF extends Function implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static ADF[] ADFs;

	static {
		ADFs = new ADF[World.getMaxChromosomes()];
		for (int i=0; i<World.getMaxChromosomes(); i++)
			ADFs[i] = new ADF(i);
	}

  public int chromosomeNum;
  // Class[] argTypes = null;
  // Argument[] args = null;
  // Stack stack = new Stack();

  /**
   * Creates an ADF node which will evaluate the given chromosome number
   * and takes the given argument types.
   *
   * @param chromosomeNum the chromosome number to evaluate
   * @param argTypes the types for the arguments
   *
   * @since 1.0
   */
  public ADF(/*int arity,*/ int chromosomeNum /*, Class[] argTypes */) {
    super(0 /*arity*/, null);
    this.chromosomeNum = chromosomeNum;
//    this.argTypes = argTypes;
//    args = new Argument[argTypes.length];
//    for (int i=0; i<argTypes.length; i++)
//      args[i] = Argument.create(i, chromosomeNum, argTypes[i]);
  }

	public int getArity() {
		return individual.getChromosome(chromosomeNum).getArity();
	}

  public String getName() {
    return "ADF" + chromosomeNum;
  }

  /**
   * Get the chromosome number this ADF refers to.
   *
   * @return the chromosome number this ADF refers to.
   *
   * @since 1.0
   */
  public int getChromosomeNum() {
    return chromosomeNum;
  }

  public boolean execute_boolean(Chromosome c, int n, Object[] args) {

		int numargs = individual.getChromosome(chromosomeNum).getArity();

    Object[] vals = new Object[numargs];
    for (int i=0; i<numargs; i++)
      vals[i] = c.execute(n, i, args);

    // Call the chromosome

    return individual.execute_boolean(chromosomeNum, vals);
  }

  public void execute_void(Chromosome c, int n, Object[] args) {

		int numargs = individual.getChromosome(chromosomeNum).getArity();

    Object[] vals = new Object[numargs];
    for (int i=0; i<numargs; i++)
      vals[i] = c.execute(n, i, args);

    // Call the chromosome
    individual.execute_void(chromosomeNum, vals);
  }

  public int execute_int(Chromosome c, int n, Object[] args) {

		int numargs = individual.getChromosome(chromosomeNum).getArity();

    Object[] vals = new Object[numargs];
    for (int i=0; i<numargs; i++)
      vals[i] = c.execute(n, i, args);

    // Call the chromosome
    return individual.execute_int(chromosomeNum, vals);
  }

  public long execute_long(Chromosome c, int n, Object[] args) {

		int numargs = individual.getChromosome(chromosomeNum).getArity();

    Object[] vals = new Object[numargs];
    for (int i=0; i<numargs; i++)
      vals[i] = c.execute(n, i, args);

    // Call the chromosome
    return individual.execute_long(chromosomeNum, vals);
  }

  public float execute_float(Chromosome c, int n, Object[] args) {

		int numargs = individual.getChromosome(chromosomeNum).getArity();

    Object[] vals = new Object[numargs];
    for (int i=0; i<numargs; i++)
      vals[i] = c.execute(n, i, args);

    // Call the chromosome
    return individual.execute_float(chromosomeNum, vals);
  }

  public Object execute_object(Chromosome c, int n, Object[] args) {

		int numargs = individual.getChromosome(chromosomeNum).getArity();

    Object[] vals = new Object[numargs];
    for (int i=0; i<numargs; i++)
      vals[i] = c.execute(n, i, args);

    // Call the chromosome
    return individual.execute_object(chromosomeNum, vals);
  }

  public double execute_double(Chromosome c, int n, Object[] args) {

		int numargs = individual.getChromosome(chromosomeNum).getArity();

    Object[] vals = new Object[numargs];
    for (int i=0; i<numargs; i++)
      vals[i] = c.execute(n, i, args);

    // Call the chromosome
    return individual.execute_double(chromosomeNum, vals);
  }

  public Class<?> getChildType(int i) {
    return individual.getChromosome(chromosomeNum).getArgType(i);
  }

  public boolean isConstant() {
    return false;
  }
}