package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    private static class WorldSequence implements Iterable<WorldState> {
        private ArrayList<WorldState> worlds;
        WorldSequence() {
            worlds = new ArrayList<>();
        }
        class WorldIterator implements Iterator<WorldState> {
            int pos;
            WorldIterator() {
                pos = worlds.size() - 1;
            }
            public boolean hasNext() {
                return pos != -1;
            }
            public WorldState next() {
                WorldState result = worlds.get(pos);
                pos -= 1;
                return result;
            }
        }
        public int size() {
            return worlds.size();
        }
        public void add(WorldState w) {
            worlds.add(w);
        }
        public Iterator<WorldState> iterator() {
            return new WorldIterator();
        }
    }
    private static class MyComparator implements Comparator<WorldState> {
        public int compare(WorldState i, WorldState j) {
            return Integer.compare(computeDistance(i), computeDistance(j));
        }
    }
    private static WorldSequence ws;
    private static MinPQ<WorldState> minPQ;
    private static HashMap<WorldState, Integer> marker;
    private static HashMap<WorldState, WorldState> linker;
    private static HashMap<WorldState, Integer> mem;
    private static HashSet<WorldState> visited;
    public Solver(WorldState initial) {
        ws = new WorldSequence();
        minPQ = new MinPQ<>(new MyComparator());
        marker = new HashMap<>();
        linker = new HashMap<>();
        visited = new HashSet<>();
        mem = new HashMap<>();
        if (initial.isGoal()) {
            ws.add(initial);
            return;
        }
        minPQ.insert(initial);
        linker.put(initial, initial);
        marker.put(initial, 0);
        while (!minPQ.isEmpty()) {
            WorldState cur = minPQ.delMin();
            visited.add(cur);
            if (cur.isGoal()) {
                trace(cur);
                return;
            }

            for (WorldState neighbor: cur.neighbors()) {
                if (!visited.contains(neighbor)) {
                    if (marker.containsKey(neighbor)
                            && (marker.get(neighbor) > (marker.get(cur) + 1))) {
                        rearrange(neighbor, cur);
                    } else if (!marker.containsKey(neighbor)) {
                        linker.put(neighbor, cur);
                        marker.put(neighbor, marker.get(cur) + 1);
                        minPQ.insert(neighbor);
                    }
                }
            }
        }
        System.out.println("wrong way");
    }
    private static int computeDistance(WorldState w) {
        if (!mem.containsKey(w)) {
            mem.put(w, w.estimatedDistanceToGoal());
        }
        return mem.get(w) + marker.get(w);
    }
    private void trace(WorldState n) {
        while (linker.get(n) != n) {
            ws.add(n);
            n = linker.get(n);
        }
        ws.add(n);
    }
    private void rearrange(WorldState neighbor, WorldState cur) {
        // no priority changing operation in MinPQ
        MinPQ<WorldState> tmp = new MinPQ<>(new MyComparator());
        while (!minPQ.isEmpty()) {
            WorldState wq = minPQ.delMin();
            if (wq.equals(neighbor)) {
                linker.put(neighbor, cur);
                marker.put(neighbor, marker.get(cur) + 1);
                minPQ.insert(neighbor);
                break;
            } else {
                tmp.insert(wq);
            }
        }
        while (!tmp.isEmpty()) {
            minPQ.insert(tmp.delMin());
        }
    }
    public int moves() {
        return ws.size() - 1;
    }
    public Iterable<WorldState> solution() {
        return ws;
    }
}
