package algorithms;

import game.Board;

import java.util.ArrayList;

import static algorithms.Heuristics.differenceOfFieldsHeuristic;

public class Minimax {

    // player is the maximizing player
    // opponent is the minimizing player

    public static Result minimax(Board board, int depth, char playerColor, char opponentColor, boolean isMaximizing) {
        if (depth == 0 || board.isGameOver()) {
            // count the difference from maximizing player perspective
            return new Result("", differenceOfFieldsHeuristic(board.getBoard(), playerColor));
        }

        // player turn
        else if (isMaximizing){
            int maxDiff = Integer.MIN_VALUE;
            Result bestResult = new Result("", Integer.MIN_VALUE);
            ArrayList<String> validMoves = board.getValidMoves(playerColor);

            if(validMoves.size() == 0) {
                bestResult =  minimax(board, depth - 1, playerColor, opponentColor, false);
            }
            else {
                for(String move : board.getValidMoves(playerColor)) {
                    Board newBoard = board.cloneBoard();
                    newBoard.makeMove(Integer.parseInt(String.valueOf(move.charAt(0))),
                            Integer.parseInt(String.valueOf(move.charAt(2))), playerColor);

                    Result result =  minimax(newBoard, depth - 1, playerColor, opponentColor, false);
                    if (result.getMaxDiff() > maxDiff) {
                        maxDiff = result.getMaxDiff();
                        bestResult = result;
                        result.setBestMove(move);
                    }
                }
            }

            return bestResult;
        }

        // opponent turn
        else{
            int minDiff = Integer.MAX_VALUE;
            Result bestResult = new Result("", Integer.MAX_VALUE);
            ArrayList<String> validMoves = board.getValidMoves(playerColor);

            if(validMoves.size() == 0) {
                bestResult =  minimax(board, depth - 1, playerColor, opponentColor, true);
            }
            else{
                for(String move : board.getValidMoves(opponentColor)) {
                    Board newBoard = board.cloneBoard();
                    newBoard.makeMove(Integer.parseInt(String.valueOf(move.charAt(0))),
                            Integer.parseInt(String.valueOf(move.charAt(2))), opponentColor);

                    Result result =  minimax(newBoard, depth - 1, playerColor, opponentColor, true);
                    if (result.getMaxDiff() < minDiff) {
                        minDiff = result.getMaxDiff();
                        bestResult = result;
                        result.setBestMove(move);
                    }
                }
            }
            return bestResult;
        }
    }
}
