import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

public class PointSET 
{
   private TreeSet<Point2D> pointset;
   private Stack points_in;

   public         PointSET()                               // construct an empty set of points
   {
       pointset = new TreeSet<Point2D>();

   }
   public           boolean isEmpty()                      // is the set empty? 
   {
       return pointset.size() == 0;
   }
   public               int size()                         // number of points in the set 
   {
       return pointset.size();
   }
   public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
       pointset.add(p);
   }
   public           boolean contains(Point2D p)            // does the set contain point p?
   {
       return pointset.contains(p);
   }
   public              void draw()                         // draw all points to standard draw 
   {
       for (Point2D point: pointset) point.draw();
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
   {
       points_in = new Stack();
       for (Point2D point: pointset)
       {
           if (rect.contains(point)) points_in.push(point);
       }
       return points_in;
   }
   public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
       double dist = Double.POSITIVE_INFINITY;
       Point2D nearest = null;
       for (Point2D point: pointset)
       {
           if (p.distanceTo(point) < dist)
           {
               dist = p.distanceTo(point);
               nearest = point;
           }
       }
       return nearest;
   }
   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {

   }
}
