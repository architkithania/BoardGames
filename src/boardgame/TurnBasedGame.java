package boardgame;

import javax.swing.*;

/**
 * TurnBasedGame is a subclass of BoardGame.
 */
public class TurnBasedGame extends BoardGame {

    protected String player1;
    protected String player2;
    protected String currentPlayer;
    protected int turn;
    protected boolean gameEnded;

    /**
     * TurnBasedGame default constructor of a 4x4 game.
     */
    public TurnBasedGame() {
        this(4, 4, "Player 1", "Player 2");
    }

    /**
     * TurnBasedGame constructor.
     * @param xCount is number of columns (width)
     * @param yCount is number of rows (height)
     * @param player1 is name of player 1
     * @param player2 is name of player 2
     */
    public TurnBasedGame(int xCount, int yCount, String player1, String player2) {
        super(xCount, yCount);
        this.player1 = player1;
        this.player2 = player2;
        this.setTitle("TurnBasedGame " + xCount + "x" + yCount + ": " + player1 + " vs " + player2);
        turn = 0;
        gameEnded = false;
        changeTurn();
    }

    final protected int changeTurn() {
        turn++;
        if (turn % 2 == 1)
            currentPlayer = player1;
        else
            currentPlayer = player2;
        addLineToOutput("Turn " + turn + " Current Player: " + currentPlayer);
        return turn;
    }

    @Override
    protected void gameAction(JButton triggeredButton) {
        triggeredButton.setEnabled(false);
        triggeredButton.setText(currentPlayer);
        addLineToOutput(currentPlayer + " piece at " + triggeredButton.getActionCommand());

        String[] coord = triggeredButton.getActionCommand().split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);

        checkEndGame(x, y);

        if (gameEnded) {
            addLineToOutput("Game ended!");
            JOptionPane.showMessageDialog(null, "Game ended!");
            System.exit(0);
        }

        changeTurn();
    }

    @Override
    protected void initGame() {
        for (int y = 0; y < yCount; y++)
            for (int x = 0; x < xCount; x++)
                pieces[x][y].setText("?");
    }

    protected boolean checkEndGame(int buttonX, int buttonY) {
        gameEnded = true;
        for (int y = 0; y < yCount; y++)
            for (int x = 0; x < xCount; x++)
                if (pieces[x][y].getText().equals("?")) {
                    gameEnded = false;
                    return gameEnded;
                }
        addLineToOutput("All pieces filled!");
        return gameEnded;
    }


    /**
     * TurnBasedGame main() DEMO
     * @param args
     */
    public static void main(String[] args) {
        TurnBasedGame tbg;
        tbg = new TurnBasedGame();
        tbg.verbose = true;

        tbg = new TurnBasedGame(3, 3, "Player A", "Player B");
        tbg.setLocation(400, 200);
        tbg.verbose = false;
    }
}