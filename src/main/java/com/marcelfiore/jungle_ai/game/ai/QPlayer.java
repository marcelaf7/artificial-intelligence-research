package com.marcelfiore.jungle_ai.game.ai;

import java.util.Dictionary;
import com.marcelfiore.jungle_ai.game.jungle.Location;
import com.marcelfiore.jungle_ai.game.jungle.Game;
import com.marcelfiore.jungle_ai.game.gui.AITrainPage;


public class QPlayer implements AIPlayer {
  public QDatabase db;

  public QPlayer() {
    db = new QDatabase();
  }

  public void train(int numGames, double learningRate, double epsilonDecayFactor, AITrainPage page) {
    double epsilon = 1.0;
    boolean graphics = page != null;

    Game game = new Game();

    for (int games = 0; games < numGames; games++) {
      boolean done = false;

      while (!done) {
        String move = epsilonGreedy(epsilon, game.getState());




      }
    }

  }

  public int[] getMove(Location[] gameState, Location[] validMoves) {
    return null;
  }

  private String epsilonGreedy(double epsilon, String state) {
    return null;
  }
}
