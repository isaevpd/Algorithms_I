import java.util.Arrays;

public class Fast 
{
   public static void main(String[] args)
   {
        In input = new In(args[0]);
        int[] rawData = input.readAllInts();
        Point[] data = new Point[(rawData.length - 1) / 2];
        Point[] aux = new Point[data.length];
        int next = 0;
        boolean flag;
        int count;  // = 0;
        int idx;

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (int i = 1; i < rawData.length; i += 2)
        {
            data[next] = new Point(rawData[i], rawData[i + 1]);
            data[next++].draw();
        }
        for (int i = 0; i < data.length; i++)
        {
            aux[i] = data[i];
        }
        for (Point p : data)
        {
            count = 0;
            idx = 0;
            Arrays.sort(aux, p.SLOPE_ORDER);
            for (int i = 0; i < aux.length - 1; i++)
            {
                flag = p.slopeTo(aux[i]) == p.slopeTo(aux[i + 1]);
                if (flag) count++;
                if ((i == aux.length - 2 && count > 1) || (!flag && count > 1))
                {
                    Arrays.sort(aux, idx, idx + count + 1);
                    if (p.compareTo(aux[idx]) < 0)
                    {
                        p.drawTo(aux[idx + count]);
                        StdOut.print(p + "->");
                        for (int index = idx; index < idx + count; index++)
                        {
                            StdOut.print(aux[index] + "->");
                        }
                        StdOut.print(aux[idx + count]);
                        StdOut.println();
                    }
                }
                if (!flag) count = 0;
                if (i > 0)
                {
                    if (p.slopeTo(aux[i]) != p.slopeTo(aux[i - 1])) idx = i;

                }
            }
        }

    }
}