import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
  private Item[] q;
  private int N = 0;
  public RandomizedQueue()                 // construct an empty randomized queue
   {
    q = (Item[]) new Object[5];
   }
  // resize the underlying array
  private void resize(int max)
    {
    assert max >= N;
    Item[] temp = (Item[]) new Object[max];
    for (int i = 0; i < N; i++)
    {
        temp[i] = q[i];
    }
    q = temp;
    }
   public boolean isEmpty()                 // is the queue empty?
   {
    return N == 0;
   }
   public int size()                        // return the number of items on the queue
   {
    return N;
   }

   public void enqueue(Item item)           // add the item
   {
    if (item == null) throw new java.lang.NullPointerException();
    if (N == q.length) resize(2 * q.length);   // double size of array if necessary
    q[N++] = item;
    // for (Item a: this)       // loop for testing
    // {
    //   StdOut.print(a + " ");
    // }
    // StdOut.println();    // end of testing loop
   }


   public Item dequeue()                    // remove and return a random item
   {
    if (isEmpty()) throw new java.util.NoSuchElementException();
    int randnum = StdRandom.uniform(0, N);
    // StdOut.println("Random choice is " + randnum);    //print the choice for testing
    if (randnum == N - 1)   // if random choice is last item in queue
    {
      Item item = q[N - 1]; // set item to return 
      q[N - 1] = null;      // set last item to null
      N--;                   // reduce length by 1
    // for (Item a: this)       // loop for testing
    // {
    //   StdOut.print(a + " ");
    // }
    // StdOut.println();        // end of testing loop
      if (N > 0 && N == q.length/4) resize(q.length/2);   // resize if necessary
      return item;      
    }
    Item choice = q[randnum]; // if random choice is not last item
    q[randnum] = q[N - 1];     // set the item at the index of choice to be the same as last(to reduce the que)
    q[N - 1] = null;           // set the last one to null
    // for (Item a: this)       // loop for testing
    // {
    //   StdOut.print(a + " ");
    // }
    // StdOut.println();       // end of loop testing
    N--;
    if (N > 0 && N == q.length/4) resize(q.length/2);  // resize if necessary
    return choice;
   }

   public Item sample()                     // return (but do not remove) a random item
   {
    if (isEmpty()) throw new java.util.NoSuchElementException();
    return q[StdRandom.uniform(0, N)];
   }

   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> 
    {
        private int i = 0;
        private Item[] temp = (Item[]) new Object[N];

        private ArrayIterator()
        {
         for (int idx = 0; idx < N; idx++)
         {
          temp[idx] = q[idx];
         } 
         StdRandom.shuffle(temp);   
        }

        public boolean hasNext()  { return i < N; }

        public void remove()      { throw new UnsupportedOperationException(); }

        public Item next()
        {
          if (!hasNext()) throw new NoSuchElementException();
          return temp[i++];
        }


    }
   public static void main(String[] args)   // unit testing
   {
    // RandomizedQueue<Integer> que = new <Integer>RandomizedQueue();
    // for (int i = 0; i < 10; i++)
    // {
    //   que.enqueue(i);
    // }
    // que.enqueue(1);
    // que.enqueue(2);
    // // // StdOut.println(que.dequeue());
    // // // que.enqueue(2);
    // // // StdOut.println(que.dequeue());
    // que.enqueue(3);
    // que.enqueue(4);
    // que.enqueue(5);
    // for (Integer a: que)
    // {
    //   StdOut.println(a);
    // }
    // StdOut.println("sample is " + que.sample());
    // StdOut.println(que.dequeue());
    // que.enqueue(6);
    // // que.resize(10);
    // que.enqueue(7);
    // que.enqueue(1);
    // que.enqueue(2);
    // que.enqueue(3);
    // que.enqueue(15);
    // que.enqueue(16);
    // que.enqueue(17);
    // for (int i = 0; i < 10; i++)
    // {
    //   que.dequeue();
    //   StdOut.println("size is " + que.size());
    // }
    // // que.resize(5);
    // que.enqueue(6);
    // que.enqueue(7);
    // que.enqueue(1);
    // que.enqueue(2); 
  }  
}