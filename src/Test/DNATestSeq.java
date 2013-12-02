package Test;

import java.util.ArrayList;

import parallel.Master;
import sequential.Kmeans;
import Interface.KMCluster;
import Interface.KMNum;
import constant.Constants;

public class DNATestSeq {

    /**
     * Sequential test of the DNA test
     * @param args
     * @param dnaSet
     * @param answer
     * @param clusterSet2 
     */
    public static void sequential(String[] args, ArrayList<KMNum> dnaSet,
            ArrayList<KMCluster> answer, ArrayList<KMCluster> clusterSet) {

        // initialize diff
        ArrayList<Double> diff = new ArrayList<Double>();
        for (int i = 0; i < Constants.K; i++) {
            diff.add(Constants.difference + 1);
        }

        // meat part for Kmeans
        Kmeans runningKM = new Kmeans(dnaSet, clusterSet, diff, Constants.K);
        long startTime = System.currentTimeMillis();
        runningKM.runKMeans();

        // validation and print out result
        long endTime = System.currentTimeMillis();
        Master.validate(runningKM.clusters, answer);

        System.out.println("This process cost " + (endTime - startTime)
                + "in KMSequential\n");
    }
}
