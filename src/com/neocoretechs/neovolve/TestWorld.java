package com.neocoretechs.neovolve;
import com.neocoretechs.neovolve.functions.*;
import com.neocoretechs.neovolve.objects.*;
import com.neocoretechs.neovolve.instruments.*;
import com.neocoretechs.bigsack.session.BufferedTreeSet;

import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.io.*;
/**
* This world uses a slightly modified jgprog.  Original needs functions
* that match variable types or exception is thrown.  This one will
* toss out structurally unfit individuals at creation time, from thereon crossover
* rules enforce structural integrity.  The computeExecutionErrorFitness method
* was added and is invoked when evolving code throws exceptions, the intent
* being to reduce fitness proportionately.
* We modified the create method in Population to trap the exception normally
* thrown and create a new individual that may work instead.<br>
* We assume all we know about are types of variables and return type.
* This moves us closer toward a true automatic programming paradigm.
* Ultimately, we want to create a framework for XGP, eXtreme Genetic
* Programming, where the unit test is the fitness function.
* We have several test cases here that might model unit tests that
* are interfaced with the computeRawFitness function.<p>
* Most functionality occurs in superclass.
* @author Groff 1/2003 Copyright 2003, 2014
*/ 
public class TestWorld extends World implements Serializable {
        static final long serialVersionUID = -1445204961930460866L;
        int MaxSteps = 50;
        int TestsPerStep = 6;
        float MinRawFitness = (float)(MaxSteps * TestsPerStep);
        int hits;
        boolean isSet = false;
        // initialize number of variables to use
        Variable[] vars = new Variable[1];
        Object[] argVals;
        //int zernum;
        //String zers;
        transient BufferedTreeSet psh;
        /**
        * Set selection method, now GreedyOverselection
        */
        public TestWorld() throws IOException {
                 selectionMethod = new GreedyOverselection();
                 try {
                        psh = new BufferedTreeSet("neovolve",10);
                        ((List) psh).clear();
                 } catch(IllegalAccessException iae) {}
        }
        /**
        * Create the population.  Here, we are just splooging some
        * possible functions.  In the regular version of jgprog, this would bomb
        * as it does not throw out individuials with illegal structure.
        */
        public void create() {
                // types we are using, type of each chromosome
                // the length of this array determines the # of chromosomes per
                // individual
                //Class[] types = {Function.integerClass};
                // return type for string functions
                Class[] types = {Strings.stringClass};
                // Return type for ArrayLists (i.e. all pairs)
                // Class[] types = {ArrayLists.arrayListClass};
                // arguments to each chromosome, creates an "argument" function ARG0..ARGN 
                //Class[][] argTypes = { {ArrayLists.arrayListClass} };
                Class[][] argTypes = { {Strings.stringClass, Strings.stringClass} };
                argVals = new Object[2];
 
                // define variables and functions that determine the domain
                // of our problem space
                /*
                Function[][] nodeSets = {
                        {
                        // our set of working vars
                        
                        vars[0]=Variable.create("S0", Strings.stringClass),
                        vars[4]=Variable.create("A0", ArrayLists.arrayListClass),
                        
                        vars[0]=ContainedVariable.create("C0", Strings.stringClass),
                        }
               };
               */
               Vector v = new Vector();
               v.addElement(Strings.stringClass);
               Function[][] nodeSets = NodeSets.getNodeSets(v);
               maxInitDepth = 7;
               maxCrossoverDepth = 128;
               maxSize = 1024;
               create(20000, types, argTypes, nodeSets);
       }

        public void create(int populationSize, Vector vreturnTypes, Vector vargTypes, Vector allClasses) {
               Function[][] nodeSets = NodeSets.getNodeSets(allClasses);
               Class[] returnTypes = new Class[vreturnTypes.size()];
               Object[] vr = vreturnTypes.toArray(returnTypes);
               System.arraycopy(vr, 0, returnTypes, 0, vreturnTypes.size());
               Class[][] argTypes = new Class[1][vargTypes.size()];
               System.arraycopy(vargTypes.toArray(), 0, argTypes[0], 0, vargTypes.size());
               argVals = new Object[vargTypes.size()];
               maxInitDepth = 7;
               maxCrossoverDepth = 128;
               maxSize = 1024;
               create(populationSize, returnTypes, argTypes, nodeSets);
       }
       /*
       * Standard fitness calc from Koza 1/(1+s) where s is standardized fitness
       * where lesser vals are fitter and 0 is perfect
       */
       public float computeFitness(Individual ind) {
                return 1.0F/(1.0F+computeRawFitness(ind));
       }

        /**
        * Add one
        */
        /*
        public float computeRawFitness(Individual ind) {
                hits = 0;
                setStepFactors(100,1);
                // unit test
                // each time this individual passes, up the hits
                for(int i = 0; i < MaxSteps; i++) {
                        vars[0].set(new Integer(i));
                        int j = ind.execute_int(0, World.noargs);
                        //System.out.println(i+" "+j);
                        if( j == i+1 ) ++hits;
                }
                //System.out.println("Hits: "+hits);
                return (MinRawFitness - (float)(hits));
        }
        */
        /**
        * X**2 + 2
        */
        /*
        public float computeRawFitness(Individual ind) {
                hits = 0;
                setStepFactors(100, 1);
                int j;
                // unit test
                for(int i = 0; i < MaxSteps; i++) {
                        vars[0].set(new Integer(i));
                        vars[1].set(new Integer(i));
                        j = ind.execute_int(0, World.noargs);
                        //System.out.println(i+" "+j);
                        if( j == ((i*i)+2) ) ++hits;
                }
                //System.out.println("Hits: "+hits);
                return (MinRawFitness - (float)(hits));
        }
        */
        /**
        * X * Y + 2
        * In the unit test we set 2 variables with our test values and
        * If the evolved code comes back and solves X * Y + 2,
        * the fitness is upped
        */
        /*
        public float computeRawFitness(Individual ind) {
                hits = 0;
                setStepFactors(400, 1);
                int j;
                // unit test
                for(int i = 0; i < 20; i++) {
                        vars[0].set(new Integer(i));
                        for(int k = 0 ; k < 20; k++) {
                                vars[1].set(new Integer(k));
                                j = ind.execute_int(0, World.noargs);
                                //j = ind.execute_int(0, new Object[]{Function.integerClass});
                                // up the hits if solved for this test case
                                if( j == ((i*k)+2) ) ++hits;
                        }
                }
                //System.out.println("Hits: "+hits);
                return (MinRawFitness - (float)(hits));
        }
        */
        /**
        * Bit more complex: if X < Y add 1 to X else subtract 1 from X
        * In the unit test we set 2 variables with our test values and
        * If the evolved code comes back and solves it,
        * the fitness is upped
        */
        /*
        public float computeRawFitness(Individual ind) {
                hits = 0;
                setStepFactors(2500, 1);
                int j;
                // unit test
                for(int i = 0; i < 50; i++) {
                        vars[0].set(new Integer(i));
                        for(int k = 0 ; k < 50; k++) {
                                vars[1].set(new Integer(k));
                                j = ind.execute_int(0, World.noargs);
                                // up the hits if solved for this test case
                                if( i < k ) {
                                        if( j == i+1)  ++hits;
                                }
                                else {
                                        if( j == i-1)  ++hits;
                                }
                        }
                }
                //System.out.println("Hits: "+hits);
                return (MinRawFitness - (float)(hits));
        }
        */
        /**
        * String test
        * If the evolved code comes back and solves it,
        * the fitness is upped
        */
        /*
        public float computeRawFitness(Individual ind) {
                hits = 0;
                setStepFactors(2500, 1);
                // unit test
                for(int i = 0; i < 50; i++) {
                        vars[0].set(new Strings("A:"+String.valueOf(i)));
                        for(int k = 0 ; k < 50; k++) {
                                vars[1].set(new Strings("B:"+String.valueOf(k)));
                                Strings j = (Strings)(ind.execute_object(0, World.noargs));
                                // up the hits if solved for this test case
                                Strings res = new Strings("A:"+String.valueOf(i)+"B:"+String.valueOf(k));
                                if( j.data.equals(res.data) )  ++hits;
                        }
                }
                //System.out.println("Hits: "+hits);
                return (MinRawFitness - (float)(hits));
        }
        */
        /**
        * Substring test
        * If the evolved code comes back and solves it,
        * the fitness is upped
        */
        /*
        public float computeRawFitness(Individual ind) {
                hits = 0;
                setStepFactors(50, 1);
                // unit test
                for(int i = 0; i < 50; i++) {
                        vars[0].set(new Strings("0000"+String.valueOf(i)));
                        Strings j = (Strings)(ind.execute_object(0, World.noargs));
                        if( j.data.equals(String.valueOf(i)) )
                                  ++hits;
                }
                //System.out.println("Hits: "+hits);
                return (MinRawFitness - (float)(hits));
        }
        */
        /**
        * We attempt to evolve a function that removes leading zeroes from a string.  
	* We loop an int and inject the zeroes on front of the string form<br>
        * We then compare the string form with the value returned from
        * the function to see if it matches original i.e. if it stripped the zeros
        */
        /*
        public float computeRawFitness(Individual ind) {
                hits = 0;
                setStepFactors(50, 5);
                // unit test
                for(int i = 0; i < 50; i++) {
                        //vars[0].set(new Strings(zers+String.valueOf(i)));
                        vars[0].set(new Strings("0"+String.valueOf(i)));
                        Strings j = (Strings)ind.execute_object(0, World.noargs);
                        vars[0].set(new Strings("00"+String.valueOf(i)));
                        Strings k = (Strings)ind.execute_object(0, World.noargs);
                        vars[0].set(new Strings("000"+String.valueOf(i)));
                        Strings l = (Strings)ind.execute_object(0, World.noargs);
                        vars[0].set(new Strings("0000"+String.valueOf(i)));
                        Strings m = (Strings)ind.execute_object(0, World.noargs);
                        vars[0].set(new Strings("00000"+String.valueOf(i)));
                        Strings n = (Strings)ind.execute_object(0, World.noargs);
                        if( j.data.equals(String.valueOf(i)) ) ++hits;
                        if( k.data.equals(String.valueOf(i)) ) ++hits;
                        if( l.data.equals(String.valueOf(i)) ) ++hits;
                        if( m.data.equals(String.valueOf(i)) ) ++hits;
                        if( n.data.equals(String.valueOf(i)) )
                                  ++hits;
                }
                //System.out.println("Hits: "+hits);
                return (MinRawFitness - (float)(hits));
        }
        */
        /**
        * In this test case we are attaching leading zeros to the string value of
        * a number and comparing the result of the evolved code to the string
        * value of the original number. The value returned from the evolved code
        * and the original number have to be the same to have stripped the leading zeros.
        * The code to not only has to return the original value when there are no
        * leading zeros, but return a 0 if the input string consists exclusively
        * of one of more zeros.
        */
        
        public float computeRawFitness(Individual ind) {
                hits = 0;
                //vars[1].set(new Strings("0"));
                setStepFactors(50, 6);
                Strings j=null,k=null,l=null,m=null,n=null,o=null;
                // unit test
                for(int i = 0; i < MaxSteps; i++) {
                        //vars[0].set(new Strings(zers+String.valueOf(i)));
                        argVals[0] = new Strings("0");
                        argVals[1] = new Strings(String.valueOf(i));
                        j = (Strings)ind.execute_object(0, argVals);
                        argVals[1] = new Strings("0"+String.valueOf(i));
                        k = (Strings)ind.execute_object(0, argVals);
                        argVals[1] = new Strings("00"+String.valueOf(i));
                        l = (Strings)ind.execute_object(0, argVals);
                        argVals[1] = new Strings("000"+String.valueOf(i));
                        m = (Strings)ind.execute_object(0, argVals);
                        argVals[1] = new Strings("0000"+String.valueOf(i));
                        n = (Strings)ind.execute_object(0, argVals);
                        argVals[1] = new Strings("00000"+String.valueOf(i));
                        o = (Strings)ind.execute_object(0, argVals);
                        if( j.data.equals(String.valueOf(i)) ) ++hits;
                        if( k.data.equals(String.valueOf(i)) ) ++hits;
                        if( l.data.equals(String.valueOf(i)) ) ++hits;
                        if( m.data.equals(String.valueOf(i)) ) ++hits;
                        if( n.data.equals(String.valueOf(i)) ) ++hits;
                        if( o.data.equals(String.valueOf(i)) ) ++hits;
                }
                float rawFit = (MinRawFitness - (float)(hits));
                if(DEBUG) {
                        System.out.println(j.data);
                        System.out.println(k.data);
                        System.out.println(l.data);
                        System.out.println(m.data);
                        System.out.println(n.data);
                        System.out.println(o.data);
                        checkAndStore(rawFit, ind);
                }
                //System.out.println("Hits: "+hits);
                return rawFit;
        }

        protected void checkAndStore(float rawFit, Individual ind) {
                try {
                        if( rawFit == 0.0F ) {
                        	System.out.println("Storing..."+ind.hashCode());
                            psh.add((Comparable) ind);
                        }
                } catch(IOException ioe) {
                        System.out.println("Persistent storage subsystem failed to store individual "+ind);
                }
        }

        /**
        * We attempt to evolve a function that removes a random
        * number of leading zeroes from a string.  We loop an int
        * and inject the random zeroes on front of the string form<br>
        * We then compare the string form with the value returned from
        * the function.  This means that not only does it need to strip
        * zeroes, but if the value is 0 return a string 0, dont just strip zeroes.
        * Sorry this didnt work, cant optimize to a moving target
        */
        /*
        public float computeRawFitness(Individual ind) {
                hits = 0;
                setStepFactors(50, 1);
                // unit test
                if( ind.getSequence() == 0 ) {
                        zernum = new Random().nextInt(5);
                        zers = "000000".substring(zernum);
                        System.out.println("Zeroes "+zers);
                }
                for(int i = 0; i < MaxSteps; i++) {
                        vars[0].set(new Strings(zers+String.valueOf(i)));
                        Strings n = (Strings)ind.execute_object(0, World.noargs);
                        if( n.data.equals(String.valueOf(i)) )
                                  ++hits;
                }
                //System.out.println("Hits: "+hits);
                return (MinFitnessValue - (float)(hits));
        }
        */
        /**
        * No way, this target really moves
        /*
        public float computeRawFitness(Individual ind) {
                hits = 0;
                setStepFactors(50, 1);
                // unit test
                for(int i = 0; i < MaxSteps; i++) {
                        zernum = new Random().nextInt(5);
                        zers = "000000".substring(zernum);
                        vars[0].set(new Strings(zers+String.valueOf(i)));
                        Strings n = (Strings)ind.execute_object(0, World.noargs);
                        if( n.data.equals(String.valueOf(i)) )
                                  ++hits;
                }
                //System.out.println("Hits: "+hits);
                return (MinRawFitness - (float)(hits));
        }
        */
        /**
        * All pairs problem.  Start with matrix
        * a b x
        * c d
        * y
        * we desire the result to be a set of pairs
        * (a,b) (a,d) (a,x) (c,b) (c,d) (c,x) (y,b) (y,d) (y,x)
        */
        /*
        public float computeRawFitness(Individual ind) {
                hits = 0;
                if( !isSet ) {
                        isSet = true;
                        setStepFactors(1, 18);
                        ArrayLists a1 = new ArrayLists();                
                        vars[0].set(a1);
                        a1.data.add(new Strings("a"));
                        a1.data.add(new Strings("b"));
                        a1.data.add(new Strings("x"));
                        a1.data.add(new Strings("c"));
                        a1.data.add(new Strings("d"));
                        a1.data.add(new Strings("y"));
                        argVals[0] = a1;
                }
                ArrayLists al = (ArrayLists)ind.execute_object(0, argVals);
                if( al.data.size() > 0 ) {
                        pairsok(al, 0, "a", "b"); 
                        //if( al.data.size() > 1 ) {
                        if( al.data.size() > 2 ) {
                                pairsok(al, 1, "a", "d");
                                //if( al.data.size() > 2 ) {
                                if( al.data.size() > 4 ) {
                                        pairsok(al, 2, "a", "x");
                                        //if( al.data.size() > 3 ) {
                                        if( al.data.size() > 6 ) {
                                                pairsok(al, 3, "c", "b");
                                                if( al.data.size() > 8 ) {
                                                        pairsok(al, 4, "c", "d");
                                                        if( al.data.size() > 10 ) {
                                                                pairsok(al, 5, "c", "x");
                                                                if( al.data.size() > 12 ) {
                                                                        pairsok(al, 6, "y", "b");
                                                                        if( al.data.size() > 14 ) {
                                                                                pairsok(al, 7, "y", "d");
                                                                                if( al.data.size() > 16 ) {
                                                                                        pairsok(al, 8, "y", "x");
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
                //if( al.data.size() == 1 && ((Strings)(al.data.get(0))).data.equals("d")) hits = 10; // test
                float rawFit = (MinRawFitness - (float)(hits));
                if( DEBUG ) {
                        for(int i = 0; i < al.data.size(); i++ )
                                System.out.print(((Strings)(al.data.get(i))).data+" ");
                        System.out.println();
                        try {
                                if( rawFit == 0.0F ) {
                                        System.out.println("Storing..."+ind.hashCode());
                                        psh.add(ind);
                                }
                        } catch(IOException ioe) {
                                System.out.println("Persistent storage subsystem failed to stor individual");
                        }
                }
                return rawFit;
        }
        */
        /**
        * All pairs helper method
        */
        /*
        private void pairsok(ArrayLists al, int elem, String pair1, String pair2) {
                Object r1 = al.data.get(elem);
                if( r1 instanceof ArrayLists ) {
                                if( ((ArrayLists)r1).data.size() == 2 ) {
                                        Object r11 = ((ArrayLists)r1).data.get(0);
                                        Object r12 = ((ArrayLists)r1).data.get(1);
                                        if( r11 instanceof Strings && r12 instanceof Strings) {
                                                if( ((Strings)r11).data.equals(pair1) &&
                                                    ((Strings)r12).data.equals(pair2) ) ++hits;
                                        }
                                }
                }
        }
        */
        private void pairsok(ArrayLists al, int elem, String pair1, String pair2) {
                Object r1 = al.data.get(elem*2);
                Object r2 = null;
                if( al.data.size() > elem*2+1)
                        r2 = al.data.get(elem*2+1);
                else
                        return;
                if( r1 instanceof Strings && ((Strings)r1).data.equals(pair1) ) ++hits;
                if( r2 instanceof Strings && ((Strings)r2).data.equals(pair2) ) ++hits;
        }
        /**
        * optional; superclass displays best result
        */
        public void output(Individual ind) {
                //System.out.println(ind);
        }
        /**
        * Adjust raw fitness based on execption condition
        * @param ex The exception thrown from evolving code
        * @return The cooked fitness
        */
        public float computeExecutionErrorFitness(Exception ex) {
                //System.out.println(ex+" "+hits);
                return 1.0F/(1.0F+(MinRawFitness - (float)(hits)));                
        }
        /**
        * Set the step factors for tests.<br>
        * MaxRawFitness = isteps * testStep
        * @param isteps The number of steps in the test
        * @param testStep  The number of tests per step
        */
        private void setStepFactors(int isteps, int testStep) {
                MaxSteps = isteps;
                int TestsPerStep = testStep;
                MinRawFitness = (float)(MaxSteps * TestsPerStep);
        }
        /**
        * Create this world and run the generations converging on
        * best computeFitness
        */
        public static void main(String[] args) {
                try {
                        World w = new TestWorld();
                        w.create();
                        w.run(500);
                } catch(Exception e) {
                        System.out.println(e);
                        e.printStackTrace();
                }
        }
}
