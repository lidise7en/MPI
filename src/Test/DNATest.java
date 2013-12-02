package Test;

import java.util.ArrayList;

import mpi.MPI;
import mpi.MPIException;
import parallel.Master;
import parallel.Slave;
import util.DNA;
import util.DNACluster;
import util.DNAGen;
import Interface.KMCluster;
import Interface.KMNum;
import constant.Constants;

public class DNATest {

    public static void main(String[] args) {
        parallel(args);
    }

    /**
     * parallel test for DNA
     * 
     * @param args
     * @param dnaSet
     * @param answer
     * @param clusterSet
     * @param newCluster
     * @return
     */
    public static void parallel(String[] args) {
        try {
            MPI.Init(args);
            int size = MPI.COMM_WORLD.Size();
            int rank = MPI.COMM_WORLD.Rank();

            if (rank == 0) {
                System.out.println("This is a master!");
                // generate DNA test set and the answer ahead
                ArrayList<KMNum> dnaSet = new ArrayList<KMNum>();
                ArrayList<KMCluster> answer = DNAGen.DNAGenerator(dnaSet);

                // add to origin cluster
                ArrayList<KMCluster> clusterSet = DNAGen
                        .centroidsGen(new ArrayList<DNA>());
                ArrayList<KMCluster> newCluster = new ArrayList<KMCluster>();

                // clone the cluster to a new one and trans to sequential test
                for (KMCluster cluster : clusterSet) {
                    newCluster.add(new DNACluster((DNA) (((DNACluster) cluster)
                            .getCentroid()).clone()));
                }
                
                System.out.println("MPI Sequential DNA test begin!!\n");
                DNATestSeq.sequential(args, dnaSet, answer, newCluster);
                System.out.println("MPI Sequential DNA test end!!\n");
                

                System.out.println("MPI Parallel DNA test begin!!\n");
                // initialize diff
                ArrayList<Double> diff = new ArrayList<Double>();
                for (int i = 0; i < Constants.K; i++) {
                    diff.add(Constants.difference + 1);
                }

                // meat part of kMeans algorithm
                Master runningMaster = new Master(dnaSet, clusterSet, diff,
                        Constants.K, size);
                long startTime = System.currentTimeMillis();
                runningMaster.runMPI();

                // output result and validation
                long endTime = System.currentTimeMillis();
                Master.validate(runningMaster.clusters, answer);

                System.out.println("This process cost " + (endTime - startTime)
                        + "in Master");
                System.out.println("MPI Parallel DNA test end!!\n");
            } else {
                System.out.println("This is a Slave.");
                Slave runningSlave = new Slave(size);
                runningSlave.waitForCompletion();
            }
            MPI.Finalize();
        } catch (MPIException e) {
            System.out.println("We have MPI Exception!\n");
        }

    }
}
