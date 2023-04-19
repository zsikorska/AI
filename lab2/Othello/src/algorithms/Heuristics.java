package algorithms;

import game.Board;

public class Heuristics {

    public static double complexHeuristic(Board currentBoard, char playerColor, double coinParityWeight,
                               double cornersCapturedWeight, double mobilityWeight, double stabilityWeight) {

        double result1 = coinParityWeight * coinParity(currentBoard, playerColor);
        double result2 = cornersCapturedWeight * cornersCaptured(currentBoard, playerColor);
        double result3 = mobilityWeight * mobility(currentBoard, playerColor);
        double result4 = stabilityWeight * stability(currentBoard, playerColor);
        return result1 + result2 + result3 + result4;
    }

    /*
        Difference in coins between maximising and minimising player.
    */
    public static double coinParity(Board currentBoard, char playerColor) {
        int playerFields = 0;
        int opponentFields = 0;
        char[][] board = currentBoard.getBoard();

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

    public static double cornersCaptured(Board board, char playerColor) {
        return cornersOccupancy(board, playerColor) + cornersCloseness(board, playerColor);
    }

    /*
        Corners are the most valuable pieces on the board. This heuristic calculates number of corners occupied by
        the player and subtracts number of corners occupied by the opponent.
    */
    public static double cornersOccupancy(Board currentBoard, char playerColor) {
        int playerCorners = 0;
        int opponentCorners = 0;
        char[][] board = currentBoard.getBoard();

        if (board[0][0] == playerColor) {
            playerCorners++;
        } else if (board[0][0] != ' ') {
            opponentCorners++;
        }

        if (board[0][board.length - 1] == playerColor) {
            playerCorners++;
        } else if (board[0][board.length - 1] != ' ') {
            opponentCorners++;
        }

        if (board[board.length - 1][0] == playerColor) {
            playerCorners++;
        } else if (board[board.length - 1][0] != ' ') {
            opponentCorners++;
        }

        if (board[board.length - 1][board.length - 1] == playerColor) {
            playerCorners++;
        } else if (board[board.length - 1][board.length - 1] != ' ') {
            opponentCorners++;
        }

        return playerCorners - opponentCorners;
    }

    /*
        Squares adjacent to corners are a huge disadvantage as it gives the opponent the opportunity to capture
        corner. Therefore, we avoid capturing close corner squares.
    */
    public static double cornersCloseness(Board currentBoard, char playerColor) {
        int playerCorners = 0;
        int opponentCorners = 0;
        char[][] board = currentBoard.getBoard();

        if (board[0][0] == ' ') {
            if (board[0][1] == playerColor) {
                playerCorners++;
            } else if (board[0][1] != ' ') {
                opponentCorners++;
            }
            if (board[1][0] == playerColor) {
                playerCorners++;
            } else if (board[1][0] != ' ') {
                opponentCorners++;
            }
            if (board[1][1] == playerColor) {
                playerCorners++;
            } else if (board[1][1] != ' ') {
                opponentCorners++;
            }
        }

        if (board[0][board.length - 1] == ' ') {
            if (board[0][board.length - 2] == playerColor) {
                playerCorners++;
            } else if (board[0][board.length - 2] != ' ') {
                opponentCorners++;
            }
            if (board[1][board.length - 1] == playerColor) {
                playerCorners++;
            } else if (board[1][board.length - 1] != ' ') {
                opponentCorners++;
            }
            if (board[1][board.length - 2] == playerColor) {
                playerCorners++;
            } else if (board[1][board.length - 2] != ' ') {
                opponentCorners++;
            }
        }

        if (board[board.length - 1][0] == ' ') {
            if (board[board.length - 2][0] == playerColor) {
                playerCorners++;
            } else if (board[board.length - 2][0] != ' ') {
                opponentCorners++;
            }
            if (board[board.length - 1][1] == playerColor) {
                playerCorners++;
            } else if (board[board.length - 1][1] != ' ') {
                opponentCorners++;
            }
            if (board[board.length - 2][1] == playerColor) {
                playerCorners++;
            } else if (board[board.length - 2][1] != ' ') {
                opponentCorners++;
            }
        }

        if (board[board.length - 1][board.length - 1] == ' ') {
            if (board[board.length - 2][board.length - 1] == playerColor) {
                playerCorners++;
            } else if (board[board.length - 2][board.length - 1] != ' ') {
                opponentCorners++;
            }
            if (board[board.length - 1][board.length - 2] == playerColor) {
                playerCorners++;
            } else if (board[board.length - 1][board.length - 2] != ' ') {
                opponentCorners++;
            }
            if (board[board.length - 2][board.length - 2] == playerColor) {
                playerCorners++;
            } else if (board[board.length - 2][board.length - 2] != ' ') {
                opponentCorners++;
            }
        }

        return opponentCorners - playerCorners;
    }


    public static double mobility(Board currentBoard, char playerColor) {
        return actualMobility(currentBoard, playerColor) + potentialMobility(currentBoard, playerColor);
    }

    /*
        Actual mobility is the number of next moves a player has, given the current state of the game.
        It is calculated by examining the board and counting the number of legal moves for the player.
    */
    public static double actualMobility(Board currentBoard, char playerColor) {
        int playerMoves = currentBoard.getValidMoves(playerColor).size();
        char opponentColor = playerColor == 'w' ? 'b' : 'w';
        int opponentMoves = currentBoard.getValidMoves(opponentColor).size();

        return playerMoves - opponentMoves;
    }


    /*
        Potential mobility is the number of possible moves the player might have over the next few moves.
        It is calculated by counting the number of empty spaces next to at least one of the opponent’s coin.
    */
    public static double potentialMobility(Board currentBoard, char playerColor) {
        int playerMoves = 0;
        int opponentMoves = 0;
        char[][] board = currentBoard.getBoard();
        char opponentColor = playerColor == 'w' ? 'b' : 'w';

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == playerColor) {
                    opponentMoves = countFreeSpacesNextToOpponentField(opponentMoves, board, i, j);
                } else if (board[i][j] == opponentColor) {
                    playerMoves = countFreeSpacesNextToOpponentField(playerMoves, board, i, j);
                }
            }
        }

        return playerMoves - opponentMoves;
    }

    private static int countFreeSpacesNextToOpponentField(int playerMoves, char[][] board, int i, int j) {
        if (i > 0 && board[i - 1][j] == ' ') {
            playerMoves++;
        }
        if (i < board.length - 1 && board[i + 1][j] == ' ') {
            playerMoves++;
        }
        if (j > 0 && board[i][j - 1] == ' ') {
            playerMoves++;
        }
        if (j < board.length - 1 && board[i][j + 1] == ' ') {
            playerMoves++;
        }
        if (i > 0 && j > 0 && board[i - 1][j - 1] == ' ') {
            playerMoves++;
        }
        if (i > 0 && j < board.length - 1 && board[i - 1][j + 1] == ' ') {
            playerMoves++;
        }
        if (i < board.length - 1 && j > 0 && board[i + 1][j - 1] == ' ') {
            playerMoves++;
        }
        if (i < board.length - 1 && j < board.length - 1 && board[i + 1][j + 1] == ' ') {
            playerMoves++;
        }
        return playerMoves;
    }


    /*
        Measure how vulnerable coin is to being flanked by the opponent. There are weighted values for each coin
        related to the stability of the coin.
    */
    public static double stability(Board currentBoard, char playerColor) {
        int playerStability = 0;
        int opponentStability = 0;
        char[][] board = currentBoard.getBoard();
        char opponentColor = playerColor == 'w' ? 'b' : 'w';

        int[][] weights = {
            {5, -3, 3, 2, 2, 3, -3, 5},
            {-3, -4, -1, -1, -1, -1, -4, -3},
            {3, -1, 1, 0, 0, 1, -1, 3},
            {2, -1, 0, 1, 1, 0, -1, 2},
            {2, -1, 0, 1, 1, 0, -1, 2},
            {3, -1, 1, 0, 0, 1, -1, 3},
            {-3, -4, -1, -1, -1, -1, -4, -2},
            {5, -3, 3, 2, 2, 3, -3, 5}
        };

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == playerColor) {
                    playerStability += weights[i][j];
                } else if (board[i][j] == opponentColor) {
                    opponentStability += weights[i][j];
                }
            }
        }
        return playerStability - opponentStability;
    }

}
