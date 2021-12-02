package controller;

import gui.ChessGUI;
import model.algorithm.AiTree;
import model.algorithm.Expectiminimax;
import model.pieces.ChessPiece;
import model.pieces.KingPiece;
import model.pieces.PawnPiece;
import model.player.*;


public class GameRunner {
    private Board board;
    ChessGUI chessGUI;
    GraphicsConnector graphicsConnector;
    private AiTree aiTree;
    private Expectiminimax expectiminimax;



    /**
     * Constructor
     */
    public GameRunner() {
        chessGUI = new ChessGUI();
        graphicsConnector = new GraphicsConnector(this);
        if(Board.GUI_ON){
            chessGUI.launchGUI(graphicsConnector);
        }
        else{
            init(1,1);
        }
    }

    /**
     * Initializes the game when it is started.
     */
    public void init(int playerOne, int playerTwo) {
        aiTree = new AiTree();
        expectiminimax = new Expectiminimax();
        board = new Board(this);
        Player player1 = createPlayer(playerOne);
        Player player2 = createPlayer(playerTwo);
        board.setPlayers(playerOne, playerTwo);
        board.setPlayerPlayers(player1, player2);
        BoardUpdater.fillGameStart(board);
        graphicsConnector.setBoard(board);
        Dice.firstMoveDiceRoll(board);
        board.checkAi();
    }

    public Player createPlayer(int playerType){
        if(playerType == 0){
            return new HumanPlayer();
        }
        else if(playerType== 1){
            return new FirstAi(board);
        }
        else if(playerType == 2){
            return new BaselineAgent();
        }
        else if(playerType == 4){
            return new TakeAi();
        }
        else{
            return new TDLearning();
        }
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

    public static void printBoard(ChessPiece[][] boardModel, Board board) {
        System.out.println("--- Board State ---\n");
        for(int i = 0; i < boardModel[0].length; i++) {
            for (int j = 0; j < boardModel.length; j++) {
                System.out.print("[ " + board.getCharOffField(j,i) + " ] ");
                // System.out.print("[ " + j + " " + i + " ] ");
            }
            System.out.println();
        }
    }
}
