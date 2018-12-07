package com.marcelfiore.jungle_ai.game.jungle.piece;

import com.marcelfiore.jungle_ai.game.jungle.tile.Den;
import com.marcelfiore.jungle_ai.game.jungle.tile.Tile;
import com.marcelfiore.jungle_ai.game.jungle.Location;
import com.marcelfiore.jungle_ai.game.jungle.Board;

import java.util.ArrayList;

/**
 * Piece class is responsible for piece-specific behavior. This includes:
 * 1. Knowing what type of piece it is; storing the name, rank, color of the piece.
 * 2. Knowing where the piece is on the board; storing it's location
 * 3. Calculating valid moves a user could make, knowing the above information.
 */
public class Piece {
    private String name;
    private int rank;
    private String color;
    private Location location;

    /**
     * Make a Piece object to represent a player-controlled piece on the game board.
     * @param name describes the piece, usually to a human. "rat", "lion", etc.
     * @param rank describes the piece's power. Higher ranked pieces typically beat lower rank pieces.
     * @param color describes which team the piece belongs to: red or blue.
     */
    public Piece(String name, int rank, String color) {
        this.name = name;
        this.rank = rank;
        this.color = color;
    }

    /**
     * Get the set of all valid moves for this piece at this location given this board state.
     * @param board state of the tiles and pieces.
     * @return the valid moves for this piece.
     */
    public ArrayList<Location> getValidMoves(Board board){
        ArrayList<Location> adjacent = getLocation().getAdjacent();
        ArrayList<Location> valid = new ArrayList<>();

        //Look at each adjacent move
        for (Location location : adjacent){
            //..., if the move is valid
            if (isValidMove(location, board)){
                //... then add it to list of valid moves.
                valid.add(location);
                System.out.println(location);
            }
        }
        //Return all the valid moves
        return valid;
    }

    /**
     * Checks if the piece can move to the location.
     * @param end location where the piece is proposed to move.
     * @param board state of the tiles and pieces.
     * @return whether the move is valid or not.
     */
    public boolean isValidMove(Location end, Board board){
        Tile endTile = board.getTile(end);

        //If it's obviously out of bounds
        if (Location.isOutOfBounds(end)){
            return false;
        }

        //If trying to move onto friendly den
        if (endTile instanceof Den){
            //If the Den has a color which is not the color of this piece
            if (((Den)endTile).getColor().equals(this.getColor())) {
                return false;
            }
        }

        //It's a valid move!
        return true;
    }

    /**
     * Checks if the location is within range of movement. Typically range == 1.
     * @param location location we are comparing to the pieces location.
     * @return whether the piece is in range or not.
     */
    boolean isInRange(Location location){
        return this.location.isAdjacent(location);
    }


    /*** Getters, Setters ***/

    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    public void setLocation(Location loc) {
        this.location = new Location(loc);
    }

    public Location getLocation() {
        return this.location;
    }

    public int getRow() {
        return this.location.getRow();
    }

    public int getCol() {
        return this.location.getCol();
    }

    public String getName() {
        return this.name;
    }

    public int getRank() {
        return this.rank;
    }

    public String getColor() {
        return this.color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;

        if (rank != piece.rank) return false;
        if (name != null ? !name.equals(piece.name) : piece.name != null) return false;
        return color != null ? color.equals(piece.color) : piece.color == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + rank;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
