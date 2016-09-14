package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int count;
    private boolean[][] grid;
    int n;
    private WeightedQuickUnionUF unionHolder;

    public Percolation(int N) {
        // 2 dimensional array of booleans, open or not open
        if (N <= 0) {
            throw new IllegalArgumentException("Size needs to be greater than 0!");
        }
        grid = new boolean[N][N];
        n = N;
        unionHolder = new WeightedQuickUnionUF(n * n + (2));
        count = 0;
    }

    // opens cell,
    // then checks if the left, right, up, and down cells
    // of the grid are opened
    public void open(int row, int col) {
        int left = Math.max(0, col - 1);
        int right = Math.min(n - 1, col + 1);
        int up = Math.max(0, row - 1);
        int down = Math.min(n - 1, row + 1);
        if (!this.isOpen(row, col)) {
            this.grid[row][col] = true;
            if (row == 0) {
                unionHolder.union(xyTo1D(row, col), (n * n));
            }
            if (row == n - 1) {
                if (!this.percolates()) {
                    unionHolder.union(xyTo1D(row, col), (n * n) + 1);
                }
            }
            if (isOpen(row, left)) {
                unionHolder.union(xyTo1D(row, col), xyTo1D(row, left));
            }
            if (isOpen(row, right)) {
                unionHolder.union(xyTo1D(row, col), xyTo1D(row, right));
            }
            if (isOpen(up, col)) {
                unionHolder.union(xyTo1D(row, col), xyTo1D(up, col));
            }
            if (isOpen(down, col)) {
                unionHolder.union(xyTo1D(row, col), xyTo1D(down, col));
            }
            count++;
        }
    }

    public int xyTo1D(int r, int c) {
        return ((r) * n) + c;
    }

    public boolean isOpen(int row, int col) {
        if (row > n || col > n || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return this.grid[row][col];
    }

    public boolean isFull(int row, int col) {
        return unionHolder.connected(xyTo1D(row, col), ((n * n)));
    }

    public int numberOfOpenSites() {
        return this.count;
    }

    public boolean percolates() {
        return unionHolder.connected((n * n), ((n * n) + 1));
    }

//    public static void main(String[] args) {
//        int i = 0;
//        int T = 1;
//        double[] ratios = new double[T];
//        while (i < T) {
//            Percolation tester = new Percolation(5);
//            int random_num1 = edu.princeton.cs.introcs.StdRandom.uniform(0, tester.n - 1);
//            int random_num2 = edu.princeton.cs.introcs.StdRandom.uniform(0, tester.n - 1);
//            System.out.println(tester.numberOfOpenSites());
//            while (!tester.percolates()) {
//                tester.open(random_num1, random_num2);
//                random_num1 = edu.princeton.cs.introcs.StdRandom.uniform(0, tester.n - 1);
//                random_num2 = edu.princeton.cs.introcs.StdRandom.uniform(0, tester.n - 1);
//            }
//            ratios[i] = (tester.numberOfOpenSites() / tester.n);
//            i++;
//        }
//    }
}
