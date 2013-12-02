package util;

import java.util.ArrayList;
import java.util.Random;

import Interface.KMCluster;
import Interface.KMNum;
import constant.Constants;
import constant.Constants.DNA_ELEMENT;

public class DNAGen {

    public static Random random = new Random();

    // generate a list of generate DNA list
    public static ArrayList<KMCluster> DNAGenerator(ArrayList<KMNum> DNASet) {
        // answer cluster
        ArrayList<KMCluster> answer;

        // generate initial centroids
        ArrayList<DNA> centroids = new ArrayList<DNA>();

        answer = centroidsGen(centroids);

        // generate the points and make sure each DNA has only one nearest
        // centroids
        int index;
        for (int i = 0; i < Constants.DNA_NUM; i++) {
            DNA dna = null;
            do {
                dna = genDNA();
            } while ((index = getCentre(dna, centroids)) < 0);
            // add DNA into real cluster
            answer.get(index).addEle(dna);
            DNASet.add(dna);
        }

        return answer;
    }

    /**
     * Generate the centroids that is relatively far apart
     * 
     * @param centroids
     * @return
     */
    public static ArrayList<KMCluster> centroidsGen(ArrayList<DNA> centroids) {
        ArrayList<KMCluster> answer = new ArrayList<KMCluster>();

        for (int i = 0; i < Constants.K; i++) {
            DNA centroid = null;
            do {
                centroid = genDNA();
            } while (tooClose(centroid, centroids));

            centroids.add(centroid);
            DNACluster cluster = new DNACluster(centroid);
            answer.add(cluster);
        }

        return answer;
    }

    /**
     * Check a generated DNA's centroid
     * 
     * @param dna
     * @param centroids
     * @return the index of the real centroids
     */
    private static int getCentre(DNA dna, ArrayList<DNA> centroids) {
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
        //return minIndex;
    }

    // generate one DNA
    private static DNA genDNA() {
        DNA_ELEMENT[] result = new DNA_ELEMENT[Constants.DNA_SIZE];
        for (int i = 0; i < Constants.DNA_SIZE; i++) {
            result[i] = DNA_ELEMENT.values()[random.nextInt(4)];
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
            if (point.CalDistance(centroid) < Constants.MIN_DNA_DIS) {
                return true;
            }
        }
        return false;
    }
}
