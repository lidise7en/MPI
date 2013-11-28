package util;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;

import Interface.KMCluster;
import Interface.KMNum;
import constant.Constant;

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
        for (int i = 0; i < Constant.K; i++) {
            NormalDistribution Xgenerator = new NormalDistribution(centroids
                    .get(i).getX(), Constant.STDIVATION);
            NormalDistribution Ygenerator = new NormalDistribution(centroids
                    .get(i).getY(), Constant.STDIVATION);

            // add points into cluster
            for (int j = 0; j < Constant.pointsInCluster; j++) {
                PointTwoD point = new PointTwoD(Xgenerator.sample(),
                        Ygenerator.sample());
                pointSet.add(point);
                answer.get(i).addEle(point);
            }

        }

        return answer;
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

        for (int i = 0; i < Constant.K; i++) {
            PointTwoD centroid = null;
            do {
                centroid = new PointTwoD(Constant.pointRealm
                        * random.nextDouble(), Constant.pointRealm
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
            if (point.CalDistance(centroid) < Constant.MIN_POINT_DIS) {
                return true;
            }
        }
        return false;
    }

}
