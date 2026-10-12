package tester2048;
import game2048logic.GameLogic;
import jh61b.grader.GradedTest;
import org.junit.jupiter.api.*;

import static com.google.common.truth.Truth.assertWithMessage;
import static tester2048.TestUtils.boardToString;

/** Tests the moveTileUpAsFarAsPossible() method of Model.
 *  Does not expect merge or score to be implemented.
 *
 *
 * @author Josh Hug, Erik Kizior
 */
@Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTask2 {

    /** No merging required. */
    @Test
    @Tag("task2")
    @Order(1)
    @DisplayName("Single tile in empty column")
    @GradedTest(number = "2.1")
    public void testOneTile() {
        int[][] board = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0}
        };

        GameLogic.moveTileUpAsFarAsPossible(board, 3, 0, 0);

        int[][] expected = {
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        assertWithMessage("Boards should match:").that(boardToString(board)).isEqualTo(boardToString(expected));
    }

    /** No merging required. Tile blocks movement. */
    @Test
    @Tag("task2")
    @Order(2)
    @DisplayName("two tiles, different values")
    @GradedTest(number = "2.2")
    public void testTwoTiles() {
        int[][] board = {
                {0, 4, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0}
        };

        GameLogic.moveTileUpAsFarAsPossible(board, 2, 1, 0);

        int[][] expected = {
                {0, 4, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        assertWithMessage("Boards should match:").that(boardToString(board)).isEqualTo(boardToString(expected));
    }

    /** No merging required. Board shouldn't change. */
    @Test
    @Tag("task2")
    @Order(3)
    @DisplayName("one tile, no movement")
    @GradedTest(number = "2.3")
    public void testTileWithItself() {
        int[][] board = {
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        GameLogic.moveTileUpAsFarAsPossible(board, 0, 2, 0);

        int[][] expected = {
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        assertWithMessage("Boards should match:").that(boardToString(board)).isEqualTo(boardToString(expected));
    }
}