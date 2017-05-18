/*
 * Name - Satish Kumar
 * 
 * Email ID - skumar34@uncc.edu
 * 
 * Student ID - 800966466
 * 
 * */

import java.util.ArrayList;
import java.util.List;


/**
 * Min Heap Implementation class for the graph
 */
public class MinHeapImpl {

	public List<Vertex> minHeap;

	public MinHeapImpl() {
		minHeap = new ArrayList<Vertex>();
		minHeap.add(null);
	}
	
	/**
	 * get minimum Vertex to the Min Heap
	 */
	public Vertex extractMinHeap() {

		if (minHeap.size() > 1) {
			Vertex verExtracted = minHeap.get(1);
			int index = minHeap.size() - 1;
			Vertex endVertex = minHeap.remove(index);
			if (index > 1) {
				minHeap.set(1, endVertex);
				reConstructHeap();
			}
			return verExtracted;
		} else
			return null;
	}
	
	/**
	 * Reconstructing the min heap
	 */
	private void reConstructHeap() {

		Vertex rootVertex = minHeap.get(1);
		int rootIndex = 1;
		int lastIndex = minHeap.size() - 1;

		while (true) {
			int childVerIndex = rootIndex * 2;
			int rightChildIndex = (rootIndex * 2) + 1;

			if (childVerIndex > lastIndex) {
				break;
			}

			Vertex childVertex = getLeftChildVertex(rootIndex);

			if ((rightChildIndex) <= lastIndex && getRightChildVertex(rootIndex).dist < childVertex.dist) {
				childVerIndex = rightChildIndex;
				childVertex = getRightChildVertex(rootIndex);
			}

			if (childVertex.dist > rootVertex.dist) {
				break;
			}
			minHeap.set(rootIndex, childVertex);
			rootIndex = childVerIndex;
		}
		minHeap.set(rootIndex, rootVertex);
	}

	/**
	 * Check for empty heap
	 */
	public boolean isEmpty() {

		return minHeap.size() == 0;
	}

	/**
	 * Get Parent Vertex
	 */
	private Vertex getParentVertex(int verIndex) {

		return minHeap.get(verIndex / 2);
	}

	/**
	 * Get Left Child Vertex
	 */
	private Vertex getLeftChildVertex(int verIndex) {

		return minHeap.get(2 * verIndex);
	}

	/**
	 * Get Right Child Vertex
	 */
	private Vertex getRightChildVertex(int verIndex) {

		return minHeap.get(2 * verIndex + 1);
	}

	/**
	 * Add Vertex to the Min Heap
	 */
	public void addNewVertex(Vertex ver) {
		minHeap.add(null);
		int verIndex = minHeap.size() - 1;

		while ((verIndex > 1) && getParentVertex(verIndex).dist > ver.dist) {
			minHeap.set(verIndex, getParentVertex(verIndex));
			verIndex = verIndex / 2;
		}

		minHeap.set(verIndex, ver);
	}

	

	
	
	/**
	 * Display Min Heap
	 */
	public void displayMinHeap() {
		List<Vertex> minHeapxList = minHeap.subList(1, minHeap.size());

		for (Vertex vertex : minHeapxList) {
			System.out.println(vertex.dist);
		}
	}
}