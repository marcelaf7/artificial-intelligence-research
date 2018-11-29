package com.marcelfiore.jungle_ai.game.jungle;

/**
 * Player class is responsible for which color the player was assigned: red or blue.
 * That's it.
 */
public class Player {
    private String color;

    Player(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

}
