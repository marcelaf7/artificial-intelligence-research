package com.marcelfiore.jungle_ai.game.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPage extends Page implements ActionListener {
    public MainMenuPage(GUI frame) {
        super(frame);
        JButton b = new JButton("Start game");
        b.setBounds(0, 0, 20, 20);
        b.setActionCommand("StartGame");
        b.addActionListener(this);
        add(b);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "StartGame":
                frame.changePageTo(new GamePage(frame));
                break;

            default:
                break;
        }
    }
}
