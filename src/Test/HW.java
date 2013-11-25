package Test;
import mpi.*;
import java.lang.*;
class HW {
	public static void main(String[] args) {
		try {
			MPI.Init(args);
			int sz = MPI.COMM_WORLD.Size();
			int me = MPI.COMM_WORLD.Rank();
			String where = MPI.Get_processor_name();
			System.out.println("Process " + me
					+ " on " + where
					+ " out of " + sz);
			MPI.Finalize();
		}
		catch(Exception e) {

		}
	}
}
