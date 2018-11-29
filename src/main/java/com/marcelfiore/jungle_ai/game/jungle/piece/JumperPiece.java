package com.marcelfiore.jungle_ai.game.jungle.piece;

import com.marcelfiore.jungle_ai.game.jungle.Board;
import com.marcelfiore.jungle_ai.game.jungle.Location;
import com.marcelfiore.jungle_ai.game.jungle.tile.Open;
import com.marcelfiore.jungle_ai.game.jungle.tile.River;
import com.marcelfiore.jungle_ai.game.jungle.tile.Tile;
import com.marcelfiore.jungle_ai.game.jungle.tile.Trap;

import java.util.ArrayList;

public class JumperPiece extends Piece{

    JumperPiece (String name, int rank, String color) {
        super(name, rank, color);
    }

    @Override
    public boolean isValidMove(Location end, Board board){
        Tile endTile = board.getTile(end);

        //Super check
        if (!super.isValidMove(end, board)){
            return false;
        }

        if (isJumping(end, board)){
            Piece q = endTile.getPiece();
            if (!(q == null)) {
                //If friendly piece
                if (q.getColor().equals(this.getColor())) {
                    return false;
                }

                //if higher rank, but is not on a trap
                else if (q.getRank() > this.getRank()){
                    return false;
                }

            }
            return true;
        }

        //If trying to move more than 1 tile without jumping OR if trying to move into a river
        if (!isInRange(end) || endTile instanceof River){
            return false;
        }

        //Check the piece on the adjacent, valid tile
        Piece q = endTile.getPiece();
        if (!(q == null)){
            //If friendly piece
            if (q.getColor().equals(this.getColor())){
                return false;
            }
            //Enemy piece
            //If you are moving onto a trap
            if (endTile instanceof Trap) {
                //... which is your enemies, and they outrank you
                if (!((Trap) endTile).getColor().equals(this.getColor()) && q.getRank() > this.getRank()) {
                    return false;
                }
                //Else, it is your trap and you always outrank
            }

            //if higher rank, but is not on a trap
            else if (q.getRank() > this.getRank()){
                return false;
            }
        }
        //It's a valid move!
        return true;
    }

    @Override
    public ArrayList<Location> getAllValidMoves(Board board){
        ArrayList<Location> valid = new ArrayList<>();
        //Look through every location on the board
        for (Location location : board.getBoard().keySet()){
            //If the jumper can move there
            if (isValidMove(location,board)){
                //..., add it to valid set.
                valid.add(location);
            }
        }
        return valid;
    }

    /**
     * Answers whether the piece is trying to jump.
     * @param end location where the piece would land.
     * @param board the current state of the game board.
     * @return whether the piece allowed to jump from it's location to end location or not.
     */
    private boolean isJumping(Location end, Board board) {
        //Can't jump 1 Tile away lol
        int distance = Location.getDistance(getLocation(), end);

        //If moving 3 or 4 places away, could be jumping.
        //Check that all of the spaces between getLocation and end are river.
        if (distance == 3 || distance == 4) {
            //Check that landing spot is Open tile
            if (!(board.getTile(end) instanceof Open)) {
                return false;
            }

            //Check that the next 2 or 3 tiles are river, and that there are no pieces in any of them.
            for (int i = 0; i < distance - 1; i++) {
                Location next;
                //Make new location based on direction
                switch (Location.getDirection(getLocation(), end)) {
                    case "up":
                        next = new Location(getLocation().getRow() - i - 1, getLocation().getCol());
                        break;
                    case "down":
                        next = new Location(getLocation().getRow() + i + 1, getLocation().getCol());
                        break;
                    case "left":
                        next = new Location(getLocation().getRow(), getLocation().getCol() - i - 1);
                        break;
                    case "right":
                        next = new Location(getLocation().getRow(), getLocation().getCol() + i + 1);
                        break;
                    default:
                        return false; //Bad direction, flaw in move definitely.
                }

                //Check that the tile is a river!
                if (!(board.getTile(next) instanceof River)) {
                    return false;
                }

                //Check that rat is not in the river
                else if (board.getTile(next).getPiece() instanceof Rat){
                    return false;
                }
            }

            //Must be jumping
            return true;
        }

        //Easy, not a valid distance
        return false;
    }

}
