package com.marcelfiore.jungle_ai.game.jungle;

public class Wolf extends Piece {
    public Wolf(String color) {
        super("Wolf", 3, color);
        if (color.equals("red")) {
            setLocation(2, 4);
        } else if (color.equals("blue")) {
            setLocation(6, 2);
        }
    }
}
