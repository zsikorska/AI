package game;

import java.util.Arrays;

public class Board {

    private char[][] board;
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

    public void printBoard() {
        System.out.println("##### Board #####");
        System.out.println();
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");

            for (int j = 0; j < size; j++) {
                if(board[i][j] == 'b')
                    System.out.print("●" + " ");
                else if(board[i][j] == 'w')
                    System.out.print("◯" + " ");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
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
            System.out.println("Player1 won !!!");
        else if(black == white)
        System.out.println("Draw !!!");
        else
            System.out.println("Player2 won !!!");
    }
}
