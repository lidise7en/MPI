package util;

import java.util.ArrayList;

import constant.Constants;
import constant.Constants.DNA_ELEMENT;

import Interface.KMCluster;
import Interface.KMNum;

public class DNACluster implements KMCluster {

	private static final long serialVersionUID = 1L;
	private ArrayList<DNA> list = new ArrayList<DNA>();
	private DNA centroid = null;
	
	public DNACluster(DNA centroid) {
		
		this.centroid = centroid;
	}
	
	public DNACluster(ArrayList<DNA> list, DNA centroid) {
		this.list = list;
		this.centroid = centroid;
	}
	
	public ArrayList<DNA> getList() {
		return list;
	}

	public ArrayList<KMNum> getFakeList() {
		ArrayList<KMNum> fakeList = new ArrayList<KMNum>();
		for(int i = 0;i < list.size();i ++) {
			fakeList.add((KMNum)list.get(i));
		}
		return fakeList;
	}

	public void setList(ArrayList<DNA> list) {
		this.list = list;
	}

	public KMNum getCentroid() {
		return centroid;
	}

	public void setCentroid(DNA centroid) {
		this.centroid = centroid;
	}

	
	public void addEle(KMNum newDNA) {
		this.list.add(((DNA)newDNA));
	}
	
	public KMNum updateCentroid() {
		if(this.list == null || this.list.size() == 0) {
			if(this.list == null)
				System.out.println("list null\n");
			return this.centroid;
		}

		/*calculte the avg of all DNAs */
		int len = this.list.get(0).getElement().length;
		int[][] num = new int[DNA_ELEMENT.values().length][len];
		
		for(int i = 0;i < this.list.size();i ++) {
			for(int j = 0;j < len;j ++) {
			    num[this.list.get(i).getElement()[j].ordinal()][j]++;
			}
		}
		
		// set the type with most number to be the centroid type
		Constants.DNA_ELEMENT[] result = new Constants.DNA_ELEMENT[len];
		for(int i = 0;i < len;i ++) {
			
			int max = Math.max(Math.max(num[0][i], num[1][i]), Math.max(num[2][i], num[3][i]));
			if(max == num[0][i]) {
				result[i] = DNA_ELEMENT.A;
			}
			else if(max == num[1][i]) {
				result[i] = DNA_ELEMENT.C;
			}
			else if(max == num[2][i]) {
				result[i] = DNA_ELEMENT.G;
			}
			else if(max == num[3][i]) {
				result[i] = DNA_ELEMENT.T;
			}
		}
		this.centroid = new DNA(result);
		return this.centroid;
	}
	
	@Override
	public String toString() {
		return "DNACluster [list=" + list + ", centroid=" + centroid + "]";
	}
	public void clearList() {
		this.list = new ArrayList<DNA>();
	}

	@Override
	public void combine(KMCluster cluster) {
		ArrayList<KMNum> fakeList = cluster.getFakeList();
		for(int i = 0;i < fakeList.size();i ++) {
			this.list.add((DNA)fakeList.get(i));
		}
		
	}


}
