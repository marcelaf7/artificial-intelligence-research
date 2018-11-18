package com.marcelfiore.jungle_ai.game.jungle;

public class Tiger extends Piece {
    public Tiger(String color) {
        super("Tiger", 6, color);
        if (color.equals("red")) {
            setLocation(0, 6);
        } else if (color.equals("blue")) {
            setLocation(8, 0);
        }
    }
}
