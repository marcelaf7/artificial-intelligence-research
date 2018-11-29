package com.marcelfiore.jungle_ai.game.jungle.piece;

public class Wolf extends GenericPiece {
    public Wolf(String color) {
        super("Wolf", 3, color);
        if (color.equals("red")) {
            setLocation(2, 4);
        } else if (color.equals("blue")) {
            setLocation(6, 2);
        }
    }
}
