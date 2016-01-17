import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> 
{
    private int N;          // size of the deque
    private Node first;     // top of deque
    private Node last;      // tail of deque

   private class Node // helper linked list class
   {
      private Item item;
      private Node next;
      private Node prev;
   }
   public Deque()                           // construct an empty deque
   {
      first = null;
      last = null;
      N = 0;
   }
   public boolean isEmpty()                 // is the deque empty?
   {
     return first == null;
   }
   public int size()                        // return the number of items on the deque
   {
     return N;
   }
   public void addFirst(Item item)          // add the item to the front
   {
        if (item == null) throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (size() == 0) last = first;
        else oldfirst.prev = first;
        N++;

   }
   public void addLast(Item item)           // add the item to the end
   {
     if (item == null) throw new NullPointerException();
     Node oldlast = last;
     last = new Node();
     last.item = item;
     last.prev = oldlast;
     last.next = null;
     if (size() == 0) first = last;
     else   oldlast.next = last;
     N++;
   }
   public Item removeFirst()                // remove and return the item from the front
   {
      if (isEmpty()) throw new NoSuchElementException("Stack underflow");
      Item item = first.item;        // save item to return
      if (size() == 1)   
      {
        last = null;
        first = null; 
      }           
      else
      {
       first = first.next;            // delete first node
       first.prev = null;
      }
      N--;
      return item;                   // return the saved item
    }
   public Item removeLast()                 // remove and return the item from the end
   {
      if (isEmpty()) throw new NoSuchElementException("Deque underflow");
      Item item = last.item;
      if (size() == 1)   
      {
        last = null;
        first = null; 
      }           
      else
      {
      last = last.prev;
      last.next = null;
      }
      N--;
      return item;
   }
    public Iterator<Item> iterator()  { return new DequeIterator();  }  // return an iterator over items in order from front to end

    // an iterator, doesn't implement remove() since it's optional
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        private int len = N;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }
        

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (N < len) remove();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }         
   
   public static void main(String[] args)   // unit testing
   {

   }
}