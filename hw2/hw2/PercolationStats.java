package hw2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PercolationStats {
    private Percolation simulate;
    private int simulateNum;
    private int[] percolationRusult;
    private int gridsize;
    private Random r1;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        simulateNum = T;
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Illegal Values");
        }
        simulate = pf.make(N);
        gridsize = N;
        percolationRusult = new int[T];
        int j = 0;
        while (j < T) {
            List<Integer> l1 = new ArrayList<>();
            for (int i = 0; i < N * N; i++) {
                l1.add(i);
            }
            int randomNum;
            while(!simulate.percolates()) {
                randomNum = r1.nextInt(l1.size() - 1);
                simulate.open(0, l1.get(randomNum));
                l1.remove(randomNum);
            }
            percolationRusult[j] = simulate.numberOfOpenSites();
            j++;
        }
    }
    // perform T independent experiments on an N-by-N grid
    public double mean() {
        int percolationSum = 0;
        for (int a : percolationRusult) {
            percolationSum += a;
        }
        return percolationSum/simulateNum;
    }
    // sample mean of percolation threshold
    public double stddev() {
        int percolationSquareSum = 0;
        for (int a : percolationRusult) {
            percolationSquareSum += (a - mean()) * (a - mean());
        }
        return Math.sqrt(percolationSquareSum/(simulateNum - 1));
    }
    // sample standard deviation of percolation threshold
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(simulateNum);
    }
    // low endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(simulateNum);
    }
    // high endpoint of 95% confidence interval
}
