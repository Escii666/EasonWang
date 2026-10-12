package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return 0.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        // TODO: Fill this in in tasks 2, 3, 4
        //如果为空 不移动
        if(board[r][c]==0)
        {
            return 0;
        }
        //进行向上移动
        while (r > minR && board[r-1][c]==0)
        {
            board[r-1][c] = board[r][c];
            board[r][c] = 0;
            r -= 1;
        }
        if(r>minR && board[r-1][c]== board[r][c])
        {
            board[r-1][c] = board[r][c]*2;
            board[r][c] = 0;
            //r -= 1;
            return r;
        }
        return 0;
    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        // TODO: fill this in in task 5
        //记录是否合并
        int mergecheck = 0;
        int minR = 0;
        for(int r=0;r<board.length;r++)
        {
            mergecheck =moveTileUpAsFarAsPossible(board,r,c,minR);

            //若合并，记录并保证不再合并
            if(mergecheck!=0)
            {
                minR = mergecheck;
            }
        }

        return;
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        // TODO: fill this in in task 6
        //遍历所有列 实现向上动的效果
        for(int c = 0;c<board[0].length;c++)
        {
            tiltColumn(board,c);
        }
        return;
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // TODO: fill this in in task 7
        if (side == Side.EAST) {
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
            return;
        } else if (side == Side.WEST) {
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
            return;
        } else if (side == Side.SOUTH) {
            rotateLeft(board);
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
            rotateRight(board);
            return;
        } else {
            tiltUp(board);
            return;
        }
    }
}
