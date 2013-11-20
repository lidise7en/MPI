package parallel;

import java.util.ArrayList;

import Interface.KMCluster;
import Interface.KMNum;
import util.MPIMessage;
import mpi.*;
public class Slave {

	private int num;
	private boolean flag = true;
	public Slave(int num) {
		this.num = num;
	}
	
	public void waitForCompletion() {
		while(flag) {
			MPIMessage[] messages = new MPIMessage[1];
			 mpi.MPI_Scatter(messages, 1, messages[0].getClass(), messages, 1, messages[0].getClass(), 0, MPI_COMM_WORLD);
			 if(messages[0] == null)
				 flag = false;
			 else {
				 ArrayList<KMNum> tmpList = messages[0].getDataList();
				 ArrayList<KMCluster> tmpCluster = messages[0].getClusterList();
			 
				 for(int i = 0;i < tmpList.size();i ++) {
					 int idx = Master.findNearestCluster(tmpList.get(i), tmpCluster);
					 tmpCluster.get(idx).addEle(tmpList.get(i).clone());
				 }
				 messages[0].setClusterList(tmpCluster);
			 }
			 MPI_Gather(messages, 1, messages[0].getClass(), messages, 1, messages[0].getClass(), 0, MPI_COMM_WORLD);
		}
	}
}
