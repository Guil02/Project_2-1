package controller;

import model.Board;
import model.BoardUpdater;
import model.pieces.BishopPiece;

public class Application {
    public static void main(String[] args) {
        Board board = new Board();
        BoardUpdater boardU = new BoardUpdater(board);
        boardU.addPiece(0, 0, new BishopPiece());
        board.printBoard();
    }
}
