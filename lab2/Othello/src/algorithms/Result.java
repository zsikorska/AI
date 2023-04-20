package algorithms;

public class Result {
    private String bestMove;
    private double value;

    public Result(String bestMove, double value) {
        this.bestMove = bestMove;
        this.value = value;
    }

    public String getBestMove() {
        return bestMove;
    }

    public double getValue() {
        return value;
    }

    public void setBestMove(String bestMove) {
        this.bestMove = bestMove;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
