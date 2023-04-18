package player;

import algorithms.Result;
import game.Board;
import algorithms.MinMax;

import java.util.ArrayList;

public class AIPlayer extends Player {

    private int depth = 3;

    public AIPlayer(char color) {
        super(color);
    }

    public AIPlayer(char color, int depth) {
        super(color);
        this.depth = depth;
    }

    @Override
    public String makeMove(Board board) {
        Result result = MinMax.minMax(board, depth, getColor(), getColor() == 'w' ? 'b' : 'w', true);
        System.out.println("Move: " + result.getBestMove());
        System.out.println();
        return result.getBestMove();
    }
}

