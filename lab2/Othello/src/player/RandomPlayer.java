package player;

public class RandomPlayer extends Player {

    public RandomPlayer(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public void makeMove() {
        System.out.println("RandomPlayer.makeMove()");
    }
}
