package model;

import model.pieces.Piece;

import java.util.Arrays;

public class Board {

    /*
    Indexes:


    [0,0]
     */
    private Piece[][] boardModel;

    public Board() {
        boardModel = new Piece[8][8];
    }

    public Piece[][] getField() {
        return boardModel;
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

    public char getCharOfField(int x, int y) {
        if (boardModel[x][y] == null) {
            return '-';
        }
        else {
            return boardModel[x][y].getPieceChar();
        }
    }

}
