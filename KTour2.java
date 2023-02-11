import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KTour2 {
    static int[][] board = new int[8][8];
    static int[][] accessibility = new int[8][8];
    static int[] row = {2, 1, -1, -2, -2, -1, 1, 2};
    static int[] col = {1, 2, 2, 1, -1, -2, -2, -1};
    static int currRow = 0;
    static int currCol = 0;

    public static void main(String[] args) {
        readAccessibility();
        board[currRow][currCol] = 1;
        int moveNumber = 2;
        while (moveNumber <= 64) {
            int minAccessibility = 9;
            int minRow = 0;
            int minCol = 0;
            for (int i = 0; i < 8; i++) {
                int nextRow = currRow + row[i];
                int nextCol = currCol + col[i];
                if (isValidMove(nextRow, nextCol) && accessibility[nextRow][nextCol] < minAccessibility) {
                    minAccessibility = accessibility[nextRow][nextCol];
                    minRow = nextRow;
                    minCol = nextCol;
                }
            }
            currRow = minRow;
            currCol = minCol;
            board[currRow][currCol] = moveNumber;
            decreaseAccessibility(currRow, currCol);
            moveNumber++;
        }
        printBoard();
        System.out.println("Number of moves: " + (moveNumber-2));
    }

    private static void readAccessibility() {
        try {
            Scanner sc = new Scanner(new File("access.txt"));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    accessibility[i][j] = sc.nextInt();
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidMove(int nextRow, int nextCol) {
        return nextRow >= 0 && nextRow < 8 && nextCol >= 0 && nextCol < 8 && board[nextRow][nextCol] == 0;
    }

    private static void decreaseAccessibility(int currRow, int currCol) {
        for (int i = 0; i < 8; i++) {
            int nextRow = currRow + row[i];
            int nextCol = currCol + col[i];
            if (isValidMove(nextRow, nextCol)) {
                accessibility[nextRow][nextCol]--;
            }
        }
    }

    private static void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
