package util;

import Interface.KMNum;

public class Point implements KMNum {

	private static final long serialVersionUID = 1L;

	private double x;
	private double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	public double CalDistance(KMNum num) {
		
		return Math.sqrt(Math.pow(this.x - ((Point)num).getX(), 2)+Math.pow(this.y - ((Point)num).getY(), 2));
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	public KMNum clone() {
		return new Point(this.x, this.y);
	}
}
