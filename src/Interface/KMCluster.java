package Interface;

import java.io.Serializable;
import java.util.ArrayList;

public interface KMCluster extends Serializable {


	public ArrayList<KMNum> listOfNum = null;
	public KMNum centroid = null;
	

	public void addEle(KMNum e);
	public KMNum updateCentroid();
	public KMNum getCentroid();
	public void clearList();
	public ArrayList<KMNum> getFakeList();
	public void combine(KMCluster cluster);
}
