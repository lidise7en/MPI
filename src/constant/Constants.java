package constant;

public class Constants {
   
    public static double difference = 1;
    // number of cluster
    public static int K = 100;
    //public static int NUM_OF_POINTS = 20;
   
    // points 2D
    public static double pointRealm = 1000;
    // the max euclidean distance between centroid
    public static int pointsInCluster = 50000;
    public static double MIN_POINT_DIS = pointRealm / Constants.K;
    public static final double STDIVATION = 1;
    public static double CLUSTER_REALM = MIN_POINT_DIS / 2;

    // DNA
    public static int DNA_NUM = 1000000;
    public static int DNA_SIZE = 10;
    public static String[] DNA_ELEMENT = { "A", "C", "G", "T" };
    public static int MIN_DNA_DIS = 0;
}
