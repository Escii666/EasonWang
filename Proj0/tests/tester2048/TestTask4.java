package tester2048;
import game2048logic.GameLogic;
import jh61b.grader.GradedTest;
import org.junit.jupiter.api.*;

import static com.google.common.truth.Truth.assertWithMessage;

/** Tests the moveTileUpAsFarAsPossible() method of Model with merges,
 *  now evaluting the minR argument.
 *
 * @author Josh Hug, Erik Kizior
 */
@Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTask4 {

    /** Merging required. Tiles of same value in same column.*/
    @Test
    @Tag("task4")
    @Order(1)
    @DisplayName("two tiles merge, minR = 0")
    @GradedTest(number = "3.1")
    public void testTwoTilesMergeMinR0() {
        int[][] board = {
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0}
        };

        int returnValue = GameLogic.moveTileUpAsFarAsPossible(board, 3, 0, 0);

        int[][] expected = {
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        assertWithMessage("Boards should match:").that(TestUtils.boardToString(board)).isEqualTo(TestUtils.boardToString(expected));

        int expectedReturnValue = 1;
        assertWithMessage("Return value should match:").that(returnValue).isEqualTo(expectedReturnValue);
    }

    /** Merging required. Tiles of same value in same column.*/
    @Test
    @Tag("task4")
    @Order(2)
    @DisplayName("two tiles merge, minR = 1")
    @GradedTest(number = "3.2")
    public void testTwoTilesMergeMinR1() {
        int[][] board = {
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0}
        };

        int returnValue = GameLogic.moveTileUpAsFarAsPossible(board, 3, 0, 1);

        int[][] expected = {
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        assertWithMessage("Boards should match:").that(TestUtils.boardToString(board)).isEqualTo(TestUtils.boardToString(expected));

        int expectedReturnValue = 0;
        assertWithMessage("Return value should match:").that(returnValue).isEqualTo(expectedReturnValue);
    }

    /** Merging required. Tiles of same value in same column.*/
    @Test
    @Tag("task4")
    @Order(3)
    @DisplayName("two tiles merge, minR = 3")
    @GradedTest(number = "3.3")
    public void testTwoTilesMergeMinR3() {
        int[][] board = {
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0}
        };

        int returnValue = GameLogic.moveTileUpAsFarAsPossible(board, 3, 0, 3);

        int[][] expected = {
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0}
        };

        assertWithMessage("Boards should match:").that(TestUtils.boardToString(board)).isEqualTo(TestUtils.boardToString(expected));

        int expectedReturnValue = 0;
        assertWithMessage("Return value should match:").that(returnValue).isEqualTo(expectedReturnValue);
    }

    /** Checks that moving up will not attempt to merge tiles separated by another unequally-valued tile. */
    @Test
    @Tag("task4")
    @Order(4)
    @DisplayName("same tile separated, no merge, minR = 0")
    @GradedTest(number = "3.4")
    public void testNoMergeThroughTiles() {
        int[][] board = new int[][]{
                {8, 0, 0, 0},
                {4, 0, 0, 0},
                {8, 0, 0, 0},
                {0, 0, 0, 0},
        };

        int returnValue = GameLogic.moveTileUpAsFarAsPossible(board, 2, 0, 0);

        int[][] expected = new int[][]{
                {8, 0, 0, 0},
                {4, 0, 0, 0},
                {8, 0, 0, 0},
                {0, 0, 0, 0},
        };

        assertWithMessage("Boards should match:").that(TestUtils.boardToString(board)).isEqualTo(TestUtils.boardToString(expected));

        int expectedReturnValue = 0;
        assertWithMessage("Return value should match:").that(returnValue).isEqualTo(expectedReturnValue);
    }

    /** Checks that a tilt will merge with the first value above it. Note that this is
     *  action alone is not achievable in an actual game. */
    @Test
    @Tag("task3")
    @Order(5)
    @DisplayName("merge up, don't skip")
    @GradedTest(number = "3.5")
    public void testMergeNoSkip() {
        int[][] board = new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 2, 0},
        };

        int returnValue = GameLogic.moveTileUpAsFarAsPossible(board, 3, 2, 0);

        int[][] expected = new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 4, 0},
                {0, 0, 0, 0},
        };

        assertWithMessage("Boards should match:").that(TestUtils.boardToString(board)).isEqualTo(TestUtils.boardToString(expected));

        int expectedReturnValue = 3;
        assertWithMessage("Return value should match:").that(returnValue).isEqualTo(expectedReturnValue);
    }

    /** Checks that a tilt will merge with the first value above it. Note that this is
     *  action alone is not achievable in an actual game. */
    @Test
    @Tag("task4")
    @Order(6)
    @DisplayName("merge up, don't skip, minR=3")
    @GradedTest(number = "3.6")
    public void testMergeNoSkipMinR3() {
        int[][] board = new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 2, 0},
        };

        int returnValue = GameLogic.moveTileUpAsFarAsPossible(board, 3, 2, 3);

        int[][] expected = new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 2, 0},
        };

        assertWithMessage("Boards should match:").that(TestUtils.boardToString(board)).isEqualTo(TestUtils.boardToString(expected));

        int expectedReturnValue = 0;
        assertWithMessage("Return value should match:").that(returnValue).isEqualTo(expectedReturnValue);
    }
}
