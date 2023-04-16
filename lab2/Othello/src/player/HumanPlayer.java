package player;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public String makeMove(ArrayList<String> moves) {
        System.out.print("Move: ");
        Scanner scanner = new Scanner(System.in);
        String move = scanner.nextLine();
        if(moves.contains(move))
            return move;
        else {
            System.out.println("Invalid move. Move should be in the form of row column from list of available moves.");
            return makeMove(moves);
        }
    }
}

