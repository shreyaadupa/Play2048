package game;

import java.util.ArrayList;

/**
 * 2048 Board
 * Methods to complete:
 * updateOpenSpaces(), addRandomTile(), swipeLeft(), mergeLeft(),
 * transpose(), flipRows(), makeMove(char letter)
 * 
 * @author Kal Pandit
 * @author Ishaan Ivaturi
 **/
public class Board {
    private int[][] gameBoard;               // the game board array
    private ArrayList<BoardSpot> openSpaces; // the ArrayList of open spots: board cells without numbers.

    /**
     * Zero-argument Constructor: initializes a 4x4 game board.
     **/
    public Board() {
        gameBoard = new int[4][4];
        openSpaces = new ArrayList<>();
    }

    /**
     * One-argument Constructor: initializes a game board based on a given array.
     * 
     * @param board the board array with values to be passed through
     **/
    public Board ( int[][] board ) {
        gameBoard = new int[board.length][board[0].length];
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                gameBoard[r][c] = board[r][c];
            }
        }
        openSpaces = new ArrayList<>();
    }

    /**
     * 1. Initializes the instance variable openSpaces (open board spaces) with an empty array.
     * 2. Adds open spots to openSpaces (the ArrayList of open BoardSpots).
     * 
     * Note: A spot (i, j) is open when gameBoard[i][j] = 0.
     * 
     * Assume that gameBoard has been initialized.
     **/
    public void updateOpenSpaces() {
        // WRITE YOUR CODE HERE
        
        openSpaces = new ArrayList<BoardSpot>();

        for(int i = 0; i<gameBoard.length; i++)
        {
            for(int j = 0; j<gameBoard[i].length; j++)
            {
                if(gameBoard[i][j] == 0)
                {
                    openSpaces.add(new BoardSpot(i,j));
                }
                    
            }
        }

    }

    /**
     * Adds a random tile to an open spot with a 90% chance of a 2 value and a 10% chance of a 4 value.
     * Requires separate uses of StdRandom.uniform() to find a random open space and determine probability of a 4 or 2 tile.
     * 
     * 1. Select a tile t by picking a random open space from openSpaces
     * 2. Pick a value v by picking a double from 0 to 1 (not inclusive of 1); < .1 means the tile is a 4, otherwise 2
     * 3. Update the tile t on gameBoard with the value v
     * 
     * Note: On the driver updateOpenStapes() is called before this method to ensure that openSpaces is up to date.
     **/
    public void addRandomTile() {
        // WRITE YOUR CODE HERE

        int t = StdRandom.uniform(0, openSpaces.size());

        //get the row and col value of the t^th number in openspaces arraylist
        //so if random number picked from openspaces was t = 2 then find the col
        //and row value of openspace.get(t).row and val

        BoardSpot index = openSpaces.get(t);

        double v = StdRandom.uniform(0.0,1.0) < 0.1 ? 4.0:2.0; //not exactly sure why this works - i thought it should be flipped

        gameBoard[index.getRow()][index.getCol()] = (int) v;
    
    }

    /**
     * Swipes the entire board left, shifting all nonzero tiles as far left as possible.
     * Maintains the same number and order of tiles. 
     * After swiping left, no zero tiles should be in between nonzero tiles. 
     * (ex: 0 4 0 4 becomes 4 4 0 0).
     **/
    public void swipeLeft() {
        // WRITE YOUR CODE HERE

        //traverse through each row and then move selected cell to left if 
        //there is open space is previous cell space

        for(int a = 0; a<gameBoard.length; a++)
        {

            for(int r = 0; r<gameBoard.length; r++)
            {
                for(int c = 1; c<gameBoard[r].length; c++)
                { 
    
                    int num = 1;
    
                    if(gameBoard[r][c-num] == 0)//if the previous cell is empty
                    {
                        gameBoard[r][c-num] = gameBoard[r][c];//place current cell value into previous cell
                        gameBoard[r][c] = 0;//set current cell value to empty     
                        num++;  
                    }//currently the code is only looping over one time each row
                    //it is not going back to check until there are no empty tiles in between nonempty tiles
                    //nvm it works now!! added a for loop at the top!
    
                }
            }

        }

    }

    /**
     * Find and merge all identical left pairs in the board. Ex: "2 2 2 2" will become "2 0 2 0".
     * The leftmost value takes on double its own value, and the rightmost empties and becomes 0.
     **/
    public void mergeLeft() {
        // WRITE YOUR CODE HERE

        //if the current cell and the left cell are the same number
        //double the value into the left cell
        //empty the current cell - make it 0
        
        for(int r = 0; r<gameBoard.length; r++)
            {
                for(int c = 1; c<gameBoard[r].length; c++)
                { 
        
                    if(gameBoard[r][c-1] == gameBoard[r][c])//if the previous cell is equal to current cell
                    {
                        gameBoard[r][c-1] = gameBoard[r][c]*2;//place current cell double value into left cell
                        gameBoard[r][c] = 0;//set current cell value to empty     
                    }
    
                }
            }


    }

    /**
     * Rotates 90 degrees clockwise by taking the transpose of the board and then reversing rows. 
     * (complete transpose and flipRows).
     * Provided method. Do not edit.
     **/
    public void rotateBoard() {
        transpose();
        flipRows();
    }

    /**
     * Updates the instance variable gameBoard to be its transpose. 
     * Transposing flips the board along its main diagonal (top left to bottom right).
     * 
     * To transpose the gameBoard interchange rows and columns.
     * Col 1 becomes Row 1, Col 2 becomes Row 2, etc.
     * 
     **/
    public void transpose() {
        // WRITE YOUR CODE HERE

        int[][] transposedBoard = new int[gameBoard.length][gameBoard[0].length]; 
        //created new transposed gameboard

        for(int i = 0; i<gameBoard.length; i++)
        {
            for(int j = 0; j<gameBoard[i].length; j++)
            {
                transposedBoard[i][j] = gameBoard[j][i];
                //put in transposed values into new board
                gameBoard[j][i] = 0;
                //set original board to 0
            }
        }

        for(int i = 0; i<gameBoard.length; i++)
        {
            for(int j = 0; j<gameBoard[i].length; j++)
            {
                gameBoard[i][j] = transposedBoard[i][j];
                //took the emptied board and refilled it with transporedboard values
            }
        }
    

    }

    /**
     * Updates the instance variable gameBoard to reverse its rows.
     * 
     * Reverses all rows. Columns 1, 2, 3, and 4 become 4, 3, 2, and 1.
     * 
     **/
    public void flipRows() {
        // WRITE YOUR CODE HERE

        //make a new array to represent flippedrows
        //set col 1 of flippedrows to be col 4 of gameboard
        //continue for all columns
        //set gameboard to 0 - empty it
        //reload flippedrows values into gameboard

        int[][] flippedRows = new int[gameBoard.length][gameBoard[0].length];

        for(int r = 0; r<gameBoard.length; r++)
        {
            flippedRows[r][0] = gameBoard[r][3];
            flippedRows[r][1] = gameBoard[r][2];
            flippedRows[r][2] = gameBoard[r][1];
            flippedRows[r][3] = gameBoard[r][0];

        }

        for(int i = 0; i<gameBoard.length; i++)
        {
            for(int j = 0; j<gameBoard[i].length; j++)
            {
                gameBoard[i][j] = flippedRows[i][j];
                //took the emptied board and refilled it with flippedRows values
            }
        }            

    }

    /**
     * Calls previous methods to make right, left, up and down moves.
     * Swipe, merge neighbors, and swipe. Rotate to achieve this goal as needed.
     * 
     * @param letter the first letter of the action to take, either 'L' for left, 'U' for up, 'R' for right, or 'D' for down
     * NOTE: if "letter" is not one of the above characters, do nothing. 
     **/
    public void makeMove(char letter) {
        // WRITE YOUR CODE HERE

        //4 if statements
        //customize each to go the direction u want by calling previous methods
        
        //left 
        if(letter == ('L'))
        {
            swipeLeft();
            mergeLeft();
            swipeLeft();
        }
        //up 
        else if(letter == 'U')
        {
            rotateBoard();
            rotateBoard();
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
            
        }
        //right
        else if(letter == 'R')
        {
            rotateBoard();
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
            rotateBoard();

        }
        //down 
        else if(letter == 'D')
        {
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
            rotateBoard();
            rotateBoard();
            
        }
        //incorrect letter --> do nothing
        else
        {

        }
    }

    /**
     * Returns true when the game is lost and no empty spaces are available. Ignored
     * when testing methods in isolation.
     * 
     * @return the status of the game -- lost or not lost
     **/
    public boolean isGameLost() {
        return openSpaces.size() == 0;
    }

    /**
     * Shows a final score when the game is lost. Do not edit.
     **/
    public int showScore() {
        int score = 0;
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                score += gameBoard[r][c];
            }
        }
        return score;
    }

    /**
     * Prints the board as integer values in the text window. Do not edit.
     **/
    public void print() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }
    /**
     * Prints the board as integer values in the text window, with open spaces denoted by "**"". Used by TextDriver.
     **/
    public void printOpenSpaces() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                for ( BoardSpot bs : getOpenSpaces() ) {
                    if (r == bs.getRow() && c == bs.getCol()) {
                        g = "**";
                    }
                }
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }

    /**
     * Seed Constructor: Allows students to set seeds to debug random tile cases.
     * 
     * @param seed the long seed value
     **/
    public Board(long seed) {
        StdRandom.setSeed(seed);
        gameBoard = new int[4][4];
    }

    /**
     * Gets the open board spaces.
     * 
     * @return the ArrayList of BoardSpots containing open spaces
     **/
    public ArrayList<BoardSpot> getOpenSpaces() {
        return openSpaces;
    }

    /**
     * Gets the board 2D array values.
     * 
     * @return the 2D array game board
     **/
    public int[][] getBoard() {
        return gameBoard;
    }
}
