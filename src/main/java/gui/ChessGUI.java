package gui;

import controller.GraphicsConnector;
import gui.GameScreenObjects.ChessBoard;
import gui.SceneLayouts.GameScreen;
import gui.SceneLayouts.StartScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * a class that run the javaFX application and link it to graphics connector
 */
public class ChessGUI extends Application {
    public static boolean COLOR = true;
    public static boolean TURN = false;
    private double width = 900;
    private double height = 900;
    private Stage stage;
    private Scene gameScene;
    private ChessBoard chessBoard;
    private static GraphicsConnector graphicsConnector;
    private GameScreen gameScreen;
    private StartScreen mainMenu;


    /**
     * @param graphicsConnector
     */
    public void launchGUI(GraphicsConnector graphicsConnector) {
        ChessGUI.graphicsConnector = graphicsConnector;
        String[] args = new String[0];
        launch(args);
    }

    /**
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        graphicsConnector.setChessGUI(this);
        this.stage=stage;
        this.mainMenu = new StartScreen(this);
        Scene startMenu = new Scene(mainMenu, width, height);

        stage.setResizable(false);
        stage.setTitle("dice chess");
        Image icon = new Image("gui/white_pawn.png");
        stage.getIcons().add(0,icon);
        stage.setScene(startMenu);
        stage.show();
    }

    /**
     * @param playerOne
     * @param PlayerTwo
     */
    public void startGame(int playerOne, int PlayerTwo){
        graphicsConnector.setPlayers(playerOne, PlayerTwo);
        graphicsConnector.init();

        ChessBoard board = new ChessBoard(graphicsConnector, this);
        this.chessBoard = board;
        board.initializeBoard();

        gameScreen = new GameScreen(board, this, graphicsConnector);
        board.setGameScreen(gameScreen);

        this.gameScene = new Scene(gameScreen, width, height);


        stage.setScene(gameScene);
    }

    /**
     * @param size
     */
    public void updateDisplaySize(double size){
        stage.setHeight(size);
        stage.setWidth(size);
        setWidth(size);
        setHeight(size);
        if(gameScreen!=null){
            gameScreen.updateGraphics(width,height);
            chessBoard.updateGraphic();
        }
        mainMenu.updateGraphics();
    }

    /**
     * @return
     */
    public double getWidth(){
        return width;
    }

    /**
     * @return
     */
    public double getHeight(){
        return height;
    }

    /**
     * @param size
     */
    public void setWidth(double size){
        width = size;
    }

    /**
     * @param size
     */
    public void setHeight(double size){
        height = size;
    }

    /**
     *
     */
    public void launchPromotionDialog(){
        this.gameScreen.launchPromotionDialog();
        chessBoard.setPromotionLock(false);
    }

    /**
     *
     */
    public void updateImages() {
        chessBoard.initializeBoard();
    }

    /**
     * @param white
     */
    public void setWin(boolean white) {
        chessBoard.setPromotionLock(false);
        gameScreen.setWin(white);
    }
}
