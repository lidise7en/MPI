package util;

import java.util.ArrayList;
import java.util.Random;

import Interface.KMCluster;
import Interface.KMNum;
import constant.Constants;

/**
 * 
 * Point generator
 * 
 */
public class PointGen {
    private static Random random = new Random();

    /**
     * Generate the point set to do clustering
     * 
     * @param pointSet
     * @return
     */
    public static ArrayList<KMCluster> pointGen(ArrayList<KMNum> pointSet) {
        // answer cluster
        ArrayList<KMCluster> answer;
        // generate each 2D centroids
        ArrayList<PointTwoD> centroids = new ArrayList<PointTwoD>();

        answer = centroidsGen(centroids);

        // generate the points for each centroid
        for (int i = 0; i < Constants.K; i++) {

            // add points into cluster
            for (int j = 0; j < Constants.pointsInCluster; j++) {
                PointTwoD point = new PointTwoD(nearPointGen(centroids.get(i)
                        .getX()), nearPointGen(centroids.get(i).getY()));
                pointSet.add(point);
                answer.get(i).addEle(point);
            }

        }

        return answer;
    }

    /**
     * Generate a double that near to the given double
     * 
     * @param x
     * @return
     */
    private static double nearPointGen(double x) {
        
        return x - Constants.CLUSTER_REALM + 2 * Constants.CLUSTER_REALM
                * random.nextDouble();
    }

    /**
     * generate the initial centroids whicha are relatively far apart
     * 
     * @param centroids
     * @return
     */
    public static ArrayList<KMCluster> centroidsGen(
            ArrayList<PointTwoD> centroids) {
        ArrayList<KMCluster> answer = new ArrayList<KMCluster>();

        for (int i = 0; i < Constants.K; i++) {
            PointTwoD centroid = null;
            do {
                centroid = new PointTwoD(Constants.pointRealm
                        * random.nextDouble(), Constants.pointRealm
                        * random.nextDouble());
            } while (tooClose(centroid, centroids));

            centroids.add(centroid);
            PointCluster cluster = new PointCluster(centroid);
            answer.add(cluster);
        }
        return answer;
    }

    /**
     * Judge if two points are too closed
     * 
     * @param point
     * @param centroids
     * @return
     */
    private static boolean tooClose(PointTwoD point,
            ArrayList<PointTwoD> centroids) {
        for (PointTwoD centroid : centroids) {
            if (point.CalDistance(centroid) < Constants.MIN_POINT_DIS) {
                return true;
            }
        }
        return false;
    }

}
