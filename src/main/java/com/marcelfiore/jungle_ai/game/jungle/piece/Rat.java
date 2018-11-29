package com.marcelfiore.jungle_ai.game.jungle.piece;

import com.marcelfiore.jungle_ai.game.jungle.tile.River;
import com.marcelfiore.jungle_ai.game.jungle.tile.Tile;
import com.marcelfiore.jungle_ai.game.jungle.tile.Trap;
import com.marcelfiore.jungle_ai.game.jungle.Location;
import com.marcelfiore.jungle_ai.game.jungle.Board;

public class Rat extends Piece {
    public Rat(String color) {
        super("Rat", 1, color);
        if (color.equals("red")) {
            setLocation(2, 0);
        } else if (color.equals("blue")) {
            setLocation(6, 6);
        }
    }

    @Override
    public boolean isValidMove(Location end, Board board){
        Tile startTile = board.getTile(getLocation());
        Tile endTile = board.getTile(end);

        //If it's obviously out of bounds or too far away
        if (!super.isValidMove(end, board)){
            return false;
        }

        //If trying to move more than 1 tile
        else if (!isInRange(end)){
            return false;
        }
        //Is it an invalid tile type?
        //Invalid: START ON river, moving onto open tile, holding a piece
        if (startTile instanceof River && !(endTile instanceof River) && !(endTile.getPiece() == null)){
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
                //... which is your enemies, and they are not an elephant
                if (!((Trap) endTile).getColor().equals(this.getColor()) && !(q instanceof Elephant)) {
                    return false;
                }
                //Else, it is your trap and you always outrank
            }

            //Not an elephant, and not a trap
            else if (!(q instanceof Elephant) && !(q instanceof Rat)){
                return false;
            }
        }

        //It's a valid move!
        return true;
    }
}
