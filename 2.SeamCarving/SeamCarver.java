import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;

public class SeamCarver {
    private Color[][] pictureMatrix;
    private double[][] energyMatrix;
    private double[][] cumulatedMatrix;
    private int[][] parentMatrix;
    private boolean isTransposed;
    private int height;
    private int width;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        height = picture.height();
        width = picture.width();
        isTransposed = false;
        pictureMatrix = constructPictureMatrix(picture);

        energyMatrix = new double[height()][width()];
        cumulatedMatrix = new double[height()][width()];
        parentMatrix = new int[height()][width()];


        // initialize energy and cumulated matrices
        for (int i = 0; i < height(); i++)
            for (int j = 0; j < width(); j++) {
                energyMatrix[i][j] = helperEnergy(i, j);
            }

        getShortestDistance();

        // printEnergyMatrix();
        // printCumulatedMatrix();
        // printParentMatrix();

    }
    /* HELPER FUNCTIONS */
    private double getDeltaX(int x, int y) {
        Color topPixel = this.pictureMatrix[x - 1][y];
        Color bottomPixel = this.pictureMatrix[x + 1][y];
        // colors for pixel above
        int topRed = topPixel.getRed();
        int topGreen = topPixel.getGreen();
        int topBlue = topPixel.getBlue();
        // colors for pixel below
        int bottomRed = bottomPixel.getRed();
        int bottomGreen = bottomPixel.getGreen();
        int bottomBlue = bottomPixel.getBlue();
        // return sum of difference squared
        return Math.pow((bottomRed - topRed), 2) + Math.pow((bottomGreen - topGreen), 2) + Math.pow((bottomBlue - topBlue), 2);
    }

    private double getDeltaY(int x, int y) {
        Color leftPixel = this.pictureMatrix[x][y - 1];
        Color rightPixel = this.pictureMatrix[x][y + 1];
        // colors for pixel above
        int leftRed = leftPixel.getRed();
        int leftGreen = leftPixel.getGreen();
        int leftBlue = leftPixel.getBlue();
        // colors for pixel below
        int rightRed = rightPixel.getRed();
        int rightGreen = rightPixel.getGreen();
        int rightBlue = rightPixel.getBlue();
        // return sum of difference squared
        return Math.pow((rightRed - leftRed), 2) + Math.pow((rightGreen - leftGreen), 2) + Math.pow((rightBlue - leftBlue), 2);
    }

    // method to build a picture from 2d Color array
    private Picture constructPicture(Color[][] matrix) {
        Picture picture = new Picture(width, height);
        for (int j = 0; j < width; j++)
            for (int i = 0; i < height; i++)
                picture.set(j, i, matrix[i][j]);
        return picture;
    }
    // method to initialize pictureMatrix in the constructor
    private Color[][] constructPictureMatrix(Picture picture) {
        Color[][] colorMatrix = new Color[this.height][this.width];
        for (int j = 0; j < this.width; j++)
            for (int i = 0; i < this.height; i++) 
                colorMatrix[i][j] = picture.get(j, i);

        return colorMatrix;
    }

    private void printEnergyMatrix() {
        StdOut.println("Energy Matrix is:");
        for (double[]row:this.energyMatrix) {
            // StdOut.println(i + "th row is: ");
            for (double num:row)
                StdOut.print(String.format("%.2f", num) + " ");
            StdOut.println();
        }
    }

    private void printCumulatedMatrix() {
        StdOut.println();
        StdOut.println("Cumulated Matrix is:");
        for (double[]row:this.cumulatedMatrix) {
            for (double num: row)
                StdOut.print(String.format("%.2f", num) + " ");
            StdOut.println();
        }
    }

    private void printParentMatrix() {
        StdOut.println();
        for (int[]row:this.parentMatrix) {
            for (int num: row)
                StdOut.print(num + " ");
            StdOut.println();
        }
    }

    // energy of pixel at row x and column y                           
    private double helperEnergy(int x, int y) {
        if (x < 0 || y < 0) throw new IndexOutOfBoundsException("at least one of the indices is less than 0");
        if (x > height() - 1 || y > width() - 1) throw new IndexOutOfBoundsException("at least one of the indices is out of bounds");
        if (x == height() - 1 || y == width() - 1) return 1000;
        if (x == 0 || y == 0) return 1000;

        double deltaX = getDeltaX(x, y);
        double deltaY = getDeltaY(x, y);
        return Math.sqrt(deltaX + deltaY);
    }
    
    // private Color[][] helperTranspose(Color[][] colorMatrix) {

    // }   
    // private int[][] helperTranspose(int[][] intMatrix) {
        
    // }
    // private double[][] helperTranspose(double[][] doubleMatrix) {
        
    // }   

    // fills parent value for row x column y
    private void getBestParent(int x, int y) {
        double midChild = this.energyMatrix[x + 1][y];  // middle child

        double currentCumulatedValue = this.cumulatedMatrix[x][y];
        double sumBelow = midChild + currentCumulatedValue;
        if (sumBelow < cumulatedMatrix[x + 1][y]) {
            this.cumulatedMatrix[x + 1][y] = sumBelow;
            this.parentMatrix[x + 1][y] = y;
        }

        if (y != 0) {
            double leftChild = this.energyMatrix[x + 1][y - 1]; // left child
            double sumLeftBelow = leftChild + currentCumulatedValue;
            if (sumLeftBelow < cumulatedMatrix[x + 1][y - 1]) {
                this.cumulatedMatrix[x + 1][y - 1] = sumLeftBelow;
                this.parentMatrix[x + 1][y - 1] = y;
            }
        }

        if (y != this.width() - 1) {
            double rightChild = this.energyMatrix[x + 1][y + 1]; // right child
            double sumRightBelow = rightChild + currentCumulatedValue;
            if (sumRightBelow < cumulatedMatrix[x + 1][y + 1]) {
                this.cumulatedMatrix[x + 1][y + 1] = sumRightBelow;
                this.parentMatrix[x + 1][y + 1] = y;
            }
        }
    }

    private void getShortestDistance() {
        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++)
                this.cumulatedMatrix[i][j] = Double.POSITIVE_INFINITY;

        for (int j = 0; j < this.width; j++)
            this.cumulatedMatrix[0][j] = 1000;

        for (int i = 0; i < this.height - 1; i++)
            for (int j = 0; j < this.width; j++) {
                this.getBestParent(i, j);
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
        // go up and save the path
        for (int i = cumulatedMatrix.length - 1; i > 0; i--) {
            parent = parentMatrix[i][parent];
            result[i - 1] = parent;
        }
        return result;     
    }

    /* ACTUAL API FUNCTIONS */

    // current picture             
    public Picture picture() {
        return constructPicture(pictureMatrix);
    }
    // width of current picture                          
    public int width() {
        return this.width;
    }
    // height of current picture                          
    public int height() {
        return this.height;
    }
    // energy of pixel at column x and row y
    public  double energy(int x, int y) {
        if (x < 0 || y < 0) throw new IndexOutOfBoundsException("at least one of the indices is less than 0");
        if (x > width() - 1 || y > height() - 1) throw new IndexOutOfBoundsException("at least one of the indices is out of bounds");
        if (x == width() - 1 || y == height() - 1) return 1000;
        if (x == 0 || y == 0) return 1000;
        int temp = x;
        x = y;
        y = temp;
        return helperEnergy(x, y);
    }
    // sequence of indices for horizontal seam          
    public int[] findHorizontalSeam() {
        // if (this.isTransposed)
            // return getSeam();
        // transpose();
        return getSeam();
    }     
    // sequence of indices for vertical seam   
    public int[] findVerticalSeam() {
        return getSeam();
    }
    // remove horizontal seam from current picture              
    public void removeHorizontalSeam(int[] seam) {

    }
    // remove vertical seam from current picture 
    public void removeVerticalSeam(int[] seam) {

    }
    // main method for testing
    public static void main(String[] args) {
        // Picture testPicture = new Picture("6x5.png");
        // SeamCarver sm = new SeamCarver(testPicture);
        // for (int n : sm.findHorizontalSeam())
        //     StdOut.print(n + " ");
        // StdOut.println();
        // StdOut.println(sm.energy(3, 2));
        // sm.picture().show();
    }     
}