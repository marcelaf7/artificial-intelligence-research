package com.marcelfiore.jungle_ai.game.jungle;

import com.marcelfiore.jungle_ai.game.jungle.piece.*;
import com.marcelfiore.jungle_ai.game.jungle.tile.*;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Board class is responsible for board-specific behavior and state. This includes:
 * 1. Knowing about every Tile on the board.
 * 2. Knowing about every Piece on the board, moving the pieces, and removing the pieces.
 * TODO: Move this to TileFactory.java 3. Creating Tiles based on location.
 * TODO: Move this to PieceFactory.java 4. Creating Pieces based on location.
 */
public class Board {
    private HashMap<Location, Tile> board;

    /**
     * Set up a new Board object for a new Game.
     */
    public Board() {
        this.board = new HashMap<>();
        setBoard();
    }

    /**
     * Set up the board:
     * 1. Make a new tile based on each Location, out of 63.
     * 2. Make a new piece and place it on the tiles that start with a piece.
     */
    private void setBoard() {
        int HEIGHT = 9;
        int WIDTH = 7;
        for (int row = 0; row < HEIGHT; ++row) {
            for (int col = 0; col < WIDTH; ++col) {
                Location location = new Location(row, col);
                Piece piece = makePiece(location);
                Tile tile = makeTile(location);
                tile.setPiece(piece); //If there is a piece (i.e., (0,0) gets a Lion), set it on the Tile
                board.put(location, tile);
            }
        }
    }

    /**
     * Create a specific type of Tile (Den, Trap, etc...) based on location.
     * @param location the coordinate of the Tile in the context of the board. Used to determine what type of Tile.
     * @return a specific Tile based on location.
     */
    //TODO: Tile Factory
    Tile makeTile(Location location){
        //Den
        if (isRedDen(location)){
            return new Den("red");
        } else if (isBlueDen(location)){
            return new Den("blue");
        }

        //Trap
        else if (isRedTrap(location)){
            return new Trap("red");
        }
        else if (isBlueTrap(location)){
            return new Trap("blue");
        }

        //River
        else if (isRiver(location)){
            return new River();
        }

        //Open
        else {
            return new Open();
        }
    }

    /**
     * Create a specific type of Piece (Lion, Elephant, etc...) based on location.
     * @param location the coordinate of the Piece in the context of the board. Used to determine what type of Piece.
     * @return a specific Piece based on location.
     */
    //TODO: Piece factory
    private Piece makePiece(Location location){
        //Lion
        if (location.equals(new Location(0, 0))){
            return new Lion("red");
        } else if (location.equals(new Location(8, 6))){
            return new Lion("blue");
        }

        //Rat
        else if (location.equals(new Location(2, 0))){
            return new Rat("red");
        } else if (location.equals(new Location(6, 6))){
            return new Rat("blue");
        }

        //Dog
        else if (location.equals(new Location(1, 1))){
            return new Dog("red");
        } else if (location.equals(new Location(7, 5))){
            return new Dog("blue");
        }

        //Leopard
        else if (location.equals(new Location(2, 2))){
            return new Leopard("red");
        } else if (location.equals(new Location(6, 4))){
            return new Leopard("blue");
        }

        //Wolf
        else if (location.equals(new Location(2, 4))){
            return new Wolf("red");
        } else if (location.equals(new Location(6, 2))){
            return new Wolf("blue");
        }

        //Cat
        else if (location.equals(new Location(1, 5))){
            return new Cat("red");
        } else if (location.equals(new Location(7, 1))){
            return new Cat("blue");
        }

        //Tiger
        else if (location.equals(new Location(0, 6))){
            return new Tiger("red");
        } else if (location.equals(new Location(8, 0))){
            return new Tiger("blue");
        }

        //Elephant
        else if (location.equals(new Location(2, 6))){
            return new Elephant("red");
        } else if (location.equals(new Location(6, 0))){
            return new Elephant("blue");
        }
        return null;
    }

    /**
     * Moves a piece to a new location, not caring about validation.
     * @param piece to be moved.
     * @param location to move piece onto.
     */
    public void move(Piece piece, Location location){
        //Pick up the piece
        board.get(piece.getLocation()).setPiece(null);

        //Set it down
        board.get(location).setPiece(piece);
        piece.setLocation(location);
    }

    /**
     *  makeTile(Location) helpers.
     */
    private boolean isRedDen(Location location){
        return location.equals(new Location(0, 3));
    }

    private boolean isBlueDen(Location location){
        return location.equals(new Location(8, 3));
    }

    private boolean isRedTrap(Location location){
        return location.equals(new Location(0, 2)) ||
                location.equals(new Location(0, 4))||
                location.equals(new Location(1, 3));
    }

    private boolean isBlueTrap(Location location){
        return location.equals(new Location(8, 2)) ||
                location.equals(new Location(8, 4)) ||
                location.equals(new Location(7, 3));
    }

    private boolean isRiver(Location location){
        return location.equals(new Location(3, 1)) || location.equals(new Location(3, 4)) ||
                location.equals(new Location(4, 1)) || location.equals(new Location(4, 4)) ||
                location.equals(new Location(5, 1)) || location.equals(new Location(5, 4)) ||
                location.equals(new Location(3, 2)) || location.equals(new Location(3, 5)) ||
                location.equals(new Location(4, 2)) || location.equals(new Location(4, 5)) ||
                location.equals(new Location(5, 2)) || location.equals(new Location(5, 5));
    }


    /*** Getters, Setters ***/

    public HashMap<Location, Tile> getBoard() {
        return board;
    }

    /**
     * Returns a tile on the board based on location.
     * @param location the location in the context of the board.
     * @return requested tile.
     */
    public Tile getTile(Location location) {
        return board.get(location);
    }

    //Another way to access getTile(Location).
    public Tile getTile(int row, int col) {
        return getTile(new Location(row, col));
    }

    /**
     * Retrieve the pieces owned by the _color_ player.
     * @param color of pieces to look for.
     * @return the set of pieces which have that color.
     */
    ArrayList<Piece> getPieces(String color){
        ArrayList<Piece> bluePieces = new ArrayList<>();
        //Look through every tile on the board
        for (Tile tile : board.values()){
            //If the tile has a piece on it
            if (tile.getPiece() != null){
                //..., and that piece is the right color
                if (tile.getPiece().getColor().equals(color)){
                    bluePieces.add(tile.getPiece());
                }
            }
        }
        return bluePieces;
    }

}
