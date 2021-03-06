package hw2;

import edu.princeton.cs.algs4.StdStats;

import static edu.princeton.cs.algs4.StdRandom.*;


public class PercolationStats {
    private final int units;
    private PercolationFactory pF;
    private int[] mem;

    public PercolationStats(int N, int T, PercolationFactory pf) throws IllegalArgumentException {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("invalid parameters");
        }
        units = N;
        pF = pf;
        mem = new int[T];
        Percolation percoSys;
        int count;
        for (int i = 0; i < T; i++) {
            int[] que = creatShuffledQueue();
            percoSys = pF.make(N);
            count = 0;
            while (!percoSys.percolates()) {
                percoSys.open(que[count] / units, que[count] % units);
                count += 1;
            }
            mem[i] = count;
        }
    }
    private int[] creatShuffledQueue() {
        int[] que = new int[units * units];
        for (int i = 0; i < que.length; i++) {
            que[i] = i;
        }
        shuffle(que);
        return que;
    }
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(mem) / (units * units);
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(mem) / (units * units);
    }
    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(mem.length);
    }
    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(mem.length);
    }
}
