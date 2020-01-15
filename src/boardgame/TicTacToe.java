package boardgame;

import java.awt.Color;

/**
 * TicTacToe is a subclass of TurnBasedGame.
 * A simple implementation of the popular game: Tic Tac Toe.
 * Game played with two predefined players: X and O.
 */

public class TicTacToe extends TurnBasedGame {

    String winner;

    public TicTacToe() {
        super(3, 3, "X", "O");
        this.setTitle("Tic Tac Toe");
    }

    @Override
    protected void initGame() {
        for (int y = 0; y < yCount; y++)
            for (int x = 0; x < xCount; x++)
                pieces[x][y].setText(" ");
    }

    @Override
    protected boolean checkEndGame(int buttonX, int buttonY) {
        // short-circuit OR checkings
        if (checkConnect3(0,0, 0,1, 0,2) ||
                checkConnect3(1,0, 1,1, 1,2) ||
                checkConnect3(2,0, 2,1, 2,2) ||

                checkConnect3(0,0, 1,0, 2,0) ||
                checkConnect3(0,1, 1,1, 2,1) ||
                checkConnect3(0,2, 1,2, 2,2) ||

                checkConnect3(0,0, 1,1, 2,2) ||
                checkConnect3(0,2, 1,1, 2,0)) {
            gameEnded = true;
            addLineToOutput("Winner is " + winner + "!");
        } else if (turn == xCount * yCount) {
            gameEnded = true;
            addLineToOutput("Draw game!");
        }
        return gameEnded;
    }

    protected boolean checkConnect3(int x0, int y0, int x1, int y1, int x2, int y2) {
        if (pieces[x0][y0].getText().equals(currentPlayer) &&
                pieces[x1][y1].getText().equals(currentPlayer) &&
                pieces[x2][y2].getText().equals(currentPlayer)) {
            winner = currentPlayer;
            pieces[x0][y0].setBackground(Color.YELLOW);
            pieces[x1][y1].setBackground(Color.YELLOW);
            pieces[x2][y2].setBackground(Color.YELLOW);
            pieces[x0][y0].setOpaque(true);
            pieces[x1][y1].setOpaque(true);
            pieces[x2][y2].setOpaque(true);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        TicTacToe ttt;
        ttt = new TicTacToe();
        ttt.setLocation(400, 200);
        ttt.verbose = false;
    }
}
