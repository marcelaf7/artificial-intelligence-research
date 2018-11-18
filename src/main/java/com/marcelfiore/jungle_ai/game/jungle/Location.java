package com.marcelfiore.jungle_ai.game.jungle;

import java.util.Arrays;

public class Location {
    private int[] location;

    public Location(int[] location) {
        this.location = new int[2];
        this.location[0] = location[0];
        this.location[1] = location[1];
    }

    public Location(int row, int col) {
        this.location = new int[2];
        this.location[0] = row;
        this.location[1] = col;
    }

    public Location(Location loc) {
        this.location = new int[2];
        this.location[0] = loc.getRow();
        this.location[1] = loc.getCol();
    }

    public int getRow() {
        return this.location[0];
    }

    public int getCol() {
        return this.location[1];
    }

    public void setRow(int row) {
        this.location[0] = row;
    }

    public void setCol(int col) {
        this.location[1] = col;
    }

    public int[] getLocation() {return this.location;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location1 = (Location) o;

        return Arrays.equals(location, location1.location);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(location);
    }
}
