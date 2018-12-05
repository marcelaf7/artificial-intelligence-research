package com.marcelfiore.jungle_ai.game.ai;

import java.util.Dictionary;
import com.marcelfiore.jungle_ai.game.jungle.Location;
import com.marcelfiore.jungle_ai.game.gui.AITrainPage;


public class QPlayer implements AIPlayer {
  public QDatabase db;

  public void train(int numGames, double learningRate, double epsilonDecayFactor, AITrainPage page) {

  }

  public int[] getMove(Location[] gameState, Location[] validMoves) {
    return null;
  }

  private Location[] epsilonGreedy(double epsilon, Location[] state) {
    return null;
  }
}
