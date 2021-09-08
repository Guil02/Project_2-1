package model;

import model.pieces.*;

public class BoardUpdater {

    private Board boardModel;

    public BoardUpdater(Board boardModel) {
        this.boardModel = boardModel;
    }

    public void fillGameStart() {
        // Black side
        addPiece(0, 0, new RookPiece(false));
        addPiece(0, 1, new KnightPiece(false));
        addPiece(0, 2, new BishopPiece(false));
        addPiece(0, 3, new QueenPiece(false));
        addPiece(0, 4, new KingPiece(false));
        addPiece(0, 5, new BishopPiece(false));
        addPiece(0, 6, new KnightPiece(false));
        addPiece(0, 7, new RookPiece(false));
        for (int i = 0; i < 8; i++)
            addPiece(1, i, new PawnPiece(false));

        // White side
        addPiece(7, 0, new RookPiece(true));
        addPiece(7, 1, new KnightPiece(true));
        addPiece(7, 2, new BishopPiece(true));
        addPiece(7, 3, new KingPiece(true));
        addPiece(7, 4, new QueenPiece(true));
        addPiece(7, 5, new BishopPiece(true));
        addPiece(7, 6, new KnightPiece(true));
        addPiece(7, 7, new RookPiece(true));
        for (int i = 0; i < 8; i++)
            addPiece(6, i, new PawnPiece(true));
    }

    public void addPiece(int x, int y, Piece piece) {
        boardModel.getBoardModel()[x][y] = piece;
    }

    public void removePiece() {

    }

    public void movePiece() {

    }
}
