package com.marcelfiore.jungle_ai.game.jungle.tile;

public class Trap extends Tile {
    private String color;

    /**
     * Create a Trap object with a color.
     * @param color describes which team's den this is: red or blue.
     */
    public Trap(String color) {
        super('T');
        this.color = color;
    }

    public String getColor(){
        return color;
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
