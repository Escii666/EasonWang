package tester2048;

import game2048rendering.Side;
import jh61b.grader.GradedTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Order;

import static tester2048.TestUtils.checkTilt;

/** Integration Tests for all methods. Broken into three sections:
 *  Tilt Tests, Multiple Move Tests, and NxN Tests.
 *
 * @author Samuel Berkun, Ergun Acikoz, Erik Kizior, Josh Hug
 */
@Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class TestIntegration {
    /*
     * ******************
     * *  TESTING TILT  *
     * ******************
     * <p>
     * The following tests determine the correctness of your `tilt`
     * method.
     */

    /** Checks that the right two pieces merge when 3 adjacent pieces have same value. */
    @Test
    @Tag("integration")
    @Order(1)
    @DisplayName("3 tile merge")
    @GradedTest(number = "8.1")
    public void testTripleMerge1() {
        int[][] before = new int[][]{
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {4, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.NORTH);
    }

    /** Checks that the right two pieces merge when 3 adjacent pieces have same value. */
    @Test
    @Tag("integration")
    @Order(2)
    @DisplayName("3 tile merge")
    @GradedTest(number = "8.2")
    public void testTripleMerge2() {
        int[][] before = new int[][]{
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
                {4, 0, 0, 0},
        };
        checkTilt(before, after, Side.SOUTH);
    }

    /** Checks two adjacent merges work. */
    @Test
    @Tag("integration")
    @Order(3)
    @DisplayName("adjacent merge")
    @GradedTest(number = "8.3")
    public void testQuadrupleMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 2},
                {0, 0, 0, 2},
                {0, 0, 0, 2},
                {0, 0, 0, 2},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 4},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.NORTH);
    }

    /** Checks that a tile only merges once per tilt. */
    @Test
    @Tag("integration")
    @Order(4)
    @DisplayName("One merge per North tilt")
    @GradedTest(number = "8.4")
    public void testSingleMergeUp() {
        int[][] before = new int[][]{
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {4, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.NORTH);
    }

    /** Checks that a tile only merges once per tilt. */
    @Test
    @Tag("integration")
    @Order(5)
    @DisplayName("One merge per South tilt")
    @GradedTest(number = "8.5")
    public void testSingleMergeSouth() {
        int[][] before = new int[][]{
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
                {2, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {4, 0, 0, 0},
        };
        checkTilt(before, after, Side.SOUTH);
    }

    /** Checks that a tile only merges once per tilt. */
    @Test
    @Tag("integration")
    @Order(6)
    @DisplayName("One merge per East tilt")
    @GradedTest(number = "8.6")
    public void testSingleMergeEast() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 0, 2, 2},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 4, 4},
        };
        checkTilt(before, after, Side.EAST);
    }

    /** Checks that a tile only merges once per tilt. */
    @Test
    @Tag("integration")
    @Order(7)
    @DisplayName("One merge per West tilt")
    @GradedTest(number = "8.7")
    public void testSingleMergeWest() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 2, 0, 4},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 4, 0, 0},
        };
        checkTilt(before, after, Side.WEST);
    }


    /** Merge adjacent tiles up. */
    @Test
    @Tag("integration")
    @Order(8)
    @DisplayName("Up tilt with merge")
    @GradedTest(number = "8.8")
    public void testUpAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.NORTH);
    }

    /** Merge non-adjacent tiles up. */
    @Test
    @Tag("integration")
    @Order(9)
    @DisplayName("Up tilt with gap and merge")
    @GradedTest(number = "8.9")
    public void testUpNonAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.NORTH);
    }

    /** Move and merge adjacent tiles up. */
    @Test
    @Tag("integration")
    @Order(10)
    @DisplayName("Up tilt with gaps and merge")
    @GradedTest(number = "8.10")
    public void testUpAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.NORTH);
    }

    /** Merge adjacent tiles right. */
    @Test
    @Tag("integration")
    @Order(11)
    @DisplayName("Adjacent right merge")
    @GradedTest(number = "8.11")
    public void testRightAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 2},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.EAST);
    }

    /** Merge non-adjacent tiles right. */
    @Test
    @Tag("integration")
    @Order(12)
    @DisplayName("Right merge with gap")
    @GradedTest(number = "8.12")
    public void testRightNonAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 2},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.EAST);
    }

    /** Move and merge adjacent tiles right. */
    @Test
    @Tag("integration")
    @Order(13)
    @DisplayName("Adjacent merge with gaps")
    @GradedTest(number = "8.13")
    public void testRightAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.EAST);
    }

    /** Move and merge non-adjacent tiles right. */
    @Test
    @Tag("integration")
    @Order(14)
    @DisplayName("Right merge with gaps")
    @GradedTest(number = "8.14")
    public void testRightNonAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.EAST);
    }

    /** Don't merge non-adjacent divided tiles right. */
    @Test
    @Tag("integration")
    @Order(15)
    @DisplayName("Right no merge with gaps")
    @GradedTest(number = "8.15")
    public void testRightNonAdjacentNoMerge() {
        int[][] before = new int[][]{
                {2, 4, 0, 2},
                {2, 0, 4, 2},
                {2, 4, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 2, 4, 2},
                {0, 2, 4, 2},
                {0, 2, 4, 2},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.EAST);
    }

    /** Merge adjacent tiles down. */
    @Test
    @Tag("integration")
    @Order(16)
    @DisplayName("Adjacent down merge")
    @GradedTest(number = "8.16")
    public void testDownAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 0, 0},
        };
        checkTilt(before, after, Side.SOUTH);
    }

    /** Merge non-adjacent tiles down. */
    @Test
    @Tag("integration")
    @Order(17)
    @DisplayName("Down merge")
    @GradedTest(number = "8.17")
    public void testDownNonAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 0, 0},
        };
        checkTilt(before, after, Side.SOUTH);
    }

    /** Move and merge adjacent tiles down. */
    @Test
    @Tag("integration")
    @Order(18)
    @DisplayName("Adjacent down move and merge")
    @GradedTest(number = "8.18")
    public void testDownAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 0, 0},
        };
        checkTilt(before, after, Side.SOUTH);
    }

    /** Move and merge non-adjacent tiles down. */
    @Test
    @Tag("integration")
    @Order(19)
    @DisplayName("Down move and merge")
    @GradedTest(number = "8.19")
    public void testDownNonAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 0, 0},
        };
        checkTilt(before, after, Side.SOUTH);
    }

    /** Don't merge non-adjacent divided tiles right. */
    @Test
    @Tag("integration")
    @Order(20)
    @DisplayName("Down no merge with gaps")
    @GradedTest(number = "8.20")
    public void testDownNonAdjacentNoMerge() {
        int[][] before = new int[][]{
                {2, 2, 2, 0},
                {4, 0, 4, 0},
                {2, 4, 0, 0},
                {0, 2, 2, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {2, 2, 2, 0},
                {4, 4, 4, 0},
                {2, 2, 2, 0},
        };
        checkTilt(before, after, Side.SOUTH);
    }

    /** Merge adjacent tiles left. */
    @Test
    @Tag("integration")
    @Order(21)
    @DisplayName("Left adjacent merge")
    @GradedTest(number = "8.21")
    public void testLeftAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {2, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.WEST);
    }

    /** Merge non-adjacent tiles left. */
    @Test
    @Tag("integration")
    @Order(22)
    @DisplayName("Left merge")
    @GradedTest(number = "8.22")
    public void testLeftNonAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {2, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.WEST);
    }

    /** Move and merge adjacent tiles left. */
    @Test
    @Tag("integration")
    @Order(23)
    @DisplayName("Adjacent merge and move")
    @GradedTest(number = "8.23")
    public void testLeftAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.WEST);
    }

    /** Move and merge non-adjacent tiles left. */
    @Test
    @Tag("integration")
    @Order(24)
    @DisplayName("Merge and move with gaps")
    @GradedTest(number = "8.24")
    public void testLeftNonAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(before, after, Side.WEST);
    }

    /*
     * *************************
     * *****  N X N TESTS  *****
     * *************************
     * <p>
     * The following tests will call the `tilt` method on boards of different sizes.
     */

    /** Tilting an empty 1 by 1 */
    @Test
    @Tag("integration")
    @Order(25)
    @DisplayName("The ants go marching")
    @GradedTest(number = "8.25")
    public void testOne() {
        int[][] before = new int[][] {
                {0},
        };
        int[][] after = new int[][] {
                {0},
        };
        checkTilt(before, after, Side.NORTH);
    }

    @Test
    @Tag("integration")
    @Order(26)
    @DisplayName("Non-merged tilts for N = 1, 2, 3")
    @GradedTest(number = "8.26")
    public void testSmallNonMergedTilts() {
        int[][] before;
        int[][] after;

        before = new int[][] {
                {0},
        };
        after = new int[][] {
                {0},
        };
        checkTilt(before, after, Side.NORTH);

        before = new int[][] {
                {0, 0},
                {2, 2},
        };
        after = new int[][] {
                {2, 2},
                {0, 0},
        };
        checkTilt(before, after, Side.NORTH);

        before = new int[][] {
                {0, 2},
                {2, 0},
        };
        after = new int[][] {
                {2, 0},
                {2, 0},
        };
        checkTilt(before, after, Side.WEST);

        before = new int[][] {
                {4, 0, 4},
                {2, 16, 2},
                {0, 0, 8},
        };
        after = new int[][] {
                {0, 0, 4},
                {4, 0, 2},
                {2, 16, 8},
        };
        checkTilt(before, after, Side.SOUTH);
    }


    /** Tilts for N = 1, 2, 3 */
    @Test
    @Tag("integration")
    @Order(27)
    @DisplayName("Tilts for N = 1, 2, 3")
    @GradedTest(number = "8.27")
    public void testSmallTilts() {
        int[][] before;
        int[][] after;

        before = new int[][] {
                {4},
        };
        after = new int[][] {
                {4},
        };
        checkTilt(before, after, Side.NORTH);

        before = new int[][] {
                {2, 2},
                {0, 2},
        };
        after = new int[][] {
                {4, 0},
                {2, 0},
        };
        checkTilt(before, after, Side.WEST);

        before = new int[][] {
                {8, 0, 2},
                {0, 0, 2},
                {0, 0, 2},
        };
        after = new int[][] {
                {0, 0, 0},
                {0, 0, 2},
                {8, 0, 4},
        };
        checkTilt(before, after, Side.SOUTH);
    }

    /** Tilt and gameOver for N = 20 */
    @Test
    @Tag("integration")
    @Order(28)
    @GradedTest(name = "TestNbyN: Large", number = "8.28")
    public void testLarge() {
        int[][] before = new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0},
                {0, 0, 0, 4, 4, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 4, 4, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 4, 4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 4, 4, 4, 4, 0, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 4, 4, 0, 0, 0, 4, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 4, 4, 0, 0, 0, 4, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 4, 0, 4, 4, 4, 4, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        int[][] after = new int[][] {
                {8, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 8, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        checkTilt(before, after, Side.WEST);
    }
}