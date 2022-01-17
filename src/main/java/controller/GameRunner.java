package controller;

import gui.ChessGUI;
import model.GeneticAlgorithm.GA;
import model.player.GeneticAlgorithmAgent;
import gui.debugWindow.DebugWindowStage;
import model.player.*;
import config.Config;

/**
 * Central controller class to run the game.
 */
public class GameRunner {

    //Variables
    private Board board;
    ChessGUI chessGUI;
    GraphicsConnector graphicsConnector;
    DebugWindowStage debugWindowStage;
    public static final boolean DEBUG = Config.DEBUG;
    public static final boolean EXPERIMENT1 =false;
    private int whiteWin = 0;
    private int blackWin = 0;
    private int games = 0;
    private static final int maxGames = 500;

    /**
     * Constructor
     */
    public GameRunner() {
        if(Config.GA){
            new GA();
        }
        else{
            chessGUI = new ChessGUI();
            graphicsConnector = new GraphicsConnector(this);
            if(Config.GUI_ON){
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
                3 = "Take Agent"
                4 = "NN Agent"
                5 = "Cheating Agent"
                6 = "GA Agent"
                */
                init(1,1);
            }
        }
    }

    /**
     * Constructor
     * @param board
     */
    public GameRunner(Board board){
        this.board = board;
        BoardUpdater.fillGameStart(board);
    }

    /**
     * GA training against another GA-Agent
     * @param agent1
     * @param agent2
     */
    public void GATraining(GeneticAlgorithmAgent agent1, GeneticAlgorithmAgent agent2){
        board.setPlayers(7,7);
        board.setPlayerPlayers(agent1,agent2);
        Dice.firstMoveDiceRoll(board);
        board.checkAi();
    }

    /**
     * GA training against a Search-Agent
     * @param agent1
     * @param agent2
     */
    public void GATraining(GeneticAlgorithmAgent agent1, SearchAgent agent2){
        board.setPlayers(7,1);
        board.setPlayerPlayers(agent1,agent2);
        Dice.firstMoveDiceRoll(board);
        board.checkAi();
    }

    /**
     * Resets the GA-Agent
     */
    public void GAReset(){
        board.movesClear();
        BoardUpdater.clearBoard(board);
        BoardUpdater.fillGameStart(board);
        board.setGameOver(false);
        board.setAmountOfTurns(1);
        board.setWhiteMove(true);
    }

    /**
     * Initializes the game when it is started.
     */
    public void init(int playerOne, int playerTwo) {
        board = new Board(this);
        Player player1 = createPlayer(playerOne);
        Player player2 = createPlayer(playerTwo);
        board.setPlayers(playerOne, playerTwo);
        board.setPlayerPlayers(player1, player2);
        BoardUpdater.fillGameStart(board);
        // Opens debug window
        if (Config.SHOW_DEBUG_WINDOW && Config.GUI_ON) {
            debugWindowStage = new DebugWindowStage(this);
            debugWindowStage.show();
        }
        graphicsConnector.setBoard(board);
        Dice.firstMoveDiceRoll(board);
        board.checkAi();
    }

    /**
     * Resets the whole game to a new one.
     */
    public void reset(){
        board.movesClear();
        BoardUpdater.clearBoard(board);
        BoardUpdater.fillGameStart(board);
        board.setGameOver(false);
        board.setAmountOfTurns(1);
        board.setWhiteMove(true);
        Dice.firstMoveDiceRoll(board);
        board.checkAi();
        games++;
    }

    /**
     * Creates a player of a certain type.
     * @param playerType type of the player
     * @return Player instance
     */
    public Player createPlayer(int playerType){
        if(playerType == 0){
            return new HumanPlayer();
        }
        else if(playerType== 1){
            return new SearchAgent(board);
        }
        else if(playerType == 2){
            return new RandomAgent();
        }
        else if(playerType == 3){
            return new GreedyAgent();
        }
        else if(playerType == 4){
            return new NNAgent();
        }
        else if(playerType == 5){
            return new CheatAgent();
        }
        else{
            return new GeneticAlgorithmAgent();
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

    /**
     * @return current board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Increment win-counter for white player.
     */
    public void incrementWhiteWin(){
        whiteWin++;
    }

    /**
     * Increment win-counter for black player.
     */
    public void incrementBlackWin(){
        blackWin++;
    }


    /**
     * @return amount of how often white player won
     */
    public int getWhiteWin() {
        return whiteWin;
    }

    /**
     * @return amount of how often black player won
     */
    public int getBlackWin() {
        return blackWin;
    }

    /**
     * @return whether the automated process is going to continue to a next game
     */
    public boolean continuePlaying(){
        return games<maxGames;
    }
}
