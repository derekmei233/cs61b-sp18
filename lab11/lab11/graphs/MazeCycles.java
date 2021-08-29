package lab11.graphs;


import java.util.*;


/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private int s;
    private boolean[] connected;
    private Map<Integer, Integer> uf;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = 0;
        uf = new HashMap<>();
        for (int i = 0; i < maze.V() * maze.V(); i++) {
            uf.put(i, i);
        }
        connected = new boolean[maze.V() * maze.V()];
    }
    @Override
    public void solve() {
        Stack<Integer> stk = new Stack<>();
        stk.add(s);
        marked[s] = true;
        connected[s] = true;
        distTo[s] = 0;
        int tmp;
        while (!stk.isEmpty()) {
            tmp = stk.pop();
            marked[tmp] = true;
            distTo[tmp] = distTo[uf.get(tmp)] + 1;
            announce();
            for (int step: maze.adj(tmp)) {
                if (marked[step] && uf.get(tmp) != step) {
                    trace(step, tmp);
                    return;
                } else if (!connected[step]) {
                    stk.add(step);
                    uf.put(step, tmp);
                    connected[step] = true;
                }
            }
        }
    }

    // Helper methods go here
    private void trace(int i, int j) {
        int revisited = i;
        int source = j;
        // step = i -> revisited, tmp = j -> source
        Stack<Integer> sI = new Stack<>();
        Stack<Integer> sJ = new Stack<>();
        while (uf.get(i) != i) {
            sI.add(i);
            i = uf.get(i);
        }
        sI.add(i);
        while (uf.get(j) != j) {
            sJ.add(j);
            j = uf.get(j);
        }
        sJ.add(j);
        int ax = 0;
        while (!sI.empty() && sI.peek().equals(sJ.peek())) {
            ax = sI.pop();
            sJ.pop();
        }
        int lastIntersection = ax;
        // sI is revisited
        Queue<Integer> queue = new LinkedList<>();
        while (!sI.isEmpty()) {
            queue.add(sI.pop());
        }
        while (!sJ.isEmpty()) {
            int cur = sJ.pop();
            edgeTo[cur] = ax;
            ax = cur;
            announce();
        }
        edgeTo[revisited] = source;
        announce();
        while (!queue.isEmpty()) {
            int cur = queue.remove();
            edgeTo[lastIntersection] = cur;
            announce();
            lastIntersection = cur;
        }

    }
}

