package Test;

import mpi.*;
import java.awt.Point;
import java.util.ArrayList;

import Interface.KMCluster;
import Interface.KMNum;

import parallel.Master;
import parallel.Slave;

import util.PointCluster;
import util.PointGen;
import util.PointTwoD;

public class PointTest {

	public static void main(String[] args) {
		
		MPI.init(args);
		int size = MPI.COMM_WORLD.size();
		int rank = MPI.COMM_WORLD.Rank();
		
		if(rank == 0) {
			System.out.println("This is a master!");
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
			Master runningMaster = new Master(pointSet, clusterSet, diff, constant.constant.K, size);
			long startTime = System.currentTimeMillis();
			runningMaster.runMPI();
			long endTime = System.currentTimeMillis();
			System.out.println("This process cost " + (endTime - startTime) + "in Master");
		}
		else {
			System.out.println("This is a Slave.");
			Slave runningSlave = new Slave(size);
			runningSlave.waitForCompletion();
		}
		MPI.finalize();
	}
}
