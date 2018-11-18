package com.marcelfiore.jungle_ai.game.jungle;

public class Cat extends Piece {
    public Cat(String color) {
        super("Cat", 2, color);
        if (color.equals("red")) {
            setLocation(1, 5);
        } else if (color.equals("blue")) {
            setLocation(7, 1);
        }
    }
}
