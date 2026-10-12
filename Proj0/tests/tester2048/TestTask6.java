package tester2048;
import game2048logic.GameLogic;
import jh61b.grader.GradedTest;
import org.junit.jupiter.api.*;

import static com.google.common.truth.Truth.assertWithMessage;
import static tester2048.TestUtils.boardToString;

/** Tests the tilt() method in the up (Side.NORTH) direction only.
 *
 * @author Josh Hug, Omar Khan, Erik Kizior
 */
@Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTask6 {

    /** Move tiles up (no merging). */
    @Test
    @Tag("task6")
    @Order(1)
    @DisplayName("Up Tilt")
    @GradedTest(number = "6.1")
    public void testUpNoMerge() {
        int[][] board = new int[][] {
                {0, 0, 4, 0},
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        GameLogic.tiltUp(board);

        int[][] expected = new int[][] {
                {0, 0, 4, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        assertWithMessage("Boards should match:").that(boardToString(board)).isEqualTo(boardToString(expected));
    }

    /** Move tiles up with a merge. Must merge in the proper order. Score does not matter. */
    @Test
    @Tag("task6")
    @Order(2)
    @DisplayName("Up Tilt")
    @GradedTest(number = "6.2")
    public void testUpMergeNoSkips() {
        int[][] board = new int[][]{
                {4, 4, 4, 4},
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {4, 4, 4, 4},
        };

        GameLogic.tiltUp(board);

        int[][] expected = new int[][]{
                {4, 4, 4, 4},
                {4, 4, 4, 4},
                {4, 4, 4, 4},
                {0, 0, 0, 0},
        };

        assertWithMessage("Boards should match:").that(boardToString(board)).isEqualTo(boardToString(expected));
    }

    /** Move tiles up with trickier merges. Score does not matter. */
    @Test
    @Tag("task6")
    @Order(3)
    @DisplayName("Up Tilt")
    @GradedTest(number = "6.3")
    public void testUpComplicated() {
        int[][] board = new int[][] {
                {4, 4, 4, 0},
                {0, 4, 8, 2},
                {2, 4, 2, 2},
                {4, 4, 2, 0},
        };

        GameLogic.tiltUp(board);

        int[][] expected = new int[][] {
                {4, 8, 4, 4},
                {2, 8, 8, 0},
                {4, 0, 4, 0},
                {0, 0, 0, 0},
        };

        assertWithMessage("Boards should match:").that(boardToString(board)).isEqualTo(boardToString(expected));
    }
}
