package com.marcelfiore.jungle_ai.game.jungle;

public class Player {
    String color;
    Piece[] pieces;

    public Player(String color) {
        this.color = color;
        this.pieces = new Piece[8];
        makePieceInstances();
    }

    private void makePieceInstances() {
        this.pieces[0] = new Rat(this.color);
        this.pieces[1] = new Cat(this.color);
        this.pieces[2] = new Wolf(this.color);
        this.pieces[3] = new Dog(this.color);
        this.pieces[4] = new Leopard(this.color);
        this.pieces[5] = new Tiger(this.color);
        this.pieces[6] = new Lion(this.color);
        this.pieces[7] = new Elephant(this.color);
    }

    public int getValidPiecesCount() {
        int numValid = 0;

        for (Piece currPiece : this.pieces) {
            if (currPiece != null) {
                ++numValid;
            }
        }
        return numValid;
    }

    public Piece[] getValidPieces() {
        int numValid = getValidPiecesCount();
        Piece[] validPieces = new Piece[numValid];
        int index = 0;

        for (Piece currPiece : this.pieces) {
            if (currPiece != null) {
                validPieces[index++] = currPiece;
            }
        }

        return validPieces;
    }

    public Piece retrievePieceByLocation(int currRow, int currCol) throws NullPointerException {
        for (Piece piece : getValidPieces()) {
            if (piece.getRow() == currRow && piece.getCol() == currCol) {
                return piece;
            }
        }
        return null;
    }

    public Piece getPiece(int pieceRank) {
        return this.pieces[pieceRank - 1];
    }

    public String getColor() {
        return this.color;
    }

    public void isCaptured(int rank) {
        this.pieces[rank-1] = null;
    }
}
