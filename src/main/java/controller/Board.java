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
        rollTheDice();
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

    /**
     * Executes the dice roll and checks if the result has a possible move.
     * @return  char - the character of the kind of piece that can be moved
     */
    public void rollTheDice(){
        runValidMoves();
        ArrayList<Character> movablePieces = new ArrayList<>();
        for(ChessPiece[] pieceArray : boardModel){
            for(ChessPiece piece : pieceArray){
                if(piece!=null && piece.hasValidMove() && (getWhiteMove()==piece.isWhite())){
                    char charToAdd = piece.getPieceChar();
                    addCharToArray(movablePieces, charToAdd);
                }
            }
        }
        movablePiece = Dice.choosePiece(movablePieces);
    }

    public ArrayList<Character> getMovablePieces(){
        runValidMoves();
        ArrayList<Character> movablePieces = new ArrayList<>();
        for(ChessPiece[] pieceArray : boardModel){
            for(ChessPiece piece : pieceArray){
                if(piece!=null && piece.hasValidMove() && (getWhiteMove()==piece.isWhite())){
                    char charToAdd = piece.getPieceChar();
                    addCharToArray(movablePieces, charToAdd);
                }
            }
        }
        return movablePieces;
    }

    /**
     * Support method to add a piece to the set of movable pieces.
     * @see #rollTheDice()
     * @param movablePieces     set of current pieces that can be moved
     * @param charToAdd         piece to add
     */
    private void addCharToArray(ArrayList<Character> movablePieces, char charToAdd) {
        if(!movablePieces.contains(charToAdd)){
            movablePieces.add(charToAdd);
        }
    }

    /**
     * Support method to run all the valid moves.
     * @see #rollTheDice()
     */
    public void runValidMoves(){
        for(ChessPiece[] piecesArray : boardModel){
            for(ChessPiece piece : piecesArray){
                if(piece!=null){
                    if(getWhiteMove()){
                        if(piece.isWhite()) {
                            piece.validMoves(this);
                        }
                    }
                    else{
                        if(!piece.isWhite()){
                            piece.validMoves(this);
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the kind of piece that can be moved in the first move.
     * Only knight and pawn are possible options.
     * @return  char - kind of piece to move
     */
    public void firstMoveDiceRoll(){
        double r = Math.random();
        if(r<0.5){
            movablePiece = 'N';
        }
        else
            movablePiece = 'P';
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
