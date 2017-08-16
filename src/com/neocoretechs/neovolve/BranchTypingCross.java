package com.neocoretechs.neovolve;

public class BranchTypingCross extends CrossMethod {

  /**
   * Crosses two individuals. A random chromosome is chosen for crossing based
   * probabilistically on the proportion of nodes in each chromosome in the first
   * individual.
   * <p>
   * @param i1 the first individual to cross
   * @param i2 the second individual to cross
   * @return an array of the two resulting individuals
   *
   * @author Robert Baruch (jgprog@sourceforge.net)
   * @version $Id: BranchTypingCross.java,v 1.1 2000/10/12 15:22:55 groovyjava Exp $
   */
   public Individual[] cross(Individual i1, Individual i2) {

    // Determine which chromosome we'll cross, probabilistically determined
    // by the sizes of the chromosomes of the first individual --
    // equivalent to Koza's branch typing.

    int[] sizes = new int[i1.chromosomes.length];
    int totalSize = 0;

    for (int i=0; i<i1.chromosomes.length; i++) {
      sizes[i] = i1.chromosomes[i].getSize(0);
      totalSize += sizes[i];
    }

    int nodeNum = World.random.nextInt(totalSize);
    int chromosomeNum;

    for (chromosomeNum=0; chromosomeNum<i1.chromosomes.length; chromosomeNum++) {
      nodeNum -= sizes[chromosomeNum];
      if (nodeNum<0)
        break;
    }

    // Cross the selected chromosomes

    Chromosome[] newChromosomes = doCross(
      i1.chromosomes[chromosomeNum],
      i2.chromosomes[chromosomeNum]);

    // Create the new individuals by copying the uncrossed chromosomes
    // and setting the crossed chromosome. There's no need to deep-copy
    // the uncrossed chromosomes because they don't change. That is,
    // even if two individuals' chromosomes point to the same chromosome,
    // the only change in a chromosome is crossing, which generates
    // deep-copied chromosomes anyway.

    Individual[] newIndividuals = { new Individual(i1.chromosomes.length),
      new Individual(i1.chromosomes.length) };

    for (int i=0; i<i1.chromosomes.length; i++)
      if (i!=chromosomeNum) {
        newIndividuals[0].chromosomes[i] = i1.chromosomes[i];
        newIndividuals[1].chromosomes[i] = i2.chromosomes[i];
      } else {
        newIndividuals[0].chromosomes[i] = newChromosomes[0];
        newIndividuals[1].chromosomes[i] = newChromosomes[1];
      }

    return newIndividuals;
  }

  /**
   * Crosses two chromsomes using branch-typing.
   * <p>
   * A random point in the first chromosome is chosen,
   * with 90% probability it will be a function and 10% probability it will be
   * a terminal. A random point in the second chromosome
   * is chosen using the same
   * probability distribution, but the node chosen must be of the same type as the chosen node
   * in the first chromosome.
   * <p>
   * If a suitable point in the second chromosome couldn't be found then the
   * chromosomes are not crossed.
   * <p>
   * If a resulting chromosome's depth is larger than the World's maximum crossover depth
   * then that chromosome is simply copied from the original rather than crossed.
   * <p>
   * @param c0 the first chromosome to cross
   * @param c1 the second chromosome to cross
   * @return an array of the two resulting chromosomes
   *
   * @since 1.0
   */
  protected static Chromosome[] doCross(Chromosome c0, Chromosome c1) {

		Chromosome[] c = { c0, c1 };

    // Choose a point in c1

    int p0;

    if (World.random.nextFloat()<0.9f) { // choose a function
      int nf = c0.numFunctions();
      if (nf==0)
        return c; // no functions
      p0 = c0.getFunction(World.random.nextInt(nf));
    } else { // choose a terminal
      p0 = c0.getTerminal(World.random.nextInt(c0.numTerminals()));
    }

    // Choose a point in c2 matching the type

    int p1;
    Class t = c0.getNode(p0).getReturnType();

    if (World.random.nextFloat()<0.9f) { // choose a function
      int nf = c1.numFunctions(t);
      if (nf==0)
        return c; // no functions of that type
      p1 = c1.getFunction(World.random.nextInt(nf), t);
    } else { // choose a terminal
      int nt = c1.numTerminals(t);
      if (nt==0)
        return c; // no terminals of that type
      p1 = c1.getTerminal(World.random.nextInt(c1.numTerminals(t)), t);
    }

    int s0 = c0.getSize(p0);
    int s1 = c1.getSize(p1);
    int d0 = c0.getDepth(p0);
    int d1 = c1.getDepth(p1);
		int c0s = c0.getSize(0);
		int c1s = c1.getSize(0);

    // System.out.println("Cross p0 " + p0 + " s0 " + s0 + " d0 " + d0 +
    //  " p1 " + p1 + " s1 " + s1 + " d1 " + d1);

    // Check for depth constraint for p1 inserted into c0

    if (d0-1+s1>World.maxCrossoverDepth)
      c[0] = c1; // choose the other parent
    else {
			c[0] = new Chromosome(c0s-s0+s1, c[0].functionSet, c[0].argTypes);
			System.arraycopy(c0.functions, 0, c[0].functions, 0, p0);
      System.arraycopy(c1.functions, p1, c[0].functions, p0, s1);
      System.arraycopy(c0.functions, p0+s0, c[0].functions, p0+s1,
        c0s-p0-s0);
      c[0].redepth();
    }

    // Check for depth constraint for p0 inserted into c1

    if (d1-1+s0>World.maxCrossoverDepth)
      c[1] = c0; // choose the other parent
    else {
      c[1] = new Chromosome(c1s-s1+s0, c[1].functionSet, c[1].argTypes);
      System.arraycopy(c1.functions, 0, c[1].functions, 0, p1);
      System.arraycopy(c0.functions, p0, c[1].functions, p1, s0);
      System.arraycopy(c1.functions, p1+s1, c[1].functions, p1+s0,
        c1s-p1-s1);
      c[1].redepth();
    }

    return c;
  }

}
