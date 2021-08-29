package lab11.graphs;

import edu.princeton.cs.algs4.Queue;


/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> queue = new Queue<>();
        marked[s] = true;
        queue.enqueue(s);
        int temp;
        while (!queue.isEmpty()) {
            temp = queue.dequeue();
            if (temp == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
            for (int step: maze.adj(temp)) {
                if (!marked[step]) {
                    distTo[step] = distTo[temp] + 1;
                    edgeTo[step] = temp;
                    marked[step] = true;
                    queue.enqueue(step);
                }
            }
            announce();
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

