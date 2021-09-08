package controller;

import model.Board;
import model.BoardUpdater;

/**
 * Application class which contains the executable method to start the game.
 */
public class Application {

    /**
     * Main method which gets executed upon start.
     * @param args
     */
    public static void main(String[] args) {
        Board board = new Board();
        BoardUpdater boardU = new BoardUpdater(board);
        boardU.fillGameStart();
        board.printBoard();
    }
}
