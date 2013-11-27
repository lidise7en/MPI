package util;

import java.util.ArrayList;
import java.util.Random;

import Interface.KMCluster;
import Interface.KMNum;

import constant.Constant;

public class DNAGen {

    public static Random random = new Random();

    // generate a list of generate DNA list
    public static ArrayList<KMCluster> DNAGenerator(ArrayList<KMNum> DNASet) {
        // answer cluster
        ArrayList<KMCluster> answer = new ArrayList<KMCluster>();

        // generate initial centroids
        ArrayList<DNA> centroids = new ArrayList<DNA>();

        for (int i = 0; i < Constant.K; i++) {
            DNA centroid = null;
            do {
                centroid = genDNA();
            } while (tooClose(centroid, centroids));
            DNACluster cluster = new DNACluster(centroid);
            answer.add(cluster);
        }

        // generate the points and make sure each DNA has only one nearest centroids
        int index;
        for (int i = 0; i < Constant.K; i++) {
            DNA dna = null;
            do {
                dna = genDNA();
            } while ((index = hasDupCentre(dna, centroids)) < 0);
            // add DNA into real cluster
            answer.get(index).addEle(dna);
            DNASet.add(dna);
        }

        return answer;
    }

    /**
     * Check if a generated DNA has duplicated centroids
     * @param dna
     * @param centroids
     * @return the index of the real centroids
     */
    private static int hasDupCentre(DNA dna, ArrayList<DNA> centroids) {
        // flag indicate the current minimum distance is duplicated
        boolean dupFlag = false;
        int minIndex = -1;
        double minDis = -1;
        
        for (int i = 0; i < centroids.size(); i++) {
            double dis = dna.CalDistance(centroids.get(i));
            if (minIndex == -1 || dis < minDis) {
                dupFlag = false;
                minIndex = i;
                minDis = dis;
            } else if (dis == minDis) {
                dupFlag = true;
            }
        }
        
        return dupFlag ? -1 : minIndex;
    }

    // generate one DNA
    private static DNA genDNA() {
        String[] result = new String[Constant.DNA_SIZE];
        for (int i = 0; i < Constant.DNA_SIZE; i++) {
            result[i] = Constant.DNA_ELEMENT[random.nextInt(4)];
        }
        return new DNA(result);
    }

    /**
     * Judge if two points are too closed
     * 
     * @param point
     * @param centroids
     * @return
     */
    private static boolean tooClose(DNA point, ArrayList<DNA> centroids) {
        for (DNA centroid : centroids) {
            if (point.CalDistance(centroid) < Constant.MIN_DNA_DIS) {
                return true;
            }
        }
        return false;
    }
}
