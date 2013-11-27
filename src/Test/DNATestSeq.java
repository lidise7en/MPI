package Test;

import java.util.ArrayList;

import parallel.Master;

import sequential.Kmeans;
import util.DNA;
import util.DNACluster;
import util.DNAGen;
import Interface.KMCluster;
import Interface.KMNum;
import constant.Constant;

public class DNATestSeq {

	public static void main(String[] args) {
		// generate DNA test set and the answer ahead
	    ArrayList<KMNum> dnaSet = new ArrayList<KMNum>();
		/*for(int i = 0;i < Constant.NUM_OF_POINTS;i ++) {
			DNA newDNA = DNAGen.genDNA();
			dnaSet.add(newDNA);
		}*/
	    ArrayList<KMCluster> answer = DNAGen.DNAGenerator(dnaSet);
		
		
		ArrayList<KMCluster> clusterSet = new ArrayList<KMCluster>();
		ArrayList<Double> diff = new ArrayList<Double>();
		for(int i = 0;i < Constant.K;i ++) {
			clusterSet.add((KMCluster)new DNACluster((DNA)dnaSet.get(i)));
			diff.add(Constant.difference + 1);
		}
		
		// meat part for Kmeans
		Kmeans runningKM = new Kmeans(dnaSet, clusterSet, diff, Constant.K);
		long startTime = System.currentTimeMillis();
		runningKM.runKMeans();
		
		// validation and print out result
		long endTime = System.currentTimeMillis();
		Master.validate(runningKM.clusters, answer);

		System.out.println("This process cost " + (endTime - startTime) + "in KMSequential\n");
	}
}

