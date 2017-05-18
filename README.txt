
/*
 * Name - Satish Kumar
 * 
 * Email ID - skumar34@uncc.edu
 * 
 * Student ID - 800966466
 * 
 * 
 * */


DESCRIPTION OF PROGRAM DESIGN: -
=============================

I have implemented 'Shortest path in a network' project by implementing Dijkstra's Algorithm. I have below classes and their corresponding functionality.

1) Graph.java --> This class implemented all functionality like to read the network file, construct a bidirectional graph out of that, Implementation
of all the changes to the graph, Finding the shortest path, Reachable vertices, Printing the Graph etc. 

2) Vertex.java --> This class implements the vertex parameters and the constructor to initialize the vertex.

3) Edge.java --> This class implements the edges parameters and the constructor to initialize the edge. 

4) VertexNameComparator.java --> This class implements two comparators, one to perform vertex name sorting and other to perform Vertex shorting.

5) MinHeapImpl.java --> This class implements the Binary Min Heap for using it as a queue in Dojkstr'a Algorithm.

For finding the shortest path, I am implemented the Dojkstra's Algorithm. I have implemented Binary min heap for using it as a queue in Dijkstra's Algorithm.
For printing the reachable vertices, I am implemented a modified BFS algorithm, where we find the adjacent vertices of a Vertex and stor that in a List. Again 
we keep finding the adjacent vertices of the child vertex and keep storing it in a List if its not already present in that list. If an adjacent vertex
is not already traversed then we print it as a rechable vertices. 

All the input are system standard input and all outputs are system standard output. The network.txt input file we are passing as command line argument.

Running Time:-
=============

For each Vertices we execute the BFS algorithm. So running time for each vertex is O(|E| + |V|). Since there are total |V| vertices, so the 
Total Running time = O(|V|(|E|+|V|)).
  

DATA STRUCTURE DESIGN: -
====================== 
For mapping the vertex and edges of a Graph, I am using a HashMap as vetrexMap & edgeMap as described below.

Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();
Map<String, Map<String, Edge>> edgeMap = new HashMap<String, Map<String, Edge>>();

For Dijkstra's Algorithm, I have implementes Min Heap, as described above.

BREAKDOWN OF FILES: -
===================

1) Graph.java
2) Vertex.java
3) Edge.java
4) VertexNameComparator.java
5) MinHeapImpl.java
6) README.txt
7) network.txt 
8) queries.txt
9) output.txt

SUMMARY OF WHAT WORKS IN PROGRAM: -
=================================
My Program can read the provided input network file. Implements all functionality like to read the network file, construct a bidirectional graph out of that, Implementation
of all the changes to the graph, Finding the shortest path, Reachable vertices, Printing the Graph. It also handles all the error situations.

PROGRAMMING LANGUAGE AND COMPILER VERSION USED: -
===============================================

Programming Language - Java (java version "1.8.0_102")
Compiler Version - javac 1.8.0_101
 
HOW TO RUN THE PROGRAM: -
=======================

For Encoder: -
------------
Step 0: Keep network.txt and queries.txt inside the src folder, where our source code is present.

Step1: Go to src directory where our source code is present

cd C:\WorksapceProject\GraphAssignment\src>

Step 2: Compile the code

javac Graph.java

Step 3: provide the command line argument for network.txt and system standard input for queries.txt 
and dump the output as system standatd to a text file.

java Graph network.txt < queries.txt > output.txt

Step 4: See the output in the output file outputfile.txt generated


