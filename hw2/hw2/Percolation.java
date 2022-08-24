package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF simulate;
    private boolean[] isopem;
    private int gridsize;
    private int openNum;

    public Percolation(int N) {

        if (N <= 0) {
            throw new IllegalArgumentException("N cannot < 0");
        }

        simulate = new WeightedQuickUnionUF(N * N);
        gridsize = N;
        isopem = new boolean[N * N];
        for (Boolean a : isopem) {
            a = false;
        }
        openNum = 0;
    }

    public void open(int row, int col) {
        if (!isopem[row * gridsize + col]) {
            isopem[row * gridsize + col] = true;
            openNum++;
        }
        int[] a = {-1, 1, -gridsize, +gridsize};
        for (int t : a) {
            if (row * gridsize + col + t < 0 || row * gridsize + col + t > gridsize * gridsize -1) {
                continue;
            }
            if ((row * gridsize + col + t) % gridsize == 0 && (row * gridsize + col) % gridsize == gridsize - 1) {
                continue;
            }
            if ((row * gridsize + col) % gridsize == 0 && (row * gridsize + col + t) % gridsize == gridsize - 1) {
                continue;
            }
            if (isopem[row * gridsize + col + t]) {
                simulate.union(row * gridsize + col, row * gridsize + col + t);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return isopem[row * gridsize + col];
    }

    public boolean isFull(int row, int col) {
        for (int i = 0; i < gridsize; i++) {
            if (simulate.find(row * gridsize + col) == simulate.find(i) && isopem[row * gridsize + col]) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openNum;
    }

    public boolean percolates() {
        for (int i = 0; i < gridsize; i++) {
            if (isFull(gridsize - 1, i)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Percolation a = new Percolation(6);
        a.open(0,5);
        a.open(1,5);
        a.open(2,5);
        a.open(3,5);
        a.open(4,5);
        a.open(4,4);
        a.open(3,3);
        a.open(2,3);
        a.open(1,3);
        a.open(1,2);
        a.open(1,1);
        a.open(1,0);
        a.open(2,0);
        System.out.println(a.isOpen(1,1));
        System.out.println(a.isOpen(2,1));
        System.out.println(a.isFull(1,1));
        System.out.println(a.isFull(2,1));
        a.open(2,1);
        System.out.println(a.isOpen(2,1));
        System.out.println(a.isFull(2,1));
    }
}
