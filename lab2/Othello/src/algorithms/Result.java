package algorithms;

public class Result {
    private String bestMove;
    private int maxDiff;

    public Result(String bestMove, int maxDiff) {
        this.bestMove = bestMove;
        this.maxDiff = maxDiff;
    }

    public String getBestMove() {
        return bestMove;
    }

    public int getMaxDiff() {
        return maxDiff;
    }

    public void setBestMove(String bestMove) {
        this.bestMove = bestMove;
    }

    public void setMaxDiff(int maxDiff) {
        this.maxDiff = maxDiff;
    }
}
