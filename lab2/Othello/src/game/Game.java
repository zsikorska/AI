package game;

import player.Player;
import player.RandomPlayer;
import player.HumanPlayer;
import player.AIPlayer;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private final Board board;

    public Game() {
        this.board = new Board();
    }

    public void printMenu() {
        System.out.println();
        System.out.println("##### Othello Game #####");
        System.out.println();
        System.out.println("Choose playing mode: ");
        System.out.println("1 <= RandomPlayer vs RandomPlayer");
        System.out.println("2 <= RandomPlayer vs HumanPlayer");
        System.out.println("3 <= RandomPlayer vs AIPlayer");
        System.out.println("4 <= HumanPlayer vs HumanPlayer");
        System.out.println("5 <= HumanPlayer vs RandomPlayer");
        System.out.println("6 <= HumanPlayer vs AIPlayer");
        System.out.println("7 <= AIPlayer vs AIPlayer");
        System.out.println("8 <= AIPlayer vs RandomPlayer");
        System.out.println("9 <= AIPlayer vs HumanPlayer");
        System.out.println();
    }

    public void chooseMode() {
        int mode = -1;
        System.out.print("Mode: ");
        try {
            Scanner scanner = new Scanner(System.in);
            mode = Integer.parseInt(scanner.nextLine());
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("This is not a number");
        }

        switch (mode) {
            case 1 -> {
                player1 = new RandomPlayer(true);
                player2 = new RandomPlayer(false);
            }
            case 2 -> {
                player1 = new RandomPlayer(true);
                player2 = new HumanPlayer(false);
            }
            case 3 -> {
                player1 = new RandomPlayer(true);
                player2 = new AIPlayer(false);
            }
            case 4 -> {
                player1 = new HumanPlayer(true);
                player2 = new HumanPlayer(false);
            }
            case 5 -> {
                player1 = new HumanPlayer(true);
                player2 = new RandomPlayer(false);
            }
            case 6 -> {
                player1 = new HumanPlayer(true);
                player2 = new AIPlayer(false);
            }
            case 7 -> {
                player1 = new AIPlayer(true);
                player2 = new AIPlayer(false);
            }
            case 8 -> {
                player1 = new AIPlayer(true);
                player2 = new RandomPlayer(false);
            }
            case 9 -> {
                player1 = new AIPlayer(true);
                player2 = new HumanPlayer(false);
            }
            default -> {
                System.out.println("Choose number between 1 and 9");
                chooseMode();
            }
        }
    }

    public void changePlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    public void start() {
        printMenu();
        chooseMode();
        currentPlayer = player1;

        board.printBoard();
        System.out.println();

        while (!board.isGameOver(player1, player2)){
            ArrayList<String> validMoves = board.getValidMoves(currentPlayer);
            if(validMoves.size() == 0) {
                if(currentPlayer.isBlack())
                    System.out.println("No valid moves for black");
                else
                    System.out.println("No valid moves for white");
                changePlayer();
            }
            else {
                if(currentPlayer.isBlack())
                    System.out.println("Black's turn");
                else
                    System.out.println("White's turn");

                System.out.println("Available moves (row column): " + validMoves);
                String move = currentPlayer.makeMove(validMoves);
                board.makeMove(Integer.parseInt(String.valueOf(move.charAt(0))),
                        Integer.parseInt(String.valueOf(move.charAt(2))), currentPlayer);
                changePlayer();
            }

            System.out.println();
            board.printBoard();
            System.out.println();
        }


        board.printResults();
    }

}
