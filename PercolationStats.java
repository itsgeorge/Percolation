package hw2;

import edu.princeton.cs.introcs.StdStats;


public class PercolationStats {
    private double[] ratios;
    double mean;
    double dev;
    int T;
    int N;

    public double runexperiment(Percolation perc) {
        int r1;
        int r2;
        while (!perc.percolates()) {
            r1 = edu.princeton.cs.introcs.StdRandom.uniform(0, N);
            r2 = edu.princeton.cs.introcs.StdRandom.uniform(0, N);
            if (!perc.isOpen(r1, r2)) {
                perc.open(r1, r2);
            }
        }
        double xratio = ((double) perc.numberOfOpenSites() / (double) (N * N));
        return xratio;
    }

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        int i = 0;
        this.T = T;
        this.N = N;
        ratios = new double[T];
        while (i < T) {
            Percolation tester = new Percolation(N);
            double ratio = runexperiment(tester);
            ratios[i] = (ratio);
            i++;
        }
    }

    public double mean() {
        mean = StdStats.mean(ratios);
        return mean;
    }

    public double stddev() {
        dev = StdStats.stddev(ratios);
        return dev;
    }

    public double confidenceLow() {
        return (mean() - ((1.96 * stddev()) / Math.sqrt(T)));
    }          // low  endpoint of 95% confidence interval

    public double confidenceHigh() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(T));
    }

//    public static void main(String[] args) {
//        PercolationStats test = new PercolationStats(2, 20);
//        System.out.println(test.confidenceHigh());
//    }


}
