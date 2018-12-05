package com.marcelfiore.jungle_ai.game.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AITrainPage extends Page implements ActionListener {
  private JProgressBar progressBar;
  private boolean cancel = false;


  public AITrainPage(GUI frame) {
    super(frame);
    progressBar = new JProgressBar();
    progressBar.setStringPainted(true);
    progressBar.setMinimum(0);

    progressBar.setMaximum(100); //TODO change this to the number of games for the AI
    progressBar.setValue(0);
    add(progressBar);

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand("cancel");
    cancelButton.addActionListener(this);
    add(cancelButton);

    JButton incrementProgress = new JButton("Increment");
    incrementProgress.setActionCommand("increment");
    incrementProgress.addActionListener(this);
    add(incrementProgress);

    //TODO run TrainQPlayer
  }

  public void infiniteLoob() {
    while (true) {
      System.out.println("Doing nothing");
    }
  }

  public void incrementProgress() {
    progressBar.setValue(progressBar.getValue() + 1);
  }

  public boolean getCancel() {
    return cancel;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    switch (actionEvent.getActionCommand()) {
      case "increment":
        incrementProgress();
        break;
      case "cancel":
        System.exit(0);
    }
  }
}
