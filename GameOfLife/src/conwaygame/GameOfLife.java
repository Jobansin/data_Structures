package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {
        
        StdIn.setFile(file);
        int row = StdIn.readInt();
        int col = StdIn.readInt();
        grid = new boolean[row][col];
        for(int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[r].length; c++)
            {
                grid[r][c] = StdIn.readBoolean();
            }
        }  
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        // WRITE YOUR CODE HERE
        return grid[row][col]; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        // WRITE YOUR CODE HERE
        for(int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[r].length; c++)
            {
                if(grid[r][c])
                    return true;
            }
        }  
        return false; // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        int aliveCells = 0;
        int newCol = col - 1;
        int newRow = row - 1;
        int moreRow = row + 1;
        int moreCol = col + 1;
        // check top left
        if(newCol < 0)
            newCol = (newCol + grid[0].length) % grid[0].length;
        if(newRow < 0)
            newRow = (newRow + grid.length) % grid.length;
        if(moreRow > grid.length - 1)
            moreRow = (moreRow - grid.length) % grid.length;
        if(moreCol > grid[0].length - 1)
            moreCol = (moreCol - grid[0].length) % grid[0].length;
        if(grid[newRow][newCol]) // make sure to check if go outside the grid range
            aliveCells++;
        // check directly top
        if(grid[newRow][ col ]) 
            aliveCells++;
        // check top right
        if(grid[newRow][ moreCol]) 
            aliveCells++;
        // check left
        if(grid[row ][newCol]) 
            aliveCells++;
        // check right
        if(grid[row ][moreCol]) 
            aliveCells++;
        // check bottom right
        if(grid[moreRow][moreCol]) 
            aliveCells++;
        // check directly bottom 
        if(grid[moreRow][ col ]) 
            aliveCells++;
        // check bottom left
        if(grid[moreRow][newCol]) 
            aliveCells++;
        return aliveCells; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        boolean [][] newGrid = new boolean[grid.length][grid[0].length];
        // WRITE YOUR CODE HERE
        for(int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[r].length; c++)
            {
                // check is alive
                if (grid[r][c])
                {
                    if(numOfAliveNeighbors (r, c) <= 1)
                        newGrid[r][c] = false;
                    if(numOfAliveNeighbors (r, c) == 2)
                        newGrid[r][c] = true;
                    if(numOfAliveNeighbors (r, c) == 3)
                        newGrid[r][c] = true;
                    if(numOfAliveNeighbors (r, c) >= 4)
                        newGrid[r][c] = false;
                }
                // check if dead  
                else
                {
                    if(numOfAliveNeighbors(r,c) == 3)
                        newGrid[r][c] = true;
                }
            }
        }  
        
        // if cell is initially alive numOfAliveNeighbors is <= 1 cell is now dead
        // if cell is initially dead numOfAliveNeighbors is = 3 cell is now alive
        // if cell is intially alive numOfAliveNeighbors is = 2 \\ 3 cell stays alive
        // if cell is intially alive numOfAliveNeighbors is >= 4 cell dies
        // Gets total from the statements above and creates new cells that can be alive or dead
        return newGrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        // WRITE YOUR CODE HERE
        grid = computeNewGrid ();
        totalAliveCells = getTotalAliveCells ();
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {
        for(int x = 1; x <= n; x++)
        nextGeneration();
// WRITE YOUR CODE HERE
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(grid.length, grid[0].length);
        ArrayList<Integer> unique = new ArrayList<>();
        for(int r = 0; r < grid.length; r++)
        {
            for(int c = 0; c < grid[r].length; c++)
            {
                if(grid[r][c])
                {
                    int newCol = c - 1;
                    int newRow = r - 1;
                    int moreRow = r + 1;
                    int moreCol = c + 1;
                    if(newCol < 0)
                        newCol = (newCol + grid[0].length) % grid[0].length;
                    if(newRow < 0)
                        newRow = (newRow + grid.length) % grid.length;
                    if(moreRow > grid.length - 1)
                        moreRow = (moreRow - grid.length) % grid.length;
                    if(moreCol > grid[0].length - 1)
                        moreCol = (moreCol - grid[0].length) % grid[0].length;
                    if(grid[newRow][newCol]) 
                        uf.union(r, c, newRow, newCol);
                    if(grid[newRow][ c ]) 
                        uf.union(r, c, newRow, c);
                    if(grid[newRow][ moreCol]) 
                        uf.union(r, c, newRow, moreCol);
                    if(grid[r ][newCol]) 
                        uf.union(r, c, r, newCol);
                    if(grid[r ][moreCol]) 
                        uf.union(r, c, r, moreCol);
                    if(grid[moreRow][moreCol]) 
                        uf.union(r, c, moreRow, moreCol);
                    if(grid[moreRow][ c ]) 
                        uf.union(r, c, moreRow, c);
                    if(grid[moreRow][newCol]) 
                        uf.union(r, c, moreRow, newCol);
                }
                else
                    continue;
            }
        }
        for(int r = 0; r < grid.length; r++)
        {
            for(int c = 0; c < grid[r].length; c++)
            {
                if(grid[r][c])
                {
                    int num = uf.find(r, c);
                    if(!unique.contains(num))
                        unique.add(num);
                }
            }
        }
        return unique.size(); // update this line, provided so that code compiles
    }
}
