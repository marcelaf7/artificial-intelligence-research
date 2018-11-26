package com.marcelfiore.jungle_ai.game.ai;

import com.marcelfiore.jungle_ai.game.jungle.Location;

public class RandomPlayer implements AIPlayer {

  public int[] getMove(Location[] gameState, Location[] validMoves) {
    int randomMove = (int) Math.floor((Math.random() * validMoves.length) / 2) * 2;
    return new int[] {
      validMoves[randomMove].getCol(),
      validMoves[randomMove].getRow(),
      validMoves[randomMove + 1].getCol(),
      validMoves[randomMove + 1].getCol()
    };
  }
}
