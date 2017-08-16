package com.neocoretechs.neovolve;

import java.util.*;
import java.io.*;

/**
 * Selects individuals proportionally according to their adjusted fitness.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: FitnessProportionateSelection.java,v 1.3 2000/10/12 15:22:55 groovyjava Exp $
 */

public class FitnessProportionateSelection extends SelectionMethod implements Serializable {

  public Individual select(World world) {

    float chosen = World.random.nextFloat()*world.getTotalFitness();
    // float origChosen = chosen;
    int num=0;
    Population pop = world.getPopulation();
    // int popSize = pop.individuals.length;
    
    num = Arrays.binarySearch(pop.fitnessRank, chosen);
    
    if (num>=0)
        return pop.getIndividual(num);
    else
        return pop.getIndividual(-num-2);
/*
    for (num=1; num<popSize; num++)
      if (chosen < pop.fitnessRank[num])
        break;
    num--;
/*
/*
    for (num=0; chosen>=0 && num<popSize; num++)
      try {
        chosen -= world.getPopulation().getIndividual(num).getFitness();
      } catch (ArrayIndexOutOfBoundsException ex) {
        System.out.println("FitnessProportionateSelection:");
        System.out.println("pop size = " + popSize);
        System.out.println("num = " + num);
        System.out.println("chosen = " + chosen);
        System.out.println("origChosen = " + origChosen);
        System.out.println("World fitness = " + world.getTotalFitness());
        throw ex;
      }

    if (num>=popSize) // Can happen if chosen==totalFitness
      num=popSize-1;
*/

//    return world.getPopulation().getIndividual(num);
  }
}
