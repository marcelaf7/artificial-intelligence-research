package com.marcelfiore.jungle_ai.game.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    JPanel currentPanel;

    public GUI() {
      createAndShowGUI();
    }

    private void createAndShowGUI() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentPanel = new MainMenuPage(this);
        add(currentPanel);

        pack();
        setVisible(true);
    }

    public void changePageTo(JPanel comp) {
        remove(currentPanel);
        add(comp);
        currentPanel = comp;
        revalidate();
        repaint();
        //pack();
    }
    public void startGUI(){
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public static void main(String[] args) {
      new GUI();
    }

}
