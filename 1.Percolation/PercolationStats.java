import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;


public class PercolationStats {
    private double[] mu;
    private int total;
    private double muMean;
    private double muStdDev;
    private double confLo;
    private double confHigh;

public PercolationStats(int N, int T) {
    if (T <= 0 || N <= 0)
        throw new IllegalArgumentException("either N ≤ 0 or T ≤ 0");
    mu = new double[T];
    total = T;
    for (int i = 0; i < T; i++) {

        Percolation init = new Percolation(N);
        double count = 0;

        while (!init.percolates()) {

            int siterow = StdRandom.uniform(N) + 1;
            int sitecol = StdRandom.uniform(N) + 1;
            if (init.isOpen(siterow, sitecol)) {
                siterow = StdRandom.uniform(N) + 1;
                sitecol = StdRandom.uniform(N) + 1;     
            }
            else {
                init.open(siterow, sitecol);
                count++;
            }
        }

        mu[i] = count / (N * N);

    }

        muMean = StdStats.mean(mu);
        muStdDev = StdStats.stddev(mu);
        confLo = muMean - ((1.96 * muStdDev) / Math.sqrt(total));
        confHigh = muMean + ((1.96 * muStdDev) / Math.sqrt(total));

    }
    // sample mean of percolation threshold
    public double mean() {
       return muMean;
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
       return StdStats.stddev(mu);
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
       return confLo;
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
       return confHigh;
    }
    // test client (described below)
    public static void main(String[] args) {

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(N, T);
        StdOut.println("mean                    = " + test.mean());
        StdOut.println("stddev                  = " + test.stddev());
        StdOut.println("95% confidence interval = " + test.confidenceLo() + ", " + test.confidenceHi());

    }
}
