package com.marcelfiore.jungle_ai.game.ai;

public class RandomPlayer implements AIPlayer {

  public int[] getMove(Location[] gameState, Location[] validMoves) {
    int randomMove = Math.floor((Math.random() * validMoves.length) / 2) * 2;
    return [
      validMoves[randomMove].getCol(),
      validMoves[randomMove].getRow(),
      validMoves[randomMove + 1].getCol(),
      validMoves[randomMove + 1].getCol()
    ];
  }
}
