package com.neocoretechs.neovolve;

import java.io.*;
import java.net.*;
import java.util.*;

import com.neocoretechs.powerspaces.Packet;
import com.neocoretechs.powerspaces.PowerSpace;
import com.neocoretechs.powerspaces.PowerSpaceException;
import com.neocoretechs.powerspaces.server.CustomerConnectionPanel;
import com.neocoretechs.powerspaces.server.PowerPlant;
import com.neocoretechs.powerspaces.server.handler.PKParallel;
/**
 * PowerKernel module for Neovolve. It receives a World,
 * computes the fitnesses of some portion of that, and returns those fitnesses.
 */
public class NeovolveKernel {
  public static void PowerKernel_Neovolve(Integer leg, CustomerConnectionPanel ccp, Packet p) throws PowerSpaceException, ClassNotFoundException, IOException {
        World world = (World)(p.getField(0).value());
        Integer numNodes = (Integer)(p.getField(1).value());
        ccp.queueReturnPacket(Neovolve(world, numNodes));
  }

  public static Packet Neovolve(World world, Integer numNodes) throws PowerSpaceException, ClassNotFoundException, IOException {
        int indChunks = world.getPopulation().getSize() / numNodes.intValue();
        int ifrom = (int)(PowerPlant.clusterID - 1) * indChunks;
        int ito;
        if( PowerPlant.clusterID == numNodes.intValue() )
                ito = world.getPopulation().getSize();
        else
                ito = (int)(PowerPlant.clusterID * indChunks);
        System.out.println("Neovolve will compute " + ifrom + "-" +
                (ito-1) + " individuals of "+world.getPopulation().getSize());
        int numInd = (ito - ifrom);
        long t = world.computeSome(ifrom, ito);
        Individual idum = null;
        world.output(idum);
        float[] fitnesses = new float[numInd];
        for (int i=0; i<numInd; i++)
                fitnesses[i] = world.getPopulation().getIndividual(ifrom+i).getFitness();
        return new Packet(new Object[]{new int[]{ifrom,ito}, fitnesses});
  }
  public static void NeovolveRoot(World world, Integer numNodes) throws PowerSpaceException, ClassNotFoundException, IOException {
        int indChunks = world.getPopulation().getSize() / numNodes.intValue();
        int ifrom = 0;
        int ito;
        if( numNodes.intValue() == 1)
                ito = world.getPopulation().getSize();
        else
                ito = indChunks;
        System.out.println("Neovolve will compute " + ifrom + "-" +
                (ito-1) + " individuals of "+world.getPopulation().getSize());
        int numInd = (ito - ifrom);
        long t = world.computeSome(ifrom, ito);
        Individual idum = null;
        world.output(idum);
  }

  /*
  public static Object PowerKernel_CreateWorld(Integer leg, CustomerConnectionPanel ccp, Integer numRuns) {
                try  {
                       long st = System.currentTimeMillis();
                       // get number of nodes
                       PKParallel.PowerKernel_BroadcastAll(leg, ccp,
                                new Packet("com.neocoretechs.powerspaces.server.handler.PSIPCHandler","Ping", new Packet()));
                       Packet ppp = (Packet)(PKParallel.PowerKernel_Collect(leg, ccp));
                       Vector v = new Vector();
                       PowerSpace.unwindPacket(ppp, v);
                       int numNodes = v.size();
                       System.out.println("Cluster ping time "+String.valueOf(System.currentTimeMillis()-st)+" ms. "+numNodes +" nodes");
                       World w = new TestWorld();
                       w.setCluster(numNodes, leg, ccp);
                       w.create();
                       w.run(numRuns.intValue());
                } catch(Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                }
                return "Ok";

  }
  */
  public static Object PowerKernel_CreateWorld(Integer leg, CustomerConnectionPanel ccp, String inFile, String outFile ) {
                try  {
                       long st = System.currentTimeMillis();
                       // get number of nodes
                       PKParallel.PowerKernel_BroadcastAll(leg, ccp,
                                new Packet("com.neocoretechs.powerspaces.server.handler.PSIPCHandler","Ping", new Packet()));
                       Packet ppp = (Packet)(PKParallel.PowerKernel_Collect(leg, ccp));
                       Vector v = new Vector();
                       PowerSpace.unwindPacket(ppp, v);
                       int numNodes = v.size();
                       System.out.println("Cluster ping time "+String.valueOf(System.currentTimeMillis()-st)+" ms. "+numNodes +" nodes");
                       XGPWorld.cluster(numNodes, leg, ccp, new String[]{inFile, outFile});
                } catch(Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                }
                return "Ok";

  }
}
