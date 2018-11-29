package com.marcelfiore.jungle_ai.game.jungle.piece;

public class Lion extends JumperPiece {
    public Lion(String color) {
        super("Lion", 7, color);
        if (color.equals("red")) {
            setLocation(0, 0);
        } else if (color.equals("blue")) {
            setLocation(8, 6);
        }
    }
}
