package com.marcelfiore.jungle_ai.game.gui;

import com.marcelfiore.jungle_ai.game.jungle.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePage extends Page implements ActionListener {
    // Blue Pieces
    private final Icon RED_CAT_ICON = new ImageIcon(new ImageIcon("src/Images/cat-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_DOG_ICON = new ImageIcon(new ImageIcon("src/Images/dog-red.png").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_ELEPHANT_ICON = new ImageIcon(new ImageIcon("src/Images/elephant-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_LEOPARD_ICON = new ImageIcon(new ImageIcon("src/Images/leopard-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_LION_ICON = new ImageIcon(new ImageIcon("src/Images/lion-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_RAT_ICON = new ImageIcon(new ImageIcon("src/Images/rat-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_TIGER_ICON = new ImageIcon(new ImageIcon("src/Images/tiger-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon RED_WOLF_ICON = new ImageIcon(new ImageIcon("src/Images/wolf-red.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

    // Red Pieces
    private final Icon BLUE_CAT_ICON = new ImageIcon(new ImageIcon("src/Images/cat-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_DOG_ICON = new ImageIcon(new ImageIcon("src/Images/dog-blue.png").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_ELEPHANT_ICON = new ImageIcon(new ImageIcon("src/Images/elephant-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_LEOPARD_ICON = new ImageIcon(new ImageIcon("src/Images/leopard-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_LION_ICON = new ImageIcon(new ImageIcon("src/Images/lion-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_RAT_ICON = new ImageIcon(new ImageIcon("src/Images/rat-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_TIGER_ICON = new ImageIcon(new ImageIcon("src/Images/tiger-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon BLUE_WOLF_ICON = new ImageIcon(new ImageIcon("src/Images/wolf-blue.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));


    // Tiles
    private final Icon DEN_ICON = new ImageIcon(new ImageIcon("src/Images/den.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon GRASS_ICON = new ImageIcon(new ImageIcon("src/Images/grass.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon TRAP_ICON = new ImageIcon(new ImageIcon("src/Images/trap.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    private final Icon WATER_ICON = new ImageIcon(new ImageIcon("src/Images/water.jpg").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

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
            Player currPlayer = game.getPlayer(i);
            Piece[] pieces = currPlayer.getValidPieces();

            for (int n = 0; n < pieces.length; n++) {
                Icon icon = null;

                switch (pieces[n].getName()) {
                    case "Cat":
                        if (pieces[n].getColor().equals("red")) {
                            icon = RED_CAT_ICON;
                        } else if (pieces[n].getColor().equals("blue")) {
                            icon = BLUE_CAT_ICON;
                        }
                        break;

                    case "Dog":
                        if (pieces[n].getColor().equals("red")) {
                            icon = RED_DOG_ICON;
                        } else if (pieces[n].getColor().equals("blue")) {
                            icon = BLUE_DOG_ICON;
                        }
                        break;

                    case "Elephant":
                        if (pieces[n].getColor().equals("red")) {
                            icon = RED_ELEPHANT_ICON;
                        } else if (pieces[n].getColor().equals("blue")) {
                            icon = BLUE_ELEPHANT_ICON;
                        }
                        break;

                    case "Leopard":
                        if (pieces[n].getColor().equals("red")) {
                            icon = RED_LEOPARD_ICON;
                        } else if (pieces[n].getColor().equals("blue")) {
                            icon = BLUE_LEOPARD_ICON;
                        }
                        break;

                    case "Lion":
                        if (pieces[n].getColor().equals("red")) {
                            icon = RED_LION_ICON;
                        } else if (pieces[n].getColor().equals("blue")) {
                            icon = BLUE_LION_ICON;
                        }
                        break;

                    case "Rat":
                        if (pieces[n].getColor().equals("red")) {
                            icon = RED_RAT_ICON;
                        } else if (pieces[n].getColor().equals("blue")) {
                            icon = BLUE_RAT_ICON;
                        }
                        break;

                    case "Tiger":
                        if (pieces[n].getColor().equals("red")) {
                            icon = RED_TIGER_ICON;
                        } else if (pieces[n].getColor().equals("blue")) {
                            icon = BLUE_TIGER_ICON;
                        }
                        break;

                    case "Wolf":
                        if (pieces[n].getColor().equals("red")) {
                            icon = RED_WOLF_ICON;
                        } else if (pieces[n].getColor().equals("blue")) {
                            icon = BLUE_WOLF_ICON;
                        }
                        break;
                }

                buttons[pieces[n].getRow()][pieces[n].getCol()].setIcon(icon);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        GameButton button = (GameButton) actionEvent.getSource();

        if (((LineBorder)button.getBorder()).getLineColor().equals(Color.BLACK)) {
            button.setBorder(new LineBorder(Color.LIGHT_GRAY));
            unhighlight();
            selectedButton = null;
        } else if (selectedButton != null) {
            game.makeMoveUi(selectedButton[0], selectedButton[1], button.getRow(), button.getCol());
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

            ArrayList<Location> validDirectionButtons = game.retrieveValidLocations(button.getRow(), button.getCol());
            int stop = validDirectionButtons.size();
            for (int i = 0; i < stop; ++i) {
                Location curr = validDirectionButtons.remove(0);
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
