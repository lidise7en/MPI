package parallel;

import java.util.ArrayList;

import mpi.MPI;
import mpi.MPIException;
import util.MPIMessage;
import Interface.KMCluster;
import Interface.KMNum;
public class Slave {

	private int num;
	private boolean flag = true;
	public Slave(int num) {
		this.num = num;
	}
	
	public void waitForCompletion() {
		while(flag) {
			MPIMessage[] messages = new MPIMessage[1];
			try {
			 MPI.COMM_WORLD.Scatter(messages, 0, 1, MPI.OBJECT, messages, 0, 1, MPI.OBJECT, 0);
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
			 MPI.COMM_WORLD.Gather(messages,0, 1, MPI.OBJECT, messages, 0, 1, MPI.OBJECT, 0);
		}
		catch(MPIException e) {
			System.out.println("We have MPI Exception!\n");
		}
		}
	}
}
