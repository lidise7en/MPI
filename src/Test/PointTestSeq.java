package Test;

import java.util.ArrayList;

import parallel.Master;
import sequential.Kmeans;
import util.PointGen;
import util.PointTwoD;
import Interface.KMCluster;
import Interface.KMNum;
import constant.Constants;

public class PointTestSeq {

    public static void main(String[] args) {
        // generate points
        ArrayList<KMNum> pointSet = new ArrayList<KMNum>();

        // record the answer we generate
        ArrayList<KMCluster> answer = PointGen.pointGen(pointSet);

        ArrayList<KMCluster> clusterSet = PointGen
                .centroidsGen(new ArrayList<PointTwoD>());

        // initialize diff
        ArrayList<Double> diff = new ArrayList<Double>();
        for (int i = 0; i < Constants.K; i++) {
            diff.add(Constants.difference + 1);
        }
        System.out.println("Sequential : Gen points accomplished\n");

        // kmeans meat part
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
