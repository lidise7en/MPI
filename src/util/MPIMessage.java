package util;
import java.io.Serializable;
import java.util.ArrayList;

import Interface.KMCluster;
import Interface.KMNum;

public class MPIMessage implements Serializable {

	private static final long serialVersionUID = 1L;



	private ArrayList<KMNum> dataList;
	private ArrayList<KMCluster> clusterList;
	
	public MPIMessage(ArrayList<KMNum> dataList,
			ArrayList<KMCluster> clusterList) {
		super();
		this.dataList = dataList;
		this.clusterList = clusterList;
	}
	
	public ArrayList<KMNum> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<KMNum> dataList) {
		this.dataList = dataList;
	}

	public ArrayList<KMCluster> getClusterList() {
		return clusterList;
	}

	public void setClusterList(ArrayList<KMCluster> clusterList) {
		this.clusterList = clusterList;
	}
	
}
