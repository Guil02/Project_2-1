package model;

import controller.GameRunner;
import model.pieces.ChessPiece;
import java.util.*;

public class Board {

    private static final int BOARDSIZE = 8;
    private ChessPiece[][] boardModel;
    private BoardUpdater boardUpdater;
    private GameRunner gameRunner;
    private int enPassantAuthorized = -1;

    public Board(GameRunner gameRunner) {
        this.gameRunner=gameRunner;
        boardModel = new ChessPiece[BOARDSIZE][BOARDSIZE];
    }

    private ArrayList<ChessPiece> whitePieces = new ArrayList<ChessPiece>();
    private ArrayList<ChessPiece> blackPieces = new ArrayList<ChessPiece>();
    public void addBlackOrWhite(ChessPiece piece) {
        if(piece.isWhite()) {
            whitePieces.add(piece);
        }
        else {
            blackPieces.add(piece);
        }
    }
    public ArrayList<ChessPiece> getWhitePieces(){ return whitePieces; }
    public ArrayList<ChessPiece> getBlackPieces(){ return blackPieces; }

    public static int getBoardSize() {
        return BOARDSIZE;
    }

    public ChessPiece[][] getBoardModel() {
        return boardModel;
    } //TODO: same methods ?
    public ChessPiece[][] getField() {
        return boardModel;
    }

    public int getEnPassantAuthorized(){
        return enPassantAuthorized;
    }

    public void setEnPassantAuthorized(int enPassantAuthorized) {
        this.enPassantAuthorized = enPassantAuthorized;
    }

    public ChessPiece getPiece(int index_x, int index_y) {
        return boardModel[index_x][index_y];
    }

    public char getCharOfField(int x, int y) {
        if (boardModel[x][y] == null) {
            return '-';
        }
        else {
            return boardModel[x][y].getPieceChar();
        }
    }

    public void printBoard() {
        System.out.println("--- Board State ---\n");
        for(int i = 0; i < boardModel[0].length; i++) {
            for (int j = 0; j < boardModel.length; j++) {
                System.out.print("[ " + getCharOfField(j, i) + " ] ");
                // System.out.print("[ " + j + " " + i + " ] ");
            }
            System.out.println();
        }
    }

    public BoardUpdater getBoardUpdater() {
        return boardUpdater;
    }

    public void setBoardUpdater(BoardUpdater boardUpdater) {
        this.boardUpdater = boardUpdater;
    }

    public boolean getWhiteMove(){
        return gameRunner.getWhiteMove();
    }

    public GameRunner getGameRunner() {
        return this.gameRunner;
    }

    public void doMove(){
        gameRunner.doMove();
    }
}
