package com.marcelfiore.jungle_ai.game.ai;

import com.marcelfiore.jungle_ai.game.jungle.Location;

interface AIPlayer {
  public int[] getMove(Location[] gameState, Location[] validMoves);
}
