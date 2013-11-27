package Test;

import java.util.ArrayList;

import sequential.Kmeans;
import util.DNA;
import util.DNACluster;
import util.DNAGen;
import Interface.KMCluster;
import Interface.KMNum;
import constant.Constant;

public class DNATestSeq {

	public static void main(String[] args) {
		ArrayList<KMNum> dnaSet = new ArrayList<KMNum>();
		for(int i = 0;i < Constant.NUM_OF_POINTS;i ++) {
			DNA newDNA = DNAGen.genDNA();
			dnaSet.add(newDNA);
		}
		ArrayList<KMCluster> clusterSet = new ArrayList<KMCluster>();
		ArrayList<Double> diff = new ArrayList<Double>();
		for(int i = 0;i < Constant.K;i ++) {
			clusterSet.add((KMCluster)new DNACluster((DNA)dnaSet.get(i)));
			diff.add(Constant.difference + 1);
		}
		Kmeans runningKM = new Kmeans(dnaSet, clusterSet, diff, Constant.K);
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

