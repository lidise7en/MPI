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

    /*
     * public static PointTwoD genPoint() { return new
     * PointTwoD((double)random.nextInt(constant.constant.pointRealm),
     * (double)random.nextInt(constant.constant.pointRealm)); }
     */

    public static ArrayList<KMCluster> pointGen(ArrayList<KMNum> pointSet) {
	    // answer cluster
        ArrayList<KMCluster> answer = new ArrayList<KMCluster>();
        // generate each 2D centroids
	    ArrayList<PointTwoD> centroids = new ArrayList<PointTwoD>();	            
	    for (int i = 0; i < Constant.K; i++) {
	        PointTwoD centroid= null;       
	        do{
	            centroid= new PointTwoD(Constant.pointRealm * random.nextDouble(), 
	                    Constant.pointRealm * random.nextDouble());
	        } while(tooClose(centroid, centroids));
	        centroids.add(centroid);
	    }
	    
	 // generate the points for each centroid
	    for (int i = 0; i < Constant.K; i++) {
	        NormalDistribution Xgenerator = new NormalDistribution(centroids.get(i).getX(), Constant.STDIVATION);
	        NormalDistribution Ygenerator = new NormalDistribution(centroids.get(i).getY(), Constant.STDIVATION);
	        PointCluster cluster = new PointCluster(centroids.get(i));
	        
	        // add points into cluster
	        for (int j = 0; j < Constant.pointsInCluster; j++) {
	            PointTwoD point = new PointTwoD(Xgenerator.sample(), Ygenerator.sample());
	            pointSet.add(point);
	            cluster.addEle(point);
	        }
	        answer.add(cluster);
	    }
	        
	    return answer;
	}

    /**
     * Judge if two points are too closed
     * @param point
     * @param centroids
     * @return
     */
    private static boolean tooClose(PointTwoD point, ArrayList<PointTwoD> centroids) {
        for (PointTwoD centroid : centroids) {
            if (point.CalDistance(centroid) < Constant.MIN_DIS) {
                return true;
            }
        }
        return false;
    }
    
}
