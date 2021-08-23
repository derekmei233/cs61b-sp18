package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int units;
    private int numOfOpen;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf4full;
    private int[] sites;
    private int flag;

    public Percolation(int N) throws IllegalArgumentException {
        // create N-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("N should greater than 0");
        }
        units = N;
        numOfOpen = 0;
        uf = new WeightedQuickUnionUF(units * units + 2);
        uf4full = new WeightedQuickUnionUF(units * units + 1);
        sites = new int[units * units];
        flag = 0;
        // uf: units * units is the index for virtual top node
        // and units * units + 1 is the index for virtual bottom node.
        // uf4full: units * units is the index for virtual top node.
        for (int i = 0; i < units; i++) {
            uf.union(i, units * units);
            uf.union(i + units * (units - 1), units * units + 1);
            uf4full.union(i, units * units);
        }
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
            uf4full.union(cordToIndex(row - 1, col), cordToIndex(row, col));
        }
        if (row + 1 <= units - 1 && sites[cordToIndex(row + 1, col)] == 1) {
            uf.union(cordToIndex(row + 1, col), cordToIndex(row, col));
            uf4full.union(cordToIndex(row + 1, col), cordToIndex(row, col));
        }
        if (col - 1 >= 0 && sites[cordToIndex(row, col - 1)] == 1) {
            uf.union(cordToIndex(row, col - 1), cordToIndex(row, col));
            uf4full.union(cordToIndex(row, col - 1), cordToIndex(row, col));
        }
        if (col + 1 <= units - 1 && sites[cordToIndex(row, col + 1)] == 1) {
            uf.union(cordToIndex(row, col + 1), cordToIndex(row, col));
            uf4full.union(cordToIndex(row, col + 1), cordToIndex(row, col));
        }
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        checkIndex(row, col);
        if (sites[cordToIndex(row, col)] == 1) {
            return;
        }
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
        return uf4full.find(units * units) == uf4full.find(cordToIndex(row, col));
    }
    public int numberOfOpenSites() {
        // number of open sites
        return numOfOpen;
    }
    public boolean percolates() {
        // does the system percolate?
        if (units == 1) {
            return sites[cordToIndex(0,0)] == 1;
        }
        return uf.find(units * units) == uf.find(units * units + 1);
    }
    public static void main(String[] args) {
        // unittest here
    }
}
