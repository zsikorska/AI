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

    public Game(Player player1, Player player2) {
        this.board = new Board();
        this.player1 = player1;
        this.player2 = player2;
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
                player1 = new RandomPlayer('b');
                player2 = new RandomPlayer('w');
            }
            case 2 -> {
                player1 = new RandomPlayer('b');
                player2 = new HumanPlayer('w');
            }
            case 3 -> {
                player1 = new RandomPlayer('b');
                player2 = new AIPlayer('w');
            }
            case 4 -> {
                player1 = new HumanPlayer('b');
                player2 = new HumanPlayer('w');
            }
            case 5 -> {
                player1 = new HumanPlayer('b');
                player2 = new RandomPlayer('w');
            }
            case 6 -> {
                player1 = new HumanPlayer('b');
                player2 = new AIPlayer('w');
            }
            case 7 -> {
                player1 = new AIPlayer('b');
                player2 = new AIPlayer('w');
            }
            case 8 -> {
                player1 = new AIPlayer('b');
                player2 = new RandomPlayer('w');
            }
            case 9 -> {
                player1 = new AIPlayer('b');
                player2 = new HumanPlayer('w');
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

        while (!board.isGameOver()){
            ArrayList<String> validMoves = board.getValidMoves(currentPlayer.getColor());
            if(validMoves.size() == 0) {
                if(currentPlayer.getColor() == 'b')
                    System.out.println("No valid moves for black");
                else
                    System.out.println("No valid moves for white");
                changePlayer();
            }
            else {
                if(currentPlayer.getColor() == 'b')
                    System.out.println("Black's turn");
                else
                    System.out.println("White's turn");

                System.out.println("Available moves (row column): " + validMoves);
                String move = currentPlayer.makeMove(board);
                board.makeMove(Integer.parseInt(String.valueOf(move.charAt(0))),
                        Integer.parseInt(String.valueOf(move.charAt(2))), currentPlayer.getColor());
                changePlayer();
            }

            System.out.println();
            board.printBoard();
            System.out.println();
        }


        board.printResults();
    }

    public void play(){
        currentPlayer = player1;

        while (!board.isGameOver()) {
            ArrayList<String> validMoves = board.getValidMoves(currentPlayer.getColor());
            if (validMoves.size() == 0) {
                changePlayer();
            } else {
                String move = currentPlayer.makeMove(board);
                board.makeMove(Integer.parseInt(String.valueOf(move.charAt(0))),
                        Integer.parseInt(String.valueOf(move.charAt(2))), currentPlayer.getColor());
                changePlayer();
            }

        }
    }

    public Board getBoard() {
        return board;
    }
}
