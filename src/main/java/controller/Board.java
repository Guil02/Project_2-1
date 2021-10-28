package controller;

import model.pieces.ChessPiece;

import java.util.ArrayList;

public class Board {
    private static final int BOARDSIZE = 8;
    private ChessPiece[][] boardModel = new ChessPiece[BOARDSIZE][BOARDSIZE];
    private GraphicsConnector graphicsConnector;
    private boolean gameOver;
    private boolean whiteMove = true;
    private char movablePiece;
    public static final boolean GUI_ON = true;

    public Board() {
    }

    private Board(ChessPiece[][] boardModel, GraphicsConnector graphicsConnector, boolean gameOver, boolean whiteMove, char movablePiece){
        this.gameOver = gameOver;
        this.whiteMove = whiteMove;
        this.movablePiece = movablePiece;
        this.graphicsConnector = graphicsConnector;
        this.boardModel = boardModel;

    }

    public void changeTurn(){
        whiteMove = !whiteMove;
        Dice.rollTheDice(this);
    }

    public char getCharOffField(int x, int y){
        if(getPieceOffField(x,y) == null){
            return '-';
        }
        return getPieceOffField(x,y).getPieceChar();
    }
    public static int getBoardSize() {
        return BOARDSIZE;
    }

    public ChessPiece[][] getBoardModel() {
        return boardModel;
    }

    public ChessPiece getPieceOffField(int x, int y) {
        return boardModel[x][y];
    }

    public void setGameOver(boolean bool) {
        gameOver = bool;
    }

    public boolean getGameOver(){ return gameOver;}

    public GraphicsConnector getGraphicsConnector() {
        return graphicsConnector;
    }

    public void setGraphicsConnector(GraphicsConnector graphicsConnector) {
        this.graphicsConnector = graphicsConnector;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean getWhiteMove() {
        return whiteMove;
    }

    public char getMovablePiece() {
        return movablePiece;
    }

    public void setMovablePiece(char movablePiece) {
        this.movablePiece = movablePiece;
    }



    @Override
    public Board clone() {
        ChessPiece[][] copy = new ChessPiece[BOARDSIZE][BOARDSIZE];
        for (int i = 0; i<BOARDSIZE; i++){
            for(int j = 0; j<BOARDSIZE; j++){
                if(boardModel[i][j]!=null){
                    copy[i][j]=boardModel[i][j].copy();
                }
            }
        }
        Board board = new Board(copy,graphicsConnector, gameOver, whiteMove, movablePiece);
        return board;
    }
}
