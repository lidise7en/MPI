package Test;

import java.util.ArrayList;

import parallel.Master;
import sequential.Kmeans;
import util.DNA;
import util.DNAGen;
import Interface.KMCluster;
import Interface.KMNum;
import constant.Constant;

public class DNATestSeq {

    public static void main(String[] args) {
        // generate DNA test set and the answer ahead
        ArrayList<KMNum> dnaSet = new ArrayList<KMNum>();
        ArrayList<KMCluster> answer = DNAGen.DNAGenerator(dnaSet);

        // add to origin cluster
        ArrayList<KMCluster> clusterSet = DNAGen
                .centroidsGen(new ArrayList<DNA>());

        // initialize diff
        ArrayList<Double> diff = new ArrayList<Double>();
        for (int i = 0; i < Constant.K; i++) {
            diff.add(Constant.difference + 1);
        }

        // meat part for Kmeans
        Kmeans runningKM = new Kmeans(dnaSet, clusterSet, diff, Constant.K);
        long startTime = System.currentTimeMillis();
        runningKM.runKMeans();

        // validation and print out result
        long endTime = System.currentTimeMillis();
        Master.validate(runningKM.clusters, answer);

        System.out.println("This process cost " + (endTime - startTime)
                + "in KMSequential\n");
    }
}
