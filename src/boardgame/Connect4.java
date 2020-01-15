package boardgame;

import javax.swing.*;
import java.awt.*;

/**
 * Connect4 is a subclass of TurnBasedGame.
 * A simple implementation of the popular game: Connect 4.
 * Game played with two predefined players: RED and BLUE.
 */
public class Connect4 extends TurnBasedGame {

    // Stores the current value for each row in order to account for gravity
    private int [] yTill = {5, 5, 5, 5, 5, 5, 5};
    // String stores the name of the winner, namely RED or BLUE
    private String winner;

    /**
     * Calls upon the constructor of TurnBasedGame in order to set the player names and the board size.
     * The board size is fixed to a 7 x 6 board.
     */
    Connect4() {
        super(7, 6, "RED", "BLUE");
        this.setTitle("Connect 4");
    }

    /**
     * Initializes an array of un-clicked, unclaimed buttons. ( The buttons can be thought of as indiviual free slots
     * holdings pieces of different colors.
     */
    @Override
    protected void initGame() {
        for (int y = 0; y < yCount; y++) {
            for (int x = 0; x < xCount; x++) {
                pieces[x][y].setText(" ");
            }
        }
    }

    /**
     * An overridden class that defines the flow of each move. Gets the first available piece in the row clicked,
     * changes the color of the piece accordingly and checks if the addition of the piece results in an end game
     * situation by calling the checkEndGame() method.
     *
     * @param triggeredButton A JButton object with the reference of the button clicked by the user.
     */
    @Override
    protected void gameAction(JButton triggeredButton) {
        int x = Integer.parseInt((triggeredButton.getActionCommand().split(","))[0]);
        int y = yTill[x]--;
        if (yTill[x] < -1) {
            JOptionPane.showMessageDialog(null, "Invalid Move");
            yTill[x]++;
        }
        else {
            pieces[x][y].setText(currentPlayer);
            if (currentPlayer.equals("RED")) {
                pieces[x][y].setBackground(Color.RED);
                pieces[x][y].setOpaque(true);
            } else {
                pieces[x][y].setBackground(Color.BLUE);
                pieces[x][y].setOpaque(true);
            }
            addLineToOutput(currentPlayer + " piece at " + pieces[x][y].getActionCommand());

            checkEndGame(x, y);

            if (gameEnded) {
                addLineToOutput("Game ended!");
                JOptionPane.showMessageDialog(null, "Game ended!");
                System.exit(0);
            }

            changeTurn();
        }
    }

    /**
     * An overridden method called by the gameAction() method after every move is made by any player. Checks if the
     * addition of a certain piece resulted in either the a player having a 4-in-a-row combo or all the available pieces
     * being filled, in which case the game would be declared draw.
     *
     * @param buttonX the X coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @param buttonY the Y coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @return A boolean representing whether the game has ended or not.
     */
    @Override
    protected boolean checkEndGame(int buttonX, int buttonY) {
        if (connected4(buttonX, buttonY)) {
            gameEnded = true;
            addLineToOutput("Winner is " + winner + "!");
        }
        else {
            if (turn == xCount * yCount) {
                gameEnded = true;
                addLineToOutput("Draw Game!");
            }
        }
        return false;
    }

    /**
     * Called by the checkEndGame() method to check whether there exists a horizontal, vertical, or diagonal pattern of
     * 4 due to the addition of the new piece.
     *
     * @param buttonX the X coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @param buttonY the Y coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @return A boolean representing if a 4-in-a-row pattern exists
     */
    private boolean connected4(int buttonX, int buttonY) {
        if (connectedHorizontally(buttonX, buttonY)) {
            winner = currentPlayer;
            return true;
        }
        else if (connectedVertically(buttonX, buttonY)) {
            winner = currentPlayer;
            return true;
        }
        else if (connectedDiagonally(buttonX, buttonY)) {
            winner = currentPlayer;
            return true;
        }
        return false;
    }

    /**
     * Checks if the addition of the added piece resulted in a horizontal 4-in-a-row pattern for the currentPlayer.
     *
     * @param buttonX the X coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @param buttonY the Y coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @return A boolean representing if a 4-in-a-row horizontal pattern exists
     */
    private boolean connectedHorizontally(int buttonX, int buttonY) {
        JButton [] checkedButtons = new JButton[4];

        int matchCount = 0;
        int checkCount = 0;
        int index = 0;

        for (int i = buttonX; i >= 0; i--) {
            if (pieces[i][buttonY].getText().equals(currentPlayer)) {
                matchCount++;
                checkedButtons[index] = pieces[i][buttonY];
                index++;
                checkCount++;
                if (matchCount == 4 || checkCount == 4) {
                    break;
                }
            }
            else {
                break;
            }
        }
        if (checkCount < 4) {
            for (int i = buttonX + 1; i <= 6; i++) {
                if (pieces[i][buttonY].getText().equals(currentPlayer)) {
                    matchCount++;
                    checkedButtons[index] = pieces[i][buttonY];
                    index++;
                    checkCount++;
                    if (matchCount == 4 || checkCount == 4) {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
        if (matchCount == 4) {
            for (JButton button : checkedButtons) {
                button.setBackground(Color.YELLOW);
                button.setOpaque(true);
            }
        }
        return (matchCount == 4);
    }

    /**
     * Checks if the addition of the added piece resulted in a vertical 4-in-a-row pattern for the currentPlayer.
     *
     * @param buttonX the X coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @param buttonY the Y coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @return A boolean representing if a 4-in-a-row vertical pattern exists
     */
    private boolean connectedVertically(int buttonX, int buttonY) {
        if (buttonY > 2) {
            return false;
        }
        JButton [] checkedButtons = new JButton[4];
        int index = 0;
        int matchCount = 0;
        for (int i = buttonY; i < buttonY + 4; i++) {
            if (pieces[buttonX][i].getText().equals(currentPlayer)) {
                matchCount++;
                checkedButtons[index] = pieces[buttonX][i];
                index++;
                if (matchCount == 4) {
                    for (JButton button : checkedButtons) {
                        button.setBackground(Color.YELLOW);
                        button.setOpaque(true);
                    }
                    return true;
                }
                continue;
            }
            break;
        }
        return false;
    }

    /**
     * Checks if the addition of the added piece resulted in a diagonal 4-in-a-row pattern for the currentPlayer.
     * Achieves this by seperatly checking if a downwards sloping diagonal pattern exists and if an upwards sloping
     * diagonal pattern exists.
     *
     * @param buttonX the X coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @param buttonY the Y coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @return A boolean representing if a 4-in-a-row diagonal pattern exists
     */
    private boolean connectedDiagonally(int buttonX, int buttonY) {
        boolean connected;
        connected = connectedDiagonallySlopingDownwards(buttonX, buttonY);
        if (connected)
            return true;
        connected = connectedDiagonallySlopingUpwards(buttonX, buttonY);
        return connected;
    }

    /**
     * Checks if the addition of the added piece resulted in a diagonal downwards sloping 4-in-a-row pattern for the currentPlayer.
     *
     * @param buttonX the X coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @param buttonY the Y coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @return A boolean representing if a 4-in-a-row diagonal downwards sloping pattern exists
     */
    private boolean connectedDiagonallySlopingDownwards(int buttonX, int buttonY) {
        JButton [] checkedButtons = new JButton[4];
        int index = 0;
        int checkCount = 0;
        int matchCount = 0;

        int i, j;
        for (i = buttonX, j = buttonY; i <= 6 && j <= 5; i++, j++) {
            if (pieces[i][j].getText().equals(currentPlayer)) {
                matchCount++;
                checkedButtons[index++] = pieces[i][j];
                checkCount++;
                if (matchCount == 4 || checkCount == 4) {
                    break;
                }
            }
            else {
                break;
            }
        }
        if (checkCount < 4) {
            for (i = buttonX - 1, j = buttonY - 1; i >= 0 && j >= 0; i--, j--) {
                if (pieces[i][j].getText().equals(currentPlayer)) {
                    matchCount++;
                    checkedButtons[index++] = pieces[i][j];
                    checkCount++;
                    if (matchCount == 4 || checkCount == 4) {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
        if (matchCount == 4) {
            for (JButton button : checkedButtons) {
                button.setBackground(Color.YELLOW);
                button.setOpaque(true);
            }
        }
        return (matchCount == 4);
    }

    /**
     * Checks if the addition of the added piece resulted in a diagonal upwards sloping 4-in-a-row pattern for the currentPlayer.
     *
     * @param buttonX the X coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @param buttonY the Y coordinate of the piece intended by the user (Not the coordinate of the button pressed)
     * @return A boolean representing if a 4-in-a-row diagonal upwards sloping pattern exists
     */
    private boolean connectedDiagonallySlopingUpwards(int buttonX, int buttonY) {
        JButton [] checkedButtons = new JButton[4];
        int index = 0;
        int checkCount = 0;
        int matchCount = 0;

        int i, j;
        for (i = buttonX, j = buttonY; i >= 0 && j <= 5; i--, j++) {
            if (pieces[i][j].getText().equals(currentPlayer)) {
                matchCount++;
                checkedButtons[index++] = pieces[i][j];
                checkCount++;
                if (matchCount == 4 || checkCount == 4) {
                    break;
                }
            }
            else {
                break;
            }
        }
        if (checkCount < 4) {
            for (i = buttonX + 1, j = buttonY - 1; i <= 6 && j >= 0; i++, j--) {
                if (pieces[i][j].getText().equals(currentPlayer)) {
                    matchCount++;
                    checkedButtons[index++] = pieces[i][j];
                    checkCount++;
                    if (matchCount == 4 || checkCount == 4) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (matchCount == 4) {
            for (JButton button : checkedButtons) {
                button.setBackground(Color.YELLOW);
                button.setOpaque(true);
            }
        }
        return (matchCount == 4);
    }

    // Execution begins.
    public static void main(String[] args) {
        Connect4 connect4 = new Connect4();
    }
}