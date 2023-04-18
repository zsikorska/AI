package player;

import game.Board;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RandomPlayer extends Player {

    private final Random random = new Random();

    public RandomPlayer(char color) {
        super(color);
    }

    @Override
    public String makeMove(Board board) {
        ArrayList<String> moves = board.getValidMoves(getColor());
        int randomIndex = random.nextInt(moves.size());
        String move = moves.get(randomIndex);
        System.out.print("Move: " + move);
        System.out.println();
        return move;
    }
}
