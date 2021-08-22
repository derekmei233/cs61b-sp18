package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int units;
    private int numOfOpen;
    private WeightedQuickUnionUF uf;
    private int[] sites;
    private int[] full;
    private int flag;

    public Percolation(int N) throws IllegalArgumentException {
        // create N-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("N should greater than 0");
        }
        units = N;
        numOfOpen = 0;
        uf = new WeightedQuickUnionUF(units * units);
        sites = new int[units * units];
        full = new int[units * units];
        flag = 0;
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
        int check = 0;
        if ((row - 1 >= 0 && full[cordToIndex(row - 1, col)] == 1)
                || (row + 1 <= units - 1 && full[cordToIndex(row + 1, col)] == 1)
                || (col - 1 >= 0 && full[cordToIndex(row, col - 1)] == 1)
                || (col + 1 <= units - 1 && full[cordToIndex(row, col + 1)] == 1)) {
            check = 1;
        }

        if (row - 1 >= 0 && sites[cordToIndex(row - 1, col)] == 1) {
            uf.union(cordToIndex(row - 1, col), cordToIndex(row, col));
            if (check == 1) {
                full[cordToIndex(row - 1, col)] = 1;
                full[cordToIndex(row, col)] = 1;
            }
        }
        if (row + 1 <= units - 1 && sites[cordToIndex(row + 1, col)] == 1) {
            uf.union(cordToIndex(row + 1, col), cordToIndex(row, col));
            if (check == 1) {
                full[cordToIndex(row + 1, col)] = 1;
                full[cordToIndex(row, col)] = 1;
            }
        }
        if (col - 1 >= 0 && sites[cordToIndex(row, col - 1)] == 1) {
            uf.union(cordToIndex(row, col - 1), cordToIndex(row, col));
            if (check == 1) {
                full[cordToIndex(row, col - 1)] = 1;
                full[cordToIndex(row, col)] = 1;
            }
        }
        if (col + 1 <= units - 1 && sites[cordToIndex(row, col + 1)] == 1) {
            uf.union(cordToIndex(row, col + 1), cordToIndex(row, col));
            if (check == 1) {
                full[cordToIndex(row, col + 1)] = 1;
                full[cordToIndex(row, col)] = 1;
            }
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
        if (full[row * units + col] == 1) {
            return true;
        }
        int pos = uf.find(cordToIndex(row, col));
        for (int i = 0; i < units; i++) {
            if (uf.find(i) == pos) {
                full[row * units + col] = 1;
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
        if (flag == 1) {
            return true;
        }
        for (int i = 0; i < units; i++) {
            if (isFull(units - 1, i)) {
                flag = 1;
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        // unittest here
    }
}
