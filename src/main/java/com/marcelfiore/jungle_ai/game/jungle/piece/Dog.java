package com.marcelfiore.jungle_ai.game.jungle.piece;


public class Dog extends GenericPiece {
    public Dog(String color) {
        super("Dog", 4, color);
        if (color.equals("red")) {
            setLocation(1, 1);
        } else if (color.equals("blue")) {
            setLocation(7, 5);
        }
    }
}
