public class Board 
{
    private int N;
    private char[] tiles;
    private int manhattan;
    private int hamming;
    private int[] zeroidx;

    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
        N = blocks.length;
        zeroidx = new int[2];
        tiles = new char[N * N];
        int idx = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                if (blocks[i][j] == 0)
                {
                    zeroidx[0] = i;
                    zeroidx[1] = j;   
                } 
                tiles[idx++] = (char) blocks[i][j];
            }
        manhattan = 0;
        hamming = 0;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                int current = (int) tiles[getij(i, j)];
                if (current == getij(i, j) + 1 || getij(i, j) == getij(zeroidx[0], zeroidx[1])) continue;
                else
                {
                    int[] goal = helper(current, N);
                    manhattan += Math.abs(goal[0] - i);
                    manhattan += Math.abs(goal[1] - j);
                    hamming++;
                }
            }
        }
    }

    private Board(char[] chartiles)
    {
        N = (int) Math.sqrt(chartiles.length);
        zeroidx = new int[2];
        tiles  = new char[chartiles.length];
        for (int i = 0; i < chartiles.length; i++)
        {
            if ((int) chartiles[i] == 0)
            {
                // StdOut.println("YESS");
                zeroidx[0] = helper(i, N)[0];
                zeroidx[1] = helper(i, N)[1] + 1;   
            } 
            tiles[i] = chartiles[i];
        }
        manhattan = 0;
        hamming = 0;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                int current = (int) tiles[getij(i, j)];
                if (current == getij(i, j) + 1 || getij(i, j) == getij(zeroidx[0], zeroidx[1])) continue;
                else
                {
                    int[] goal = helper(current, N);
                    manhattan += Math.abs(goal[0] - i);
                    manhattan += Math.abs(goal[1] - j);
                    hamming++;
                }
            }
        }

    }


    private Board(char[] chartiles, int man, int[] zero, int hamm)
    {
        N = (int) Math.sqrt(chartiles.length);
        manhattan = man;
        hamming = hamm;
        zeroidx = new int[2];
        zeroidx[0] = zero[0];
        zeroidx[1] = zero[1];
        // hamming = 0;
        tiles = new char[chartiles.length];
        for (int i = 0; i < chartiles.length; i++)
        {
            tiles[i] = chartiles[i];
        }

    }


    private boolean down(int i, int j)
    {
        if (i == N - 1) return false;
        return true;
    }

    private boolean up(int i, int j)
    {
        if (i == 0) return false;
        return true;
    }

    private boolean left(int i, int j)
    {
        if (j == 0) return false;
        return true;
    }

    private boolean right(int i, int j)
    {
        if (j == N - 1) return false;
        return true;
    }


    private int[] helper(int n, int dim)      
    {
        int[] result = new int[2];        //calculates coordinates of a certain point in a goal board
        int row;                          // i.e. helper(5, 3) == (1, 1)
        int col;
        if (n % dim != 0) row = n / dim;
        else row = n / dim - 1;
        if (n % dim != 0) col = n % dim;
        else col = dim;
        result[0] = row;
        result[1] = col - 1;
        return result;
    }

    private int manhattanOne(char[] chartiles, int i, int j)
    {
        int[] goal;
        int point = (int) chartiles[getij(i, j)];
        if (point == 0) return 0;
        goal = helper(point, (int) Math.sqrt(chartiles.length));
        return Math.abs(i - goal[0]) + Math.abs(j - goal[1]);
    }

    private int getij(int i, int j)
    {
        return i * N + j;
    }
    public int dimension()                 // board dimension N
    {
        return N;
    }

    public int manhattan()
    {
        return manhattan;
    }
    public int hamming()
    {
        return hamming;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        return manhattan == 0;
    }
    public Board twin()                    // a board that is obtained by exchanging two adjacent blocks in the same row
    {
        char[] twintiles = new char[tiles.length];
        for (int i = 0; i < tiles.length; i++)
        {
            twintiles[i] = tiles[i];     
        }
        if ((int) tiles[getij(0, 0)] != 0 && (int) tiles[getij(0, 1)] != 0)
        {
            twintiles[getij(0, 0)] = tiles[getij(0, 1)];
            twintiles[getij(0, 1)] = tiles[getij(0, 0)];
        }
        else
        {
            twintiles[getij(1, 0)] = tiles[getij(1, 1)];
            twintiles[getij(1, 1)] = tiles[getij(1, 0)];

        }   
        return new Board(twintiles);

    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        if (this.manhattan() != that.manhattan()) return false;
        for (int i = 0; i < tiles.length; i++)
        {
            if (tiles[i] != that.tiles[i]) return false;
        }
        return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> neighborsstack = new Stack<Board>();
        char[] neighbor = new char[N * N];
        int distance;
        int hammin;
        int[] zeronew = new int[2];
        for (int i = 0; i < tiles.length; i++)
        {
            neighbor[i] = tiles[i];
        }
        int i = zeroidx[0]; // zero coordinates
        int j = zeroidx[1];
        // int[] goal = new int[2];
        if (this.down(i, j))
        {
            neighbor[getij(i, j)] = tiles[getij(i + 1, j)];
            neighbor[getij(i + 1, j)] = 0;
            zeronew[0] = i + 1;
            zeronew[1] = j;
            // StdOut.print("Hello");
            // StdOut.println((int) neighbor[getij(i, j)]);
            // StdOut.println((int) tiles[getij(i + 1, j)]);
            distance = this.manhattan() - manhattanOne(tiles, i + 1, j) + manhattanOne(neighbor, i, j);
            if (manhattanOne(this.tiles, i + 1, j) == 0)
            {
                hammin = this.hamming + 1;
            }
            else if (manhattanOne(neighbor, i, j) == 0)
            {
                hammin = this.hamming - 1;
            }
            else hammin = this.hamming;
            neighborsstack.push(new Board(neighbor, distance, zeronew, hammin));
            neighbor[getij(i, j)] = 0;
            neighbor[getij(i + 1, j)] = tiles[getij(i + 1, j)];
        }
        if (this.up(i, j))
        {
            neighbor[getij(i, j)] = tiles[getij(i - 1, j)];
            neighbor[getij(i - 1, j)] = 0;
            zeronew[0] = i - 1;
            zeronew[1] = j;
            distance = this.manhattan() - manhattanOne(tiles, i - 1, j) + manhattanOne(neighbor, i , j);            
            if (manhattanOne(this.tiles, i - 1, j) == 0)
            {
                hammin = this.hamming + 1;
            }
            else if (manhattanOne(neighbor, i, j) == 0)
            {
                hammin = this.hamming - 1;
            }
            else hammin = this.hamming;
            neighborsstack.push(new Board(neighbor, distance, zeronew, hammin));
            neighbor[getij(i, j)] = 0;
            neighbor[getij(i - 1, j)] = tiles[getij(i - 1, j)];
        }
        if (this.left(i, j))
        {
            neighbor[getij(i, j)] = tiles[getij(i, j - 1)];
            neighbor[getij(i, j - 1)] = 0;
            zeronew[0] = i;
            zeronew[1] = j - 1;
            // StdOut.println((int) tiles[getij(i, j)]);
            // StdOut.println(manhattanOne(tiles, i, j + 1));
            // StdOut.println(manhattanOne(neighbor, i, j));
            distance = this.manhattan() - manhattanOne(tiles, i, j - 1) + manhattanOne(neighbor, i, j);            
            if (manhattanOne(this.tiles, i, j - 1) == 0)
            {
                hammin = this.hamming + 1;
            }
            else if (manhattanOne(neighbor, i, j) == 0)
            {
                hammin = this.hamming - 1;
            }
            else hammin = this.hamming;
            neighborsstack.push(new Board(neighbor, distance, zeronew, hammin));
            neighbor[getij(i, j)] = 0;
            neighbor[getij(i, j - 1)] = tiles[getij(i, j - 1)];
        }
        if (this.right(i, j))
        {
            neighbor[getij(i, j)] = tiles[getij(i, j + 1)];
            neighbor[getij(i, j + 1)] = 0;
            zeronew[0] = i;
            zeronew[1] = j + 1;
            // StdOut.println((int) neighbor[getij(i, j)]+ " Neighbor tile");
            // StdOut.println((int) tiles[getij(i, j + 1)]+ " original tile");
            // StdOut.println(manhattanOne(tiles, i, j) + " tiles manhattan");
            // StdOut.println(manhattanOne(neighbor, i, j + 1) + " neighbor manhattan");
            distance = this.manhattan() - manhattanOne(tiles, i, j + 1) + manhattanOne(neighbor, i, j);            
            if (manhattanOne(this.tiles, i, j + 1) == 0)
            {
                hammin = this.hamming + 1;
            }
            else if (manhattanOne(neighbor, i, j) == 0)
            {
                hammin = this.hamming - 1;
            }
            else hammin = this.hamming;
            neighborsstack.push(new Board(neighbor, distance, zeronew, hammin));
            neighbor[getij(i, j)] = 0;
            neighbor[getij(i, j + 1)] = tiles[getij(i, j + 1)];
        }

        return neighborsstack;

    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
    StringBuilder s = new StringBuilder();
    s.append(N + "\n");
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            s.append(String.format("%2d ", (int) tiles[getij(i, j)]));
        }
        s.append("\n");
    }
    return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {

    }
}