import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation 
{
   private boolean[] grid;
   private int dimension;
   private WeightedQuickUnionUF components;


   private int convert_to_1d(int i, int j, int N)
   {
      return N * i + j;
   }

   public Percolation(int N)               // create N-by-N grid, with all sites blocked
   {
       dimension = N;
       grid = new boolean[N * N];
       components = new WeightedQuickUnionUF(N * N)
       for (int i = 0; i < N * N; i++)
            grid[i] = false;

   }
   public void open(int i, int j)          // open site (row i, column j) if it is not open already
   {
       grid[convert_to_1d(i - 1, j - 1, dimension)] = true;
   }
   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
   {
        return grid[convert_to_1d(i - 1, j - 1, dimension)];
   }
   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   {
        return false;
   }
   public boolean percolates()             // does the system percolate?
   {
        return false;
   }

   public static void main(String[] args)  // test client (optional)
   {
        // Percolation test = new Percolation(4);
        // // Percolation test1 = new Percolation(10);
        // for (int i = 0; i < test.dimension; i++)
        // {
        //   StdOut.println();
        //   for (int j = 0; j < test.dimension; j++)
        //     StdOut.print(test.grid[test.convert_to_1d(i, j, test.dimension)] + " ");  
        // }
        // StdOut.println();
        // StdOut.println(test.dimension);


   }
}