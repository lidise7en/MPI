package parallel;

import java.util.ArrayList;
import java.util.HashSet;

import mpi.MPI;
import mpi.MPIException;
import util.MPIMessage;
import Interface.KMCluster;
import Interface.KMNum;
import constant.Constants;

public class Master {

    private ArrayList<KMNum> data;
    public ArrayList<KMCluster> clusters;
    private ArrayList<Double> diff;
    private int k;
    private int num;

    public Master(ArrayList<KMNum> data, ArrayList<KMCluster> clusters,
            ArrayList<Double> diff, int k, int num) {
        super();
        this.data = data;
        this.clusters = clusters;
        this.diff = diff;
        this.k = k;
        this.num = num;

    }

    public void runMPI() {

        if (data.size() <= this.k) {
            System.out.println("We have too much data.\n");
            return;
        }
        ArrayList<ArrayList<KMNum>> result = partition();
        while (!CalDifference()) {
            /* clear the elements in clusters */
            for (int i = 0; i < this.clusters.size(); i++) {
                this.clusters.get(i).clearList();
            }
            /* start kmeans */
            /* create the messages */
            MPIMessage[] messages = new MPIMessage[result.size()];
            for (int i = 0; i < result.size(); i++) {
                messages[i] = new MPIMessage(result.get(i), this.clusters);
            }
            try {
                MPI.COMM_WORLD.Scatter(messages, 0, 1, MPI.OBJECT, messages, 0,
                        1, MPI.OBJECT, 0);

                ArrayList<KMNum> tmpList = messages[0].getDataList();
                ArrayList<KMCluster> tmpCluster = messages[0].getClusterList();

                for (int i = 0; i < tmpList.size(); i++) {
                    int idx = findNearestCluster(tmpList.get(i), tmpCluster);
                    tmpCluster.get(idx).addEle(tmpList.get(i).clone());
                }
                messages[0].setClusterList(tmpCluster);
                MPI.COMM_WORLD.Gather(messages, 0, 1, MPI.OBJECT, messages, 0,
                        1, MPI.OBJECT, 0);
            } catch (MPIException e) {
                System.out.println("We have MPI Exception!\n");
            }
            ArrayList<KMCluster> newClusters = messages[0].getClusterList();
            for (int j = 1; j < messages.length; j++) {
                for (int m = 0; m < messages[j].getClusterList().size(); m++) {
                    newClusters.get(m).combine(
                            messages[j].getClusterList().get(m));
                }
            }
            this.clusters = newClusters;

            for (int i = 0; i < this.clusters.size(); i++) {
                KMNum oldCentroid = this.clusters.get(i).getCentroid();
                KMNum newCentroid = this.clusters.get(i).updateCentroid();
                this.diff
                        .set(i, Math.abs(oldCentroid.CalDistance(newCentroid)));
            }

            /*
             * for(int i = 0;i < this.data.size();i ++) { int idx =
             * findNearestCluster(this.data.get(i));
             * this.clusters.get(idx).addEle(this.data.get(i).clone()); }
             * for(int i = 0;i < this.clusters.size();i ++) { KMNum oldCentroid
             * = this.clusters.get(i).getCentroid(); KMNum newCentroid =
             * this.clusters.get(i).updateCentroid(); this.diff.set(i,
             * Math.abs(oldCentroid.CalDistance(newCentroid))); }
             */
        }

        /*
         * for(int i = 0;i < this.clusters.size();i ++) {
         * System.out.println("Cluster" + i + "\n"); ArrayList<KMNum> kmList =
         * this.clusters.get(i).getFakeList(); for(KMNum num : kmList) {
         * System.out.println(num.toString() + "\n"); } }
         */
        /* kill all slaves */
        MPIMessage[] killMessages = new MPIMessage[num];
        for (int i = 0; i < num; i++)
            killMessages[i] = null;
        try {
            MPI.COMM_WORLD.Scatter(killMessages, 0, 1, MPI.OBJECT,
                    killMessages, 0, 1, MPI.OBJECT, 0);

            MPI.COMM_WORLD.Gather(killMessages, 0, 1, MPI.OBJECT, killMessages,
                    0, 1, MPI.OBJECT, 0);
        } catch (MPIException e) {

        }
    }

    public ArrayList<ArrayList<KMNum>> partition() {
        if (this.data == null)
            return null;
        ArrayList<ArrayList<KMNum>> result = new ArrayList<ArrayList<KMNum>>();
        int size = (this.data.size() + this.num - 1) / this.num;

        for (int i = 0; i < this.data.size(); i += size) {
            if (i + size > this.data.size()) {
                result.add(new ArrayList<KMNum>(this.data.subList(i,
                        this.data.size())));
            } else
                result.add(new ArrayList<KMNum>(this.data.subList(i, i + size)));
        }
        return result;
    }

    public static int findNearestCluster(KMNum num,
            ArrayList<KMCluster> clusterList) {
        double minDis = num.CalDistance(clusterList.get(0).getCentroid());
        int idx = 0;
        for (int i = 1; i < clusterList.size(); i++) {
            if (minDis > num.CalDistance(clusterList.get(i).getCentroid())) {
                minDis = num.CalDistance(clusterList.get(i).getCentroid());
                idx = i;
            }
        }
        return idx;
    }

    public boolean CalDifference() {

        for (int i = 0; i < this.clusters.size(); i++) {
            if (this.diff.get(i) > Constants.difference) {
                return false;
            }
        }
        if (this.clusters.size() != this.diff.size())
            return false;
        return true;
    }

    /**
     * validate the result from the real result to kMeans result
     * 
     * @param clusters
     * @param answer
     */
    public static void validate(ArrayList<KMCluster> clusters,
            ArrayList<KMCluster> answer) {
        // print final result
        showResult(clusters);
        showResult(answer);
        
        int mismatch = 0;
        // validation formula
        for (KMCluster cluster : clusters) {
            KMNum num = cluster.getCentroid();
            double minDis = -1;
            KMCluster match = null;

            // find the generated centriod closest centriod
            for (KMCluster ansCluster : answer) {
                double tmpDis = num.CalDistance(ansCluster.getCentroid());
                if (minDis < 0 || tmpDis < minDis) {
                    minDis = tmpDis;
                    match = ansCluster;
                }
            }
            
            // compute the mismatch number
            HashSet<KMNum> answerList =  new HashSet<KMNum>(match.getFakeList());
            for (KMNum genNum : cluster.getFakeList()) {
                if (!answerList.contains(genNum)) {
                    mismatch++;
                }
            }
        }
        
        System.out.println("Mismatch Number :" + mismatch);
    }

    /**
     * print a cluster result
     * 
     * @param clusters
     */
    private static void showResult(ArrayList<KMCluster> clusters) {
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster" + i + "\n");
            ArrayList<KMNum> kmList = clusters.get(i).getFakeList();
            for (KMNum num : kmList) {
                System.out.println(num.toString() + "\n");
            }
        }

    }
}
