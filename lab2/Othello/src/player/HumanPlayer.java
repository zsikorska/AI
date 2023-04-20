package player;

import game.Board;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(char color) {
        super(color);
    }

    @Override
    public String makeMove(Board board) {
        ArrayList<String> moves = board.getValidMoves(getColor());
        System.out.print("Move: ");
        Scanner scanner = new Scanner(System.in);
        String move = scanner.nextLine();
        if(moves.contains(move))
            return move;
        else {
            System.out.println("Invalid move. Move should be in the form of row column from list of available moves.");
            return makeMove(board);
        }
    }
}

