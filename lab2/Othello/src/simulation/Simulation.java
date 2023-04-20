package simulation;

import algorithms.Result;
import game.Board;
import game.Game;
import player.AIPlayer;
import player.Player;
import player.RandomPlayer;

import java.util.ArrayList;

public class Simulation {

    public static void simulation1() {
        Player player1 = new AIPlayer('b', 5, 1, 0, 0, 0);
        Player player2 = new AIPlayer('w', 5, 1, 0, 0, 0);

        Player player3 = new AIPlayer('b', 5, 0, 1, 0, 0);
        Player player4 = new AIPlayer('w', 5, 0, 1, 0, 0);

        Player player5 = new AIPlayer('b', 5, 0, 0, 1, 0);
        Player player6 = new AIPlayer('w', 5, 0, 0, 1, 0);

        Player player7 = new AIPlayer('b', 5, 0, 0, 0, 1);
        Player player8 = new AIPlayer('w', 5, 0, 0, 0, 1);

        Player player9 = new AIPlayer('b', 5);
        Player player10 = new AIPlayer('w', 5);

        play1(player9, player8);


    }

    public static void play1(Player player1, Player player2) {
        Game game;
        Board board = new Board();
        ArrayList<String> movesBlack = board.getValidMoves('b');
        int blackWins = 0;
        int whiteWins = 0;
        int draws = 0;

        int blackResult = 0;
        int whiteResult = 0;

        int numberOfGames = 0;


        for (String move : movesBlack) {
            board = new Board();
            board.makeMove(Integer.parseInt(String.valueOf(move.charAt(0))),
                    Integer.parseInt(String.valueOf(move.charAt(2))), 'b');
            ArrayList<String> movesWhite = board.getValidMoves('w');

            for (String move2 : movesWhite) {
                game = new Game(player1, player2);
                numberOfGames++;
                Board gameBoard = game.getBoard();
                gameBoard.makeMove(Integer.parseInt(String.valueOf(move.charAt(0))),
                        Integer.parseInt(String.valueOf(move.charAt(2))), 'b');

                gameBoard.makeMove(Integer.parseInt(String.valueOf(move2.charAt(0))),
                        Integer.parseInt(String.valueOf(move2.charAt(2))), 'w');

                game.play();

                int blacks = gameBoard.countPoints('b');
                int whites = gameBoard.countPoints('w');

                blackResult = blackResult + blacks;
                whiteResult = whiteResult + whites;

                if(blacks > whites)
                    blackWins++;
                else if(whites > blacks)
                    whiteWins++;
                else
                    draws++;

            }
        }

        System.out.println("Black wins: " + blackWins);
        System.out.println("White wins: " + whiteWins);
        System.out.println("Draws: " + draws);
        System.out.println();
        System.out.println("Black points average: " + ((double) blackResult / numberOfGames));
        System.out.println("White points average: " + ((double) whiteResult / numberOfGames));


    }

    public static void simulation2(int depth){
        AIPlayer player1 = new AIPlayer('b', depth);
        AIPlayer player2 = new AIPlayer('w', depth);

        AIPlayer player3 = new AIPlayer('b', 5, 1, 0, 0, 0);
        AIPlayer player4 = new AIPlayer('w', 5, 1, 0, 0, 0);


        System.out.println("##### Depth: " + depth + " #####");
        play2(player1, player4);
    }

    public static void play2(AIPlayer player1, AIPlayer player2){
        Game game;
        Board board = new Board();
        ArrayList<String> movesBlack = board.getValidMoves('b');
        int numberOfGames = 0;
        int evaluationCount = 0;
        double totalTime = 0;
        int numberOfMinimax = 0;

        for (String move : movesBlack) {
            board = new Board();
            board.makeMove(Integer.parseInt(String.valueOf(move.charAt(0))),
                    Integer.parseInt(String.valueOf(move.charAt(2))), 'b');
            ArrayList<String> movesWhite = board.getValidMoves('w');

            for (String move2 : movesWhite) {
                    game = new Game(player1, player2);
                    numberOfGames++;
                    Board gameBoard = game.getBoard();
                    gameBoard.makeMove(Integer.parseInt(String.valueOf(move.charAt(0))),
                            Integer.parseInt(String.valueOf(move.charAt(2))), 'b');

                    gameBoard.makeMove(Integer.parseInt(String.valueOf(move2.charAt(0))),
                            Integer.parseInt(String.valueOf(move2.charAt(2))), 'w');

                    AIPlayer currentPlayer = player1;

                    while (!gameBoard.isGameOver()) {
                        ArrayList<String> validMoves = gameBoard.getValidMoves(currentPlayer.getColor());
                        if (validMoves.size() == 0) {
                            if (currentPlayer == player1) {
                                currentPlayer = player2;
                            } else {
                                currentPlayer = player1;
                            }
                        } else {
                            System.out.println("Moves: " + validMoves);
                            String newMove = currentPlayer.makeMove(gameBoard);
                            gameBoard.makeMove(Integer.parseInt(String.valueOf(newMove.charAt(0))),
                                    Integer.parseInt(String.valueOf(newMove.charAt(2))), currentPlayer.getColor());

                            if(currentPlayer == player1) {
                                //Result result = currentPlayer.getCurrentResult();
                                evaluationCount += currentPlayer.getCounter();
                                totalTime += currentPlayer.getTime();
                                numberOfMinimax++;
                            }


                            if (currentPlayer == player1) {
                                currentPlayer = player2;
                            } else {
                                currentPlayer = player1;
                            }
                        }

                    }
            }
        }
        System.out.println();
        System.out.println("Number of games: " + numberOfGames);
        System.out.println("Average evaluation count: " + ((double) evaluationCount / numberOfMinimax));
        System.out.println("Average time: " + ((double) totalTime / numberOfMinimax));
    }


    public static void main(String[] args) {
        simulation2(5);
    }
}
