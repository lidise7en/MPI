package constant;

public class Constant {
   
    public static double difference = 1;
    // number of cluster
    public static int K = 20;

    // points 2D
    public static int NUM_OF_POINTS = 1000000;
    public static double pointRealm = 1000;
    // the max euclidean distance between centroid
    public static double MIN_DIS = pointRealm / Constant.K;
    public static int pointsInCluster = Constant.NUM_OF_POINTS / Constant.K;
    public static final double STDIVATION = 1;

    // DNA
    public static int DNA_SIZE = 10;
    public static String[] DNA_ELEMENT = { "A", "C", "G", "T" };
}
