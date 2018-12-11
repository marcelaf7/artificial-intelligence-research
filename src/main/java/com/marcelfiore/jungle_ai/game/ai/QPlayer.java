package com.marcelfiore.jungle_ai.game.ai;

import java.util.Dictionary;
import com.marcelfiore.jungle_ai.game.jungle.Location;
import com.marcelfiore.jungle_ai.game.jungle.Game;
import com.marcelfiore.jungle_ai.game.gui.AITrainPage;


public class QPlayer implements AIPlayer {
  public QDatabase db;
  String lastState;
  String lastMove;

  public QPlayer() {
    db = new QDatabase();
  }

  public void train(int numGames, double learningRate, double epsilonDecayFactor, AITrainPage page) {
    RandomPlayer randPlayer = new RandomPlayer();
    double epsilon = 1.0;
    boolean graphics = page != null;

    for (int games = 0; games < numGames; games++) {
      Game game = new Game();

      boolean done = false;
      int step = 0;
      boolean firstMove = false;
      while (!done) {
        step++;

        if (!firstMove) {
          int[] otherPlayerMove = randPlayer.getMove(game.getAllValidMoves());
          game.makeMove(otherPlayerMove[0], otherPlayerMove[1], otherPlayerMove[2], otherPlayerMove[3]);
          firstMove = true;
        }

        // TODO need to skip this the first time.
        String move = epsilonGreedy(epsilon, game.getState(), game.getAllValidMoves());
        System.out.println("Parsing move " + move);
        int[] moveInts = parseMove(move);
        game.makeMove(moveInts[0], moveInts[1], moveInts[2], moveInts[3]);

        String state = game.getState();
        if (db.getQ(state, move) == -1) {
          db.setQ(state, move, 0);
        }

        if (game.winnerCheck() == 0) {
          db.setQ(state, move, 1);
          done = true;
        } else if (game.winnerCheck() == 1) {
          db.setQ(state, move, 0);
          done = true;
        } else {
          int[] otherPlayerMove = randPlayer.getMove(game.getAllValidMoves());
          game.makeMove(otherPlayerMove[0], otherPlayerMove[1], otherPlayerMove[2], otherPlayerMove[3]);

          if (game.winnerCheck() == 1) {
            double q = db.getQ(state, move);
            db.setQ(state, move, learningRate * (-1 - q));
            done = true;
          }

          if (step > 1) {
            double lastMoveQ = db.getQ(lastState, lastMove);
            lastMoveQ += learningRate * (db.getQ(state, move) - lastMoveQ);
            db.setQ(lastState, lastMove, lastMoveQ);
          }

        }

        lastState = game.getState();
        lastMove = move;

      }
      if (graphics) {
        page.incrementProgress();
      }
    }

  }

  private int[] parseMove(String moveString) {
    String[] moveStrings = moveString.split(";");
    int[] moveInts = new int[4];

    String[] startLocation = moveStrings[0].split(",");
    String[] endLocation = moveStrings[1].split(",");
    moveInts[0] = Integer.parseInt(startLocation[0]);
    moveInts[1] = Integer.parseInt(startLocation[1]);
    moveInts[2] = Integer.parseInt(endLocation[0]);
    moveInts[3] = Integer.parseInt(endLocation[1]);
    return moveInts;
  }

  public int[] getMove(Location[] gameState, Location[] validMoves) {
    return null;
  }

  private String epsilonGreedy(double epsilon, String state, String[] validMoves) {
    if (Math.random() < epsilon) {
      return validMoves[(int) Math.floor(Math.random() * validMoves.length)];
    } else {
      double q = Integer.MAX_VALUE;
      String move = null;

      for (int i = 0; i < validMoves.length; i++) {
        String currMove = validMoves[i];
        double currQ = db.getQ(state, validMoves[i]);
        if(currQ < q) {
          q = currQ;
          move = currMove;
        }
      }
      return move;
    }
  }
}
