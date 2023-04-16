package player;

public class HumanPlayer extends Player {

    public HumanPlayer(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public void makeMove() {
        System.out.println("HumanPlayer.makeMove()");
    }
}

