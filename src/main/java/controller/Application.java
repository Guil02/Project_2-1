package controller;

import model.Board;
import model.BoardUpdater;

public class Application {
    public static void main(String[] args) {
        Board board = new Board();
        BoardUpdater boardU = new BoardUpdater(board);
        boardU.fillGameStart();
        board.printBoard();
    }
}
