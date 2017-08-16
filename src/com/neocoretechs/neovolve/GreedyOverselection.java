package com.neocoretechs.neovolve;

import java.util.Random;
import java.io.*;

/**
 * Selects individuals according to the Greedy Over-Selection method.
 * This method fitness-proportionally chooses a group I individual 80% of the time,
 * and a group II individual 20% of the time. Group I consists of all
 * individuals contributing towards the top c% of total fitness, where
 * c=32% below a population size of 2000, c=16% between 2000 and 4000,
 * c=8% between 4000 and 8000, and c=4% for population sizes 8000 and above.
 * Group II consists of all other individuals.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: GreedyOverselection.java,v 1.3 2000/10/12 15:22:55 groovyjava Exp $
 */
public class GreedyOverselection extends SelectionMethod implements Serializable {

  public Individual select(World world) {

    // Determine where group I starts.

    float sum=0.0f;
    int popSize = world.getPopulation().getSize();
    float cutoff =
      popSize<2000 ? 0.32f :
      popSize<4000 ? 0.16f :
      popSize<8000 ? 0.08f : 0.04f;
    float origChosen;
    float chosen;

    // cutoff is the amount of total fitness in group I

    cutoff *= world.getTotalFitness();

    // Suppose the fitnesses are 1 1 1 1 2 2 2 3 3 4 6. Then the total fitness
    // is 26. Suppose that we choose c=32%. 26*0.32=8.32 is the cutoff
    // fitness. Adding fitnesses from the end,
    // we would get a sum of 6, then 6+4=10. Since 10>8.32, we would stop
    // and set the cutoff index to the current element + 1, so that group I is the
    // last element and group II is all others.
    //
    // Even if the cutoff fitness were exactly 6 (or more generally, exactly
    // the sum of the fitnesses from the end so far), we would still have to
    // add the next fitness for the running sum to exceed the cutoff.
    //
    // There should never be a case where there aren't any individuals in
    // group I since the populations are large enough.

    int cutoffIndex=popSize-1;
    while (sum<=cutoff) // <= is the negation of >
      sum+=world.getPopulation().getIndividual(cutoffIndex--).getFitness();
    cutoffIndex++;
    sum -= world.getPopulation().getIndividual(cutoffIndex-1).getFitness();

    // Readjust the cutoff fitness to be exactly enough fitness. In the
    // above example, we'd readjust to 6 instead of keeping 8.32.

    cutoff = sum;

    // Now from 0 to cutoffIndex-1 contains group II, and
    // from cutoffIndex to popSize-1 contains group I.

    if (World.random.nextFloat()<0.2) { // Choose a group II individual

      chosen = World.random.nextFloat()*(world.getTotalFitness()-cutoff);
      origChosen = chosen;

      int num=0;

      for (num=0; chosen>=0 && num<cutoffIndex-1; num++)
        try {
          chosen -= world.getPopulation().getIndividual(num).getFitness();
        } catch (ArrayIndexOutOfBoundsException ex) {
          System.out.println("Group II:");
          System.out.println("pop size = " + world.getPopulation().getSize());
          System.out.println("num = " + num);
          System.out.println("chosen = " + chosen);
          System.out.println("origChosen = " + origChosen);
          System.out.println("cutoff = " + cutoff);
          System.out.println("cutoffIndex = " + cutoffIndex);
          System.out.println("World fitness = " + world.getTotalFitness());
          throw ex;
        }

      if (num>=cutoffIndex-1) // Can happen if chosen==totalFitness-cutoff
        num=cutoffIndex-1;

      return world.getPopulation().getIndividual(num);

    } else {

      chosen = World.random.nextFloat()*cutoff;
      origChosen = chosen;

      int num;

      for (num=cutoffIndex; chosen>=0 && num<popSize; num++)
        try {
          chosen -= world.getPopulation().getIndividual(num).getFitness();
        } catch (ArrayIndexOutOfBoundsException ex) {
          System.out.println("Group I:");
          System.out.println("pop size = " + world.getPopulation().getSize());
          System.out.println("num = " + num);
          System.out.println("chosen = " + chosen);
          System.out.println("origChosen = " + origChosen);
          System.out.println("cutoff = " + cutoff);
          System.out.println("cutoffIndex = " + cutoffIndex);
          System.out.println("World fitness = " + world.getTotalFitness());
          throw ex;
        }

      if (num>=popSize-1) // Can happen if chosen==cutoff
        num=popSize-1;

      return world.getPopulation().getIndividual(num);
    }

  }

}