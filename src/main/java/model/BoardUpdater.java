package model;

import model.pieces.Piece;

public class BoardUpdater {

    private Board boardModel;

    public BoardUpdater(Board boardModel) {
        this.boardModel = boardModel;
    }

    public void fillGameStart() {

    }

    public void addPiece(int x, int y, Piece piece) {
        boardModel.getBoardModel()[x][y] = piece;
    }

    public void removePiece() {

    }

    public void movePiece() {

    }
}
