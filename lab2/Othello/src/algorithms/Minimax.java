package algorithms;

import game.Board;

import java.util.ArrayList;

import static algorithms.Heuristics.complexHeuristic;

public class Minimax {

    private double coinParityWeight = 1;
    private double cornersCapturedWeight = 1;
    private double mobilityWeight = 1;
    private double stabilityWeight = 1;

    public Minimax() {
    }

    public Minimax(double coinParityWeight, double cornersCapturedWeight, double mobilityWeight, double stabilityWeight) {
        this.coinParityWeight = coinParityWeight;
        this.cornersCapturedWeight = cornersCapturedWeight;
        this.mobilityWeight = mobilityWeight;
        this.stabilityWeight = stabilityWeight;
    }

    // player is the maximizing player
    // opponent is the minimizing player
    public Result minimax(Board board, int depth, char playerColor, char opponentColor, boolean isMaximizing) {
        if (depth == 0 || board.isGameOver()) {
            // count the difference from maximizing player perspective
            return new Result("", complexHeuristic(board, playerColor, coinParityWeight, cornersCapturedWeight,
                    mobilityWeight, stabilityWeight));
        }

        // player turn
        else if (isMaximizing){
            double maxValue = Integer.MIN_VALUE;
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
                    if (result.getValue() > maxValue) {
                        maxValue = result.getValue();
                        bestResult = result;
                        result.setBestMove(move);
                    }
                }
            }

            return bestResult;
        }

        // opponent turn
        else{
            double minValue = Integer.MAX_VALUE;
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
                    if (result.getValue() < minValue) {
                        minValue = result.getValue();
                        bestResult = result;
                        result.setBestMove(move);
                    }
                }
            }
            return bestResult;
        }
    }
}
