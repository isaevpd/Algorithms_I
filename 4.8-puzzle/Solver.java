import java.util.ArrayList;

public class Solver {
    private ArrayList<Board> solutionStep;
    private Board init;
    private Node node1;
    private Node node2;
    private boolean flag;
    private int movetrack;
    private MaxPQ<Node> que1;
    private MinPQ<Node> que2;

    private class Node implements Comparable<Node>
    {
        private Node prev;
        private int movenum;
        private Board board;
        private int priority;
        public int compareTo(Node that)
        {
            if (this.priority < that.priority) return 1;
            else return -1;
        }

    }
    public Solver(Board board)           // find a solution to the initial board (using the A* algorithm)
    {
        solutionStep = new ArrayList<Board>();
        int count = 0;
        init = board;
        // Comparator<Node> comparator = new 
        que1 = new MaxPQ<Node>();
        // que2 = new MinPQ<Node>();
        node1 = new Node();
        node1.prev = null;
        node1.movenum = 0;
        node1.board = init;
        node1.priority = init.manhattan();
        que1.insert(node1);
        while (true)
        {
      
            // StdOut.println("looping");
            count++;
            Node oldnode = que1.delMax();
            // StdOut.print(oldnode.board);
            // solutionStep.add(oldnode.board);
            if (oldnode.board.isGoal())
            {
                Node temp = new Node();
                temp = oldnode;
                solutionStep.add(temp.board);
                // StdOut.println("halo");
                // StdOut.print(oldnode.board);
                while (temp.prev != null)
                {
                    temp = temp.prev;
                    solutionStep.add(0, temp.board);
                }
                flag = true;
                movetrack = solutionStep.size() - 1;
                break;
            }
            // StdOut.println("entering loop...");
            for (Board b: oldnode.board.neighbors())
            {
                // StdOut.println("Starting the loop!");
                // StdOut.println("start of loop");
                node1 = new Node();
                node1.prev = oldnode;
                node1.movenum = count;
                node1.board = b;
                node1.priority = b.manhattan() + count;
                if (count > 1)
                {
                    if (b.isGoal())
                    {
                        que1.insert(node1);
                        // StdOut.print("Adding goal board to que \n" + node1.board);
                        // StdOut.print("ASDASDASDSADAS\nASDQJWEBASDHKJQWE\nASDQJWKEASD\n");
                        break;
                    }
                    if (!oldnode.prev.board.equals(b))
                    {
                        que1.insert(node1);
                        // StdOut.print("adding regular board \n" + node1.board);   
                    } 
                           
                }
                else
                {
                    que1.insert(node1);
                    // StdOut.print("Addin board in else \n" + node1.board); 
                } 
                
            }
            // for (Node n: que1)
            // {
            //     StdOut.print(n.board);
            //     StdOut.print("manhattan is " + n.board.manhattan());
            //     StdOut.println(" moves " + n.movenum);
            //     StdOut.println(n.priority());
            // } 
            // if (count == 3) break;         
        }


        // for (Board b: solutionStep) StdOut.print(b);
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return flag;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return movetrack;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        return null;
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else 
        {
        StdOut.println("Minimum number of moves = " + solver.moves());
        // for (Board board : solver.solution())
        //     StdOut.println(board);
        }
    }
}