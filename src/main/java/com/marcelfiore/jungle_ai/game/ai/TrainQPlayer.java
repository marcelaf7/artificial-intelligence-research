package com.marcelfiore.jungle_ai.game.ai;

import com.marcelfiore.jungle_ai.game.gui.AITrainPage;

public class TrainQPlayer extends Thread {

  private AITrainPage page;
  private int numGames;

  public TrainQPlayer(AITrainPage page, int numGames) {
    this.page = page;
    this.numGames = numGames;
  }

  public void train() {
    QPlayer ai = new QPlayer();
    ai.train(numGames, 0.1, 0.1, page);
  }
}
