package com.marcelfiore.jungle_ai.game.gui;

import com.marcelfiore.jungle_ai.game.jungle.piece.Piece;
import com.marcelfiore.jungle_ai.game.jungle.tile.Tile;
import com.marcelfiore.jungle_ai.game.jungle.Game;
import com.marcelfiore.jungle_ai.game.jungle.Location;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePage extends Page implements ActionListener {
    // Blue Pieces
    private final Icon RED_CAT_ICON = new ImageIcon(new ImageIcon("src/main/images/cat-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_DOG_ICON = new ImageIcon(new ImageIcon("src/main/images/dog-red.png").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_ELEPHANT_ICON = new ImageIcon(new ImageIcon("src/main/images/elephant-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_LEOPARD_ICON = new ImageIcon(new ImageIcon("src/main/images/leopard-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_LION_ICON = new ImageIcon(new ImageIcon("src/main/images/lion-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_RAT_ICON = new ImageIcon(new ImageIcon("src/main/images/rat-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_TIGER_ICON = new ImageIcon(new ImageIcon("src/main/images/tiger-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_WOLF_ICON = new ImageIcon(new ImageIcon("src/main/images/wolf-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

    // Red Pieces
    private final Icon BLUE_CAT_ICON = new ImageIcon(new ImageIcon("src/main/images/cat-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_DOG_ICON = new ImageIcon(new ImageIcon("src/main/images/dog-blue.png").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_ELEPHANT_ICON = new ImageIcon(new ImageIcon("src/main/images/elephant-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_LEOPARD_ICON = new ImageIcon(new ImageIcon("src/main/images/leopard-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_LION_ICON = new ImageIcon(new ImageIcon("src/main/images/lion-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_RAT_ICON = new ImageIcon(new ImageIcon("src/main/images/rat-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_TIGER_ICON = new ImageIcon(new ImageIcon("src/main/images/tiger-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_WOLF_ICON = new ImageIcon(new ImageIcon("src/main/images/wolf-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));


    // Tiles
    private final Icon DEN_ICON = new ImageIcon(new ImageIcon("src/main/images/den.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon GRASS_ICON = new ImageIcon(new ImageIcon("src/main/images/grass.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon TRAP_ICON = new ImageIcon(new ImageIcon("src/main/images/trap.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon WATER_ICON = new ImageIcon(new ImageIcon("src/main/images/water.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

    private Game game;
    private JButton[][] buttons;
    private int[] selectedButton = null;
    private ArrayList<Location> currentlyHighlighted;

    public GamePage(GUI frame) {
        super(frame);

        game = new Game();
        currentlyHighlighted = new ArrayList<>();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(9, 7));
        buttonPanel.setPreferredSize(new Dimension(7*75, 9*75));
        buttons = new JButton[9][7];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                JButton button = new GameButton(i, j);
                button.setBorder(new LineBorder(Color.LIGHT_GRAY));
                button.addActionListener(this);
                buttons[i][j] = button;
                buttonPanel.add(button);
            }
        }

        add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Main Page");
        backButton.addActionListener(this);
        backButton.setActionCommand("back");
        add(backButton);

        resetBoard();
        updateBoard();
    }

    public void resetBoard() {
        // Fill the entire board with grass (some tiles will change in the next few lines)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 7; col++) {
                buttons[row][col].setIcon(GRASS_ICON);
            }
        }

        // Draw left river
        buttons[3][1].setIcon(WATER_ICON);
        buttons[3][2].setIcon(WATER_ICON);
        buttons[4][1].setIcon(WATER_ICON);
        buttons[4][2].setIcon(WATER_ICON);
        buttons[5][1].setIcon(WATER_ICON);
        buttons[5][2].setIcon(WATER_ICON);

        // Draw right river
        buttons[3][4].setIcon(WATER_ICON);
        buttons[3][5].setIcon(WATER_ICON);
        buttons[4][4].setIcon(WATER_ICON);
        buttons[4][5].setIcon(WATER_ICON);
        buttons[5][4].setIcon(WATER_ICON);
        buttons[5][5].setIcon(WATER_ICON);

        // Draw Dens
        buttons[0][3].setIcon(DEN_ICON);
        buttons[8][3].setIcon(DEN_ICON);

        // Draw Top Traps
        buttons[1][3].setIcon(TRAP_ICON);
        buttons[0][2].setIcon(TRAP_ICON);
        buttons[0][4].setIcon(TRAP_ICON);

        // Draw Bottom Traps
        buttons[7][3].setIcon(TRAP_ICON);
        buttons[8][2].setIcon(TRAP_ICON);
        buttons[8][4].setIcon(TRAP_ICON);
    }

    public void updateBoard() {
        resetBoard();

        for (int i = 0; i < 2; i++) {
            //Piece[] pieces = currPlayer.getValidPieces();
            ArrayList<Piece> pieces = new ArrayList<Piece>();
            for (Tile tile : game.getBoard().getBoard().values()) {
                if (tile.getPiece() != null){
                    pieces.add(tile.getPiece());
                }
            }

            for (int n = 0; n < pieces.size() ; n++) {
                Icon icon = null;

                switch (pieces.get(n).getName()) {
                    case "Cat":
                        if (pieces.get(n).getColor().equals("red")) {
                            icon = RED_CAT_ICON;
                        } else if (pieces.get(n).getColor().equals("blue")) {
                            icon = BLUE_CAT_ICON;
                        }
                        break;

                    case "Dog":
                        if (pieces.get(n).getColor().equals("red")) {
                            icon = RED_DOG_ICON;
                        } else if (pieces.get(n).getColor().equals("blue")) {
                            icon = BLUE_DOG_ICON;
                        }
                        break;

                    case "Elephant":
                        if (pieces.get(n).getColor().equals("red")) {
                            icon = RED_ELEPHANT_ICON;
                        } else if (pieces.get(n).getColor().equals("blue")) {
                            icon = BLUE_ELEPHANT_ICON;
                        }
                        break;

                    case "Leopard":
                        if (pieces.get(n).getColor().equals("red")) {
                            icon = RED_LEOPARD_ICON;
                        } else if (pieces.get(n).getColor().equals("blue")) {
                            icon = BLUE_LEOPARD_ICON;
                        }
                        break;

                    case "Lion":
                        if (pieces.get(n).getColor().equals("red")) {
                            icon = RED_LION_ICON;
                        } else if (pieces.get(n).getColor().equals("blue")) {
                            icon = BLUE_LION_ICON;
                        }
                        break;

                    case "Rat":
                        if (pieces.get(n).getColor().equals("red")) {
                            icon = RED_RAT_ICON;
                        } else if (pieces.get(n).getColor().equals("blue")) {
                            icon = BLUE_RAT_ICON;
                        }
                        break;

                    case "Tiger":
                        if (pieces.get(n).getColor().equals("red")) {
                            icon = RED_TIGER_ICON;
                        } else if (pieces.get(n).getColor().equals("blue")) {
                            icon = BLUE_TIGER_ICON;
                        }
                        break;

                    case "Wolf":
                        if (pieces.get(n).getColor().equals("red")) {
                            icon = RED_WOLF_ICON;
                        } else if (pieces.get(n).getColor().equals("blue")) {
                            icon = BLUE_WOLF_ICON;
                        }
                        break;
                }

                buttons[pieces.get(n).getLocation().getRow()][pieces.get(n).getLocation().getCol()].setIcon(icon);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("back")) {
            frame.changePageTo(new MainMenuPage(frame));
            return;
        }

        GameButton button = (GameButton) actionEvent.getSource();

        if (((LineBorder)button.getBorder()).getLineColor().equals(Color.BLACK)) {
            button.setBorder(new LineBorder(Color.LIGHT_GRAY));
            unhighlight();
            selectedButton = null;
        } else if (selectedButton != null) {
            //game.makeMoveUi(selectedButton[0], selectedButton[1], button.getRow(), button.getCol());
            game.makeMove(selectedButton[0], selectedButton[1], button.getRow(), button.getCol());
            updateBoard();
            buttons[selectedButton[0]][selectedButton[1]].setBorder(new LineBorder(Color.LIGHT_GRAY));
            selectedButton = null;
            unhighlight();

            if (game.winnerCheck() == 1) {
                game.endGame();
                frame.changePageTo(new GameEndPage(frame, false));
            } else if (game.winnerCheck() == 0) {
                game.endGame();
                frame.changePageTo(new GameEndPage(frame, true));
            }

        } else if (((LineBorder)button.getBorder()).getLineColor().equals(Color.LIGHT_GRAY)) {
            button.setBorder(new LineBorder(Color.BLACK));
            selectedButton = new int[]{button.getRow(), button.getCol()};

            ArrayList<Location> validDirectionButtons = game.getValidMoves(button.getRow(), button.getCol());
            for (int i = 0; i < validDirectionButtons.size(); ++i) {
                Location curr = validDirectionButtons.get(i);
                int row = curr.getRow();
                int col = curr.getCol();
                buttons[row][col].setBorder(new LineBorder(Color.BLUE));

                currentlyHighlighted.add(curr);
            }
        }
    }

    /**
     * Takes all highlighted squares are reverts them back to LIGHT_GRAY
     */
    public void unhighlight() {
        for (Location curr : currentlyHighlighted) {
            int row = curr.getRow();
            int col = curr.getCol();
            buttons[row][col].setBorder(new LineBorder(Color.LIGHT_GRAY)); // take back your voodoo magic of color;
        }

        currentlyHighlighted = new ArrayList<>();
    }
}
