package com.marcelfiore.jungle_ai.game.jungle;

public class Tile {
    private char attribute;
    private Piece piece;

    public Tile(char attribute) {
        this.attribute = attribute;
    }

    public char getAttribute() {
        return attribute;
    }


    public Piece getPiece() { return piece;}

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        return attribute == tile.attribute;
    }

    @Override
    public int hashCode() {
        return (int) attribute;
    }
}