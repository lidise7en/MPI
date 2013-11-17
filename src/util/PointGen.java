package util;

import java.util.Random;

public class PointGen {

	public static Random random = new Random();
	
	public static Point genPoint() {
		return new Point((double)random.nextInt(constant.constant.pointRealm), 
				(double)random.nextInt(constant.constant.pointRealm));
	}
}
