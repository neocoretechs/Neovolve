package com.neocoretechs.neovolve;
import com.neocoretechs.neovolve.functions.*;
import java.io.*;

/**
 * The same as MultiplexerWorld, except uses two ADF's with three arguments
 * each. As in MultiplexerWorld, chromosome 0 is the result-producing branch.
 *
 * <P>
 * Copyright (c) 2000 Robert Baruch. This code is released under
 * the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a> (GPL).<p>
 *
 * @author Robert Baruch (jgprog@sourceforge.net)
 * @version $Id: MultiplexerADFWorld.java,v 1.3 2000/10/12 15:22:55 groovyjava Exp $
 */
public class MultiplexerADFWorld extends MultiplexerWorld {

  // Use 2 ADF's with 3 args each

  public MultiplexerADFWorld() throws IOException {
  }

  public void create() {

    Class[] types = { Function.booleanClass, Function.booleanClass, Function.booleanClass };
    Class[][] argTypes = {
      {},
      { Function.booleanClass, Function.booleanClass, Function.booleanClass },
      { Function.booleanClass, Function.booleanClass, Function.booleanClass },
    };
    Function[][] nodeSets = {
      { // result-producing chromosome (0)
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
        new ADF(1 /*, argTypes[1] */),
        new ADF(2 /*, argTypes[2] */),
        new And(),
        new Or(),
        new Not(),
        new If(Function.booleanClass)
      },
      { // chromosome 1
        new And(),
        new Or(),
        new Not(),
        new If(Function.booleanClass)
      },
      { // chromosome 2
        new And(),
        new Or(),
        new Not(),
        new If(Function.booleanClass)
      }
    };

    create(4000, types, argTypes, nodeSets);
  }

	public void output(Individual ind) {
	}

  public static void main(String[] args) {

		try {
			World world = new MultiplexerADFWorld();

			world.create();
			world.run(50);
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
  }
}
