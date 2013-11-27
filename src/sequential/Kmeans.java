package sequential;

import java.util.ArrayList;

import Interface.KMCluster;
import Interface.KMNum;
import constant.Constant;

public class Kmeans {

	private ArrayList<KMNum> data;
	public ArrayList<KMCluster> clusters;
	private ArrayList<Double> diff;
	private int k;

	public Kmeans(ArrayList<KMNum> data, ArrayList<KMCluster> clusters,
			ArrayList<Double> diff, int k) {
		
		this.data = data;
		this.clusters = clusters;
		this.diff = diff;
		this.k = k;
	}
	
	public void runKMeans() {
		if(data.size() <= this.k) {
			System.out.println("We have too much data.\n");
			return;
		}
		
		while(!CalDifference()) {
			/*clear the elements in clusters */
			for(int i = 0;i < this.clusters.size();i ++) {
				this.clusters.get(i).clearList();
			}
			/*start kmeans */
			for(int i = 0;i < this.data.size();i ++) {
				int idx = findNearestCluster(this.data.get(i));
				this.clusters.get(idx).addEle(this.data.get(i).clone());
			}
			for(int i = 0;i < this.clusters.size();i ++) {
				KMNum oldCentroid = this.clusters.get(i).getCentroid();
				KMNum newCentroid = this.clusters.get(i).updateCentroid();
				this.diff.set(i, Math.abs(oldCentroid.CalDistance(newCentroid)));
			}
		}
		/*
		for(int i = 0;i < this.clusters.size();i ++) {
                        System.out.println("Cluster" + i + "\n");
                        ArrayList<KMNum> kmList = this.clusters.get(i).getFakeList();
                        for(KMNum num : kmList) {
                                System.out.println(num.toString() + "\n");
                        }
                }
		*/
	}
	
	public int findNearestCluster(KMNum num) {
		double minDis = num.CalDistance(this.clusters.get(0).getCentroid());
		int idx = 0;
		for(int i = 1;i < this.clusters.size();i ++) {
			if(minDis > num.CalDistance(this.clusters.get(i).getCentroid())) {
				minDis = num.CalDistance(this.clusters.get(i).getCentroid());
				idx = i;
			}
		}
		return idx;
	}
	
	public boolean CalDifference() {
		
		for(int i = 0;i < this.clusters.size();i ++) {
			if(this.diff.get(i) > Constant.difference) {
				return false;
			}
		}
		if(this.clusters.size() != this.diff.size())
			return false;
		return true;
	}
	
	public String toString() {
		String result = new String();
		for(int i = 0;i < this.clusters.size();i ++) {
			result += "Cluster " + i + ": " + this.clusters.get(i).toString();
			result += "*****";
		}
		return result;
	}
}
