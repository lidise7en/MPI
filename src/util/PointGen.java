package util;

import java.util.Random;

public class PointGen {

	public static Random random = new Random();
	
	public static PointTwoD genPoint() {
		return new PointTwoD((double)random.nextInt(constant.constant.pointRealm), 
				(double)random.nextInt(constant.constant.pointRealm));
	}
}
