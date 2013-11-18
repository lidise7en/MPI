package util;

import java.util.ArrayList;

import Interface.KMCluster;
import Interface.KMNum;

public class PointCluster implements KMCluster {

	private static final long serialVersionUID = 1L;
	private ArrayList<Point> list = null;
	private Point centroid = null;
	
	public PointCluster() {
		 this.list = new ArrayList<Point>();
	}
	
	public PointCluster(ArrayList<Point> list, Point cen) {
		this.list = list;
		this.centroid = cen;
	}
	
	public ArrayList<Point> getList() {
		return list;
	}

	public void setList(ArrayList<Point> list) {
		this.list = list;
	}

	public KMNum getCentroid() {
		return centroid;
	}

	public void setCentroid(Point centroid) {
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
		this.list.add(((Point)newNum));
	}


	public KMNum updateCentroid() {
		// TODO Auto-generated method stub
		double xSum = 0;
		double ySum = 0;
		for(int i = 0;i < this.list.size();i ++) {
			xSum += this.list.get(i).getX();
			ySum += this.list.get(i).getY();
		}
		this.centroid = new Point(xSum/this.list.size(), ySum/this.list.size());
		return this.centroid;
	}

	public void clearList() {
		this.list = new ArrayList<Point>();
	}

	@Override
	public void combine(KMCluster cluster) {
		ArrayList<KMNum> fakeList = cluster.getFakeList();
		for(int i = 0;i < fakeList.size();i ++) {
			this.list.add((Point)fakeList.get(i));
		}
		
	}
	
}
