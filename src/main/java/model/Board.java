package model;

import controller.GameRunner;
import model.pieces.ChessPiece;
import java.util.*;

/**
 * class representing the chess board
 */
public class Board {
    // variables
    private static final int BOARDSIZE = 8;
    private ChessPiece[][] boardModel;
    private BoardUpdater boardUpdater;
    private GameRunner gameRunner;
    private int enPassantAuthorized = -1;
    private boolean enPassant = false;

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    /**
     * board constructor
     * @param gameRunner connect the board with the game runner
     */
    public Board(GameRunner gameRunner) {
        this.gameRunner=gameRunner;
        boardModel = new ChessPiece[BOARDSIZE][BOARDSIZE];
    }

    private ArrayList<ChessPiece> whitePieces = new ArrayList<ChessPiece>();
    private ArrayList<ChessPiece> blackPieces = new ArrayList<ChessPiece>();

    /**
     * method that distinguish white from black pieces and store their data into separate Arraylists
     * @param piece can be either white or black
     */
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
    }
    public ChessPiece[][] getField() {
        return boardModel;
    }

    public int getEnPassantAuthorized(){
        return enPassantAuthorized;
    }

    public void setEnPassantAuthorized(int enPassantAuthorized) {
        this.enPassantAuthorized = enPassantAuthorized;
    }

    /**
     * method that allow to get the piece position on the board
     * @param index_x
     * @param index_y
     * @return piece position within the board
     */
    public ChessPiece getPiece(int index_x, int index_y) {
        return boardModel[index_x][index_y];
    }

    /**
     * method that assigns respective chars to corresponding chess piece on every field of the board
     * @param x
     * @param y
     * @return the current field in chars
     */
    public char getCharOfField(int x, int y) {
        if (boardModel[x][y] == null) {
            return '-';
        }
        else {
            return boardModel[x][y].getPieceChar();
        }
    }

    /**
     * representation of the state of the board and the positions of its pieces in a certain way that each piece (represented by its char) is viewable in the terminal
     */
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
