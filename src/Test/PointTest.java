package Test;

import java.util.ArrayList;

import mpi.MPI;
import mpi.MPIException;
import parallel.Master;
import parallel.Slave;
import util.PointCluster;
import util.PointGen;
import util.PointTwoD;
import Interface.KMCluster;
import Interface.KMNum;
import constant.Constants;

public class PointTest {

    public static void main(String[] args) {
        // generate points
        ArrayList<KMNum> pointSet = new ArrayList<KMNum>();
        // record the answer we generate
        ArrayList<KMCluster> answer = PointGen.pointGen(pointSet);

        // generate first centroids which is relatively far apart
        ArrayList<KMCluster> clusterSet = PointGen
                .centroidsGen(new ArrayList<PointTwoD>());

        ArrayList<KMCluster> newCluster = new ArrayList<KMCluster>();

        // clone the cluster to a new one and trans to sequential test
        for (KMCluster cluster : clusterSet) {
            newCluster.add(new PointCluster(
                    (PointTwoD) (((PointCluster) cluster).getCentroid())
                            .clone()));
        }

        System.out.println("MPI Sequential POINT test begin!!\n");
        PointTestSeq.sequential(args, pointSet, answer, clusterSet);
        System.out.println("MPI Sequential POINT test end!!\n");

        System.out.println("MPI Parallel POINT test begin!!\n");
        parallel(args, pointSet, answer, newCluster);
        System.out.println("MPI Parallel POINT test end!!\n");

    }

    /**
     * Parallel test for point
     * 
     * @param args
     * @param pointSet
     * @param answer
     * @param clusterSet
     */
    private static void parallel(String[] args, ArrayList<KMNum> pointSet,
            ArrayList<KMCluster> answer, ArrayList<KMCluster> clusterSet) {

        try {
            MPI.Init(args);
            int size = MPI.COMM_WORLD.Size();
            System.out.println("args" + args);
            System.out.println("size is " + size + "\n");
            int rank = MPI.COMM_WORLD.Rank();
            System.out.println("rank is " + rank + "\n");

            if (rank == 0) {
                System.out.println("This is a master!");

                // initialize diff
                ArrayList<Double> diff = new ArrayList<Double>();
                for (int i = 0; i < Constants.K; i++) {
                    diff.add(Constants.difference + 1);
                }
                System.out.println("MPI : Gen points accomplished\n");

                // kmeans meat part
                Master runningMaster = new Master(pointSet, clusterSet, diff,
                        Constants.K, size);
                long startTime = System.currentTimeMillis();
                runningMaster.runMPI();
                long endTime = System.currentTimeMillis();

                // validation the result
                Master.validate(runningMaster.clusters, answer);

                System.out.println("This process cost " + (endTime - startTime)
                        + "in Master");
            } else {
                System.out.println("This is a Slave.");
                Slave runningSlave = new Slave(size);
                runningSlave.waitForCompletion();
            }

            // debug
            System.out.println("before Finalize in PointTest\n");
            MPI.Finalize();
            System.out.println("After Finalize in PointTest\n");
        } catch (MPIException e) {
            System.out.println("We have a MPI Exception\n");
        }

    }

}
