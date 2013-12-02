package Test;

import java.util.ArrayList;

import parallel.Master;
import sequential.Kmeans;
import Interface.KMCluster;
import Interface.KMNum;
import constant.Constants;

public class PointTestSeq {

    /**
     * Sequential test for points
     * 
     * @param args
     * @param pointSet
     * @param answer
     * @param clusterSet
     */
    public static void sequential(String[] args, ArrayList<KMNum> pointSet,
            ArrayList<KMCluster> answer, ArrayList<KMCluster> clusterSet) {

        // initialize diff
        ArrayList<Double> diff = new ArrayList<Double>();
        for (int i = 0; i < Constants.K; i++) {
            diff.add(Constants.difference + 1);
        }
        System.out.println("Sequential : Gen points accomplished\n");

        // kmeans meat part
        // debug
        //for (int i = 0; i < clusterSet.size(); i++)
        //    System.out.println("ClusterSet is"
        //            + clusterSet.get(i).getCentroid().toString() + "\n");

        Kmeans runningKM = new Kmeans(pointSet, clusterSet, diff, Constants.K);
        long startTime = System.currentTimeMillis();
        runningKM.runKMeans();
        long endTime = System.currentTimeMillis();

        // validation the result
        Master.validate(runningKM.clusters, answer);

        System.out.println("This process cost " + (endTime - startTime)
                + "in KMSequential\n");

    }
}
