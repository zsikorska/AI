package game;

import player.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final char[][] board;
    private final int size = 8;

    public Board() {
        board = new char[size][size];

        // Set up the board
        for (int i = 0; i < size; i++) {
            Arrays.fill(board[i], ' ');
        }

        board[3][3] = 'w';
        board[3][4] = 'b';
        board[4][3] = 'b';
        board[4][4] = 'w';
    }

    public char[][] getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }

    public Board cloneBoard(){
        Board newBoard = new Board();
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, newBoard.board[i], 0, size);
        }
        return newBoard;
    }

    public void printBoard() {
        System.out.println("##### Board #####");
        System.out.println();
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");

            for (int j = 0; j < size; j++) {
                if(board[i][j] == 'b')
                    System.out.print("◯" + " ");
                else if(board[i][j] == 'w')
                    System.out.print("●" + " ");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
    }

    public int countPoints(char color) {
        int points = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board[i][j] == color)
                    points++;
            }
        }
        return points;
    }

    public void printResults() {
        int black = 0;
        int white = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board[i][j] == 'b')
                    black++;
                else if(board[i][j] == 'w')
                    white++;
            }
        }

        System.out.println();
        System.out.println("##### Results #####");
        System.out.printf("\n%8s %3s %8s","player1", "vs", "player2");
        System.out.printf("\n%4d %8s %4d \n", black, "", white);
        System.out.println("-------------------");

        if(black > white)
            System.out.println("Black won !!!");
        else if(black == white)
        System.out.println("Draw !!!");
        else
            System.out.println("White won !!!");
    }

    public ArrayList<String> getValidMoves(char color) {
        ArrayList<String> validMoves = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(isValidMove(i, j, color))
                    validMoves.add(i + " " + j);
            }
        }
        return validMoves;
    }

    public boolean isValidMove(int currentRow, int currentCol, char playerColor) {
        if (board[currentRow][currentCol] != ' ')
            return false;

        char opponentColor = playerColor == 'b' ? 'w' : 'b';

        // Check all 8 directions of possible moves
        int[] rowDirections = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colDirections = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            // Check if the move is valid in the current direction
            int row = currentRow + rowDirections[i];
            int col = currentCol + colDirections[i];

            // Check if it is not end of the board and the next cell is opponent's color
            if (row >= 0 && row < size && col >= 0 && col < size && board[row][col] == opponentColor) {

                // Check if there is a player's color in the same direction, if not continue
                while (row >= 0 && row < size && col >= 0 && col < size && board[row][col] == opponentColor) {
                    row += rowDirections[i];
                    col += colDirections[i];
                }

                // Check if a player's color is found
                if (row >= 0 && row < size && col >= 0 && col < size && board[row][col] == playerColor)
                    return true;
            }
        }
        return false;
    }

    public boolean isGameOver() {
        // Check if there are no valid moves for both players
        return getValidMoves('b').size() == 0 && getValidMoves('w').size() == 0;
    }

    public void makeMove(int currentRow, int currentCol, char playerColor) {
        char opponentColor = playerColor == 'b' ? 'w' : 'b';

        // Check all 8 directions of possible moves
        int[] rowDirections = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colDirections = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            // Check if the move is valid in the current direction
            int row = currentRow + rowDirections[i];
            int col = currentCol + colDirections[i];

            // Check if it is not end of the board and the next cell is opponent's color
            if (row >= 0 && row < size && col >= 0 && col < size && board[row][col] == opponentColor) {

                // Check if there is a player's color in the same direction, if not continue
                while (row >= 0 && row < size && col >= 0 && col < size && board[row][col] == opponentColor) {
                    row += rowDirections[i];
                    col += colDirections[i];
                }

                // Check if a player's color is found
                if (row >= 0 && row < size && col >= 0 && col < size && board[row][col] == playerColor) {
                    // Go back to the first opponent's color and change it to player's color
                    while (row != currentRow || col != currentCol) {
                        row -= rowDirections[i];
                        col -= colDirections[i];
                        board[row][col] = playerColor;
                    }
                }
            }
        }
        board[currentRow][currentCol] = playerColor;
    }
}
