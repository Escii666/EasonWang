package game2048logic;

public class MatrixUtils {
    /**
     * Rotates the given board 90 degrees to the left (counter-clockwise).
     * Assumes board is a square matrix (N x N).
     */
    public static void rotateLeft(int[][] board) {
        int n = board.length;
        // Step 1: Transpose the matrix
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = board[i][j];
                board[i][j] = board[j][i];
                board[j][i] = temp;
            }
        }
        // Step 2: Reverse each column
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n / 2; i++) {
                int temp = board[i][j];
                board[i][j] = board[n - 1 - i][j];
                board[n - 1 - i][j] = temp;
            }
        }
    }

    /**
     * Rotates the given board 90 degrees to the right (clockwise).
     * Assumes board is a square matrix (N x N).
     */
    public static void rotateRight(int[][] board) {
        int n = board.length;
        // Step 1: Transpose the matrix
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = board[i][j];
                board[i][j] = board[j][i];
                board[j][i] = temp;
            }
        }
        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = board[i][j];
                board[i][j] = board[i][n - 1 - j];
                board[i][n - 1 - j] = temp;
            }
        }
    }
}
