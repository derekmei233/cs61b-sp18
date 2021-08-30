package lab11.graphs;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private PriorityQueue<Integer> minPQ;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        minPQ = new PriorityQueue<Integer>(100, new MyComparator());
    }
    private class MyComparator implements Comparator<Integer> {
        public int compare(Integer i, Integer j) {
            return Integer.compare(h(i), h(j));
        }
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int distance;
        distance = distTo[v] + Math.abs(maze.toX(v) - maze.toX(t))
                + Math.abs(maze.toY(v) - maze.toY(t));
        return distance;
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        if (!minPQ.isEmpty()) {
            return minPQ.peek();
        }
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        minPQ.add(s);
        marked[s] = true;
        while (!minPQ.isEmpty()) {
            int cur = minPQ.remove();
            for (int i: maze.adj(cur)) {
                if (!marked[i]) {
                    minPQ.add(i);
                    marked[i] = true;
                    distTo[i] = distTo[cur] + 1;
                    edgeTo[i] = cur;
                    announce();
                    if (marked[t]) {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

