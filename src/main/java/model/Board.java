package model;

import model.pieces.Piece;

public class Board {


    private Piece[][] boardModel;

    public final static int BOARDSIZE = 8;

    public Board() {
        boardModel = new Piece[8][8];
    }

    public Piece[][] getField() {
        return boardModel;
    }

    public void printBoard() {
        System.out.println("--- Board State ---\n");
        for (int i = 0; i < boardModel.length; i++) {
            for (int j = 0; j < boardModel[0].length; j++) {
                System.out.print("[ " + getCharOfField(i, j) + " ] ");
            }
            System.out.println();
        }
    }

    public char getCharOfField(int x, int y) {
        if (boardModel[x][y] == null) {
            return '-';
        }
        else {
            return boardModel[x][y].getPieceChar();
        }
    }

}
