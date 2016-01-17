/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> 
{

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>()
    {
        public int compare(Point q, Point r) 
        {

            if (slopeTo(q) <  slopeTo(r)) return -1;
            else if (slopeTo(q) == slopeTo(r)) return 0;
            else return 1;

        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) 
    {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() 
    {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) 
    {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y)       // degenerate line segment
        {
            return Double.NEGATIVE_INFINITY;
        }
        else if (this.y == that.y)                     // horizontal
        {
            return 0;
        }
        else if (this.x == that.x)                     // vertical 
        {
            return Double.POSITIVE_INFINITY;
        }
        return (that.y - this.y) / (double) (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) 
    {
        if (this.y == that.y && this.x == that.x) return 0;
        else if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
        else return 1;

    }

    // return string representation of this point
    public String toString() 
    {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) 
    {

        // Point p = new Point(14000, 10000);
        // Point q = new Point(18000, 10000);
        // Point r = new Point(19000, 10000);
        // StdOut.println(p.compareTo(q));
        // StdOut.println(5 / (double) 2);
        // StdOut.println(-1.0 == -1.0);
    }
}
