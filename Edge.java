/*
 * Name - Satish Kumar
 * 
 * Email ID - skumar34@uncc.edu
 * 
 * Student ID - 800966466
 * 
 * */

/**
 * Edge class for the graph
 */
public class Edge {

	public Vertex startVertex;
	public Vertex endVertex;
	public float edgeWeight;
	public String edgeStatus;

	/**
	 * Edge constructor to initialize the edge.
	 */
	public Edge(Vertex startVertex, Vertex endVertex, float edgeWeight) {
		super();
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.edgeWeight = edgeWeight;
		this.edgeStatus = "UP";
	}

	@Override
	public String toString() {
		return "Edge [startVertex=" + startVertex.name + ", endVertex=" + endVertex.name + ", edgeWeight="
				+ edgeWeight + ", edgeStatus=" + edgeStatus + "]";
	}

}
