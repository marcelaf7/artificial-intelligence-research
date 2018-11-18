package com.marcelfiore.jungle_ai.game.gui;

import javax.swing.*;

public class GameButton extends JButton {
    private int row;
    private int col;

    public GameButton(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
