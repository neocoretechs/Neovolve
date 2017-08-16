package com.neocoretechs.neovolve;

import java.io.*;
import com.neocoretechs.neovolve.functions.*;

/**
 * The 11-multiplexer problem from Koza[1992], to find a formula to choose
 * one of eight boolean inputs based on the values of three other boolean inputs.
 * Uses a population size of 4000 with greedy overselection, and runs for 50 generations.
 * <p>
 * Note: This is where I found the need to optimize fitness calculations. By running this
 * program under the Java profiler (i.e. java -Xprof) I was able to determine where a majority
 * of the run time was being taken up. Originally the accesses to
 * {@link Variable Variable} were the most time-consuming,
 * so I optimized Variable. Now the majority of time is taken by the code of
 * {@link #computeRawFitness computeRawFitness}, which I can't seem to get down any further.
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: MultiplexerWorld.java,v 1.3 2000/10/12 15:22:55 groovyjava Exp $
 */
public class MultiplexerWorld extends World {

  // This is so that we don't have to use the Variable's Hashtable to get
  // values all the time, which is time-consuming.

  Variable[] vars = new Variable[11];

  public MultiplexerWorld() throws IOException {

    selectionMethod = new GreedyOverselection();

  }

  public void create() {

    Class[] types = { Function.booleanClass };
    Class[][] argTypes = { {} };
    Function[][] nodeSets = {
      {
        vars[0]=Variable.create("D0", Function.booleanClass),
        vars[1]=Variable.create("D1", Function.booleanClass),
        vars[2]=Variable.create("D2", Function.booleanClass),
        vars[3]=Variable.create("D3", Function.booleanClass),
        vars[4]=Variable.create("D4", Function.booleanClass),
        vars[5]=Variable.create("D5", Function.booleanClass),
        vars[6]=Variable.create("D6", Function.booleanClass),
        vars[7]=Variable.create("D7", Function.booleanClass),
        vars[8]=Variable.create("A0", Function.booleanClass),
        vars[9]=Variable.create("A1", Function.booleanClass),
        vars[10]=Variable.create("A2", Function.booleanClass),
        new And(),
        new Or(),
        new Not(),
        new If(Function.booleanClass)
      }
    };

    create(4000, types, argTypes, nodeSets);
  }

  public float computeFitness(Individual ind) {
    return 1.0f/(1.0f+(2048.0f-computeRawFitness(ind)));
  }

  public float computeRawFitness(Individual ind) {
    int hits = 0;
    int mask = 1;
    int bit;

    for (int addr=0; addr<8; addr++, mask<<=1) {
      vars[8].set( (addr&1)==0 ? Boolean.FALSE : Boolean.TRUE );
      vars[9].set( (addr&2)==0 ? Boolean.FALSE : Boolean.TRUE );
      vars[10].set( (addr&4)==0 ? Boolean.FALSE : Boolean.TRUE );

      // Set the variables to their proper values based on i. We check
      // to see if a given variable needs to change, and we don't change
      // it if it doesn't need to be changed. This is for efficiency.

      for (int data=0; data<256; data++) {
        bit=0;
        for (int dmask=1; (dmask&0x100)==0; dmask<<=1, bit++)
          if (data==0 || (data&dmask)!=((data-1)&dmask))
            vars[bit].set( (data&dmask)==0 ? Boolean.FALSE : Boolean.TRUE );

        if (ind.execute_boolean(0, World.noargs)==((data&mask)!=0))
          hits++;
      }
    }
    return (float)hits;
  }

	public void output(Individual ind) {
	}
  public float computeExecutionErrorFitness(Exception ex) { return 0.0F; }

  public static void main(String[] args) {

		try {
			World world = new MultiplexerWorld();

			world.create();
			world.run(50);
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
  }
}
