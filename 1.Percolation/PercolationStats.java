import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;



public class PercolationStats 
{
   private double[] mu;
   private int total;
   private double mumean;
   private double mustddev;
   private double conflo;
   private double confhigh;

   public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
   {
   if (T <= 0 || N <= 0)
    {
        throw new IllegalArgumentException("either N ≤ 0 or T ≤ 0");
    }
     mu = new double[T];
     total = T;


     for (int i = 0; i < T; i++)
     {

          Percolation init = new Percolation(N);
          double count = 0;

     while (!init.percolates())
     {
          int siterow = StdRandom.uniform(N) + 1;
          int sitecol = StdRandom.uniform(N) + 1;
          if (init.isOpen(siterow, sitecol))
          {
             siterow = StdRandom.uniform(N) + 1;
             sitecol = StdRandom.uniform(N) + 1;     
          }
          else
          {
              init.open(siterow, sitecol);
              count++;
          }
      }
    //StdOut.println(count / (N * N));
    mu[i] = count / (N * N);
   }

   mumean = StdStats.mean(mu);
   mustddev = StdStats.stddev(mu);
   conflo = mumean - ((1.96 * mustddev) / Math.sqrt(total));
   confhigh = mumean + ((1.96 * mustddev) / Math.sqrt(total));

  }


   public double mean()                      // sample mean of percolation threshold
   {
       return mumean;
   }
   public double stddev()                    // sample standard deviation of percolation threshold
   {
       return StdStats.stddev(mu);
   }
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   {
       return conflo;
   }
   public double confidenceHi()              // high endpoint of 95% confidence interval
   {
       return confhigh;
   }

   public static void main(String[] args)    // test client (described below)
   {

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(N, T);
        StdOut.println("mean                    = " + test.mean());
        StdOut.println("stddev                  = " + test.stddev());
        StdOut.println("95% confidence interval = " + test.confidenceLo() + ", " + test.confidenceHi());


   }
}