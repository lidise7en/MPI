package Test;

import java.util.ArrayList;

import mpi.MPI;
import mpi.MPIException;
import parallel.Master;
import parallel.Slave;
import util.DNA;
import util.DNAGen;
import Interface.KMCluster;
import Interface.KMNum;
import constant.Constants;

public class DNATest {

    public DNATest() {
    }

    public static void main(String[] args) {
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
