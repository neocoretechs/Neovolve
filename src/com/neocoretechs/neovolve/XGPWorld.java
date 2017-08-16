package com.neocoretechs.neovolve;
import com.neocoretechs.powerspaces.server.CustomerConnectionPanel;
import com.neocoretechs.neovolve.fit.*;
import java.lang.reflect.InvocationTargetException;
import java.io.*;
/**
* A true automatic programming paradigm.
* A framework for XGP, eXtreme Genetic
* Programming, where the unit test is the fitness function.
* @author Groff 1/2003
*/ 
public class XGPWorld extends TestWorld implements Serializable {
        //static final long serialVersionUID = -1445204961930460866L;
        public Fixture fixture;
        public Parse parse;
        public String input;
        public transient static PrintWriter output;
        public int globalRight, globalWrong, globalExceptions;

        public XGPWorld() throws IOException {
                super();
        }
        /**
        */
        public float computeRawFitness(Individual ind) {
                hits = 0;
                MinRawFitness = 0;
                ((XGPFixture)fixture).setIndividual(ind);
                try {
                        parse = new Parse(input);
                } catch(java.text.ParseException pex) {}
                fixture.doTable(parse);
                MinRawFitness = (float)(fixture.counts.right + fixture.counts.wrong + fixture.counts.exceptions);
                hits = fixture.counts.right;
                globalRight += fixture.counts.right;
                globalWrong += fixture.counts.wrong;
                globalExceptions += fixture.counts.exceptions;
                fixture.counts.right = fixture.counts.wrong = fixture.counts.exceptions = fixture.counts.ignores = 0;
                //System.out.println("Hits: "+hits);
                float rawFit = (MinRawFitness - (float)(hits));
                if( DEBUG ) {
                        checkAndStore(rawFit, ind);
                        parse.print(output);
                        output.flush();
                }
                return rawFit;
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

        // Utilities for command line exec

        public void process() {
                try {
                        parse = new Parse(input);
                        XGPFixture.doTables(parse, this);
                } catch (Exception e) {
                        exception(e);
                }
                parse.print(output);
        }

        public void args(String[] argv) {
                if (argv.length != 2) {
                        System.err.println("usage: java com.neocoretechs.neovolve.XGPWorld input-file output-file");
                        System.exit(-1);
                }
                File in = new File(argv[0]);
                File out = new File(argv[1]);
                //fixture.summary.put("input file", in.getAbsolutePath());
                //fixture.summary.put("input update", new Date(in.lastModified()));
                //fixture.summary.put("output file", out.getAbsolutePath());
                try {
                        input = read(in);
                        output = new PrintWriter(new BufferedWriter(new FileWriter(out)));
                } catch (IOException e) {
                        System.err.println(e.getMessage());
                        System.exit(-1);
                }
        }

        protected String read(File input) throws IOException {
                char chars[] = new char[(int)(input.length())];
                FileReader in = new FileReader(input);
                in.read(chars);
                in.close();
                return new String(chars);
        }

        public void exception(Exception e) {
                parse = new Parse("body","Unable to parse input. Input ignored.", null, null);
                while(e.getClass().equals(InvocationTargetException.class)) {
                        e =(Exception)(((InvocationTargetException)e).getTargetException());
                }
                final StringWriter buf = new StringWriter();
                e.printStackTrace(new PrintWriter(buf));
                parse.addToBody("<hr><font size=-2><pre>" + (buf.toString()) + "</pre></font>");
                parse.addToTag(" bgcolor=\"#ffffcf\"");
                parse.print(output);
                output.flush();
        }

        protected void exit() {
                output.close();
                System.err.println(fixture.counts());
                System.exit(fixture.counts.wrong + fixture.counts.exceptions);
        }
        /*
        public void output(Individual ind) {
                try {
                        parse = new Parse(input);
                        DEBUG = true;
                        fixture.doTable(parse);
                        DEBUG = false;
                        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("XGPout.html")));
                        parse.print(pw);
                        pw.flush();
                        pw.close();
                 } catch(Exception ex) {
                        System.out.println(ex);
                 }
        }
        */        
        public void output(Individual ind) {
                System.out.println(">>>>>right: "+globalRight+" wrong:"+globalWrong+" exceptions:"+globalExceptions+" Total fitness "+totalFitness);
                globalRight = globalWrong = globalExceptions = 0;
        }
        /**
        * Create this world and run the generations converging on
        * best computeFitness
        */
        public static void main(String[] argv) {
                XGPWorld xgp = null;
                try {
                        xgp = new XGPWorld();
                } catch(IOException ex) {
                        System.out.println(ex);
                        System.exit(1);
                }
                xgp.args(argv);
                xgp.process();
                xgp.exit();
        }
        /**
        * Create this world and run the generations converging on
        * best computeFitness in the cluster
        */
        public static void cluster(int numNodes, Integer leg, CustomerConnectionPanel ccp, String[] argv) throws IOException {
                XGPWorld xgp = new XGPWorld();
                xgp.setCluster(numNodes, leg, ccp);
                xgp.args(argv);
                xgp.process();
                xgp.exit();
        }

}
