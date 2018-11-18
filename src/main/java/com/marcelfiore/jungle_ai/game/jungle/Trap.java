package com.marcelfiore.jungle_ai.game.jungle;

public class Trap extends Tile {

    private String color;

    public Trap(String color) {
        super('T');
        this.color = color;
    }
    public Trap() {
        super('T');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Trap trap = (Trap) o;

        return color != null ? color.equals(trap.color) : trap.color == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}