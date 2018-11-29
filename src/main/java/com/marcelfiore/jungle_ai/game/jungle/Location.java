package com.marcelfiore.jungle_ai.game.jungle;

import java.util.ArrayList;

/**
 * Location class is a wrapper class which makes using row, col pairs easier.
 * It also functions as a partial functor with static methods, to learn about relations between multiple locations.
 * It is responsible for:
 * 1. Wrapping row, col pairs for ease of use in /src/.../Client package.
 */
public class Location {
    private int row, col;

    /**
     * Make a new location.
     * @param row coordinate.
     * @param col coordinate.
     */
    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //Another way to make a location.
    public Location(Location loc) {
        this(loc.getRow(), loc.getCol());
    }

    /**
     * Checks if the next move's location is out of bounds.
     * @param location the location in question.
     * @return true if it is OOB, false if in bounds.
     */
    public static boolean isOutOfBounds(Location location) {
        return (location.getRow() < 0 || location.getRow() > 8 || location.getCol() < 0 || location.getCol() > 6);
    }

    /**
     * Checks if this location and the given location are next to each other.
     * @param location to check.
     * @return whether the locations are adjacent or not.
     */
    public boolean isAdjacent(Location location){
        return (this.getCol()+1 == location.getCol() && this.getRow()   == location.getRow() || //Moving right
            this.getCol()-1 == location.getCol() && this.getRow()   == location.getRow() || //Moving left
            this.getCol()   == location.getCol() && this.getRow()-1 == location.getRow() || //Moving up
            this.getCol()   == location.getCol() && this.getRow()+1 == location.getRow());  //Moving down
    }


    /*** Getters ***/

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    /**
     * Grab the adjacent locations.
     * @return set of adjacent locations.
     */
    public ArrayList<Location> getAdjacent(){
        ArrayList<Location> adjacent = new ArrayList<>();
        //Go through and look at up/down/left/right, returning the set of those which are in bounds.
        if (!(isOutOfBounds(new Location(getRow() + 1, getCol())))){
            adjacent.add(new Location(getRow() + 1, getCol()));
        }

        if (!(isOutOfBounds(new Location(getRow() - 1, getCol())))) {
            adjacent.add(new Location(getRow() - 1, getCol()));
        }

        if (!(isOutOfBounds(new Location(getRow(), getCol() + 1)))){
            adjacent.add(new Location(getRow(), getCol() + 1));
        }

        if (!(isOutOfBounds(new Location(getRow(), getCol() - 1)))){
            adjacent.add(new Location(getRow(), getCol() - 1));
        }
        return adjacent;
    }

    /**
     * Gives amount of moves to reach location, disregarding bounds and cardinality.
     * @param start location.
     * @param end location.
     * @return how many moves it takes to get from start to end.
     */
    public static int getDistance(Location start, Location end){
        return Math.abs(start.getCol()-end.getCol()) + Math.abs(start.getRow() - end.getRow());
    }

    /**
     * Tells which direction from start to end.
     * @param start location.
     * @param end location.
     * @return direction from start to end: up, down, left, right, same, bad.
     */
    public static String getDirection(Location start, Location end){
        if (start.getRow() > end.getRow() && start.getCol() == end.getCol()){
            return "up";
        } else if (start.getRow() < end.getRow() && start.getCol() == end.getCol()){
            return "down";
        } else if (start.getRow() == end.getRow() && start.getCol() > end.getCol()){
            return "left";
        } else if (start.getRow() == end.getRow() && start.getCol() < end.getCol()){
            return "right";
        }  else if (start.getRow() == end.getRow() && start.getCol() == end.getCol()){
            return "same";
        } else {
            return "bad";
        }
    }

    @Override
    public String toString(){
        return "[" + this.getRow() + ", " + this.getCol() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return (row == location.row) && col == location.col;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }
}
