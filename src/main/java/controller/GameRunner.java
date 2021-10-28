package controller;

import gui.ChessGUI;

public class GameRunner {
    private Board board;
    private BoardUpdater boardUpdater;
    private boolean whiteMove;
    ChessGUI chessGUI;
    GraphicsConnector graphicsConnector;
    private char movablePiece;
    //private Dice dice;



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
        whiteMove = true;

        board = new Board();
        BoardUpdater.fillGameStart(board);
        graphicsConnector.setBoard(board);
        Dice.firstMoveDiceRoll(board);
    }

    @Override
    protected GameRunner clone(){
        try{
            return (GameRunner) super.clone();
        }
        catch(CloneNotSupportedException e){
            System.err.println("failed to make copy.");
        }
        return new GameRunner();
    }
}
