/*
 * Name - Satish Kumar
 * 
 * Email ID - skumar34@uncc.edu
 * 
 * Student ID - 800966466
 * 
 * */
import java.util.Comparator;

/**
 * VertexComparator class to compare two Vertices.
 */
class VertexComparator implements Comparator<Vertex>{

		@Override
		public int compare(Vertex o1, Vertex o2) {
			// TODO Auto-generated method stub
			return o1.name.compareTo(o2.name);
		}
		
		
	}

/**
 * VertexNameComparator class to compare two Vertices name.
 */
public class VertexNameComparator implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		return o1.compareTo(o2);
	}
	
	
}
