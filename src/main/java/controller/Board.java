package controller;

import config.Config;
import model.pieces.ChessPiece;
import model.player.*;
import utils.FenEvaluator;

import java.util.ArrayList;

public class Board {
    private static final int BOARDSIZE = 8;
    public static boolean hasMoved = false;
    private ChessPiece[][] boardModel = new ChessPiece[BOARDSIZE][BOARDSIZE];
    private GraphicsConnector graphicsConnector;
    private GameRunner gameRunner;
    private boolean gameOver;
    private boolean whiteMove = true;
    private char movablePiece;
    private int player1 = 0;
    private int player2 = 0;
    Player playerOne;
    Player playerTwo;
    private boolean isOriginal = false;
    private int amountOfTurns = 1;
    private ArrayList<String> moves;
    private boolean enPassantActive = false;
    private int enPassantColumn = 0;
    public int plyCount = 0;

    public Board(GameRunner gameRunner) {
        isOriginal = true;
        this.gameRunner = gameRunner;
        moves = new ArrayList<>();
    }

    public Board() {
        isOriginal = true;
        moves = new ArrayList<>();
    }

    private Board(ChessPiece[][] boardModel, GraphicsConnector graphicsConnector, boolean gameOver, boolean whiteMove, char movablePiece){
        this.gameOver = gameOver;
        this.whiteMove = whiteMove;
        this.movablePiece = movablePiece;
        this.graphicsConnector = graphicsConnector;
        this.boardModel = boardModel;
        moves = new ArrayList<>();
    }

    public void changeTurn(){
        amountOfTurns++;
        whiteMove = !whiteMove;

        Dice.rollTheDice(this);
        if(!gameOver){
            checkAi();
        }
    }

    public void checkAi() {
        if(whiteMove){
            if(player1>0){
                if(player1==1){
                    ((SearchAgent) playerOne).launch(this);
                }
                else if(player1==2){
                    ((BaselineAgent) playerOne).launch(this);
                }
                else if(player1 == 3){
                    ((TDLearningAgent) playerOne).launch(this);
                }
                else if(player1 == 4){
                    ((TakeAgent) playerOne).launch(this);
                }
                else if(player1 == 5){
                    ((NNAgent) playerOne).launch(this);
                }
            }
        }
        else{
            if(player2 > 0){
                if(player2==1){
                    ((SearchAgent) playerTwo).launch(this);
                }
                else if(player2==2){
                    ((BaselineAgent) playerTwo).launch(this);
                }
                else if(player2==3){
                    ((TDLearningAgent) playerTwo).launch(this);
                }
                else if(player2==4){
                    ((TakeAgent) playerTwo).launch(this);
                }
                else if(player2==5){
                    ((NNAgent) playerTwo).launch(this);
                }
            }
        }
    }

    public int getAmountOfPieces(char c){
        int count = 0;
        ChessPiece[][] model = getBoardModel();
        for(int i=0; i<getBoardSize(); i++){
            for(int j=0; j<getBoardSize(); j++){
                if(model[i][j] != null &&model[i][j].getPieceChar()==c){
                    count++;
                }
            }
        }
        return count;
    }

    public ArrayList<ChessPiece> getPieces(char c){
        ArrayList<ChessPiece> list = new ArrayList<>();
        ChessPiece[][] model = getBoardModel();
        for(int i=0; i<getBoardSize(); i++){
            for(int j=0; j<getBoardSize(); j++){
                if(model[i][j] != null && model[i][j].getPieceChar()==c){
                    list.add(model[i][j]);
                }
            }
        }
        return list;
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
        board.gameRunner = this.gameRunner;
        return board;
    }

    public void launchGuiUpdate(){
        if(Config.GUI_ON){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            getGameRunner().debugWindowStage.incrementPlyCount();
            graphicsConnector.updateImages();
            graphicsConnector.changeTurn();
        }
    }

    public boolean isHumanPlayer(){
        if(whiteMove){
            return player1==0;
        }
        else{
            return player2==0;
        }
    }

    public void setPlayerPlayers(Player player1, Player player2) {
        this.playerOne = player1;
        this.playerTwo = player2;
    }

    public void storeMove(){
        moves.add(FenEvaluator.write(this));
    }

    public ArrayList<String> getBoardStates() {
        return moves;
    }

    public boolean isEnPassantActive() {
        return enPassantActive;
    }

    public void setEnPassantActive(boolean enPassantActive) {
        this.enPassantActive = enPassantActive;
    }

    public int getEnPassantColumn() {
        return enPassantColumn;
    }

    public void setEnPassantColumn(int enPassantColumn) {
        this.enPassantColumn = enPassantColumn;
    }

    public boolean containsKing(boolean white){
        for(int i = 0; i<BOARDSIZE; i++){
            for(int j = 0; j<BOARDSIZE; j++){
                if(white){
                    if(getCharOffField(i,j)=='K'){
                        return true;
                    }
                }
                else{
                    if (getCharOffField(i, j) == 'k') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public GameRunner getGameRunner() {
        return gameRunner;
    }

    public void setAmountOfTurns(int amountOfTurns) {
        this.amountOfTurns = amountOfTurns;
    }

    public int getAmountOfTurns() {
        return amountOfTurns;
    }

    public void movesClear() {
        moves.clear();
    }
}