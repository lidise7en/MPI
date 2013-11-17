package util;

import java.util.ArrayList;

import Interface.KMCluster;

public class DNACluster implements KMCluster {

	private ArrayList<DNA> list = new ArrayList<DNA>();
	private DNA centroid = null;
	
	public DNACluster(ArrayList<DNA> list, DNA centroid) {
		this.list = list;
		this.centroid = centroid;
	}
	
	public ArrayList<DNA> getList() {
		return list;
	}

	public void setList(ArrayList<DNA> list) {
		this.list = list;
	}

	public DNA getCentroid() {
		return centroid;
	}

	public void setCentroid(DNA centroid) {
		this.centroid = centroid;
	}

	
	public void addDNA(DNA newDNA) {
		this.list.add(newDNA);
	}
	
	public DNA updateCentroid() {
		if(this.list == null || this.list.size() == 0) {
			System.out.println("Cannot update the centroid.");
			return null;
		}
		/*calculte the avg of all DNAs */
		int len = this.list.get(0).getElement().length;
		int[] numA = new int[len];
		int[] numC = new int[len];
		int[] numG = new int[len];
		int[] numT = new int[len];
		
		for(int i = 0;i < this.list.size();i ++) {
			for(int j = 0;j < len;j ++) {
				if(this.list.get(i).getElement()[j].equals("A")) {
					numA[j] ++;
				}
				else if(this.list.get(i).getElement()[j].equals("C")) {
					numC[j] ++;
				}
				else if(this.list.get(i).getElement()[j].equals("G")) {
					numG[j] ++;
				}
				else if(this.list.get(i).getElement()[j].equals("T")) {
					numT[j] ++;
				}
			}
		}
		
		String[] result = new String[len];
		for(int i = 0;i < len;i ++) {
			int a = numA[i];
			int c = numC[i];
			int g = numG[i];
			int t = numT[i];
			
			int max = Math.max(Math.max(a, c), Math.max(g, t));
			if(max == a) {
				result[i] = "A";
			}
			else if(max == c) {
				result[i] = "C";
			}
			else if(max == g) {
				result[i] = "G";
			}
			else if(max == t) {
				result[i] = "T";
			}
		}
		this.centroid = new DNA(result);
		return this.centroid;
	}
	
	@Override
	public String toString() {
		return "DNACluster [list=" + list + ", centroid=" + centroid + "]";
	}
}
