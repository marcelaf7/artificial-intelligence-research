package com.marcelfiore.jungle_ai.game.ai;

public Move() {
  private int startCol;
  private int startRow;
  private int endCol;
  private int endRow;

  public Move(int startCol, int startRow, int endCol, int endRow) {
    this.startCol = startCol;
    this.startRow = startRow;
    this.endCol = endCol;
    this.endRow = endRow;
  }

  public int[] getMove() {
    return [startCol, startRow, endCol, endRow];
  }

  @Override
  public String toString() {
    String str = "";
    str += startCol + ";" + startRow + ";" + endCol + ";" + endRow;
    return str;
  }

  @Override
  public String equals(Obejct obj) {
    if (obj instanceof Move) {
      if (obj.getMove().equals(getMove())) {
        return true;
      }

      return false;
    }

    return false;
  }

}
