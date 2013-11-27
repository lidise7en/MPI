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
import constant.Constant;

public class PointTest {

    public static void main(String[] args) {
        try {
            MPI.Init(args);
            int size = MPI.COMM_WORLD.Size();
            System.out.println("args" + args);
            System.out.println("size is " + size + "\n");
            int rank = MPI.COMM_WORLD.Rank();
            System.out.println("rank is " + rank + "\n");

            if (rank == 0) {
                System.out.println("This is a master!");

                // generate points
                ArrayList<KMNum> pointSet = new ArrayList<KMNum>();
                // record the answer we generate
                ArrayList<KMCluster> answer = PointGen.pointGen(pointSet);
                /*
                 * for(int i = 0;i < constant.constant.NUM_OF_POINTS;i ++) {
                 * PointTwoD newPoint = PointGen.genPoint();
                 * pointSet.add(newPoint); }
                 */

                ArrayList<KMCluster> clusterSet = new ArrayList<KMCluster>();
                ArrayList<Double> diff = new ArrayList<Double>();
                for (int i = 0; i < Constant.K; i++) {
                    clusterSet.add((KMCluster) new PointCluster(
                            (PointTwoD) pointSet.get(i)));
                    diff.add(Constant.difference + 1);
                }
                System.out.println("MPI : Gen points accomplished\n");

                // kmeans meat part
                Master runningMaster = new Master(pointSet, clusterSet, diff,
                        Constant.K, size);
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
