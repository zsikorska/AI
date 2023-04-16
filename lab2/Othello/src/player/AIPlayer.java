package player;

import java.util.ArrayList;

public class AIPlayer extends Player {

    public AIPlayer(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public String makeMove(ArrayList<String> moves) {
        System.out.println("AIPlayer.makeMove()");
        return null;
    }
}

