import java.util.ArrayList;

public class Solver {
    private ArrayList<Board> solutionStep;
    private Board init;
    private Node node1;
    private Node node2;
    private boolean flag;
    private int movetrack;
    private MinPQ<Node> que1;
    private MinPQ<Node> que2;

    private class Node implements Comparable<Node>
    {
        private Node prev;
        private int movenum;
        private Board board;
        public int compareTo(Node that)
        {
            if (this.board.manhattan() + this.movenum < that.board.manhattan() + that.movenum) return -1;
            else if (this.board.manhattan() + this.movenum > that.board.manhattan() + that.movenum) return 1;
            else return 0;

        }

    }
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        solutionStep = new ArrayList<Board>();
        init = initial;
        que1 = new MinPQ<Node>();
        que2 = new MinPQ<Node>();
        node2 = new Node();
        node2.prev = null;
        node2.movenum = 0;
        node2.board = init.twin();
        que2.insert(node2);
        node1 = new Node();
        node1.prev = null;
        node1.movenum = 0;
        node1.board = init;
        que1.insert(node1);
        while (true)
        {   
            Node oldnode = que1.delMin();
            Node twinnode = que2.delMin();
            // oldnode = que1.delMin();
            // StdOut.println("Que size is " + que1.size());
            if (oldnode.board.isGoal())
            {
                Node temp = new Node();
                temp = oldnode;
                solutionStep.add(temp.board);
                while (temp.prev != null)
                {
                    temp = temp.prev;
                    solutionStep.add(0, temp.board);
                }
                flag = true;
                movetrack = solutionStep.size() - 1;
                break;
            }
            if (twinnode.board.isGoal())
            {
                flag = false;
                solutionStep = null;
                movetrack = -1;
                break;
            }
            // StdOut.println(neighbors.getClass().getName());
            for (Board b: oldnode.board.neighbors())
            {
                node1 = new Node();
                node1.prev = oldnode;
                node1.movenum = oldnode.movenum + 1;
                node1.board = b;
                if (node1.movenum > 1 && !b.equals(oldnode.prev.board)) que1.insert(node1);
                else if (node1.movenum == 1) que1.insert(node1);
            }
            for (Board b: twinnode.board.neighbors())
            {
                node2 = new Node();
                node2.prev = twinnode;
                node2.movenum = twinnode.movenum + 1;
                node2.board = b;
                if (node2.movenum > 1 && !b.equals(twinnode.prev.board)) que2.insert(node2);
                else if (node1.movenum == 1) que2.insert(node2);
            }
            
        }
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
        return solutionStep;
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
        for (Board board : solver.solution())
            StdOut.println(board);
        }
    }
}