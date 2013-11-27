package util;

import java.util.Random;

import constant.Constant;

public class DNAGen {

	public static Random random = new Random();
	
	public static DNA genDNA() {
		String[] result = new String[Constant.DNA_SIZE];
		for(int i = 0;i < Constant.DNA_SIZE;i ++) {
			result[i] = Constant.DNA_ELEMENT[random.nextInt(4)];
		}
		return new DNA(result);
	}
}
