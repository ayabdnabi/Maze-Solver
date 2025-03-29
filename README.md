# Maze-Solver
This Java application solves mazes with special door mechanics where:
- Each door requires a specific number of coins (0-9) to open
- The solver must find a path from entrance to exit within a given coin budget
- Coins cannot be reused once spent

The program represents mazes as undirected graphs and uses a modified DFS algorithm to find valid paths while tracking coin usage.

### How to run?
Prerequisites
- Java JDK 8 or later

## Running the program
Compile the code:
- javac *.java

Run the code:
- java Solve path/to/maze_file.txt
  
Example maze files are included in the repository.
In order to see the person actually move, make sure to press ENTER in terminal in order to see which path he takes!
