package com.marcelfiore.jungle_ai.game.jungle;

public class Den extends Tile {
    private String color;

    public Den(String color) {
        super('D');
        this.color = color;
    }
    public Den() {
        super('D');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Den den = (Den) o;

        return color != null ? color.equals(den.color) : den.color == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}