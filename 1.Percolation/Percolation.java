/*Assignment 1 - Percolation, Algorithms 2016*/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int dimension;
    private int size;
    private boolean flag;
    private boolean[] opened;
    private boolean[] inSetBottom;
    private WeightedQuickUnionUF components;
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N is too low, min value is 1");
        dimension = N;
        size = dimension * dimension;
        opened = new boolean[size];
        inSetBottom = new boolean[size + 1];
        components = new WeightedQuickUnionUF(size + 1);
        flag = false;
    }
    // checks if index is in a grid
    private void valid(int i) {
        if (i <= 0 || i > dimension)
            throw new IndexOutOfBoundsException(String.format("index %d out of bounds", i));
    }
    // converts 2d coordinates into 1d coordinate
    private int xyTo1D(int i, int j) {
        valid(i);
        valid(j);
        return ((i - 1) * dimension) + (j - 1);
    }
    // helper functions for public open method
    private void connectLeft(int i, int j) {
        if (j > 1) {
            if (isOpen(i, j - 1)) {
                if (inSetBottom[components.find(xyTo1D(i, j - 1))]) {
                    inSetBottom[xyTo1D(i, j)] = true;
                    inSetBottom[components.find(xyTo1D(i, j))] = true;         
                }
                if (i == dimension || inSetBottom[components.find(xyTo1D(i, j))]) {
                    inSetBottom[xyTo1D(i, j - 1)] = true;
                    inSetBottom[components.find(xyTo1D(i, j - 1))] = true;
                }
                components.union(xyTo1D(i, j), xyTo1D(i, j - 1));
            }
        }
    }
    private void connectRight(int i, int j) {
        if (j < dimension) {
            if (isOpen(i, j + 1)) {
                if (inSetBottom[components.find(xyTo1D(i, j + 1))]) {
                    inSetBottom[xyTo1D(i, j)] = true;
                    inSetBottom[components.find(xyTo1D(i, j))] = true;  
                }
                if (i == dimension || inSetBottom[components.find(xyTo1D(i, j))]) {
                    inSetBottom[xyTo1D(i, j + 1)] = true;
                    inSetBottom[components.find(xyTo1D(i, j + 1))] = true;
                }
                components.union(xyTo1D(i, j), xyTo1D(i, j + 1));
            }

        }
    }
    private void connectTop(int i, int j) {
        if (i > 1) {
            if (isOpen(i - 1, j)) {
                if (inSetBottom[components.find(xyTo1D(i - 1, j))]) {
                    inSetBottom[xyTo1D(i, j)] = true;
                    inSetBottom[components.find(xyTo1D(i, j))] = true;
                }
                if (i == dimension || inSetBottom[components.find(xyTo1D(i, j))]) {
                    inSetBottom[xyTo1D(i - 1, j)] = true;
                    inSetBottom[components.find(xyTo1D(i - 1, j))] = true;
                }
                components.union(xyTo1D(i, j), xyTo1D(i - 1, j));
            }
            
        }
    }
    private void connectBottom(int i, int j) {
        if (i < dimension) {
            if (isOpen(i + 1, j)) {
                if (inSetBottom[components.find(xyTo1D(i + 1, j))]) {
                    inSetBottom[xyTo1D(i, j)] = true;
                    inSetBottom[components.find(xyTo1D(i, j))] = true;
                }
                if (i == dimension || inSetBottom[components.find(xyTo1D(i, j))]) {
                    inSetBottom[xyTo1D(i + 1, j)] = true;
                    inSetBottom[components.find(xyTo1D(i + 1, j))] = true;                            
                }
                components.union(xyTo1D(i, j), xyTo1D(i + 1, j));
            }
        }
    }
    // open site (row i, column j) if it is not open already          
    public void open(int i, int j) {
        opened[xyTo1D(i, j)] = true;
        if (i == 1) 
            components.union(size, xyTo1D(i, j));
        if (i == dimension) {
            inSetBottom[xyTo1D(i, j)] = true;
            inSetBottom[components.find(xyTo1D(i, j))] = true;
        } 
        connectLeft(i, j);
        connectRight(i, j);
        connectTop(i, j);
        connectBottom(i, j);
        if (inSetBottom[xyTo1D(i, j)] && (components.find(xyTo1D(i, j)) == components.find(size)))
            flag = true;
    }
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return opened[xyTo1D(i, j)];
    }   
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
       return components.connected(size, xyTo1D(i, j));
    }  
    // does the system percolate?
    public boolean percolates() {
        return flag;
    }          
    // test client (optional)
    public static void main(String[] args) {

    }
}