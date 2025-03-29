/*
 * Name: Aya Abdul Nabi
 * Student ID: 251307615
 * CS2210 Programming Assignment 3
 */

// this class represents an edge of a graph
public class GraphEdge {
	
	// private variables such as a starting point, ending point, type of edge and label of edge
	private GraphNode start;
	private GraphNode destination;
	private int type;
	private String edgeLabel;
	
	// constructor used to initialize the variables
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.start = u;
		this.destination = v;
		this.type = type;
		this.edgeLabel = label;
	}
	
	// getter method to get the first endpoint of an edge
	public GraphNode firstEndpoint() {
		return start;
	}
	
	// getter method to get the second endpoint of an edge
	public GraphNode secondEndpoint() {
		return destination;
	}
	
	// getter method to get the type of an edge
	public int getType() {
		return type;
	}
	
	// setter method to set the type of an edge to a specific value
	public void setype(int type) {
		this.type = type;
	}
	
	// getter method to get the label of an edge
	public String getLabel() {
		return edgeLabel;
	}
	
	// setter method to set the label of an edge
	public void setLabel(String label) {
		this.edgeLabel = label;
	}
	
}