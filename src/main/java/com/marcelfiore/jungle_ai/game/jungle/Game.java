package com.marcelfiore.jungle_ai.game.jungle;

import java.util.ArrayList;
//import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player[] players;
    private int turn;
    private Board board;
    private final int FAILURE = -1;
    private final int SUCCESS = 100;
    private final Location FAILURE_DESTINATION = new Location(FAILURE, FAILURE);
    private final Location SUCCESS_DESTINATION = new Location(SUCCESS, SUCCESS);

    //Constructor
    public Game () {
        players = new Player[2];
        players[0] = new Player("red");
        players[1] = new Player("blue");
//        Random r = new Random(System.currentTimeMillis());
//        turn = r.nextInt(999999) % 2;
        turn = 1; // 1 means that Bottom Player makes the first move
        board = new Board();
    }

    /***WINNER CODE***/

    /**
     * Called from winnerCheck method and used for determining if any Piece got the the enemy's Den
     * @param whichPlayer which Player's turn is it?
     * @param location [row, col] on the board containing the Piece's current location
     * @return true if the Piece is at the enemy's Den, false if no winner
     */
    public boolean isDen(int whichPlayer, Location location) {
        if (location.getCol() == 3) {
            if (whichPlayer == 0) { // top Player
                return (location.getRow() == 8); // reached bot's Den
            } else { // bot Player
                return (location.getRow() == 0); // reached top's Den
            }
        }
        return false;
    }

    /**
     * There are two ways to win in Jungle:
     * 1. You reach the enemy Den
     * 2. You have no more Pieces (the count variable is 0)
     * @return 0 for top Player, 1 for bottom Player, -1 for no Winners yet
     */
    public int winnerCheck() {
        int count;

        // iterate through both players
        for (int p = 0; p < 2; ++p) {
            count = 0;

            // iterate through the players' pieces
            for (Piece piece : players[p].getValidPieces()) {
                ++count;

                // if a Piece is at the enemy den
                if (isDen(p, piece.getLocation())) {
                    return p;
                }
            }

            // if this player doesn't have any remaining Pieces
            if (count == 0) {
                return otherPlayer(p); // the other Player is the winner
            }
        }

        return -1; // no winner
    }

    /**
     * Match has concluded. Prints who won this match.
     */
    public void endGame() {
        // Prints the Winner statement
        whoseTurnIsIt(winnerCheck(), " is the winner!");
    }


    /***CLI CODE***/
    public void makeMoveCli() {
        NextMove nextMove = retrieveCliInput();

        moveThePiece(nextMove);
    }

    /**
     * Used in the CLI interface only.
     * Reads input from user and only if the user inputs an integer between [1, 8] inclusive.
     * Does not stop until you give it a valid integer.
     * @param sc No need to create multiple Scanners across all of the CLI_Input methods
     * @return the Piece's rank
     */
    public int retrieveCliPieceRank(Scanner sc) {
        String pieceInput;
        int pieceRank;

        while (true) {
            System.out.println("What Piece number do you choose? ");
            System.out.println("  A piece can be selected by it's rank. '1' for Rat, '2' for Cat, etc");
            pieceInput = sc.nextLine();
            if (!pieceInput.isEmpty()) {
                try {
                    pieceRank = Integer.parseInt(pieceInput.trim());
                    if (pieceRank >= 1 && pieceRank <= 8) {
                        return pieceRank;
                    } else {
                        System.out.println("\tERROR: " + pieceRank + " must be between 1 and 8");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\tERROR: " + pieceInput + " is not a valid rank");
                }
            } else {
                System.out.println("\tERROR: must specify a rank");
            }
        }
    }

    /**
     * Used only in the CLI interface. Retrieve the direction.
     * Only cares about the first char in the input string: "down" => 'd', "u1e78F9dw" => 'u', etc.
     * You can input a 'q' if you no longer want to move the Piece you input from the method above.
     * @param sc No need to create multiple Scanners across all of the CLI_Input methods
     * @return the direction IN {'u', 'd', 'l', 'r'}
     */
    public char retrieveCliDirection(Scanner sc) {
        String directionInput = sc.nextLine();

        if (!directionInput.isEmpty()) {
            char direction = Character.toLowerCase(directionInput.trim().charAt(0));

            if (direction == 'u' || direction == 'd' || direction == 'l' || direction == 'r') {
                return direction;
            } else if (direction == 'q') {
                debugPrint("You chose to quit moving this Piece.");
                return direction;
            } else {
                System.out.println("\tERROR: " + direction + " is not a valid direction");
            }
        } else {
            System.out.println("\tERROR: must specify a direction");
        }
        return '#';
    }

    /**
     * Used in CLI interface only.
     * This macro method is used to retrieve the user input from the console.
     * Will only break out of the nested while loops if you give it a vaid Piece and a valid direction.
     * @return
     */
    public NextMove retrieveCliInput() {
        Scanner sc = new Scanner(System.in);
        NextMove nextMove = null;

        boolean continueAsking = true;
        boolean validPiece;
        boolean validDirection;

        Piece piece = null;
        int pieceRank = FAILURE;
        char direction = '#';

        while (continueAsking) {
            //printBoard();
            validDirection = false;
            validPiece = false;
            whoseTurnIsIt(turn, "'s turn.");

            while (!validPiece) {
                if ((pieceRank = retrieveCliPieceRank(sc)) != FAILURE) {
                    if (players[turn].getPiece(pieceRank) != null) {
                        validPiece = true;
                    } else {
                        System.out.println("\tERROR: " + pieceRank + " no longer exists");
                    }
                } else {
                    System.out.println("Not a valid Piece");
                }
            } // We now have a valid Piece.rank: {1, 2, 3, 4, 5, 6, 7, 8}

            piece = players[turn].getPiece(pieceRank);

            while (!validDirection) {
                System.out.println("Which direction do you want to move " + piece.getName() + "? ");
                System.out.println("  Directions can be 'u', 'd', 'l', or 'r' Or 'q' to Quit moving your " + piece.getName());
                if ((direction = retrieveCliDirection(sc)) != '#') {
                    validDirection = true;
                }
            } // We now have a valid direction: {u, d, l, r || q}

            if (direction != 'q'){
                nextMove = getDirection(piece, direction);
                nextMove = new NextMove(piece, isValidMove(nextMove, true));
                debugPrint("Sanity Check: " + nextMove.getRow() + ", " + nextMove.getCol());

                if (nextMove.getRow() != FAILURE && nextMove.getCol() != FAILURE) {
                    System.out.println("\t\t\t Valid move");
                    continueAsking = false;
                } else {
                    System.out.println("\t\t\t Invalid move");
                    printBoard();
                }
            } else {
                System.out.println("You chose to quit moving your " + piece.getName() + ".");
            }
        }

        return nextMove;
    }


    /***GUI CODE***/

    /**
     * UI calls this version to test if the Piece is trying to jump.
     * @param nextMove the (Piece, row, col) of the destination
     * @return true if the Tiger|Lion is trying to jump across the River. False if not.
     */
    public boolean isTryingToJumpUi(NextMove nextMove) {
        Piece lt = nextMove.getPiece();
        if (lt instanceof Tiger || lt instanceof Lion) {
            debugPrint(lt.getName() + ": " + nextMove.getRow() + " " + nextMove.getCol());
            return (jumpInTheUi(nextMove));
        }
        return false;
    }

    /**
     * Key word is "Try" and move via the UI interface. This is called for every first-click of the the mouse and
     * makes the borders highlighted.
     * If it is a valid move, return the Location on the next move (or landing spot). Else return [-1, -1] for "not valid"
     * @param currRow
     * @param currCol
     * @param nextRow
     * @param nextCol
     * @param print "Do we want to print to the console?" Dear God, no! We don't want it littered with garbage.
     * @return
     */
    public Location tryAndMoveUi(int currRow, int currCol, int nextRow, int nextCol, boolean print) {
        if (notYourPieceToMove(currRow, currCol)) {
            System.out.println("\tERROR: Cannot move an enemy Piece.");
            return FAILURE_DESTINATION;
        }

        NextMove nextMove = new NextMove(players[turn], currRow, currCol, nextRow, nextCol);
        Piece p = nextMove.getPiece();

        if (nextMove.getPiece() == null ) {
            System.out.println("\tERROR: Not a valid Piece.");
            return FAILURE_DESTINATION;
        }

        // Calculate the difference in row  and difference in col
        int deltaRow = Math.abs(nextRow - currRow);
        int deltaCol = Math.abs(nextCol - currCol);
        boolean jumping = false;

        if (isTryingToJumpUi(nextMove)) {
            System.out.println("You are trying to jump with your " + p.getName());
            jumping = jumpInTheUi(nextMove);
        } else if (deltaRow == 1 && deltaCol == 0) {
            debugPrint("You are trying to move vertically.");
        } else if (deltaRow == 0 && deltaCol == 1) {
            debugPrint("You are trying to move horizontally.");
        } else {
            System.out.println("\tERROR: Not a valid move. A Piece can only move one Tile.");
            return FAILURE_DESTINATION;
        }

        Location loc;

        if (!jumping) { // Not a Lion or Tiger
            return isValidMove(nextMove, print);
        } else { // Lion or Tiger
            //return new Location(nextMove.getRow(), nextMove.getCol());
            return normalizeJump(currRow, currCol, nextRow, nextCol);
        }
    }

    /**
     * UI controller calls this method to move the Piece
     * @param currRow the first click's horizontal location on the board
     * @param currCol the first click's vertical location on the board
     * @param nextRow the second click's horizontal location on the board
     * @param nextCol the second click's vertical location on the board
     */
    public boolean makeMoveUi(int currRow, int currCol, int nextRow, int nextCol) {
        NextMove nextMove = new NextMove(players[turn], currRow, currCol, nextRow, nextCol);
        Piece p = nextMove.getPiece();

        Location loc = tryAndMoveUi(currRow, currCol, nextRow, nextCol, true);

        if (!doesPieceLocationMatch(loc, FAILURE, FAILURE)) {
            nextMove = new NextMove(p, loc);
            moveThePiece(nextMove);
            return true;
        } else {
            return false;
        }
    }

    /**
     * The UI is a little different than the console. A Lion or Tiger can be selected to jump "into" the River.
     * Therefore, we need to check if the landing is valid, so we have to manually generate where it will go.
     * This has a helper method to "normalize" the jump, because the Lion or Tiger cannot actually land in the River.
     * @param n the Lion or Tiger (previously checked for instanceof check).
     * @return true if both are true: 1. Path is free of Rats  2. if Piece @ landing, is it valid to land on them?
     */
    public boolean jumpInTheUi(NextMove n) {
        boolean validGeometry = false;
        Piece p = n.getPiece();
        int currRow = p.getRow();
        int currCol = p.getCol();
        int nextRow = n.getRow();
        int nextCol = n.getCol();

        if (currRow == 2 && (currCol == nextCol) && (nextRow >= 3 && nextRow <= 6) && (currCol == 1 || currCol == 2 || currCol == 4 || currCol == 5)) {
            // vertical
            validGeometry = true;
        } else if (currRow == 6 && (currCol == nextCol) && (nextRow >= 2 && nextRow <= 5) && (currCol == 1 || currCol == 2 || currCol == 4 || currCol == 5)) {
            // vertical
            validGeometry = true;
        } else if (currCol == 0 && (currRow == nextRow) && (nextCol >= 1 && nextRow <= 3) && (currRow >= 3 && currRow <= 5)) {
            // horizontal
            validGeometry = true;
        } else if (currCol == 3 && (currRow == nextRow) && (currRow >= 3 && currRow <= 5) && currRow == nextRow) {
            // horizontal
            if (nextCol >= 0 && nextCol <= 2) {
                validGeometry =  true;
            } else if (nextCol >= 4 && nextCol <= 6) {
                validGeometry = true;
            }
        } else if (currCol == 6 && (currRow == nextRow) && (nextCol >= 3 && nextCol <= 5) && (currRow > 3 && currRow < 5)) {
            // horizontal
            validGeometry = true;
        }

        if (validGeometry) {
            Location nextLoc = normalizeJump(currRow, currCol, nextRow, nextCol);
            return (isLandingValid(p, nextLoc, false));
        }
        return false;
    }


    /***MOVE CODE***/

    public void moveThePiece(NextMove nextMove) {
        Piece piece = nextMove.getPiece();
        int nextRow = nextMove.getRow();
        int nextCol = nextMove.getCol();
        int pieceRank = piece.getRank();

        // Move the Piece
        players[turn].getPiece(pieceRank).setLocation(nextRow, nextCol);

        int enemyPieceRank;
        if ((enemyPieceRank = containsPiece(otherPlayer(), piece.getRow(), piece.getCol())) != -1) {
            System.out.println("Capturing enemy Piece with your " + piece.getName());
            players[otherPlayer()].isCaptured(enemyPieceRank); // sets captured Piece to null
        }
        debugPrint("Moved piece " + piece.getName()+ " to (" + piece.getRow() + "," + piece.getCol() + ")\n");

        incrementTurn();
    }

    /**
     * Checks if the next move's location is a River Tile.
     * @param row the next move's horizontal location on the board
     * @param col the next move's vertical location on the board
     * @return true if it is a River Tile
     */
    public boolean isNextMoveARiver(int row, int col) {
        return (board.isRiver(row, col));
    }

    /**
     * Checks if the next move's location is a Trap Tile.
     * @param row the next move's horizontal location on the board
     * @param col the next move's vertical location on the board
     * @return true if ... Well, Admiral Ackbar says it best.
     */
    public boolean isNextMoveATrap(int row, int col) {
        return (board.isTrap(row, col));
    }

    /**
     * Who owns this Piece? If it's the enemy's Piece, then you cannot use it [returns true == "You cannot use it"]
     * @param row horizontal location used to retrieve the Piece in question
     * @param col vertical location used to retrieve the Piece in question
     * @return true if the enemy owns it, false if you own it.
     */
    public boolean notYourPieceToMove(int row, int col) {
        return !(players[otherPlayer()].retrievePieceByLocation(row, col) == null);
    }


    /***VALIDATE CODE***/

    /**
     * Called from makeMove method and is used to validate if the next move desired is valid.
     * If the move enters on of the 8 exceptions listed, then it will return within the same conditional, regardless of outcome.
     * // TODO Should we not return FAILURE_DESTINATION so many times (read one line above), and instead return the failure after exception #8?
     *
     * The order of checking for exceptions is as follows:
     *
     * 1. Is the next move's location out of bounds horizontally or vertically? [fail]
     * 2. Is the p a Lion or Tiger and attempting to jump across the River?
     *      Is the path blocked by a Rat in the River? [fail]
     *      Is there a friendly piece in the landing location? [fail]
     *      Is there an enemy in the landing location?
     *          Does p outrank the enemy? [succ]
     * 3. Is p a Rat, currently in the River, and wants to emerge from the River?
     *      Is there a Piece located in the next move's location that will block this move? [fail]
     * 4. Is the next move's location a River Tile?
     *      Is p a Rat? [succ]
     * 5. Is the next move's location a Trap Tile?
     *      Is there a Piece in the Trap Tile?
     *          Is it an enemy Piece?
     *              Are they in your Trap? [succ] // enemy rank = 0
     *              Else: The enemy is in their own Trap [follow rest of rules]
     *          Else: It is a friendly Piece [fail]
     * 6. Is p a Rat and want to capture the enemy Elephant? [succ]
     * 7. Is p an Elephant and is trying to capture the enemy Rat? [fail]
     * 8. Does the next move's location contain an enemy Piece?
     *      Is the next move's location on a Trap Tile? [succ]
     *      Does p outrank the enemy? [succ]
     * 9. Does the next move's location contain a friendly Piece? [fail]
     * 10.If no aforementioned conditions are met, then it is a valid move.
     * @param nextMove the (Piece, row, col) all packed into one element.
     * @param print "Shall I print the failures for you?" false == "shh"
     * @return [-1, -1] called "FAILURE_DESTINATION" to represent an invalid move.
     *         [nextMovesRow, nextMovesCol] to represent a valid move in an int array
     */
    public Location isValidMove(NextMove nextMove, boolean print) {
        int enemyPieceRank;
        Piece p = nextMove.getPiece();
        int row = nextMove.getRow();
        int col = nextMove.getCol();
        Piece pieceInLocation;

        if (isOutOfBounds(row, col)) {
            // 1. The next move's location out of bounds
            print("Out of bounds!", print);
            return FAILURE_DESTINATION;

        } else if (isTryingToJump(p, row, col)) {
            // 2. p is a Lion or Tiger and is attempting to jump across the River
            Location nextDestination = isAbleToJump(p, row, col, "trying to jump for real");
            if (!doesPieceLocationMatch(nextDestination, FAILURE, FAILURE)) {
                return nextDestination;
            } else {
                print("Your " + p.getName() + " cannot jump across at this time.", print);
                return FAILURE_DESTINATION;
            }

        } else if (isRatTryingToEmerge(p, row, col)) {
            // 3. p is a Rat, is currently in the River, and wants to emerge from the River
            debugPrint("Rat is trying to emerge from the River onto a Land-like Tile.");
            if (isAbleToEmerge(row, col)) {
                debugPrint("Rat will successfully emerge from the River.");
                return new Location(row, col);
            } else {
                return FAILURE_DESTINATION;
            }

        } else if (isNextMoveARiver(row, col)) {
            // 4. The next move's location a River Tile
            debugPrint("Next Tile is a River. Only Rats can enter this Tile.");
            if (p.isRat()) {
                debugPrint("Rat will successfully move to (" + row + ", " + col + ")");
                return new Location(row, col);
            } else {
                print("Cannot move " + p.getName() + " into the River.", print);
                return FAILURE_DESTINATION;
            }

        } else if (isNextMoveATrap(row, col)) {
            // 5. The next move's location is a Trap Tile
            debugPrint("Next move's location is a Trap.");

            if ((pieceInLocation = tryAndRetrieveAPiece(row, col)) != null) {
                debugPrint("There is a Piece in the Trap.");

                if (whoOwnsThisPiece(pieceInLocation) == otherPlayer()) {
                    debugPrint("Enemy Piece in the Trap.");

                    if (isEnemysRankReducedToZero(pieceInLocation)) {
                        print("Enemy " + pieceInLocation.getName() + "'s rank is reduced to 0.", print);
                        return new Location(row, col);
                    } else {
                        debugPrint("Enemy is in their own Trap.");
                        return FAILURE_DESTINATION;
                        // TODO: There's a bug! Major restructuring needed.
                        // We need to run the rest of the rules/exceptions
                        // possible solution:
                        // 1. refactor isValidMove into method calls
                        // 2. Make a method call that captures all of these nested conditions.
                        //
                        // Flow MUST check if it's a valid move by testing:
                        //     Rat trying to capture Elephant?
                        //     Elephant trying to capture Rat?
                        //     your Rank >= enemy rank?
                    }
                } else {
                    debugPrint("Cannot move there because there's a friendly Piece in the Trap.");
                    return FAILURE_DESTINATION;
                }
            } else {
                debugPrint("Trap without any Pieces");
                return new Location(row, col);
            }

        } else if (ratCapturesElephant(p, row, col)) {
            // 6. p is a Rat and wants to capture the enemy Elephant
            print("Rat will sneak up and eat the Elephant's brain!", print);
            return new Location(row, col);

        } else if (elephantTryingToCaptureRat(p, row, col)) {
            // 7. p is an Elephant and is trying to capture the enemy Rat
            print("Elephant cannot capture the Rat because he's too afraid of the Rat...", print);
            return FAILURE_DESTINATION;

        } else if ((enemyPieceRank = containsPiece(otherPlayer(), row, col)) != -1) {
            // 8. The next move's location contains an enemy Piece
            debugPrint("Only an equal or higher rank can capture an enemy Piece.");
            debugPrint("Your Piece's rank: " + p.getRank());
            if (p.getRank() >= enemyPieceRank) {
                // your Piece's ranks beats the enemy Piece's rank
                return new Location(row, col);
            } else {
                print("You cannot capture an enemy with a higher rank.", print);
                return FAILURE_DESTINATION;
            }

        } else if (containsPiece(turn, row, col) != -1) {
            // 9. The next move's location contains a friendly Piece
            debugPrint("There is a friendly Piece located here.");
            print("You can't capture your own Piece.", print);
            return FAILURE_DESTINATION;
        } else {
            // 10. It is a valid move
            debugPrint("\tJust a regular move without any exceptions, thus is a valid move");
            return new Location(row, col);
        }
    }

    /**
     * If the enemy is in your Trap, their rank is reduced to zero
     * @param enemyPiece the enemy's Piece
     * @return true if the enemy is not in their own Trap. False if they're in their Trap.
     */
    public boolean isEnemysRankReducedToZero(Piece enemyPiece) {
        int row = enemyPiece.getRow();

        if (enemyPiece.getColor().equals("red")) {
            debugPrint("Is the top Piece in top trap? " + (row == 0 || row == 1));
            return !(row == 0 || row == 1);
        } else {
            debugPrint("Is the bot Piece in bot trap? " + (row == 7 || row == 8));
            return !(row == 7 || row == 8);
        }
    }


    /***GETTER CODE***/

    public ArrayList<Location> retrieveValidLocations(int row, int col) {
        ArrayList<Location> validDirections = new ArrayList<>();
        Piece p = players[turn].retrievePieceByLocation(row, col);

        if (p != null) {
            Location up = tryAndMoveUi(row, col, row - 1, col, false);
            Location down = tryAndMoveUi(row, col, row + 1, col, false);
            Location left = tryAndMoveUi(row, col, row, col - 1, false);
            Location right = tryAndMoveUi(row, col, row, col + 1, false);

            if (!doesPieceLocationMatch(up, -1, -1)) {
                validDirections.add(up);
            }
            if (!doesPieceLocationMatch(down, -1, -1)) {
                validDirections.add(down);
            }
            if (!doesPieceLocationMatch(left, -1, -1)) {
                validDirections.add(left);
            }
            if (!doesPieceLocationMatch(right, -1, -1)) {
                validDirections.add(right);
            }
        }
//        else {
//            System.out.println("That Tile does not contains a Piece.");
//        }
        return validDirections;
    }

    /**
     * Is only called from the CLI interface. Retrieves a valid direction
     * @param p the Piece in question
     * @param direction the direction the user wants to move said Piece.
     * @return
     */
    public NextMove getDirection(Piece p, char direction) {
        NextMove nextMove = new NextMove(p, p.getRow(), p.getCol());
        debugPrint("\tfrom (" + nextMove.getRow() + ", " + nextMove.getCol() + ")");

        if (direction =='d') {
            nextMove.incRow();
        } else if (direction == 'u') {
            nextMove.decRow();
        } else if (direction == 'r') {
            nextMove.incCol();
        } else if (direction == 'l') {
            nextMove.decCol();
        }

        debugPrint("\tto   (" + nextMove.getRow() + ", " + nextMove.getCol() + ")");
        return nextMove;
    }

    /**
     * Self-explanatory, retrieves the *private* Player
     * @param whichPlayer the index in the private array of 2 Players
     * @return the Player desired
     */
    public Player getPlayer(int whichPlayer) {
        return players[whichPlayer];
    }

    /**
     * Used to acces the private variable. Will be useful for testing & Ui interface
     * @return turn {0, 1}
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * @return the index (associated to the next Player's turn) in the array of Players
     */
    public int otherPlayer() {
        return (this.turn + 1) % 2;
    }

    /**
     * Returns the index of the opponent
     * @param thisPlayer the index of whatever Player you want
     * @return the index of the other Player
     */
    public int otherPlayer(int thisPlayer) {
        return (thisPlayer + 1) % 2;
    }


    /***DEBUG CODE***/

    /**
     * Feel free to change the debug to true or false depending on if we're demo-ing or not
     * @param message Could be an int, String, Object, doesn't matter it will print what you want
     */
    public void debugPrint(Object message) {
        boolean debug = false;

        if (debug) {
            System.out.println("[ Debug ] : " + message);
        }
    }

    public void print(String message, boolean printIt) {
        if (printIt) {
            System.out.println(message);
        }
    }


    /***OTHER CODE***/

    /**
     * Is only called from the CLI interface.
     * Prints the board to the console output.
     */
    public void printBoard() {
        this.board.printBoard(players);
    }

    /**
     * Depending on the turn parameter, Prints out whose turn it is.
     * @param turn 0 for top Player, 1 for bottom Player
     * @param message whatever you want to add to the string, i.e. "'s turn"
     */
    public void whoseTurnIsIt(int turn, String message) {
        if (turn == 0) {
            System.out.println("\tTop Player" + message);
        } else {
            System.out.println("\tBottom Player" + message);
        }
    }

    /**
     * Handy little method for returning the index (in Player[] players)
     * to see if the top Player owns the Piece
     * @param p the Piece in question
     * @return 0 if it belongs to the top Player, 1 if bot's Piece
     */
    public int whoOwnsThisPiece(Piece p) {
        return ((p.getColor().equals("red")) ? 0 : 1);
    }

    /**
     * Every valid move will increment the turn so it will be the next Player's turn
     */
    public void incrementTurn() {
        this.turn = otherPlayer();
    }

    /**
     * Compares the Location's row and col against the row and col provided.
     * Usually used in a "is this an invalid move?" == -1 sense.
     * @param loc [currRow, currCol]
     * @param row the next move's horizontal location on the board
     * @param col the next move's vertical location on the board
     * @return true if they match, false if they do not
     */
    public boolean doesPieceLocationMatch(Location loc, int row, int col) {
        return (loc.getRow() == row && loc.getCol() == col);
    }

    /**
     * Compare's the Piece's row and col against the row and col provided.
     * @param p the piece we're using to extract the currRow and currCol from
     * @param row the value we're testing the Piece's row against
     * @param col the value we're testing the Piece's col against
     * @return true if they match, false if they don't
     */
    public boolean doesPieceLocationMatch(Piece p, int row, int col) {
        return (p.getRow() == row && p.getCol() == col);
    }

    /**
     * Same as above
     * @param nextMove contains the NextMove's row and col
     * @param row the value we're testing the Piece's row against
     * @param col the value we're testing the Piece's col against
     * @return true if they match, false if they don't match
     */
    public boolean doesPieceLocationMatch(NextMove nextMove, int row, int col) {
        return (nextMove.getRow() == row && nextMove.getCol() == col);
    }

    /**
     * Answers the question: Is there a piece in the nextMove's location?
     * @param row the next move's horizontal location on the board
     * @param col the next move's vertical location on the board
     * @return the Piece if it exists, else null
     */
    public Piece tryAndRetrieveAPiece(int row, int col) {
        for (Player currPlayer : players) {
            Piece p = currPlayer.retrievePieceByLocation(row, col);
            if (p != null) {
                debugPrint("A " + p.getName() +  " is at (" + row + ", " + col + ") !");
                return p;
            }
        }
        return null;
    }

    /**
     * Checks if the next move's location is out of bounds
     * @param row the next move's horizontal location on the board
     * @param col the next move's vertical location on the board
     * @return true if it is OOB, false if in bounds
     */
    public boolean isOutOfBounds(int row, int col) {
        return (row < 0 || row > 8 || col < 0 || col > 6);
    }

    /**
     * Called from isValidMove method and is used for when validating a move from user.
     * If there is an existing Piece, it will return the rank, else -1.
     * @param playerNumber used for checking either enemy Pieces or friendly Pieces
     * @param row the next move's horizontal location on the board
     * @param col the next move's vertical location on the board
     * @return the rank (1-8) of a Piece, else -1 for no Pieces present.
     */
    public int containsPiece(int playerNumber, int row, int col) {
        for (Piece currPiece : players[playerNumber].getValidPieces()) {
            if (row == currPiece.getRow() && col == currPiece.getCol()) {
                return currPiece.getRank();
            }
        }
        return -1;
    }

    /**
     * Checks if the Rat is trying to capture the enemy Elephant
     * @param p the (possible) Rat
     * @param row the next move's horizontal location on the board
     * @param col the next move's vertical location on the bard
     * @return true if Rat captures the enemy Elephant, false if not.
     */
    public boolean ratCapturesElephant(Piece p, int row, int col) {
        if (p instanceof Rat) {
            Piece enemyElephant = players[otherPlayer()].getPiece(8);

            if (enemyElephant != null) {
                return (doesPieceLocationMatch(enemyElephant.getLocation(), row, col));
            }
        }
        return false;
    }

    /**
     * Checks if the Elephant is attempting to capture the enemy Rat.
     * @param p the (possible) Elephant
     * @param row the next move's horizontal location on the board
     * @param col the next move's vertical location on the board.
     * @return true if the Elephant is attempting to capture the enemy Rat, false if not.
     */
    public boolean elephantTryingToCaptureRat(Piece p, int row, int col) {
        if (p instanceof Elephant) {
            Piece rat = players[otherPlayer()].getPiece(1);

            if (rat != null) {
                return (doesPieceLocationMatch(rat.getLocation(), row, col));
            } else {
                System.out.println("Rat is already captured");
            }
        }
        debugPrint("Your Piece is not an Elephant.");
        return false;
    }

    /**
     * Used when the Lion or Tiger is, in fact, trying to jump. Checks if there are not Rats, and if the landing is clear.
     *     First, we have to determine if it's a horizontal jump or a vertical jump and set the min and max
     *     Next, check if there are any Rats in the River.
     * If the landing contains a Piece:
     *     Check if it's a friendly Piece [fail]
     *     Check if the enemy has a lower rank [pass] else [fail]
     * @param p
     * @param nextLoc
     * @param print
     * @return
     */
    public boolean isLandingValid(Piece p, Location nextLoc, boolean print) {
        return isLandingValid(p, nextLoc.getRow(), nextLoc.getCol(), print);
    }


    public boolean isLandingValid(Piece p, int row, int col, boolean print) {
        // Check if a Rat is in the River (specifically if it is in the path of the jump)
        Location currLocation = p.getLocation();
        for (Player currPlayer : players) {
            Piece rat = currPlayer.getValidPieces()[0];
            if (rat != null) {
                int min;
                int max;

                //determine if the Lion|Tiger is moving horizontally|vertically
                if ((currLocation.getRow() - row) != 0) {
                    // Moving vertically across the River
                    // We're going to check up to down regardless of direction of travel
                    min = (currLocation.getRow() < row) ? currLocation.getRow() : row;
                    max = (currLocation.getRow() > row) ? currLocation.getRow() : row;

                    debugPrint("Checking for Rat from ([" + (min+1) + " to " + (max-1) + "], " + col + ").");

                    for (int checkThisRow = min + 1; checkThisRow < max; ++checkThisRow) {
                        if (rat.getRow() == checkThisRow && rat.getCol() == col) {
                            print("The vertical jump is blocked by a Rat in the River.", print);
                            return false;
                        }
                    }
                } else if ((currLocation.getCol() - col) != 0) {
                    // Moving horizontally across the River
                    // We're going to check left to right regardless of direction of travel
                    min = (currLocation.getCol() < col) ? currLocation.getCol() : col;
                    max = (currLocation.getCol() > col) ? currLocation.getCol() : col;
                    debugPrint("Checking for Rat from (" + row + ", [" + (min+1) + " to " + (max-1) + "]).");

                    for (int checkThisCol = min + 1; checkThisCol < max; ++checkThisCol) {
                        if (rat.getRow() == row && rat.getCol() == checkThisCol){
                            print("The horizontal jump is blocked by a Rat in the River.", print);
                            return false;
                        }
                    }
                }
            }
        } // There aren't any Rat's in the jump path
        debugPrint("There aren't any Rats in the jump path over the River.");

        // Check the landing spot to see if the Lion||Tiger can land there
        for (Player currPlayer : players) {
            for (Piece piece : currPlayer.getValidPieces()) {
                Location location = piece.getLocation();
                if (row == location.getRow() && col == location.getCol()) {
                    if (!piece.getColor().equals(p.getColor())) {
                        debugPrint("There is an enemy in your landing spot.");
                        debugPrint("You outrank them: " + (p.getRank() >= piece.getRank()));
                        return (p.getRank() >= piece.getRank()); // returns true if I am of an equal or higher rank than you
                    } else {
                        print("Cannot jump across and capture a friendly Piece.", print);
                        return false;
                    }
                }
            }
        } // There aren't any more Pieces to check

        debugPrint("Good news, everyone!");
        debugPrint("\tThere is nothing blocking your " + p.getName() + "'s jump to (" + row + ", " + col + ").");
        return true; // Landing Tile is not occupied
    }

    /**
     * Used to check if p is a Tiger or Lion. If it is, is it able to jump?
     * Two uses: checks if it wants to jump (isTryingToJump), then checks if it can jump for real.
     * The nextRow & nextCol parameters are lies! They are only one Tile off from the starting Tile.
     * Therefore, we need to calculate where p will land, hence why this returns a Location.
     * @param p the Piece in question
     * @param nextRow Which horizontal direction does p want to go?
     * @param nextCol Which vertical direction does p want to go?
     * @param typeOfMove {"testing jump", "jump for real"}
     * @return the Location of where it will land or [-1, -1] for "not a valid jump"
     */
    public Location isAbleToJump(Piece p, int nextRow, int nextCol, String typeOfMove) {
        int startingRow = nextRow;
        int startingCol = nextCol;
        Location returnDestination = new Location(FAILURE, FAILURE);


        if (p instanceof Tiger || p instanceof Lion) {
            Location currLocation = p.getLocation();
            int currRow = currLocation.getRow();
            int currCol = currLocation.getCol();
            debugPrint("Current location: (" + currRow + ", " + currCol + ")");

            if (currRow == 2 && nextRow == 3 && (currCol == 1 || currCol == 2 || currCol == 4 || currCol == 5)) {
                nextRow = 6;
            } else if (currRow == 6 && nextRow == 5 && (currCol == 1 || currCol == 2 || currCol == 4 || currCol == 5)) {
                nextRow = 2;
            } else if (currCol == 0 && nextCol == 1 && (currRow >= 3 && currRow <= 5)) {
                nextCol = 3;
            } else if (currCol == 3 && (currRow >= 3 && currRow <= 5)) {
                if (nextCol == 2) {
                    nextCol = 0;
                } else if (nextCol == 4) {
                    nextCol = 6;
                }
            } else if (currCol == 6 && nextCol == 5 && (currRow >= 3 && currRow <= 5)) {
                nextCol = 3;
            } else {
                debugPrint("Your Lion or Tiger is NOT trying to jump across the River.");
                return returnDestination;
            }

            if (typeOfMove.equals("testing trying to jump")) {
                debugPrint("Row transformation: " + startingRow + " => " + nextRow);
                debugPrint("Col transformation: " + startingCol + " => " + nextCol);
                if (!(startingRow == nextRow && startingCol == nextCol)) {
                    return SUCCESS_DESTINATION;//return "Yes, Lion|Tiger is trying to Jump"
                } else {
                    return FAILURE_DESTINATION;
                }
            } else {
                debugPrint("Your Lion or Tiger is trying to jump across the River to (" + nextRow + ", " + nextCol + ").");
                boolean isThisAValidJump = isLandingValid(p, nextRow, nextCol, true);
                debugPrint("This is a valid jump: " + isThisAValidJump);
                debugPrint("The jump is shooting for " + nextRow + " " + nextCol);
                if (isThisAValidJump) {
                    returnDestination = new Location(nextRow, nextCol);
                    debugPrint("The jump is successfully going to land on (" + returnDestination.getRow() + ", " + returnDestination.getCol() + ").");
                    return returnDestination;
                } else {
                    return FAILURE_DESTINATION;
                }
            }
        }

        debugPrint("Your " + p.getName() + " is apparently not a Lion or Tiger");
        return FAILURE_DESTINATION;
    }

    /**
     * The CLI calls this version to test if p is trying to jump.
     * Calls isAbleToJump to test if the Piece is a Lion|Tiger
     * Once we determine the Piece is a Lion|Tiger,
     * isAbleToJump will determine if it's heading in the River Tile.
     * If yes to both, then we return true
     * @param p the piece in question
     * @param row the next move's horizontal location on the board
     * @param col the next move's vertical location on the board
     * @return true if the Tiger|Lion is trying to jump across the River. False if not.
     */
    public boolean isTryingToJump(Piece p, int row, int col) {
        Location nextDestination = isAbleToJump(p, row, col, "testing trying to jump");
        return (doesPieceLocationMatch(nextDestination, SUCCESS, SUCCESS));
    }

    /**
     * Tests if:
     * 1. Piece in question is a Rat
     * 2. Rat is currently located in the River and
     * 3. Rat wants to emerge from the River (onto a Jump Tile).
     * @param p the piece in question
     * @param row the next move's horizontal location on the board
     * @param col the next move's vertical location on the board
     * @return true only if all 3 conditions are true
     */
    public boolean isRatTryingToEmerge(Piece p, int row, int col) {
        debugPrint("Rat:" + p.isRat() + ". River:" + board.isRiver(p.getRow(), p.getCol()) + ". Land:" + board.isLand(row, col) + ".");

        return (p.isRat() && board.isRiver(p.getRow(), p.getCol()) && board.isLand(row, col));
    }

    /**
     * Once the piece is deemed a Rat, in the River and is attempting to emerge:
     * we will now iterate through both Players' Pieces to see if the movement is blocked
     * @param row the next move's horizontal location on the board
     * @param col the next move's vertical location on the board
     * @return true if there aren't any Pieces at the next move's location
     */
    public boolean isAbleToEmerge(int row, int col) {
        for (Player currPlayer : players) {
            for (Piece currPiece : currPlayer.getValidPieces()) {
                if (doesPieceLocationMatch(currPiece, row, col)) {
                    if (currPlayer == players[turn]) {
                        System.out.println("Cannot capture a friendly piece.");
                    } else {
                        System.out.println("Cannot attack from the water.");
                    }
                    return false;
                }
            }
        }
        // No Pieces are blocking the move to exit the water
        return true;
    }

    /**
     * Lions and Tigers cannot land IN the River, so this returns the actual landing spot as normal.
     * @param currRow
     * @param currCol
     * @param nextRow Which direction is the Lion or Tiger headed?
     * @param nextCol Which direction is the Lion or Tiger headed?
     * @return the Location of the actual landing spot.
     */
    public Location normalizeJump(int currRow, int currCol, int nextRow, int nextCol) {
        int row = nextRow;
        int col = nextCol;

        if (currRow == 2) {
            row = 6;
        } else if (currRow == 6) {
            row = 2;
        } else if (currCol == 0 || currCol == 6) {
            col = 3;
        } else if (currCol == 3) {
            if (nextCol < 3) {
                col = 0;
            } else if (nextCol > 3) {
                col = 6;
            }
        }
        return new Location(row, col);
    }













}
