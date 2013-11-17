package util;

import java.util.Random;

public class DNAGen {

	public static Random random = new Random();
	
	public static DNA genDNA() {
		String[] result = new String[constant.constant.DNA_SIZE];
		for(int i = 0;i < constant.constant.DNA_SIZE;i ++) {
			result[i] = constant.constant.DNA_ELEMENT[random.nextInt(4)];
		}
		return new DNA(result);
	}
}
