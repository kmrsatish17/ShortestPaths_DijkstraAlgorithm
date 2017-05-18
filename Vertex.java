/*
 * Name - Satish Kumar
 * 
 * Email ID - skumar34@uncc.edu
 * 
 * Student ID - 800966466
 * 
 * */

import java.util.LinkedList;
import java.util.List;

// Represents a vertex in the graph.
public class Vertex {
	public String name; // Vertex name
	public List<Vertex> adj; // Adjacent vertices
	public Vertex prev; // Previous vertex on shortest path
	public float dist; // Distance of path
	public String vertexStatus ;

	/**
	 * Vertex class constructor for initializing Vertex
	 */
	public Vertex(String nm) {
		name = nm;
		adj = new LinkedList<Vertex>();
		vertexStatus = "UP";
		reset();
	}

	/**
	 * For resetting the vertex.
	 */
	public void reset() {
		dist = Graph.INFINITY;
		prev = null;

	}

}