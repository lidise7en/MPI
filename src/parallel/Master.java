package parallel;

import mpi.*;
import java.util.ArrayList;

import util.MPIMessage;

import Interface.KMCluster;
import Interface.KMNum;


public class Master {
	

	private ArrayList<KMNum> data;
	private ArrayList<KMCluster> clusters;
	private ArrayList<Double> diff;
	private int k;
	private int num;

	
	public Master(ArrayList<KMNum> data, ArrayList<KMCluster> clusters,
			ArrayList<Double> diff, int k, int num) {
		super();
		this.data = data;
		this.clusters = clusters;
		this.diff = diff;
		this.k = k;
		this.num = num;

	}
	
	public void runMPI() {
		
		if(data.size() >= this.k) {
			System.out.println("We have too much data.\n");
			return;
		}
		ArrayList<ArrayList<KMNum>> result = partition();
		while(!CalDifference()) {
			/*clear the elements in clusters */
			for(int i = 0;i < this.clusters.size();i ++) {
				this.clusters.get(i).clearList();
			}
			/*start kmeans */
			/* create the messages */
			MPIMessage[] messages = new MPIMessage[result.size()];
			for(int i = 0;i < result.size();i ++) {
				messages[i] = new MPIMessage(result.get(i), this.clusters);
			}
			
			 MPI_Scatter(messages, 1, messages[0].getClass(), messages, 1, messages[0].getClass(), 0, MPI_COMM_WORLD);
			 
			 ArrayList<KMNum> tmpList = messages[0].getDataList();
			 ArrayList<KMCluster> tmpCluster = messages[0].getClusterList();
			 
			 for(int i = 0;i < tmpList.size();i ++) {
					int idx = findNearestCluster(tmpList.get(i), tmpCluster);
					tmpCluster.get(idx).addEle(tmpList.get(i).clone());
			 }
			 messages[0].setClusterList(tmpCluster);
			 MPI_Gather(messages, 1, messages[0].getClass(), messages, 1, messages[0].getClass(), 0, MPI_COMM_WORLD);
			 
			 ArrayList<KMCluster> newClusters = messages[0].getClusterList();
			 for(int j = 1;j < messages.length;j ++) {
				 for(int m = 0;m < messages[j].getClusterList().size();m ++) {
					 newClusters.get(m).combine(messages[j].getClusterList().get(m));
				 }
			 }
			 this.clusters = newClusters;
			 
			 for(int i = 0;i < this.clusters.size();i ++) {
					KMNum oldCentroid = this.clusters.get(i).getCentroid();
					KMNum newCentroid = this.clusters.get(i).updateCentroid();
					this.diff.set(i, Math.abs(oldCentroid.CalDistance(newCentroid)));
			 }
			 
			/*for(int i = 0;i < this.data.size();i ++) {
				int idx = findNearestCluster(this.data.get(i));
				this.clusters.get(idx).addEle(this.data.get(i).clone());
			}
			for(int i = 0;i < this.clusters.size();i ++) {
				KMNum oldCentroid = this.clusters.get(i).getCentroid();
				KMNum newCentroid = this.clusters.get(i).updateCentroid();
				this.diff.set(i, Math.abs(oldCentroid.CalDistance(newCentroid)));
			}
			*/
		}
		
	}
	
	public ArrayList<ArrayList<KMNum>> partition() {
		if(this.data == null)
			return null;
		ArrayList<ArrayList<KMNum>> result = new ArrayList<ArrayList<KMNum>>();
		int size = (this.data.size() + this.num - 1)/this.num;

		for(int i = 0;i < this.data.size();i += size) {
			if(i + size > this.data.size()) {
				result.add(new ArrayList<KMNum>(this.data.subList(i, this.data.size())));
			}
			else
				result.add(new ArrayList<KMNum>(this.data.subList(i, i + size)));
		}
		return result;
	}
	
	public static int findNearestCluster(KMNum num, ArrayList<KMCluster> clusterList) {
		double minDis = num.CalDistance(clusterList.get(0).getCentroid());
		int idx = 0;
		for(int i = 1;i < clusterList.size();i ++) {
			if(minDis > num.CalDistance(clusterList.get(i).getCentroid())) {
				minDis = num.CalDistance(clusterList.get(i).getCentroid());
				idx = i;
			}
		}
		return idx;
	}
	
	public boolean CalDifference() {
		
		for(int i = 0;i < this.clusters.size();i ++) {
			if(this.diff.get(i) > constant.constant.difference) {
				return false;
			}
		}
		if(this.clusters.size() != this.diff.size())
			return false;
		return true;
	}
}
