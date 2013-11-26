package Test;

import java.util.ArrayList;

import sequential.Kmeans;
import util.PointCluster;
import util.PointGen;
import util.PointTwoD;
import Interface.KMCluster;
import Interface.KMNum;

public class PointTestSeq {

	
	public static void main(String[] args) {
		ArrayList<KMNum> pointSet = new ArrayList<KMNum>();
		for(int i = 0;i < constant.constant.NUM_OF_POINTS;i ++) {
			PointTwoD newPoint = PointGen.genPoint();
			pointSet.add(newPoint);
		}
		ArrayList<KMCluster> clusterSet = new ArrayList<KMCluster>();
		ArrayList<Double> diff = new ArrayList<Double>();
		for(int i = 0;i < constant.constant.K;i ++) {
			clusterSet.add((KMCluster)new PointCluster((PointTwoD)pointSet.get(i)));
			diff.add(constant.constant.difference + 1);
		}
		System.out.println("Gen points accomplished\n");
		Kmeans runningKM = new Kmeans(pointSet, clusterSet, diff, constant.constant.K);
		long startTime = System.currentTimeMillis();
		runningKM.runKMeans();
		long endTime = System.currentTimeMillis();
		for(int i = 0;i < runningKM.clusters.size();i ++) {
                        System.out.println("Cluster" + i + "\n");
                        ArrayList<KMNum> kmList = runningKM.clusters.get(i).getFakeList();
                        for(KMNum num : kmList) {
                                System.out.println(num.toString() + "\n");
                        }
                }
		System.out.println("This process cost " + (endTime - startTime) + "in KMSequential\n");
		
	}
}

