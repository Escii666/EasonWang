package tester2048;

import game2048rendering.Model;
import game2048rendering.Side;
import game2048logic.GameLogic;

import java.util.Formatter;

import static com.google.common.truth.Truth.assertWithMessage;

public class TestUtils {
    /**
     * Checks that performing a tilt in the specified direction on the before
     * Model results in the after Model
     */
    public static void checkTilt(int[][] board, int[][] expected, Side direction) {
        String expectedBoard = boardToString(expected);

        String beforeBoard = boardToString(board);
        GameLogic.tilt(board, direction);
        String afterBoard = boardToString(board);

        String errMsg = String.format("Board incorrect. Before tilting towards"
                        + " %s, your board looked like:%s%nAfter the call to"
                        + " tilt, we expected:%s%nBut your board looks like:%s.",
                direction, beforeBoard, expectedBoard, afterBoard);
        assertWithMessage(errMsg).that(afterBoard).isEqualTo(expectedBoard);
    }

    public static String boardToString(int[][] board) {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int r = 0; r < board.length; r += 1) {
            for (int c = 0; c < board.length; c += 1) {
                if (board[r][c] == 0) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", board[r][c]);
                }
            }
            out.format("|%n");
        }
        return out.toString();
    }
}
