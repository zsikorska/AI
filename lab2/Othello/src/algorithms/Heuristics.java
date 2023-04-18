package algorithms;

import player.Player;

public class Heuristics {

    public static int differenceOfFieldsHeuristic(char[][] board, char playerColor) {
        int playerFields = 0;
        int opponentFields = 0;

        for (char[] chars : board) {
            for (int j = 0; j < board.length; j++) {
                if (chars[j] == playerColor) {
                    playerFields++;
                } else if (chars[j] != ' ') {
                    opponentFields++;
                }
            }
        }

        return playerFields - opponentFields;
    }
}
