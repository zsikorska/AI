package player;

public class AIPlayer extends Player {

    public AIPlayer(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public void makeMove() {
        System.out.println("AIPlayer.makeMove()");
    }
}

