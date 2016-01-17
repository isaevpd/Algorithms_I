import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
// import java.lang.NullPointerException;


public class KdTree 
{
    private Node root;
    private int N = 0;
    private boolean flag = false;

    private static class Node 
    {
       private Point2D p;      // the point
       private RectHV rect;    // the axis-aligned rectangle corresponding to this node
       private Node lb;        // the left/bottom subtree
       private Node rt;        // the right/top subtree
       private boolean odd;    // level of separation
    }

   public         KdTree()                               // construct an empty tree
   {

   }
   public           boolean isEmpty()                      // is the tree empty? 
   {
    return N == 0;
   }
   public               int size()                         // number of points in the tree 
   {
    return N;
   }

   private RectHV rectHVRight(RectHV rect, Point2D p)
   {
       double xmin = p.x();
       double ymin = rect.ymin();
       double xmax = rect.xmax();
       double ymax = rect.ymax();
       return new RectHV(xmin, ymin, xmax, ymax);
   }

   private RectHV rectHVLeft(RectHV rect, Point2D p)
   {
       double xmin = rect.xmin();
       double ymin = rect.ymin();
       double xmax = p.x();
       double ymax = rect.ymax();
       return new RectHV(xmin, ymin, xmax, ymax);
   }

   private RectHV rectHVTop(RectHV rect, Point2D p)
   {
       double xmin = rect.xmin();
       double ymin = p.y();
       double xmax = rect.xmax();
       double ymax = rect.ymax();
       return new RectHV(xmin, ymin, xmax, ymax);
   }

   private RectHV rectHVBottom(RectHV rect, Point2D p)
   {
       double xmin = rect.xmin();
       double ymin = rect.ymin();
       double xmax = rect.xmax();
       double ymax = p.y();
       return new RectHV(xmin, ymin, xmax, ymax);
   }

   private void rangeHelper(Node r, RectHV q, TreeSet points)
   {
       if (isEmpty()) return;
       if (q.contains(r.p))
       {
          points.add(r.p);
       }

       if (r.lb != null && q.intersects(r.lb.rect))
        {
           rangeHelper(r.lb, q, points);
        }

       if (r.rt != null && q.intersects(r.rt.rect))
        {
           rangeHelper(r.rt, q, points);
        }
   }

   private Node search(Point2D p)
   {
       flag = false;
       Node ptr = root;
       while (ptr != null)
       {
           if (p.compareTo(ptr.p) == 0)
           {
               return ptr;
           }
           if (!flag)
           {
               if (p.x() < ptr.p.x())
               {
                   ptr = ptr.lb; 
               }
               else
               {
                   ptr = ptr.rt; 
               }
           }
           else
           {
              if (p.y() < ptr.p.y())
              {
                   ptr = ptr.lb; 
              }
              else
              {
                   ptr = ptr.rt; 
              }
           }

            flag = !flag;
           }
        return null;
      }



   public              void insert(Point2D p)              // add the point to the tree (if it is not already in the tree)
   {
     if (N == 0)
     {
        root = new Node();
        root.p = p;
        root.lb = null;
        root.rt = null;
        root.odd = false;
        root.rect = new RectHV(0, 0, 1, 1);
        N++;
     }
     else
     {

       flag = false;
       Node ptr = root;
       Node prev = null;
       boolean left = false;
       while (ptr != null)
       {
           if (p.compareTo(ptr.p) == 0)
           {
               return;
           }
           prev = ptr;
           if (!flag)
           {
               if (p.x() < ptr.p.x())
               {
                   ptr = ptr.lb; 
                   left = true;
               }
               else
               {
                   ptr = ptr.rt; 
                   left = false;
               }
           }
           else
           {
              if (p.y() < ptr.p.y())
              {
                   ptr = ptr.lb; 
                   left = true;
              }
              else
              {
                   ptr = ptr.rt; 
                   left = false;
              }
           }

            flag = !flag;
           }

           Node temp = new Node();
           temp.p = p;
           temp.lb = null;
           temp.rt = null;
           temp.odd = flag;
           if (temp.odd)
           {
              if (left)
              {
                  temp.rect = rectHVLeft(prev.rect, prev.p);
              }
              else
              {
                  temp.rect = rectHVRight(prev.rect, prev.p);
              }
              
           }
           else
           {
              if (left)
              {
                  temp.rect = rectHVBottom(prev.rect, prev.p);
              }
              else
              {
                  temp.rect = rectHVTop(prev.rect, prev.p);
              }
           }
           
           if (left) prev.lb = temp;
           else prev.rt = temp;
           N++;

      }


   }
   public           boolean contains(Point2D p)            // does the tree contain point p?
   {
       if (isEmpty()) return false;
       flag = false;
       Node ptr = root;
       while (ptr != null)
       {
           if (p.compareTo(ptr.p) == 0)
           {
               return true;
           }
           if (!flag)
           {
               if (p.x() < ptr.p.x())
               {
                   ptr = ptr.lb; 
               }
               else
               {
                   ptr = ptr.rt; 
               }
           }
           else
           {
              if (p.y() < ptr.p.y())
              {
                   ptr = ptr.lb; 
              }
              else
              {
                   ptr = ptr.rt; 
              }
           }

            flag = !flag;
           }
        return false;
   }

   public              void draw()                         // draw all points to standard draw
   {
    Queue<Node> queue = new Queue<Node>();
    if (root == null)
        return;
    assert queue.isEmpty();
    queue.enqueue(root);
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.setPenRadius();
    StdDraw.line(root.p.x(), root.rect.ymin(), root.p.x(), root.rect.ymax());
    while (!queue.isEmpty())
    {
        Node node = queue.dequeue();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        node.p.draw();
        if (node.odd)
        {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        else
        {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());   
        }
        if (node.lb != null) queue.enqueue(node.lb);
        if (node.rt != null) queue.enqueue(node.rt);
    }

   }

   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   {
      TreeSet pts = new TreeSet();
      rangeHelper(root, rect, pts);
      return pts;
   }

    private Point2D nearest(Node x, Point2D point, Point2D min) {
        if (x != null) {
            if (min == null) 
            {
                min = x.p;
            }
            if (min.distanceSquaredTo(point) >= x.rect.distanceSquaredTo(point)) 
                {
                if (x.p.distanceSquaredTo(point) < min.distanceSquaredTo(point)) 
                {
                    min = x.p;
                }

                if (x.rt != null && x.rt.rect.contains(point)) 
                {
                    min = nearest(x.rt, point, min);
                    min = nearest(x.lb, point, min);
                } 
                else 
                {
                    min = nearest(x.lb, point, min);
                    min = nearest(x.rt, point, min);
                }
            }
        }

        return min;
    }




   public           Point2D nearest(Point2D p)             // a nearest neighbor in the tree to point p; null if the tree is empty
   {  
      if (isEmpty()) return null;
      // Point2D champion = root.p;
      return nearest(root, p, root.p);
      // return champion;
   }

   public static void main(String[] args)                  // unit testing of the methods (optional)
   {
        KdTree tree = new KdTree();
        Point2D p0 = new Point2D(0.206107, 0.095492);
        Point2D p1 = new Point2D(0.975528, 0.654508);
        // Point2D p2 = new Point2D(0.024472, 0.345492);
        // Point2D p3 = new Point2D(0.793893, 0.095492);
        // Point2D p4 = new Point2D(0.793893, 0.904508);
        // Point2D p5 = new Point2D(0.975528, 0.345492);
        // Point2D p6 = new Point2D(0.206107, 0.904508);
        // Point2D p7 = new Point2D(0.500000, 0.000000);
        // Point2D p8 = new Point2D(0.024472, 0.654508);
        // Point2D p9 = new Point2D(0.500000, 1.000000);
        // Point2D p10 = new Point2D(0.800, 0.3);



        // StdOut.println(p0.distanceSquaredTo(p0));

        tree.insert(p0);
        tree.insert(p1);

        StdOut.println(tree.nearest(p0));
        // // tree.insert(p2);
        // tree.insert(p3);
        // // tree.insert(p4);
        // // tree.insert(p5);
        // // tree.insert(p6);
        // // tree.insert(p7);
        // // tree.insert(p8);
        // // tree.insert(p9);

        // StdOut.println(tree.nearest(p0));


        // // // StdDraw.setPenColor(StdDraw.BLACK);
        // // // StdDraw.setPenRadius(.05);
        // // // RectHV test = tree.rectHVBottom(tree.root.rect, p0);
        // // // test.draw();
        // // StdOut.println(tree.size());
        // // // StdOut.println(tree.root.rt.p);
        // // // StdOut.println(tree.root.p);
        // // // StdOut.println(tree.root.odd);
        // // // StdOut.println(tree.root.rt.odd);
        // // // StdOut.println(tree.root.lb.odd);
        // // // StdOut.println(tree.root.lb.rt.odd);
        // // // StdOut.println(tree.contains(p0));
        // tree.draw();
        // StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.setPenRadius(.05);
        // RectHV test = new RectHV(0.5, 0, 1, 1);
        // test.draw();
        // for (Point2D p: tree.range(test))
        // {
        //     StdOut.println(p);
        // }

        
   }
}
