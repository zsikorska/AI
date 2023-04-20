package algorithms;

public class Result {
    private String bestMove;
    private double value;

    private int evaluationCount;

    public Result(String bestMove, double value, int evaluationCount) {
        this.bestMove = bestMove;
        this.value = value;
        this.evaluationCount = evaluationCount;
    }

    public String getBestMove() {
        return bestMove;
    }

    public double getValue() {
        return value;
    }

    public int getEvaluationCount() {
        return evaluationCount;
    }

    public void setBestMove(String bestMove) {
        this.bestMove = bestMove;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setEvaluationCount(int evaluationCount) {
        this.evaluationCount = evaluationCount;
    }
}
