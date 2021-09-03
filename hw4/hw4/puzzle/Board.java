package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements  WorldState {
    private int[][] state;
    private int N;
    private int col;
    private int row;
    private int[][] goal;
    private int[][] makeGoal() {
        int count = 1;
        goal = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                goal[i][j] = count % (N * N);
                count += 1;
            }
        }
        return goal;
    }
    public Board(int[][] tiles) {
        N = tiles.length;
        state = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                state[i][j] = tiles[i][j];
                if (state[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }
        goal = makeGoal();
    }
    public int tileAt(int i, int j) {
        if (j < 0 || j >= N || i < 0 || i >= N) {
            throw new IndexOutOfBoundsException();
        }
        return state[i][j];
    }
    public int size() {
        return N;
    }
    private Board copySwap(int or, int oc, int dr, int dc) {
        Board newBoard = new Board(state);
        int tmp = newBoard.state[or][oc];
        newBoard.state[or][oc] = newBoard.state[dr][dc];
        newBoard.state[dr][dc] = tmp;
        newBoard.col = dc;
        newBoard.row = dr;
        return newBoard;
    }
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        if (row - 1 >= 0) {
            neighbors.enqueue(this.copySwap(row, col, row - 1, col));
        }
        if (row + 1 <= N - 1) {
            neighbors.enqueue(this.copySwap(row, col, row + 1, col));
        }
        if (col - 1 >= 0) {
            neighbors.enqueue(this.copySwap(row, col, row, col - 1));
        }
        if (col + 1 <= N - 1) {
            neighbors.enqueue(this.copySwap(row, col, row, col + 1));
        }
        return neighbors;
    }
    public int hamming() {
        int err = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (state[i][j] != goal[i][j] && state[i][j] != 0) {
                    err += 1;
                }
            }
        }
        return err;
    }
    public int manhattan() {
        int err = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int cur = state[i][j];
                if (cur != 0) {
                    int r = (cur - 1) / N;
                    int c = (cur - 1) % N;
                    err += (Math.abs(i - r)) + (Math.abs(j - c));
                }
            }
        }
        return err;
    }
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board by = (Board) y;
        if (this.col != by.col || this.row != by.row) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (by.state[i][j] != this.state[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        int tN = size();
        s.append(tN + "\n");
        for (int i = 0; i < tN; i++) {
            for (int j = 0; j < tN; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
