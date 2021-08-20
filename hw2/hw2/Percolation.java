package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.net.CookieHandler;

public class Percolation {
    private final int units;
    private int numOfOpen;
    private WeightedQuickUnionUF uf;
    private int[] sites;

    public Percolation(int N) throws IllegalArgumentException{
        // create N-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("N should greater than 0");
        }
        units = N;
        numOfOpen = 0;
        uf = new WeightedQuickUnionUF(units * units);
        sites = new int[units * units];
    }

    private void checkIndex(int row, int col) throws IndexOutOfBoundsException {
        if (row < 0 || row >= units || col < 0 || col >= units) {
            throw new IndexOutOfBoundsException("invalid (row, col) combination");
        }
    }

    private int cordToIndex(int x, int y) {
        return x * units + y;
    }

    private void neighboring(int row, int col) {
        if (row - 1 >= 0 && sites[cordToIndex(row - 1, col)] == 1) {
            uf.union(cordToIndex(row - 1, col), cordToIndex(row, col));
        }
        if (row + 1 <= units - 1 && sites[cordToIndex(row + 1, col)] == 1) {
            uf.union(cordToIndex(row + 1, col), cordToIndex(row, col));
        }
        if (col - 1 >= 0 && sites[cordToIndex(row, col - 1)] == 1) {
            uf.union(cordToIndex(row, col - 1), cordToIndex(row, col));
        }
        if (col + 1 <= units - 1 && sites[cordToIndex(row, col + 1)] == 1) {
            uf.union(cordToIndex(row, col + 1), cordToIndex(row ,col));
        }
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        checkIndex(row, col);
        sites[cordToIndex(row, col)] = 1;
        numOfOpen += 1;
        neighboring(row, col);
    }
    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        checkIndex(row, col);
        return sites[cordToIndex(row, col)] == 1;
    }
    public boolean isFull(int row, int col) {
        // is the site(row, col) full?
        checkIndex(row, col);
        if (sites[cordToIndex(row, col)] == 0) {
            return false;
        }
        int pos = uf.find(cordToIndex(row, col));
        for (int i = 0; i < units; i ++) {
            if (uf.find(i) == pos) {
                return true;
            }
        }
        return false;
    }
    public int numberOfOpenSites() {
        // number of open sites
        return numOfOpen;
    }
    public boolean percolates() {
        // does the system percolate?
        for (int i = 0; i < units; i++) {
            if (isFull(units - 1, i)) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        // unittest here
    }
}
