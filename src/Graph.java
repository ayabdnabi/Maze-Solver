/*
 * Name: Aya Abdul Nabi
 * Student ID: 251307615
 * CS2210 Programming Assignment 3
 */

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 
 // this class represents the actual undirected graph using an adjacency list
 public class Graph implements GraphADT {
	 
	 // map that stores nodes and their corresponding list of edges
	 private Map<GraphNode, List<GraphEdge>> adjacencyList;
	 // map that stores nodes
	 private Map<Integer, GraphNode> nodes;
	 
	 
	 // construcor used to initialize graph with 'n' nodes and no edges
	 public Graph(int n) {
		 adjacencyList = new HashMap<>();
		 nodes = new HashMap<>();
		 // intitialize the graph nodes and adjacency list for each node
		 for (int i = 0; i < n; i++) {
			 GraphNode node = new GraphNode(i);
			 nodes.put(i, node);
			 adjacencyList.put(node, new ArrayList<>());
		 }
	 }
	 
	 // this method inserts an undirected edge between two nodes
	 @Override
	 public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
		 // if neither node actually exists, throw an exceptipn
		 if (!adjacencyList.containsKey(nodeu) || !adjacencyList.containsKey(nodev)) {
			 throw new GraphException("Key does not exist");
		 }
		 
		 // if an edge already exists between the two nodes, throw an exception
		 List<GraphEdge> edges = adjacencyList.get(nodeu);
		 for (int i = 0; i < edges.size(); i++) {
			 if (edges.get(i).secondEndpoint().equals(nodev)) {
				 throw new GraphException("Edge already exists");
			 }
		 }
		 
		 // otherwise, insert the edge between the two nodes both ways (u to v, v to u) since this is an undirected graph
		 GraphEdge edge = new GraphEdge(nodeu, nodev, type, label);
		 adjacencyList.get(nodeu).add(edge);
			 
		 GraphEdge reversed = new GraphEdge(nodev, nodeu, type, label);
		 adjacencyList.get(nodev).add(reversed);
		 }
		 
 
	 // this method is used to get a node with a specific name
	 @Override
	 public GraphNode getNode(int u) throws GraphException {
		 // if no node with the name exists, throw an exception
		 if (!nodes.containsKey(u)) {
			 throw new GraphException("No node with this name exists");
		 }
		 // otherwise, return the name
		 return nodes.get(u);
	 }
 
	 // this method is used to store all the edges incident on a specific node
	 @Override
	 public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		 // if the node doesn't exist, throw an exception
		 if (!adjacencyList.containsKey(u)) {
			 throw new GraphException("Node does not exist");
		 }
		 
		 // get the list of edges for that specific node
		 List<GraphEdge> edges = adjacencyList.get(u);
		 if (edges.isEmpty()) {
			 return null; // if there are no edges incident on that node, return null
		 } else {
			 return edges.iterator(); // otherwise, iterate over the list of edges
		 }
	 }
 
	 // this method is used to get the edge connecting two nodes
	 @Override
	 public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		 // if neither of the nodes exist, throw an exception
		 if (!adjacencyList.containsKey(u) || !adjacencyList.containsKey(v)) {
			 throw new GraphException("Keys do not exist");
		 }
		 
		 // get the list of edges for that specific node
		 List<GraphEdge> edges = adjacencyList.get(u);
		 // if the destination of u is v, return the edge
		 for (int i = 0; i < edges.size(); i++) {
			 if (edges.get(i).secondEndpoint().equals(v)) {
				 return edges.get(i);
			 }
		 }
		 // otherwise, return the fact that no edge exists between them
		 throw new GraphException("No edge exists");
	 }
 
	 // this method checks if two nodes are adjacent 
	 @Override
	 public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		 // if neither node exists, throw an exception
		 if (!adjacencyList.containsKey(u) || !adjacencyList.containsKey(v)) {
			 throw new GraphException("Keys do not exist");
		 }
		 
		 // get the list of edges for node u
		 List<GraphEdge> edges = adjacencyList.get(u);
		 // if the destination of u is v, return true, meaning they are adjacent
		 for (int i = 0; i < edges.size(); i++) {
			 if (edges.get(i).secondEndpoint().equals(v)) {
				 return true;
			 }
		 }
		 // otherwise, return false, as they are not false
		 return false;
	 }
 
 }