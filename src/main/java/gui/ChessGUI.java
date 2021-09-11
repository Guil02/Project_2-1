package gui;

import controller.GraphicsConnector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class ChessGUI extends Application {
    private static final String initialFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private double width = 900;
    private double height = 900;
    private Stage stage;
    private Scene gameScene;
    private ChessBoard chessBoard;
    private static GraphicsConnector graphicsConnector;
    private MediaPlayer mediaPlayer;


    public void launchGUI(GraphicsConnector graphicsConnector) {
        ChessGUI.graphicsConnector = graphicsConnector;
        String[] args = new String[0];
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        music();
        this.stage=stage;
        MainMenu mainMenu = new MainMenu(this);
        Scene startMenu = new Scene(mainMenu, width, height);

        ChessBoard board = new ChessBoard(graphicsConnector, this);
        this.chessBoard = board;
        board.initializeBoard();

        GameScreen gameScreen = new GameScreen(board, this);

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
        stage.setScene(gameScene);
    }

    public void updateDisplaySize(double size){
        stage.setHeight(size);
        stage.setWidth(size);
        setWidth(size);
        setHeight(size);
        chessBoard.updateGraphic();
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

    public void music(){
        String s = "build/classes/java/main/gui/music.mp3";
        Media media = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
