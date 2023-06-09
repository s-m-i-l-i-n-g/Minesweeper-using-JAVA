import java.util.Random;
import java.util.Scanner;
public class Minesweeper {
    private int rows;
    private int cols;
    private int numMines;
    private char[][] board;
    private boolean[][] revealed;
    private boolean gameWon;
    private boolean gameLost;
    public Minesweeper(int rows, int cols, int numMines) {
        this.rows = rows;
        this.cols = cols;
        this.numMines = numMines;
        this.board = new char[rows][cols];
        this.revealed = new boolean[rows][cols];
        this.gameWon = false;
        this.gameLost = false;
    }
    public void play() {
        initializeBoard();
        printBoard();
        while (!gameWon && !gameLost) {
            int[] move = getPlayerMove();
            int row = move[0];
            int col = move[1];
            if (board[row][col] == 'X') {
                gameLost = true;
                revealBoard();
                System.out.println("Game Over! You stepped on a mine.");
            } else {
                revealCell(row, col);
                printBoard();
                if (checkWin()) {
                    gameWon = true;
                    System.out.println("Congratulations! You cleared all the mines.");
                }
            }
        }
    }
    private void initializeBoard() {
        Random random = new Random();
        int count = 0;
        while (count < numMines) {
            int randRow = random.nextInt(rows);
            int randCol = random.nextInt(cols);

            if (board[randRow][randCol] != 'X') {
                board[randRow][randCol] = 'X';
                count++;
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] != 'X') {
                    int mines = countAdjacentMines(i, j);
                    board[i][j] = Character.forDigit(mines, 10);
                }
            }
        }
    }
    private int countAdjacentMines(int row, int col) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isValidCell(row + i, col + j) && board[row + i][col + j] == 'X') {
                    count++;
                }
            }
        }

        return count;
    }
    private boolean isValidCell(int row, int col) {
        return (row >= 0 && row < rows && col >= 0 && col < cols);
    }
    private int[] getPlayerMove() {
        Scanner scanner = new Scanner(System.in);
        int[] move = new int[2];

        System.out.print("Enter row number: ");
        move[0] = scanner.nextInt();

        System.out.print("Enter column number: ");
        move[1] = scanner.nextInt();

        return move;
    }
    private void revealCell(int row, int col) {
        if (isValidCell(row, col) && !revealed[row][col]) {
            revealed[row][col] = true;

            if (board[row][col] == '0') {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        revealCell(row + i, col + j);
                    }
                }
            }
        }
    }
    private boolean checkWin() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] != 'X' && !revealed[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    private void revealBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                revealed[i][j] = true;
            }
        }
    }
    private void printBoard() {
        System.out.println("Minesweeper Board:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (revealed[i][j]) {
                    System.out.print(board[i][j] + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Minesweeper game = new Minesweeper(8, 8, 10);
        game.play();
    }
}
