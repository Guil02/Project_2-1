package controller;

import gui.ChessGUI;
import model.algorithm.AiTree;
import model.algorithm.Expectiminimax;
import model.pieces.ChessPiece;
import model.player.*;


public class GameRunner {
    private Board board;
    ChessGUI chessGUI;
    GraphicsConnector graphicsConnector;
    private AiTree aiTree;
    private Expectiminimax expectiminimax;
    public static final boolean DEBUG = false;
    public static final boolean GENERATE_GAMES = false;
    public static final boolean GUI_ON = true;
    public static final boolean EXPERIMENT1 =false;
    private int whiteWin = 0;
    private int blackWin = 0;
    private int games = 0;
    private static final int maxGames = 100;

    public void incrementWhiteWin(){
        whiteWin++;
    }
    public void incrementBlackWin(){
        blackWin++;
    }
    public void incrementGames(){
        games++;
    }
    public int getWhiteWin() {
        return whiteWin;
    }
    public int getBlackWin() {
        return blackWin;
    }
    public int getGames(){
        return games;
    }
    public boolean continuePlaying(){
        return games<maxGames;
    }




    /**
     * Constructor
     */
    public GameRunner() {
        chessGUI = new ChessGUI();
        graphicsConnector = new GraphicsConnector(this);
        if(Board.GUI_ON){
            try{
                chessGUI.launchGUI(graphicsConnector);
            }
            catch (IllegalStateException e){
                init(board.getPlayer1(), board.getPlayer2());
            }
        }
        else{
            /*
            0 = "Human"
            1 = "Search Agent"
            2 = "Random Agent"
            3 = "TD learning Agent"
            4 = "Take Agent"
            5 = "NN Agent"
            6 = "Cheating Agent"
             */
            init(3,3);
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

    public void reset(){
        board.movesClear();
        BoardUpdater.clearBoard(board);
        BoardUpdater.fillGameStart(board);
        board.setGameOver(false);
        board.setAmountOfTurns(1);
        board.setWhiteMove(true);
        Dice.firstMoveDiceRoll(board);
        board.checkAi();
    }

    public Player createPlayer(int playerType){
        if(playerType == 0){
            return new HumanPlayer();
        }
        else if(playerType== 1){
            return new SearchAgent(board);
        }
        else if(playerType == 2){
            return new BaselineAgent();
        }
        else if (playerType == 3){
            return new TDLearningAgent();
        }
        else if(playerType == 4){
            return new TakeAgent();
        }
        else if(playerType == 5){
            return new NNAgent();
        }
        else{
            return new CheatAgent();
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
