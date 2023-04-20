package player;

import algorithms.Result;
import game.Board;
import algorithms.Minimax;
import algorithms.AlphaBeta;

public class AIPlayer extends Player {

    private int depth = 5;
    private Minimax minimax;

    private AlphaBeta alphaBeta;

    private double time;

    private int counter = 0;

    private Result currentResult;

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

    public Result getCurrentResult() {
        return currentResult;
    }

    public double getTime() {
        return time;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public String makeMove(Board board) {
        alphaBeta.setCounter(0);
        minimax.setCounter(0);
        counter = 0;

        double startTime;
        double endTime;

        startTime = System.currentTimeMillis();
        //Result result = minimax.minimax(board, depth, getColor(), getColor() == 'w' ? 'b' : 'w', true);
        Result result = alphaBeta.minimaxAlphaBeta(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, getColor(), getColor() == 'w' ? 'b' : 'w', true);
        endTime = System.currentTimeMillis();
        time += endTime - startTime;
        //counter = minimax.getCounter();
        counter = alphaBeta.getCounter();
        currentResult = result;
        System.out.println("Move: " + result.getBestMove());
        //System.out.println("counter: " + minimax.getCounter());
        //System.out.println("counter: " + alphaBeta.getCounter());
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

