package com.marcelfiore.jungle_ai.game.ai;

import com.marcelfiore.jungle_ai.game.jungle.Location;

public class RandomPlayer implements AIPlayer {

  //The valid moves array is in the format [startingLocation, endingLocation, startingLocation, endingLocation, etc...]
  public int[] getMove(Location[] gameState, Location[] validMoves) {
    int randomMove = (int) Math.floor((Math.random() * validMoves.length) / 2) * 2;
    return new int[] {
      validMoves[randomMove].getCol(),
      validMoves[randomMove].getRow(),
      validMoves[randomMove + 1].getCol(),
      validMoves[randomMove + 1].getCol()
    };
  }

  public int[] getMove(String[] validMoves) {
    Location[] validMovesLocations = new Location[validMoves.length * 2];

    for (int i = 0; i < validMoves.length; i++) {
      int[] currMoveInts = parseMove(validMoves[i]);
      Location moveStart = new Location(currMoveInts[0], currMoveInts[1]);
      Location moveEnd = new Location(currMoveInts[2], currMoveInts[3]);
      validMovesLocations[i * 2] = moveStart;
      validMovesLocations[(i * 2) + 1] = moveEnd;
    }

    return getMove(null, validMovesLocations);
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
}
