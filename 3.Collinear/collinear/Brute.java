import java.util.Arrays;

public class Brute 
{
   public static void main(String[] args)
   {
        In input = new In(args[0]);

        int[] rawData = input.readAllInts();

        Point[] data = new Point[(rawData.length - 1) / 2];

        int next = 0;

        Point[] temp;

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (int i = 1; i < rawData.length; i += 2)
        {
            data[next] = new Point(rawData[i], rawData[i + 1]);
            data[next].draw();
            next++;
        }
        for (Point p : data)
        {
            for (Point q: data)
            {
                if (p == q) break;
                for (Point r: data)
                {
                    if (q == r) break;
                    for (Point s:data)
                    {
                        if (r == s) break;
                        if (q == s) break;
                        if (p == r) break;
                        if (p.slopeTo(q) == p.slopeTo(r) & p.slopeTo(r) == p.slopeTo(s))
                            {
                                temp = new Point[]{p, q, r, s};
                                Arrays.sort(temp);
                                temp[0].drawTo(temp[3]);
                                for (int i = 0; i < temp.length; i++)
                                {
                                    if (i != temp.length - 1) StdOut.print(temp[i] + " -> ");
                                    else StdOut.println(temp[i]);
                                }                      
                            }

                    }
                }
            }
        }              
   }
}