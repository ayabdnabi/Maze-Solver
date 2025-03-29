/*
 * Name: Aya Abdul Nabi
 * Student ID: 251307615
 * CS2210 Programming Assignment 3
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// this class is used to represent the actual maze
public class Maze {
	// variables to represent the graph, starting point. ending point, number of coins, and the current path
	private Graph graph;
	private int coins, start, end;
	private List<GraphNode> path;
	

	// constructor method used to read the input file and builds the graph based off the maze
	public Maze(String inputFile) throws MazeException {
		try {
			// open the input file and read the base
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			readInput(reader);
			// if file does not exist or the format is wrong, throw an exception
		} catch (IOException | GraphException e){
			e.printStackTrace();
		} 
	}

	// getter method to return the graph
	public Graph getGraph() throws MazeException {
		// if the graph is null, throw an exception
		if (graph == null){
			throw new MazeException("Graph is null");
		}
		// otherwise return the graph normally
		return graph;
	}

	// returns an iterator containing the nodes of the path from start -> finish
	public Iterator<GraphNode> solve() {
		try {
			// initialize the path as an empty list
			path = new ArrayList<>();
			Iterator<GraphNode> resultingPath;
			// if the path exists, return the path after performing DFS
			if (path !=null){
				resultingPath = DFS(coins, graph.getNode(start));
				return resultingPath;
			} else {
				// otherwise return null
				return null;
			}
		} catch (GraphException e){
			return null;
		}
	}

	// this method is used to perform a DFS traversal
	private Iterator<GraphNode> DFS(int k, GraphNode go) throws GraphException {
		go.mark(true); // mark the node you are currently on as marked
		path.add(go); // add current node to path list
		// if we reached the end of the maze, return the path sequence 
		if (go.getName() == end) {
			return path.iterator();
		}
	
		// get all incident edges for the current node
		Iterator<GraphEdge> edges = graph.incidentEdges(go);
		// traverse through all the edges of go as long as it isn't null and it actually has an adjacent edge
		while (edges != null && edges.hasNext()) {
			GraphEdge edge = edges.next();
			
			// determine the adjacent node to go depending on if it is the first or second endpoint 
			GraphNode adj;
			if (edge.firstEndpoint().equals(go)) {
				adj = edge.secondEndpoint(); // if it is the first, adj becomes the second end point
			} else {
				adj = edge.firstEndpoint(); // if it is the second, adj becomes the first end point
			}
	
			// if the adjacent node isn't marked AND we have enough coins
			if (!adj.isMarked() && k >= edge.getType()) {
				// calculate the amount of coins we have after traversing an edge
				int remainingCoins = k - edge.getType();
				// if we have enough coins to continue through the maze, continue the DFS from the adjacent node
				if (remainingCoins >= 0) {
					Iterator<GraphNode> correctPath = DFS(remainingCoins, adj); // recursively call DFS with the remaining coins and adjacent node
					// if a valid path is found, return it
					if (correctPath != null) {
						return correctPath;
					}
				}
			}
		}
		// backtrack if no valid path is found from the node
		// unmark the current node and remove it from the math
		go.mark(false);
		path.remove(path.size() - 1); 
		return null; // return  null to show that no valid path was found
	}
	

	// this method is used to read the actual content of the maze
	private void readInput(BufferedReader inputReader) throws IOException, GraphException {
		String scale = inputReader.readLine(); // this is ignored
		// read the length, width, and the number of coins we start off with
		int width = Integer.parseInt(inputReader.readLine());
		int length = Integer.parseInt(inputReader.readLine());
		coins = Integer.parseInt(inputReader.readLine());

		graph = new Graph(width * length); // create a new graph from that width and lenght

		String line;
		List<String> lines = new ArrayList<>(); // hold all the lines from the input file

		// read the maze layout into a list of lines
		while ((line = inputReader.readLine())!= null){
			lines.add(line);
		}

		// process the actual maze layout
		for (int i = 0; i < lines.size(); i++){
			String mazePath = lines.get(i);
			// skip empty lines
			if (mazePath.length() == 0){
				continue;
			}

			// determine if the row is even or add, and handle it accordingly
			if (i % 2 == 0){
				processEvenRow(mazePath, i, width); // process rooms and horizontal edges
			} else {
				processOddRow(mazePath, i, width); // process odd rows (vertical edges)
			}
		}
	}

	// method used to process rows with an even index 
	private void processEvenRow(String mazePath, int i, int width) throws GraphException{
		for (int j = 0; j < mazePath.length(); j++){
			// if the column is even
			if (j % 2 == 0){
				char room = mazePath.charAt(j); // get the character representing the room
				int ID = (i / 2) * width + (j / 2); // calculate the ID based on rows and cols
				// set the start or end node based on the room character
				if (room == 's'){
					start = ID; // starting point
				} else if (room == 'x'){
					end = ID; // ending point
				}
				// otherwise, if the column is odd
			} else {
				char edge = mazePath.charAt(j); // get the character representing the edge
				if (edge != 'w'){ // skip the 'w' walls
					int firstNode = (i / 2) * width + (j - 1) / 2; // calculate the first node of the edge
					int secondNode = (i / 2) * width + (j + 1) / 2; // calculate the second node of the edge
					int typeEdge;
					// determine the edge type (0 for corridor, numeric for doors)
					if (edge == 'c') {
						typeEdge = 0;
					} else {
						typeEdge = Character.getNumericValue(edge);
					}
					String edgeLabel;
					// determine the edge label (c for corridor, d for door)
					if (edge == 'c'){
						edgeLabel = "corridor";
					} else {
						edgeLabel = "door";
					}
					// insert edge in graph
					insertEdge(firstNode, secondNode, typeEdge, edgeLabel);
				}
			}
		}
	}

	// method used to process rows with odd index (representing vertical edges)
	private void processOddRow(String mazePath, int i, int width) throws GraphException{
		for (int j = 0; j < mazePath.length(); j++){
			// if the column is even
			if (j % 2 == 0){ 
				char edge = mazePath.charAt(j); // get the character representing the edge
				if (edge != 'w'){ // skip the 'w' walls
					int firstNode = ((i - 1)/2)*width + (j / 2); // calculate the first node of the edge
					int secondNode = ((i+1)/2)*width + (j/2); // calculate the second node of the edge
					int typeEdge;
					// determine the edge type (0 for corridor, numeric for doors)
					if (edge == 'c') {
						typeEdge = 0;
					} else {
						typeEdge = Character.getNumericValue(edge);
					}
					String edgeLabel;
					// determine the edge label (c for corridor, d for door)
					if (edge == 'c'){
						edgeLabel = "corridor";
					} else {
						edgeLabel = "door";
					}
					// insert the edge into the graph
					insertEdge(firstNode, secondNode, typeEdge, edgeLabel);
				}
			}
		}
	}


	// inserts an edge between two nodes
	private void insertEdge(int node1, int node2, int linkType, String label) throws GraphException {
		try {
			// get the first and second node and try inserting it with its type and label
			GraphNode firstNode = graph.getNode(node1);
			GraphNode seconedNode = graph.getNode(node2);
			graph.insertEdge(firstNode, seconedNode, linkType, label);
		} catch (GraphException e) {
			e.printStackTrace(); // throw a graph exception if you can't insert
		}
	}

}
