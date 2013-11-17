package Interface;

import java.io.Serializable;

public interface KMNum extends Serializable {

	public double CalDistance(KMNum e);
	public String toString();
	public KMNum clone();
}
