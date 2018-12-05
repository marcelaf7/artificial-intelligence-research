package com.marcelfiore.jungle_ai.game.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPage extends Page implements ActionListener {
  public MainMenuPage(GUI frame) {
    super(frame);
    JButton twoPlayerButton = new JButton("2 Player Game");
    twoPlayerButton.setActionCommand("twoPlayer");
    twoPlayerButton.addActionListener(this);
    add(twoPlayerButton);

    JButton aIButton = new JButton("Play against AI");
    aIButton.setActionCommand("aIGame");
    aIButton.addActionListener(this);
    add(aIButton);

    JButton trainingButton = new JButton("Train AI");
    trainingButton.setActionCommand("train");
    trainingButton.addActionListener(this);
    add(trainingButton);
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    switch (actionEvent.getActionCommand()) {
      case "twoPlayer":
        frame.changePageTo(new GamePage(frame));
        break;
      case "aIGame":
        frame.changePageTo(new GamePage(frame));
        break;
      case "train":
        frame.changePageTo(new AITrainPage(frame));
        break;
      default:
        break;
    }
  }
}
