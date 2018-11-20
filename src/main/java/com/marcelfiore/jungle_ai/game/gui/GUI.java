package com.marcelfiore.jungle_ai.game.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;

public class GUI extends JFrame {

    JPanel currentPanel;

    public GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        pack();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's gui
        GUI g = new GUI();
        javax.swing.SwingUtilities.invokeLater(() -> g.createAndShowGUI());

    }
}
