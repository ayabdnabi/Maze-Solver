/*
 * Name: Aya Abdul Nabi
 * Student ID: 251307615
 * CS2210 Programming Assignment 3
 */

// This class represents a node in a graph
public class GraphNode {
	// private variables for this specific class
	private int newName;
	private boolean newMark;
	
	// constructor used to initialize variables
	public GraphNode(int name) {
		this.newName = name;
		this.newMark = false;
	}

	// setter method for mark
	public void mark(boolean mark) {
		this.newMark = mark;
	}
	
	// returns the value with which the node has been marked 
	public boolean isMarked() {
		return this.newMark;
	}
	
	// getter method to return the name of the node
	public int getName() {
		return this.newName;
	}
	
}
