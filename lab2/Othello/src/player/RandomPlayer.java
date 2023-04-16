package player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RandomPlayer extends Player {

    private final Random random = new Random();

    public RandomPlayer(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public String makeMove(ArrayList<String> moves) {
        int randomIndex = random.nextInt(moves.size());
        String move = moves.get(randomIndex);
        System.out.print("Move: " + move);
        System.out.println();
        return move;
    }
}
