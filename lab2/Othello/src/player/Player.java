package player;

import game.Board;

import java.util.ArrayList;

public abstract class Player {

    private final char color;

    public Player(char color) {
        this.color = color;
    }

    public char getColor() {
        return color;
    }

    public abstract String makeMove(Board board);
}
