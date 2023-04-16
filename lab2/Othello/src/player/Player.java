package player;

import java.util.ArrayList;

public abstract class Player {

    private final boolean isBlack;

    public Player(boolean isBlack) {
        this.isBlack = isBlack;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public abstract String makeMove(ArrayList<String> moves);
}
