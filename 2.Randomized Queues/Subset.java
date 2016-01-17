public class Subset {
   public static void main(String[] args)
   {
      int k = Integer.parseInt(args[0]);
      RandomizedQueue<String> que = new<String> RandomizedQueue();
    while (!StdIn.isEmpty())
      {
         String s = StdIn.readString();
         // if (StdIn.isEmpty(s)) break;
         que.enqueue(s);
      }
      for (int i = 0; i < k; i++)
      {
         StdOut.println(que.dequeue());
      }
   }
}