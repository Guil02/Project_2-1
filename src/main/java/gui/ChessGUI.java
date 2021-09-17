package gui;

import controller.GraphicsConnector;
import gui.SceneLayouts.GameScreen;
import gui.SceneLayouts.StartScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class ChessGUI extends Application {
    private double width = 900;
    private double height = 900;
    private Stage stage;
    private Scene gameScene;
    private ChessBoard chessBoard;
    private static GraphicsConnector graphicsConnector;
    private GameScreen gameScreen;
    private StartScreen mainMenu;


    public void launchGUI(GraphicsConnector graphicsConnector) {
        ChessGUI.graphicsConnector = graphicsConnector;
        String[] args = new String[0];
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage=stage;
        this.mainMenu = new StartScreen(this);
        Scene startMenu = new Scene(mainMenu, width, height);

        ChessBoard board = new ChessBoard(graphicsConnector, this);
        this.chessBoard = board;
        board.initializeBoard();

        gameScreen = new GameScreen(board, this);

        this.gameScene = new Scene(gameScreen, width, height);


        stage.setResizable(false);
        stage.setTitle("dice chess");
        Image icon = new Image("gui/white_pawn.png");
        stage.getIcons().add(0,icon);
        stage.setScene(startMenu);
        stage.show();
    }

    public void startGame(int playerOne, int PlayerTwo){
        graphicsConnector.setPlayers(playerOne, PlayerTwo);
        graphicsConnector.init();
        stage.setScene(gameScene);
    }

    public void updateDisplaySize(double size){
        stage.setHeight(size);
        stage.setWidth(size);
        setWidth(size);
        setHeight(size);

        gameScreen.updateGraphics();
        chessBoard.updateGraphic();
        mainMenu.updateGraphics();
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public void setWidth(double size){
        width = size;
    }

    public void setHeight(double size){
        height = size;
    }

}
