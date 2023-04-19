package player;

import algorithms.Result;
import game.Board;
import algorithms.Minimax;
import algorithms.AlphaBeta;

public class AIPlayer extends Player {

    private int depth = 5;
    private Minimax minimax;

    private AlphaBeta alphaBeta;

    public AIPlayer(char color, int depth, double coinParityWeight, double cornersCapturedWeight, double mobilityWeight,
                    double stabilityWeight) {
        super(color);
        this.depth = depth;
        alphaBeta = new AlphaBeta(coinParityWeight, cornersCapturedWeight, mobilityWeight, stabilityWeight);
        minimax = new Minimax(coinParityWeight, cornersCapturedWeight, mobilityWeight, stabilityWeight);
    }

    public AIPlayer(char color, int depth) {
        super(color);
        this.depth = depth;
        alphaBeta = new AlphaBeta();
        minimax = new Minimax();
    }

    public AIPlayer(char color) {
        super(color);
        alphaBeta = new AlphaBeta();
        minimax = new Minimax();
    }

    @Override
    public String makeMove(Board board) {
        Result result = alphaBeta.minimaxAlphaBeta(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE,
                getColor(), getColor() == 'w' ? 'b' : 'w', true);
        System.out.println("Move: " + result.getBestMove());
        System.out.println();
        return result.getBestMove();
    }

    public String makeMoveMinimax(Board board) {
        Result result = minimax.minimax(board, depth, getColor(), getColor() == 'w' ? 'b' : 'w', true);
        System.out.println("Move: " + result.getBestMove());
        System.out.println();
        return result.getBestMove();
    }
}

