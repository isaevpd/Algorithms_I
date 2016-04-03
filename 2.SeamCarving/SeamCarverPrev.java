import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
// import java.awt.Color;

public class SeamCarver {
    private Picture pictureObject;
    private double[][] energyMatrix;
    private double[][] cumulatedMatrix;
    private int[][] parentMatrix;
    private boolean isTransposed;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
 
        isTransposed = false;
        pictureObject = new Picture(picture);
        energyMatrix = new double[height()][width()];
        cumulatedMatrix = new double[height()][width()];
        parentMatrix = new int[height()][width()];
        // StdOut.println(width());
        for (int i = 0; i < height(); i++)
            for (int j = 0; j < width(); j++) {
                // StdOut.println(x + " " + y);
                // StdOut.println(i + " " + j);
                energyMatrix[i][j] = energy(j, i);
                cumulatedMatrix[i][j] = Double.POSITIVE_INFINITY;
                // StdOut.println("we're fine");
            }

        // initialize cumulatedMatrix's first row to 1000s
        for (int j = 0; j < width(); j++) 
            cumulatedMatrix[0][j] = energyMatrix[0][j];

        // fill in cumulatedMatrix and parentMatrix
        getDistance();

        // PRINTS the computed Energy matrix
        // for (int i = 0; i < height(); i++) {
        // StdOut.println(i + "th row is: ");
        // for (double num:energyMatrix[i])
        // StdOut.print(String.format("%.2f", num) + " ");
        // StdOut.print(" ");
        //   StdOut.println();
        // }

        // StdOut.println("Print first row ");
        // for (double num:energyMatrix[])
        //   StdOut.println(num);

        // PRINTS the initialized cumulated matrix
        // StdOut.println();
        // for (double[]row:cumulatedMatrix) {
        //   for (double num: row)
        //     StdOut.print(String.format("%.2f", num) + " ");
        //     // StdOut.print(" ");
        //   StdOut.println();
        // }


    }

    private void printCumulated() {
        StdOut.println();
        for (double[]row:cumulatedMatrix) {
            for (double num: row)
                StdOut.print(String.format("%.2f", num) + " ");
                // StdOut.print(" ");
            StdOut.println();
        }
    }

    private void printParent() {
        StdOut.println();
        for (int[]row:parentMatrix) {
            for (int num: row)
                StdOut.print(num + " ");
                // StdOut.print(" ");
            StdOut.println();
        }

    }

    // returns the column(x) index of the best parent
    private void getBestParent(int x, int y) {
        // int best;
        double mid = energy(x, y + 1);  // middle child
        // StdOut.println(mid);
        // StdOut.println();
        // StdOut.println("Checking 3 bottom from " + cumulatedMatrix[y][x]);
        // StdOut.println("Comparing " + (mid + cumulatedMatrix[y][x]) + " vs " + cumulatedMatrix[y + 1][x]);
        if (mid + cumulatedMatrix[y][x] < cumulatedMatrix[y + 1][x]) {
            // StdOut.println("taking " + (mid + cumulatedMatrix[y][x]));
            cumulatedMatrix[y + 1][x] = mid + cumulatedMatrix[y][x];
            parentMatrix[y + 1][x] = x;
            // StdOut.println(x + " is a parent of " + cumulatedMatrix[y + 1][x] + "(" + mid + ")m");
        }

        if (x != 0) {
            double left = energy(x - 1, y + 1); // left child
            // StdOut.println("Comparing " + (left + cumulatedMatrix[y][x]) + " vs " + cumulatedMatrix[y + 1][x - 1]);
            if (left + cumulatedMatrix[y][x] < cumulatedMatrix[y + 1][x - 1]) {
                // StdOut.println("taking " + (left + cumulatedMatrix[y][x]));
                cumulatedMatrix[y + 1][x - 1] = left + cumulatedMatrix[y][x];
                parentMatrix[y + 1][x - 1] = x;
                // StdOut.println(x + " is a parent of " + cumulatedMatrix[y + 1][x - 1] + "(" + left + ")l");
            }
        }

        if (x != width() - 1) {
            double right = energy(x + 1, y + 1); // right child
            // StdOut.println("Comparing " + (right + cumulatedMatrix[y][x]) + " vs " + cumulatedMatrix[y + 1][x + 1]);
            if ((right + cumulatedMatrix[y][x]) < cumulatedMatrix[y + 1][x + 1]) {
                // StdOut.println("taking " + (right + cumulatedMatrix[y][x]));
                cumulatedMatrix[y + 1][x + 1] = right + cumulatedMatrix[y][x];
                parentMatrix[y + 1][x + 1] = x;
                // StdOut.println(energy(x, y) + " is a parent of " + cumulatedMatrix[y + 1][x + 1] + "(" + right + ")r");
            }
        }

        // StdOut.println("Options are " + left + ", " + mid + ", " + right);
        // if (mid <= left && mid <= right)
        // {
        //   best = x;
        //   // StdOut.println("taking " + mid);
        // }
        // else if (left <= right && left <= mid)
        // {
        //   best = x - 1;
        // }
        // else
        // {
        //   best = x + 1;
        // }
        // printCumulated();
        // parentMatrix[y][x] = best;
    }

    // function to compute cumulatedMatrix and parentMatrix
    private void getDistance() {
        for (int i = 0; i < height() - 1; i++) {
            for (int j = 0; j < width(); j++) {
                // StdOut.println("taking " + sm.energy(i, j));
                getBestParent(j, i);
            }
        }
    }

    private int[] getSeam() {
        // iterate over last row in cumulatedMatrix to get min parent
        int[] result = new int[cumulatedMatrix.length];
        double min = Double.POSITIVE_INFINITY;
        int parent = -1;
        for (int j = 0; j < cumulatedMatrix[0].length; j++) {

            if (cumulatedMatrix[cumulatedMatrix.length - 1][j] < min) {
                min = cumulatedMatrix[cumulatedMatrix.length - 1][j];
                parent = j;
            }
        }

        result[cumulatedMatrix.length - 1] = parent;

        for (int i = cumulatedMatrix.length - 1; i > 0; i--) {
            // StdOut.println(parentMatrix[i][parent]);
            // StdOut.println("i is " + i);
            parent = parentMatrix[i][parent];
            result[i - 1] = parent;
            // StdOut.println("parent is " + parent);
        }
        // StdOut.println(parent);
        // StdOut.println(min);

        // for (int i: result) StdOut.println(i);
        return result;     
    }

    // helper function to compute energy of a pixel, computes for x values
    private double deltaX(int x, int y) {
        // get colors from pixel on the right
        int red1 = pictureObject.get(x + 1, y).getRed();
        int green1 = pictureObject.get(x + 1, y).getGreen();
        int blue1 = pictureObject.get(x + 1, y).getBlue();
        // get colors from pixel on the left
        int red2 = pictureObject.get(x - 1, y).getRed();
        int green2 = pictureObject.get(x - 1, y).getGreen();
        int blue2 = pictureObject.get(x - 1, y).getBlue();

        return Math.pow((green2 - green1), 2) + Math.pow((red2 - red1), 2) + Math.pow((blue2 - blue1), 2);    
    }
    // helper function to compute energy of a pixel, computes for y values
    private double deltaY(int x, int y) {
        // get colors from pixel below
        int red1 = pictureObject.get(x, y + 1).getRed();
        int green1 = pictureObject.get(x, y + 1).getGreen();
        int blue1 = pictureObject.get(x, y + 1).getBlue();
        // get colors from pixel above
        int red2 = pictureObject.get(x, y - 1).getRed();
        int green2 = pictureObject.get(x, y - 1).getGreen();
        int blue2 = pictureObject.get(x, y - 1).getBlue();

        return Math.pow((green2 - green1), 2) + Math.pow((red2 - red1), 2) + Math.pow((blue2 - blue1), 2);
    }

    // current picture
    public Picture picture() {
        return pictureObject;
    }
    // width of current picture
    public int width() {
      return pictureObject.width();
    }
    // height of current picture
    public int height() {
      return pictureObject.height();
    }

    // energy of pixel at column x and row y
    public  double energy(int x, int y) {
        if (x < 0 || y < 0) throw new IndexOutOfBoundsException("at least one of the indices is less than 0");
        if (x > width() - 1 || y > height() - 1) throw new IndexOutOfBoundsException("at least one of the indices is out of bounds");
        if (x == width() - 1 || y == height() - 1) return 1000;
        if (x == 0 || y == 0) return 1000;
        //get delta x
        double deltaX = deltaX(x, y);
        double deltaY = deltaY(x, y);
        return Math.sqrt(deltaX + deltaY);
    }

    // sequence of indices for horizontal seam
    public   int[] findHorizontalSeam() {
      // if (!isTransposed)
        return getSeam();
    }
    // sequence of indices for vertical seam
    public   int[] findVerticalSeam() {
        return getSeam();
    }
    // remove horizontal seam from current picture
    public    void removeHorizontalSeam(int[] seam) {
    }
    // remove vertical seam from current picture
    public    void removeVerticalSeam(int[] seam) {    

    }
    public static void main(String[] args) {
      // Picture pic = new Picture("6x5.png");
      // SeamCarver sm = new SeamCarver(pic);
      // sm.getDistance();
      // for (int i = 1; i < sm.width() - 1; i++) {
      //   StdOut.println("taking " + sm.energy(i, 0));
      //   sm.getBestParent(i, 0);

      // }

      // for (int i = 0; i < sm.height() - 1; i++)
      // {
      //   for (int j = 1; j < sm.width() - 1; j++)
      //   {
      //     // StdOut.println("taking " + sm.energy(i, j));
      //     sm.getBestParent(j, i);
      //   }

      // }

      // sm.getDistance();
      // for (int i:sm.getSeam()) StdOut.println(i);
      // sm.printCumulated();
      // sm.printParent();
      // StdOut.println(pic.get(2, 2));
      // StdOut.println(Double.MAX_VALUE);
      // StdOut.println(sm.energy(1, 2));
      // int green = pic.get(0, 0).getGreen();
      // StdOut.println(green);
      // StdOut.println(sm.width());
      // StdOut.println(sm.height());
    }
}



