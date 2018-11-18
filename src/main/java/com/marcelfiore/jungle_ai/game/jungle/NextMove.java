package com.marcelfiore.jungle_ai.game.jungle;

public class NextMove {
    Piece piece;
    int nextMovesRow;
    int nextMovesCol;

    public NextMove(Piece p, Location nextLocation) {
        this.piece = p;
        this.nextMovesRow = nextLocation.getRow();
        this.nextMovesCol = nextLocation.getCol();
    }

    public NextMove(Piece p, int row, int col) {
        this.piece = p;
        this.nextMovesRow = row;
        this.nextMovesCol = col;
    }

    public NextMove(Player myTurn, int currRow, int currCol, int nextRow, int nextCol) {
        this.piece = myTurn.retrievePieceByLocation(currRow, currCol);
        this.nextMovesRow = nextRow;
        this.nextMovesCol = nextCol;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getRow() {
        return this.nextMovesRow;
    }

    public int getCol() {
        return this.nextMovesCol;
    }

    public void incRow() {
        ++this.nextMovesRow;
    }

    public void decRow() {
        --this.nextMovesRow;
    }

    public void incCol() {
        ++this.nextMovesCol;
    }

    public void decCol() {
        --this.nextMovesCol;
    }

    public void setLocation(int[] nextLocation) {
        this.nextMovesRow = nextLocation[0];
        this.nextMovesCol = nextLocation[1];
    }
}
