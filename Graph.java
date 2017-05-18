/*
 * Name - Satish Kumar
 * 
 * Email ID - skumar34@uncc.edu
 * 
 * Student ID - 800966466
 * 
 * */

import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

// Used to signal violations of preconditions for
// various shortest path algorithms.
class GraphException extends RuntimeException {
	public GraphException(String name) {
		super(name);
	}
}

// Graph class: evaluate shortest paths.
public class Graph {
	public static final int INFINITY = Integer.MAX_VALUE;
	private Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();
	private Map<String, Map<String, Edge>> edgeMap = new HashMap<String, Map<String, Edge>>();
	public static final String UP = "UP";
	public static final String DOWN = "DOWN";

	/**
	 * Add a new edge to the graph.
	 */
	public void addEdge(String startName, String endName, float weight, boolean biDirectional) {
		Vertex startVertex = getVertex(startName);
		Vertex endVertex = getVertex(endName);

		if (startVertex.adj.indexOf(endVertex) == -1)
			startVertex.adj.add(endVertex);

		if (edgeMap.containsKey(startName)) // something here???
			edgeMap.get(startVertex.name).put(endVertex.name, new Edge(startVertex, endVertex, weight));
		else {
			Map<String, Edge> temp = new HashMap<>();
			temp.put(endVertex.name, new Edge(startVertex, endVertex, weight));
			edgeMap.put(startVertex.name, temp);
		}

		if (biDirectional) {
			if (endVertex.adj.indexOf(startVertex) == -1) {
				endVertex.adj.add(startVertex);
			}

			if (edgeMap.containsKey(endName)) {
				edgeMap.get(endVertex.name).put(startVertex.name, new Edge(endVertex, startVertex, weight));
			} else {
				Map<String, Edge> temp = new HashMap<>();
				temp.put(startVertex.name, new Edge(endVertex, startVertex, weight));
				edgeMap.put(endVertex.name, temp);
			}
		}

	}

	/**
	 * Driver routine to print total distance. It calls recursive routine to
	 * print shortest path to destNode after a shortest path algorithm has run.
	 */
	public void printPath(String endVerName) {
		Vertex endVertex = vertexMap.get(endVerName);

		if (endVertex == null)
			System.out.println("End vertex does not exist");
		else if (endVertex.dist == INFINITY)
			System.out.println(endVerName + " is unreachable");
		else {
			printPath(endVertex);
			System.out.print("  " + Math.round(endVertex.dist * 100.00) / 100.00);
		}
		System.out.println();
	}

	/**
	 * If vertexName is not present, add it to vertexMap. In either case, return
	 * the Vertex.
	 */
	private Vertex getVertex(String vertexName) {
		Vertex vertex = vertexMap.get(vertexName);
		if (vertex == null) {
			vertex = new Vertex(vertexName);
			vertexMap.put(vertexName, vertex);
		}
		return vertex;

	}

	/**
	 * Recursive routine to print shortest path to dest after running shortest
	 * path algorithm. The path is known to exist.
	 */
	private void printPath(Vertex end) {
		if (end.prev != null) {
			printPath(end.prev);
			System.out.print(" ");
		}
		System.out.print(end.name);
	}

	/**
	 * Initializes the vertex output info prior to running any shortest path
	 * algorithm.
	 */
	private void clearAll() {
		for (Vertex vertex : vertexMap.values()) {
			vertex.reset();
		}
	}

	/**
	 * Get distance between two vertices. 
	 */
	private float getDistance(Vertex start, Vertex end) {

		float dist = 0;
		if (edgeMap.containsKey(start.name) && edgeMap.get(start.name).containsKey(end.name)) {
			dist = edgeMap.get(start.name).get(end.name).edgeWeight;
		} else {
			new Graph(1, start.name, end.name);
			return -9;
		}

		return dist;
	}

	/**
	 * Check for Edge existance
	 */
	public boolean isEdgeCheck(String startName, String endName) {
		return edgeMap.containsKey(startName) && edgeMap.get(startName).containsKey(endName);
	}

	/**
	 * Dijkstra's algorithm implementation.
	 */
	public void dijkstraAlgoImpl(String startName, String endName) {
		clearAll();

		Vertex start = vertexMap.get(startName);
		if (start == null) {
			System.out.println("Start vertex does not exist. ");
			return;
		}

		if (start.vertexStatus.equals(DOWN)) {
			// System.out.println("Start vertex is down.");
			return;
		}

		MinHeapImpl queue = new MinHeapImpl();
		queue.addNewVertex(start);
		start.dist = 0;
		System.out.println("");

		while (!queue.isEmpty()) {
			Vertex verMin = queue.extractMinHeap();

			if (verMin == null) {
				//System.out.println("Path does not exist.");
				return;
			}

			if (verMin.name.equals(endName)) {
				break;
			}

			for (Vertex adjVer : verMin.adj) {
				if (adjVer.vertexStatus.equals(UP) && getDistance(verMin, adjVer) > 0
						&& adjVer.dist > verMin.dist + getDistance(verMin, adjVer)
						&& isEdgeCheck(verMin.name, adjVer.name)
						&& edgeMap.get(verMin.name).get(adjVer.name).edgeStatus.equals(UP)) {
					adjVer.dist = verMin.dist + getDistance(verMin, adjVer);
					adjVer.prev = verMin;
					queue.addNewVertex(adjVer);
				}
			}
		}

	}

	/**
	 * Print the current graph.
	 */
	public void printGraph(Graph graph) {
		System.out.println("");
		Set<String> vertices = graph.vertexMap.keySet();
		List<String> myList = new ArrayList<String>();
		myList.addAll(vertices);
		myList.sort(new VertexNameComparator());

		for (String vertex : myList) {
			String verStatus = graph.vertexMap.get(vertex).vertexStatus;
			if (!verStatus.isEmpty() && verStatus.equalsIgnoreCase(DOWN)) {
				System.out.print(vertex + "  " + DOWN + "\n");
			} else {
				System.out.print(vertex + "\n");
			}

			List<Vertex> adjList = graph.getVertex(vertex).adj;
			adjList.sort(new VertexComparator());
			for (Vertex innerVertex : adjList) {

				Edge edge = graph.edgeMap.get(vertex).get(innerVertex.name);
				String edgeStatus = edge.edgeStatus;

				if (!edgeStatus.isEmpty() && edgeStatus.equalsIgnoreCase(DOWN)) {
					System.out.println("  " + innerVertex.name + "  " + edge.edgeWeight + "  " + DOWN);
				} else {
					System.out.println("  " + innerVertex.name + "  " + edge.edgeWeight);
				}
			}
		}

	}

	/**
	 * Return the list of adjacent vertices.
	 */
	private ArrayList<Vertex> getAdjReachableVertex(Vertex vertex, ArrayList<Vertex> visitedVertex,
			ArrayList<Vertex> reachableVertex) {
		List<Vertex> adjVertex = vertex.adj;
		adjVertex.sort(new VertexComparator());
		Iterator<Vertex> itr = adjVertex.iterator();
		while (itr.hasNext()) {
			Vertex adjVer = (Vertex) itr.next();
			if (visitedVertex.indexOf(adjVer) == -1) {
				if (adjVer.vertexStatus.equals(UP)
						&& (edgeMap.containsKey(vertex.name) && edgeMap.get(vertex.name).containsKey(adjVer.name))
						&& edgeMap.get(vertex.name).get(adjVer.name).edgeStatus.equals(UP)) {
					visitedVertex.add(adjVer);
					reachableVertex.add(adjVer);
					getAdjReachableVertex(adjVer, visitedVertex, reachableVertex);
				}
			}
		}
		return reachableVertex;

	}

	/**
	 * Perform getting all the reachable vertices in the graph. It prints all
	 * the reachable vertices with Status UP and all for which there exist an
	 * edge with status UP. This implemented algorithm is a modified BFS. For
	 * one vertex the running time is O(|E| + |V|). Therefore for V vertex, the
	 * running time for this algorithm is O(|V|(|E| + |V|)).
	 * 
	 */
	public void perfReachable(Graph g) {
		List<Vertex> vertexList = new ArrayList<Vertex>(g.vertexMap.values());
		vertexList.sort(new VertexComparator());
        System.out.println();
		for (Vertex vertex : vertexList) {

			if (vertex.vertexStatus.equals(UP)) {
				System.out.println(vertex.name);
				ArrayList<Vertex> visitedVertex = new ArrayList<Vertex>();

				visitedVertex.add(vertex);
				ArrayList<Vertex> reachVertex = getAdjReachableVertex(vertex, visitedVertex, new ArrayList<Vertex>());
				reachVertex.sort(new VertexComparator());
				for (Vertex rVertex : reachVertex)
					System.out.println("  " + rVertex.name);
			}

		}

	}

	/**
	 * Process a request; return false if end of file.
	 */
	public boolean processRequest(String qLine, Graph g) {
		try {
			StringTokenizer st = new StringTokenizer(qLine);

			switch (st.countTokens()) {
			case 4:
				switch (st.nextToken().toLowerCase().trim()) {
				case "addedge":
					perfAddEdge(g, st);
					break;
				default:
					System.out.println("Invalid query");
					break;
				}
				break;
			case 3:
				switch (st.nextToken().toLowerCase().trim()) {
				case "path":
					findShortestPath(g, st);
					break;

				case "deleteedge":
					perfDeleteEdge(g, st);
					break;

				case "edgedown":
					perfEdgeDown(g, st);
					break;

				case "edgeup":
					perfEdgeUp(g, st);
					break;
					
				case "addedge":
					perfAddEdge(g, st);
					break;

				default:
					System.out.println("Invalid query");
					break;
				}
				break;
			case 2:
				switch (st.nextToken().toLowerCase().trim()) {
				case "vertexdown":
					perfVertexDown(g, st);
					break;
				case "vertexup":
					perfVertexUp(g, st);
					break;
				default:
					System.out.println("Invalid query");
					break;
				}
				break;
			case 1:
				switch (st.nextToken().toLowerCase().trim()) {
				case "print":
					printGraph(g);
					break;
				case "reachable":
					perfReachable(g);
					break;
				case "quit":
					return false;
				default:
					System.out.println("Invalid query");
					break;
				}
				break;
			default:
				System.out.println("Invalid query");
				break;
			}

		} catch (NoSuchElementException e) {
			return false;
		} catch (GraphException e) {
			System.out.println(e);
		}
		return true;
	}

	/**
	 * @param g
	 * @param st
	 * Make the Vertex UP if it exist.
	 */
	private void perfVertexUp(Graph g, StringTokenizer st) {
		String startVerName;
		startVerName = st.nextToken().trim();
		if (g.vertexMap.containsKey(startVerName)) {
			g.getVertex(startVerName).vertexStatus = UP;
		} else {
			System.out.println();
			new Graph(2);
		}

	}

	/**
	 * @param g
	 * @param st
	 * Make the Vertex DOWN if it exist.
	 */
	private void perfVertexDown(Graph g, StringTokenizer st) {
		String startVerName;
		startVerName = st.nextToken().trim();
		if (g.vertexMap.containsKey(startVerName)) {
			g.getVertex(startVerName).vertexStatus = DOWN;
		} else {
			System.out.println();
			new Graph(2);
		}

	}

	/**
	 * @param g
	 * @param st
	 * Make the Edge UP if it exist.
	 */
	private void perfEdgeUp(Graph g, StringTokenizer st) {
		String startVerName;
		String endVerName;
		startVerName = st.nextToken().trim();
		endVerName = st.nextToken().trim();
		if (g.edgeMap.containsKey(startVerName) && g.edgeMap.get(startVerName).containsKey(endVerName)) {
			g.edgeMap.get(startVerName).get(endVerName).edgeStatus = UP;
		} else {
			new Graph(1, startVerName, endVerName);
		}

	}

	/**
	 * @param g
	 * @param st
	 * Make the Vertex DOWN if it exist.
	 */
	private void perfEdgeDown(Graph g, StringTokenizer st) {
		String startVerName;
		String endVerName;
		startVerName = st.nextToken().trim();
		endVerName = st.nextToken().trim();
		if (g.edgeMap.containsKey(startVerName) && g.edgeMap.get(startVerName).containsKey(endVerName)) {
			g.edgeMap.get(startVerName).get(endVerName).edgeStatus = DOWN;
		} else {

			new Graph(1, startVerName, endVerName);
		}

	}

	/**
	 * @param g
	 * @param st
	 * Print the shortest path between two vertices.
	 */
	private void findShortestPath(Graph g, StringTokenizer st) {
		String startVerName;
		String endVerName;
		startVerName = st.nextToken().trim();
		endVerName = st.nextToken().trim();

		if (!vertexMap.containsKey(startVerName)) {
			new Graph(3);
		}

		if (!vertexMap.containsKey(endVerName)) {
			new Graph(4);
		}

		g.dijkstraAlgoImpl(startVerName, endVerName);
		g.printPath(endVerName);

	}

	/**
	 * @param g
	 * @param st
	 * Add a new edge to the network. If its an existing edge then update that edge.
	 */
	private void perfAddEdge(Graph g, StringTokenizer st) {
		String startVerName;
		String destVerName;
		float edgeWeight = 1;
		startVerName = st.nextToken().trim();
		destVerName = st.nextToken().trim();
		if (st.hasMoreTokens()){
			edgeWeight = Float.parseFloat(st.nextToken());
		}
		if (!startVerName.isEmpty() && !destVerName.isEmpty()) {
			g.addEdge(startVerName, destVerName, edgeWeight, false);
		}
	}

	/**
	 * @param g
	 * @param st
	 * Remove an edge from the network.
	 */
	private void perfDeleteEdge(Graph g, StringTokenizer st) {
		String startVerName;
		String endVerName;
		startVerName = st.nextToken().trim();
		endVerName = st.nextToken().trim();
		if (g.edgeMap.containsKey(startVerName) && g.edgeMap.get(startVerName).containsKey(endVerName)) {
			g.edgeMap.get(startVerName).remove(endVerName); 
			g.getVertex(startVerName).adj.remove(g.getVertex(endVerName));
		} else if (!startVerName.equals(endVerName)) {
			new Graph(1, startVerName, endVerName);
		}
	}

	/**
	 * Default Graph constructor
	 */
	public Graph() {
	}

	/**
	 * Graph Constructor to print error messages.
	 */
	public Graph(int errStatus, String... verticesName) {
		switch (errStatus) {
		case 0:
			System.out.println("Command not valid. ");
			break;
		case 1:
			System.out.println();
			System.out.println("There is no edge between " + verticesName[0] + " and " + verticesName[1]);
			break;
		case 2:
			
			System.out.println("The Vertex does not exist. ");
			break;
		case 3:
			// System.out.println("The Start vertex does not exist. ");
			break;
		case 4:
			// System.out.println("The End vertex does not exist. ");
			break;
		default:
			System.out.println("Error in Graph. ");
			break;
		}
	}

	/**
	 * A main routine that: 1. Reads a file containing edges (supplied as a
	 * command-line parameter); 2. Forms the graph; 3. Repeatedly prompts for
	 * two vertices and runs the shortest path algorithm. The data file is a
	 * sequence of lines of the format source destination
	 */
	public static void main(String[] args) {
		Graph g = new Graph();

		try {
			FileReader fin = new FileReader(args[0]);
			Scanner graphFile = new Scanner(fin);
			Scanner queryFile = new Scanner(System.in);

			// Read the edges and insert
			String line;
			while (graphFile.hasNextLine()) {
				line = graphFile.nextLine();
				StringTokenizer st = new StringTokenizer(line);

				try {
					if (st.countTokens() < 2) {
						System.out.println("Skipping ill-formatted line " + line);
						continue;
					}
					String startVertex = st.nextToken();
					String endVertex = st.nextToken();
					float weight = 1;
					if (st.hasMoreTokens()) {
						weight = Float.parseFloat(st.nextToken());
					}
					g.addEdge(startVertex, endVertex, weight, true);
				} catch (NumberFormatException e) {
					System.out.println("Skipping ill-formatted line " + line);
				}
			}

			String queryLine;
			while (queryFile.hasNextLine()) {
				queryLine = queryFile.nextLine().trim();
				g.processRequest(queryLine, g);
			}

		} catch (IOException e) {
			System.out.println(e);
		}
	}
}