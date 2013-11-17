package util;

import java.util.ArrayList;

import Interface.KMCluster;
import Interface.KMNum;

public class PointCluster implements KMCluster {


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

	public Point getCentroid() {
		return centroid;
	}

	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}

	

	public void addData(Point newNum) {
		// TODO Auto-generated method stub
		this.list.add(newNum);
	}


	public Point updateCentroid() {
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

	
	
}
