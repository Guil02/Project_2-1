package controller;

import config.Config;
import model.pieces.ChessPiece;
import model.player.*;
import utils.FenEvaluator;
import java.util.ArrayList;

/**
 * Class representing a board state of the game.
 */
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

    /**
     * Constructor
     * @param gameRunner control unit of the board
     */
    public Board(GameRunner gameRunner) {
        isOriginal = true;
        this.gameRunner = gameRunner;
        moves = new ArrayList<>();
    }

    /**
     * Default constructor
     */
    public Board() {
        isOriginal = true;
        moves = new ArrayList<>();
    }

    /**
     * Private constructor
     * @param boardModel model of the board represented in a two-dimensional array of ChessPieces
     * @param graphicsConnector connected graphicsConnector
     * @param gameOver boolean that indicates whether the game is over or not
     * @param whiteMove indicates if white can make a move now or black
     * @param movablePiece the piece that can be moved in this ply determined by the dice
     */
    private Board(ChessPiece[][] boardModel, GraphicsConnector graphicsConnector, boolean gameOver, boolean whiteMove, char movablePiece){
        this.gameOver = gameOver;
        this.whiteMove = whiteMove;
        this.movablePiece = movablePiece;
        this.graphicsConnector = graphicsConnector;
        this.boardModel = boardModel;
        moves = new ArrayList<>();
    }

    /**
     * Switch turn from one player to the opponent
     */
    public void changeTurn(){
        amountOfTurns++;
        whiteMove = !whiteMove;

        Dice.rollTheDice(this);
        if(!gameOver){
            checkAi();
        }
    }

    /**
     * Check which player got selected
     */
    public void checkAi() {
        if(whiteMove){
            if(player1 > 0){
                if(player1 == 1){
                    ((SearchAgent) playerOne).launch(this);
                }
                else if(player1 == 2){
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
                if(player2 == 1){
                    ((SearchAgent) playerTwo).launch(this);
                }
                else if(player2 == 2){
                    ((BaselineAgent) playerTwo).launch(this);
                }
                else if(player2 == 3){
                    ((TDLearningAgent) playerTwo).launch(this);
                }
                else if(player2 == 4){
                    ((TakeAgent) playerTwo).launch(this);
                }
                else if(player2 == 5){
                    ((NNAgent) playerTwo).launch(this);
                }
            }
        }
    }

    /**
     * Returns the amount of pieces on the board of a given piece-type.
     * @param c Piece-type in the default notation
     * @return number of pieces with the specified char
     */
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

    /**
     * Returns an ArrayList of pieces according to the input char.
     * @param c Piece-type in the default notation
     * @return Arraylist with all pieces of that char
     */
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

    /**
     * Gets the char of a piece depending on the position on the board.
     * @param x x-Pos on the board
     * @param y y-Pos on the board
     * @return char corresponding to the piece on the specified field
     */
    public char getCharOffField(int x, int y){
        if(getPieceOffField(x,y) == null){
            return '-';
        }
        return getPieceOffField(x,y).getPieceChar();
    }

    /**
     * @return size of the board (8 for a normal chessboard representing 8x8)
     */
    public static int getBoardSize() {
        return BOARDSIZE;
    }

    /**
     * @return model of the board as a two-dimensional array of ChessPieces
     */
    public ChessPiece[][] getBoardModel() {
        return boardModel;
    }

    /**
     * @param x x-Pos
     * @param y y-Pos
     * @return gets the ChessPiece off a certain position
     */
    public ChessPiece getPieceOffField(int x, int y) {
        return boardModel[x][y];
    }

    /**
     * Sets the current board to a state that indicates that the game is over.
     * @param bool true, if the game is over
     */
    public void setGameOver(boolean bool) {
        gameOver = bool;
    }

    /**
     * @return whether the game is over or not
     */
    public boolean getGameOver(){
        return gameOver;
    }

    /**
     * @return current instance of the GraphicsConnector
     */
    public GraphicsConnector getGraphicsConnector() {
        return graphicsConnector;
    }

    /**
     * Sets the GraphicsConnector to an instance
     * @param graphicsConnector current instance
     */
    public void setGraphicsConnector(GraphicsConnector graphicsConnector) {
        this.graphicsConnector = graphicsConnector;
    }

    /**
     * @return true if white can move now
     */
    public boolean getWhiteMove() {
        return whiteMove;
    }

    /**
     * Sets the colour to move a piece next
     * @param whiteMove true if white shall move, false for black
     */
    public void setWhiteMove(boolean whiteMove) {
        this.whiteMove = whiteMove;
    }

    /**
     * @return gets the char of the movable piece determined by the dice
     */
    public char getMovablePiece() {
        return movablePiece;
    }

    /**
     * Sets the piece that can be moved in this turn
     * @param movablePiece char of the piece that shall be moved
     */
    public void setMovablePiece(char movablePiece) {
        this.movablePiece = movablePiece;
    }

    /**
     * Setter of both players
     * @param player1 player one
     * @param player2 player two
     */
    public void setPlayers(int player1, int player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * @return player-type of player1
     */
    public int getPlayer1() {
        return player1;
    }

    /**
     * @return player-type of player2
     */
    public int getPlayer2() {
        return player2;
    }

    /**
     * @return true if it is original
     */
    public boolean isOriginal() {
        return isOriginal;
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

    /**
     * Launches the update of the GUI so that the user can see changes on screen
     */
    public void launchGuiUpdate(){
        if(Config.GUI_ON){
            getGameRunner().debugWindowStage.incrementPlyCount();
            graphicsConnector.updateImages();
            graphicsConnector.changeTurn();
        }
    }

    /**
     * @return true if the currently moving player is a human player
     */
    public boolean isHumanPlayer(){
        if(whiteMove){
            return player1==0;
        }
        else{
            return player2==0;
        }
    }

    /**
     * Setter for both players
     * @param player1 player one
     * @param player2 player two
     */
    public void setPlayerPlayers(Player player1, Player player2) {
        this.playerOne = player1;
        this.playerTwo = player2;
    }

    /**
     * Stores a move in Fen-notation to the list of moves
     */
    public void storeMove(){
        moves.add(FenEvaluator.write(this));
    }

    /**
     * @return list of all board states
     */
    public ArrayList<String> getBoardStates() {
        return moves;
    }

    /**
     * @return true if the en passant property is given for the current move
     */
    public boolean isEnPassantActive() {
        return enPassantActive;
    }

    /**
     * Sets the en passant property
     * @param enPassantActive true or false for the corresponding property
     */
    public void setEnPassantActive(boolean enPassantActive) {
        this.enPassantActive = enPassantActive;
    }

    /**
     * @return column of the current en passant property (pawn-column)
     */
    public int getEnPassantColumn() {
        return enPassantColumn;
    }

    /**
     * Sets the column of the en passant property
     * @param enPassantColumn column index
     */
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

    /**
     * @return current GameRunner
     */
    public GameRunner getGameRunner() {
        return gameRunner;
    }

    /**
     * Sets the amount of turns
     * @param amountOfTurns number for the amount of turns
     */
    public void setAmountOfTurns(int amountOfTurns) {
        this.amountOfTurns = amountOfTurns;
    }

    /**
     * @return amount of turns for this board
     */
    public int getAmountOfTurns() {
        return amountOfTurns;
    }

    /**
     * Method to clear all moves that have been saved to this board
     */
    public void movesClear() {
        moves.clear();
    }
}