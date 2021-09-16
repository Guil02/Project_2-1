package model;

import model.pieces.ChessPiece;

public class Board {

    private ChessPiece[][] boardModel;
    private BoardUpdater boardUpdater;

    public Board() {
        boardModel = new ChessPiece[8][8];
    }

    public ChessPiece[][] getField() {
        return boardModel;
    }

    public ChessPiece getPiece(int index_x, int index_y) {
        return boardModel[index_x][index_y];
    }

    public void printBoard() {
        System.out.println("--- Board State ---\n");
        for (int i = 0; i < boardModel[0].length; i++) {
            for (int j = 0; j < boardModel.length; j++) {
                System.out.print("[ " + getCharOfField(j, i) + " ] ");
                // System.out.print("[ " + j + " " + i + " ] ");
            }
            System.out.println();
        }


    }

    public void setBoardUpdater(BoardUpdater boardUpdater) {
        this.boardUpdater = boardUpdater;
    }

    public BoardUpdater getBoardUpdater() {
        return boardUpdater;
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
