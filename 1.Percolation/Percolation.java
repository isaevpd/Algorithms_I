/*Assignment 1 - Percolation, Algorithms 2015*/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;



public class Percolation
{
   private boolean[] grid;
   private int dim;
   private WeightedQuickUnionUF struct;
   private WeightedQuickUnionUF backwash;
   public Percolation(int N)               // create N-by-N grid, with all sites blocked
   {
      if (N <= 0) throw new IllegalArgumentException("N is too low, min value is 1");
      grid = new boolean[N * N];
      dim = N;
      struct = new WeightedQuickUnionUF(N * N + 2);
      backwash = new WeightedQuickUnionUF(N * N + 1);
   }
   private void valid(int i)       // checks if index is in a grid          
   {
       if (i <= 0 || i > dim) throw new IndexOutOfBoundsException("row index i out of bounds");
   }
   private int xyTo1D(int i, int j)         // converts i, j indices into WeightedQuickUnionUF object
   {
       valid(i);
       valid(j);
       return ((i - 1) * dim) + (j - 1);
   }
   public void open(int i, int j)          // open site (row i, column j) if it is not open already
   {
       valid(i);
       valid(j);
       grid[xyTo1D(i, j)] = true;
       if (dim == 1) { struct.union(dim, xyTo1D(1, 1)); 
                       backwash.union(dim, xyTo1D(1, 1));
                       struct.union(dim + 1, xyTo1D(1, 1)); }
       else if (i == 1 && j == 1) { struct.union(dim * dim, xyTo1D(1, 1));
                                   backwash.union(dim * dim, xyTo1D(1, 1));
           if (isOpen(1, 2)) { struct.union(xyTo1D(1, 2), xyTo1D(1, 1));
                             backwash.union(xyTo1D(1, 2), xyTo1D(1, 1)); }
           if (isOpen(2, 1)) { struct.union(xyTo1D(2, 1), xyTo1D(1, 1));
                               backwash.union(xyTo1D(2, 1), xyTo1D(1, 1)); }
       }
       else if (i == dim && j == 1)
       {
           struct.union(dim * dim + 1, xyTo1D(dim, 1));
           if (isOpen(dim, 2))
           {
               struct.union(xyTo1D(dim, 2), xyTo1D(dim, 1));
               backwash.union(xyTo1D(dim, 2), xyTo1D(dim, 1));
           }
           if (isOpen(dim - 1, 1))
           {
               struct.union(xyTo1D(dim - 1, 1), xyTo1D(dim, 1));
               backwash.union(xyTo1D(dim - 1, 1), xyTo1D(dim, 1));
           }
       }
       else if (i == 1 && j == dim)
       {
           struct.union(dim * dim, xyTo1D(1, dim));
           backwash.union(dim * dim, xyTo1D(1, dim));
           if (isOpen(1, dim - 1))
           {
               struct.union(xyTo1D(1, dim - 1), xyTo1D(1, dim));
               backwash.union(xyTo1D(1, dim - 1), xyTo1D(1, dim));
           }
           if (isOpen(2, dim))
           {
               struct.union(xyTo1D(2, dim), xyTo1D(1, dim));
               backwash.union(xyTo1D(2, dim), xyTo1D(1, dim));
           }
       }
       else if (i == dim && j == dim)
       {
           struct.union(dim * dim + 1, xyTo1D(dim, dim));
           if (isOpen(dim - 1, dim))
           {
               struct.union(xyTo1D(dim - 1, dim), xyTo1D(dim, dim));
               backwash.union(xyTo1D(dim - 1, dim), xyTo1D(dim, dim));
           }
           if (isOpen(dim, dim - 1))
           {
               struct.union(xyTo1D(dim, dim - 1), xyTo1D(dim, dim));
               backwash.union(xyTo1D(dim, dim - 1), xyTo1D(dim, dim));
           }
       }
       else if (i == 1)
       {
           struct.union(dim * dim, xyTo1D(i, j));
           backwash.union(dim * dim, xyTo1D(i, j));
           if (isOpen(i + 1, j))
           {
               struct.union(xyTo1D(i, j), xyTo1D(i + 1, j));
               backwash.union(xyTo1D(i, j), xyTo1D(i + 1, j));
           }   
       }
       else if (j == 1)
       {
           if (isOpen(i, j + 1))
           {
              struct.union(xyTo1D(i, j), xyTo1D(i, j + 1));
              backwash.union(xyTo1D(i, j), xyTo1D(i, j + 1));
           }
           if (isOpen(i - 1, j))
           {
              struct.union(xyTo1D(i, j), xyTo1D(i - 1, j));
              backwash.union(xyTo1D(i, j), xyTo1D(i - 1, j));         
           }
           if (isOpen(i + 1, j))
           {
              struct.union(xyTo1D(i, j), xyTo1D(i + 1, j));
              backwash.union(xyTo1D(i, j), xyTo1D(i + 1, j));
           }
       }
       else if (i == dim)
       {
           struct.union(dim * dim + 1, xyTo1D(i, j));
           if (isOpen(i - 1, j))
           {
               struct.union(xyTo1D(i, j), xyTo1D(i - 1, j));
               backwash.union(xyTo1D(i, j), xyTo1D(i - 1, j));
           }
           if (isOpen(i, j + 1))
           {
               struct.union(xyTo1D(i, j), xyTo1D(i, j + 1));
               backwash.union(xyTo1D(i, j), xyTo1D(i, j + 1));

           }
           if (isOpen(i, j - 1))
           {
               struct.union(xyTo1D(i, j), xyTo1D(i, j - 1));
               backwash.union(xyTo1D(i, j), xyTo1D(i, j - 1));
           }
       }
       else if (j == dim)
       {
           if (isOpen(i, j - 1))
           {
              struct.union(xyTo1D(i, j), xyTo1D(i, j - 1));
              backwash.union(xyTo1D(i, j), xyTo1D(i, j - 1));
           }
           if (isOpen(i - 1, j))
           {
              struct.union(xyTo1D(i, j), xyTo1D(i - 1, j));
              backwash.union(xyTo1D(i, j), xyTo1D(i - 1, j));            
           }
           if (isOpen(i + 1, j))
           {
              struct.union(xyTo1D(i, j), xyTo1D(i + 1, j));
              backwash.union(xyTo1D(i, j), xyTo1D(i + 1, j));
           }
       }
       else
       {
           if (isOpen(i, j - 1))
           {
              struct.union(xyTo1D(i, j), xyTo1D(i, j - 1));
              backwash.union(xyTo1D(i, j), xyTo1D(i, j - 1));
           }
           if (isOpen(i - 1, j))
           {
              struct.union(xyTo1D(i, j), xyTo1D(i - 1, j)); 
              backwash.union(xyTo1D(i, j), xyTo1D(i - 1, j));       
           }
           if (isOpen(i + 1, j))
           {
              struct.union(xyTo1D(i, j), xyTo1D(i + 1, j));
              backwash.union(xyTo1D(i, j), xyTo1D(i + 1, j));
           }
           if (isOpen(i, j + 1))
           {
              struct.union(xyTo1D(i, j), xyTo1D(i, j + 1));
              backwash.union(xyTo1D(i, j), xyTo1D(i, j + 1));
           }
       }
   }
   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
   {
       valid(i);
       valid(j);
       return grid[xyTo1D(i, j)];
   }
   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   {
       valid(i);
       valid(j);
       return backwash.connected((dim) * (dim), xyTo1D(i, j));
   }
   public boolean percolates()             // does the system percolate?
   {
       return struct.connected(dim * dim, dim * dim + 1);
   }
   public static void main(String[] args)   // test client (optional)
   {

   }
}