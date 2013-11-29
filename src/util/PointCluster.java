package util;

import java.util.ArrayList;

import Interface.KMCluster;
import Interface.KMNum;

public class PointCluster implements KMCluster {

	private static final long serialVersionUID = 1L;
	private ArrayList<PointTwoD> list = null;
	private PointTwoD centroid = null;
	
	public PointCluster(PointTwoD cen) {
		 this.list = new ArrayList<PointTwoD>();
		 this.centroid = cen;
	}
	
	public PointCluster(ArrayList<PointTwoD> list, PointTwoD cen) {
		this.list = list;
		this.centroid = cen;
	}
	
	public ArrayList<PointTwoD> getList() {
		return list;
	}

	public void setList(ArrayList<PointTwoD> list) {
		this.list = list;
	}

	public KMNum getCentroid() {
		return centroid;
	}

	public void setCentroid(PointTwoD centroid) {
		this.centroid = centroid;
	}

	public ArrayList<KMNum> getFakeList() {
		ArrayList<KMNum> fakeList = new ArrayList<KMNum>();
		for(int i = 0;i < list.size();i ++) {
			fakeList.add((KMNum)list.get(i));
		}
		return fakeList;
	}

	public void addEle(KMNum newNum) {
		// TODO Auto-generated method stub
		this.list.add(((PointTwoD)newNum));
	}


	public KMNum updateCentroid() {
		// TODO Auto-generated method stub
		double xSum = 0;
		double ySum = 0;
		if(this.list.size() == 0) {
			return this.centroid;
		}
		for(int i = 0;i < this.list.size();i ++) {
			xSum += this.list.get(i).getX();
			ySum += this.list.get(i).getY();
		}
		this.centroid = new PointTwoD(xSum/this.list.size(), ySum/this.list.size());
		return this.centroid;
	}

	public void clearList() {
		this.list = new ArrayList<PointTwoD>();
	}

	@Override
	public void combine(KMCluster cluster) {
		ArrayList<KMNum> fakeList = cluster.getFakeList();
		for(int i = 0;i < fakeList.size();i ++) {
			this.list.add((PointTwoD)fakeList.get(i));
		}
		
	}
	
}
