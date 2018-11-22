package com.marcelfiore.jungle_ai.game.jungle;

import java.util.HashMap;

public class Board {
    private final int HEIGHT = 9;
    private final int WIDTH = 7;
    private Tile[][] board;

    public Board() {
        this.board = new Tile[HEIGHT][WIDTH];
        setBoard();
    }

    /**
     * Iterates through each row, then each column, and
     * instantiates each Tile (subtype) based off the location
     */
    public void setBoard() {
        for (int row = 0; row < HEIGHT; ++row) {
            for (int col = 0; col < WIDTH; ++col) {
                this.board[row][col] = makeTile(row, col);
            }
        }
    }

    /**
     * Returns a tile on the board
     * @param row horizontal location on board
     * @param col vertical location on board
     * @return requested tile
     */
    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    /**
     * If the Tile at (row, col) is suppose to be a River Tile
     * @param row horizontal location on board
     * @param col vertical location on board
     * @return true if it's suppose to be a River Tile. false if not
     */
    public boolean isDen(int row, int col) {
        return ((row == 0 || row == 8) && col == 3);
    }

    /**
     * If the Tile at (row, col) is suppose to be a Trap Tile
     * @param row horizontal location on board
     * @param col vertical location on board
     * @return true if it's suppose to be a Trap Tile. false if not
     */
    public boolean isTrap(int row, int col) {
        // if there is a Den below, above, to right or to left
        if (isDen(row + 1, col) || isDen(row - 1, col)
                || isDen(row, col + 1) || isDen(row, col - 1)) {
            return true;
        }
        return false;
    }

    /**
     * If the Tile at (row, col) is suppose to be a River Tile
     * @param row horizontal location on board
     * @param col vertical location on board
     * @return true if it's suppose to be a River Tile. false if not
     */
    public boolean isRiver(int row, int col) {
        return ((row >= 3 && row <= 5) && (col == 1 || col == 2 || col == 4 || col == 5));
    }

    /**
     * If the Tile at (row, col) is suppose to be a Jump Tile
     * @param row horizontal location on board
     * @param col vertical location on board
     * @return true if it's suppose to be a Jump Tile. false if not
     */
    public boolean isJump(int row, int col) {
        //if there is a River Tile above, below, to right, or to left
        if (isRiver(row + 1, col) || isRiver(row - 1, col)
                || isRiver(row, col + 1) || isRiver(row, col - 1)) {
            return true;
        }
        return false;
    }

    /**
     * Used for when Rat is trying to emerge from the River onto a Land Tile.
     * We only need to check if it is a Jump Tile, and skip checking if it is an Open Tile
     * @param row horizontal location on board
     * @param col vertical location on board
     * @return true if it is a Jump Tile (Land)
     */
    public boolean isLand(int row, int col) {
        return (this.board[row][col] instanceof Jump);
    }

    /**Make instance of Tile inside the 2d array of Tiles
     * based off the (row, col) location inside the board.
     * @param row horizontal location on board
     * @param col vertical location on board
     * @return a new instance of it's corresponding Tiles
     */
    public Tile makeTile(int row, int col) {
        if (isDen(row, col)) {
            return new Den();
        } else if (isTrap(row, col)) {
            return new Trap();
        } else if (isRiver(row, col)) {
            return new River();
        } else if (isJump(row, col)) {
            return new Jump();
        } else {
            return new Open();
        }
    }

    public Tile makeTile(Location location){
        //Den
        if (isRedDen(location)){
            return new Den("Red");
        } else if (isBlueDen(location)){
            return new Den("Blue");
        }

        //Trap
        else if (isRedTrap(location)){
            return new Trap("Red");
        }
        else if (isBlueTrap(location)){
            return new Trap("Blue");
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

    /**     For crude implementation ONLY
     * Creates a new 2d array of char to render as the output
     * then iterates through each char and if there isn't a piece there, retrieve the real board's Tile.
     * After completing construction, it prints the temp board
     * @param players the array of two Players that have Pieces on the real board
     */
    public void printBoard(Player[] players) {
        char[][] draw = new char[HEIGHT][WIDTH];

        for (int row = 0; row < HEIGHT; ++row) {
            for (int col = 0; col < WIDTH; ++col) {
                if (draw[row][col] == '\0') {
                    draw[row][col] = this.board[row][col].getAttribute();
                }
            }
        }

        for (int row = 0; row < HEIGHT; ++row) {
            for (int col = 0; col < WIDTH; ++col) {
                if (col != WIDTH - 1) {
                    System.out.print(draw[row][col] + " ");
                } else {
                    System.out.println(draw[row][col]);
                }
            }
        }
    }

    /**
     *  makeTile(Location) helpers
     */
    private boolean isRedDen(Location location){
        if (location.equals(new Location(0, 3))){
            return true;
        }
        return false;
    }

    private boolean isBlueDen(Location location){
        if (location.equals(new Location(8, 3))){
            return true;
        }
        return false;
    }

    private boolean isRedTrap(Location location){
        if (location.equals(new Location(0, 2)) ||
                location.equals(new Location(0, 4))||
                location.equals(new Location(1, 3))){
            return true;
        }
        return false;
    }

    private boolean isBlueTrap(Location location){
        if (location.equals(new Location(8, 2)) ||
                location.equals(new Location(8, 4)) ||
                location.equals(new Location(7, 3))){
            return true;
        }
        return false;
    }

    private boolean isRiver(Location location){
        if (location.equals(new Location(3, 1)) || location.equals(new Location(3, 4)) ||
                location.equals(new Location(4, 1)) || location.equals(new Location(4, 4)) ||
                location.equals(new Location(5, 1)) || location.equals(new Location(5, 4)) ||
                location.equals(new Location(3, 2)) || location.equals(new Location(3, 5)) ||
                location.equals(new Location(4, 2)) || location.equals(new Location(4, 5)) ||
                location.equals(new Location(5, 2)) || location.equals(new Location(5, 5))){
            return true;
        }
        return false;
    }

}
