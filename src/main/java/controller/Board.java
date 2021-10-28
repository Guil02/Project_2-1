package controller;

import model.pieces.ChessPiece;

public class Board {
    private static final int BOARDSIZE = 8;
    private ChessPiece[][] boardModel = new ChessPiece[BOARDSIZE][BOARDSIZE];
    private GraphicsConnector graphicsConnector;
    private GameRunner gameRunner;
    private boolean gameOver;
    private boolean whiteMove = true;
    private char movablePiece;
    public static final boolean GUI_ON = true;
    private int player1 = 0;
    private int player2 = 0;
    private boolean isOriginal = false;

    public Board(GameRunner gameRunner) {
        isOriginal = true;
        this.gameRunner = gameRunner;
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
        checkAi();
    }

    public void checkAi() {
        if(whiteMove){
            if(player1>0){
                gameRunner.doAiMove(this, player1);
            }
        }
        else{
            if(player2 > 0){
                gameRunner.doAiMove(this, player2);
            }
        }
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

    public void setWhiteMove(boolean whiteMove) {
        this.whiteMove = whiteMove;
    }

    public char getMovablePiece() {
        return movablePiece;
    }

    public void setMovablePiece(char movablePiece) {
        this.movablePiece = movablePiece;
    }

    public void setPlayers(int player1, int player2){
        System.out.println("playerOne: "+player1+"\nplayerTwo: "+player2);
        this.player1 = player1;
        this.player2 = player2;
    }

    public int getPlayer1() {
        return player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public void setOriginal(boolean original) {
        isOriginal = original;
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
