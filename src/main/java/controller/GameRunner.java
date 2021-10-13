package controller;

import gui.ChessGUI;
import model.Board;
import model.BoardUpdater;
import model.Dice.Dice;

/**
 * Keeps track of the game loop and acts as the core piece of the controller.
 */
public class GameRunner {

    // Variables
    private int totalMoves;
    private boolean whiteMove;
    private Board board;
    private BoardUpdater boardUpdater;
    ChessGUI chessGUI;
    GraphicsConnector graphicsConnector;
    private char movablePiece;
    private Dice dice;

    /**
     * Constructor
     */
    public GameRunner() {
        chessGUI = new ChessGUI();
        graphicsConnector = new GraphicsConnector(this);
        chessGUI.launchGUI(graphicsConnector);

    }

    /**
     * Initializes the game when it is started.
     */
    public void init() {
        totalMoves = 0;
        whiteMove = true;

        board = new Board(this);
        boardUpdater = new BoardUpdater(board);
        board.setBoardUpdater(boardUpdater);
        boardUpdater.setGraphicsConnector(graphicsConnector);
        boardUpdater.fillGameStart();
        dice = new Dice(this);
        movablePiece = dice.firstMoveDiceRoll();
        boolean[][] testValidMoves = board.getPiece(2,0).validMoves();
        graphicsConnector.initConnector();
    }

    /**
     * Tries to move a piece from one position to the other.
     * Validity is checked in used methods.
     * @param xFrom     initial x position
     * @param yFrom     initial y position
     * @param xTo       target x position
     * @param yTo       target y position
     */
    public void attemptMove(int xFrom, int yFrom, int xTo, int yTo) {
        // Check validity

        // If check is okay, move piece to requested position
        boardUpdater.movePiece(xFrom, yFrom, xTo, yTo);
        totalMoves++;
    }

    /**
     * Getter for the current game board.
     * @return  current game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Getter for the current game board updater.
     * @return  current game board updater
     */
    public BoardUpdater getBoardUpdater() {
        return boardUpdater;
    }

    /**
     * Getter to see, whose turn it is.
     * @return  white move or not
     */
    public boolean getWhiteMove(){
        return whiteMove;
    }

    /**
     * Setter of the current player to move a piece.
     * @param whiteMove     "true" for white, "false" for black
     */
    public void setWhiteMove(boolean whiteMove) {
        this.whiteMove = whiteMove;
    }

    /**
     * Executes the move of a piece.
     */
    public void doMove(){
        whiteMove = !whiteMove;
        setMovablePiece(dice.rollTheDice());
    }

    /**
     * Getter of a movable pieces in the game.
     * @return  char of the piece
     */
    public char getMovablePiece() {
        return movablePiece;
    }

    /**
     * Setter of a movable piece in the game.
     * @param movablePiece  the movable piece to pass
     */
    public void setMovablePiece(char movablePiece) {
        this.movablePiece = movablePiece;
    }

}
