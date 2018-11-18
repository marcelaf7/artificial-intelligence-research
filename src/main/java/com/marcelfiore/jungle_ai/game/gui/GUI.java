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

        Background b = new Background("src/Images/jungle.jpg");
        add(b);

        currentPanel = new MainMenuPage(this);
        add(currentPanel);

        pack();
        setVisible(true);
    }

    public void changePage(String panelName) {
        switch (panelName) {
            case "StartGame":
                remove(currentPanel);
                currentPanel = new GamePage(this);
                add(currentPanel);
                break;

            default:
                break;
        }

        revalidate();
        repaint();
    }

    public void changePageTo(JPanel comp) {
        remove(currentPanel);
        add(comp);
        currentPanel = comp;
        revalidate();
        repaint();
        pack();
    }
    public void startGUI(GUI g, Socket client){
        //javax.swing.SwingUtilities.invokeLater(() -> g.createAndShowGUI());
        final JFrame frame = new JFrame("Jungle");
        final JButton btnLogin = new JButton("Login");
        final JButton btnRegister = new JButton("Register");
        btnLogin.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        LoginDialog loginDlg = new LoginDialog(frame, client);
                        loginDlg.setVisible(true);
                        // if logon successfully
                        if(loginDlg.isSucceeded()){
                            //btnLogin.setText("Hi " + loginDlg.getUsername() + "!");
                            javax.swing.SwingUtilities.invokeLater(() -> g.createAndShowGUI());
                        }
                    }
                });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.getContentPane().add(btnRegister);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's gui
        GUI g = new GUI();
        javax.swing.SwingUtilities.invokeLater(() -> g.createAndShowGUI());

    }
}
