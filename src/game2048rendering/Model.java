package game2048rendering;

import edu.princeton.cs.algs4.Queue;
import game2048logic.GameLogic;

import java.util.*;

import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;


/** The state of a game of 2048.
 *  @author P. N. Hilfinger + Josh Hug
 */
public class Model {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;

    /* Coordinate System: column x, row y of the board (where x = 0,
     * y = 0 is the lower-left corner of the board) will correspond
     * to board.tile(x, y).  Be careful!
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = 0;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (x, y) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score) {
        board = new Board(rawValues);
        this.score = score;
    }

    /** Return the current Tile at (x, y), where 0 <= x < size(),
     *  0 <= y < size(). Returns null if there is no tile there.
     *  Used for testing. */
    public Tile tile(int x, int y) {
        return board.tile(x, y);
    }

    /** Return the number of squares on one side of the board. */
    public int size() {
        return board.size();
    }

    /** Return the current score. */
    public int score() {
        return score;
    }


    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        board.clear();
    }


    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        return maxTileExists() || !atLeastOneMoveExists();
    }

    /** Returns this Model's board. */
    public Board getBoard() {
        return board;
    }

    /** Returns true if at least one space on the board is empty.
     *  Empty spaces are stored as null.
     * */
    public boolean emptySpaceExists() {
        for (int x = 0; x < board.size(); x++) {
            for (int y = 0; y < board.size(); y++) {
                if (board.tile(x, y) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public boolean maxTileExists() {
        for (int x = 0; x < board.size(); x++) {
            for (int y = 0; y < board.size(); y++) {
                Tile t = board.tile(x, y);
                if (t != null && t.value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public boolean atLeastOneMoveExists() {
        if (emptySpaceExists()) {
            return true;
        }
        for (int r = 0; r < board.size(); r += 1) {
            for (int c = 0; c < board.size() - 1; c += 1) {
                if (board.tile(c, r).value() == board.tile(c + 1, r).value()) {
                    return true;
                }
            }
        }
        for (int r = 0; r < board.size() - 1; r += 1) {
            for (int c = 0; c < board.size(); c += 1) {
                if (board.tile(c, r).value() == board.tile(c, r + 1).value()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void tilt(Side side) {
        int[][] before = copyBoard(this.board.rawValues());
        int[][] after = this.board.rawValues();
        GameLogic.tilt(after, side);

        // If at least something changed, then try to interpolate
        // the moves that led to that change.
        if (!Arrays.deepEquals(before, after)) {
            interpolateMoves(after, side);
        }
        updateToMatchBoardState(after, side);
    }

    /** collect all tiles in current column from top to bottom */
    private Queue<Tile> buildTileQueue(int x) {
        Queue<Tile> q = new Queue<>();
        for (int y = board.size() - 1; y >= 0; y -= 1) {
            if (board.tile(x, y) != null) {
                q.enqueue(board.tile(x, y));
            }
        }
        return q;
    }

    /** Utility class for tracking tile weights that still need to
     *  be accomodated. Shoutout to GPT-o1 for writing this for me because
     *  I am lazy.
     */
    private class NumberTracker {

        // We will store the sequence in a LinkedList for easy removal of front elements.
        private LinkedList<Integer> numbers;

        // Keep track of how many elements have been deleted.
        private int deletedCount;

        /**
         * Constructor that initializes the NumberTracker with a sequence of numbers.
         * The sequence of numbers is column x from the after array.
         */
        public NumberTracker(int after[][], int x) {
            // Copy the incoming list into a LinkedList
            this.numbers = new LinkedList<>();
            for (int r = 0; r < after.length; r += 1) {
                this.numbers.addLast(after[r][x]);
            }
            this.deletedCount = 0;
        }

        /**
         * Subtracts the given value X from the current "front" number of the list.
         * If the front number reaches zero after subtraction, it is removed from the list.
         * You may assume X is always <= the current front number.
         *
         * @param X the value to subtract from the front element.
         */
        public void subtractValue(int X) {
            // Get the current front element (assume the list is not empty).
            int front = numbers.getFirst();

            // Subtract X from this front value.
            front -= X;

            // Check if the front element has become zero.
            if (front == 0) {
                // Remove the front element entirely.
                numbers.removeFirst();
                // Increment our count of deleted numbers.
                deletedCount += 1;
            } else {
                // Otherwise, update the front element with the new value.
                numbers.set(0, front);
            }
        }

        /**
         * Returns the number of elements that have been deleted so far.
         *
         * @return the count of deleted elements.
         */
        public int numDeleted() {
            return deletedCount;
        }
    }

    /** Moves tiles so that they match column x of the student's
     *  after array.
     * @param after
     * @param x
     */
    private void processColumn(int[][] after, int x) {
        // move every tile, possibly to its current position
        Queue<Tile> q = buildTileQueue(x);

        // create a data structure to track each number in column x
        NumberTracker nt = new NumberTracker(after, x);

        while (!q.isEmpty()) {
            Tile nextTile = q.dequeue();

            // the Board class uses board.size() - 1 as its top row
            // whereas the student uses 0 as their top row
            int targetY = board.size() - nt.numDeleted() - 1;

            // if the tile isn't already there, move it
            if (board.tile(x, targetY) != nextTile) {
                board.move(x, targetY, nextTile);
            }

            if (nextTile.wasMerged()) {
                score += nextTile.value() * 2;
            }

            // remove the weight of the tile we just moved
            // (and if that weight becomes, zero, nt advances one position)
            nt.subtractValue(nextTile.value());
        }
    }

    /** Makes a copy of the given 2D array of integers. */
    private static int[][] copyBoard(int[][] m) {
        if (m == null) {
            return null;
        }
        return Arrays.stream(m)
                .map(row -> row == null ? null : row.clone())
                .toArray(int[][]::new);
    }

    /** Figures out the moves to get from the before state to the after state */
    private void interpolateMoves(int[][] after, Side side) {
        board.resetMerged();

        // create a defensive copy
        after = copyBoard(after);
        board.setViewingPerspective(side);

        adjustStudentBoardPerspective(after, side);
        for (int x = 0; x < board.size(); x += 1) {
            processColumn(after, x);
        }

        board.setViewingPerspective(Side.NORTH);
    }

    /**
     * Checks that the board is in the state specified by the student.
     * If the student's implementation is buggy, we may not match,
     * since the interpolated moves may be wrong.
     *
     * @param after the expected board state
     */
    private void updateToMatchBoardState(int[][] after, Side side) {
        if (!Arrays.deepEquals(board.rawValues(), after)) {
            System.out.println("Your GameLogic generated a strange output. Resetting board to match your output.");
            board = new Board(after);
        }
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int y = size() - 1; y >= 0; y -= 1) {
            for (int x = 0; x < size(); x += 1) {
                if (tile(x, y) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(x, y).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (game is %s) %n", score(), over);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Model m) && this.toString().equals(m.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    private static void adjustStudentBoardPerspective(int[][] board, Side s) {
        switch (s) {
            case SOUTH:
                rotateRight(board);
                rotateRight(board);
                break;
            case EAST:
                rotateLeft(board);
                break;
            case WEST:
                rotateRight(board);
                break;
        }
    }

}