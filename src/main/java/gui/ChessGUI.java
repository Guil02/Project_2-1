package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ChessGUI extends Application {
    private static final String initialFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    private Stage stage;
    private Scene gameScene;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        this.stage=stage;
        MainMenu mainMenu = new MainMenu(this);
        Scene startMenu = new Scene(mainMenu,WIDTH,HEIGHT);

//        FENReader f = new FENReader();
//        ChessBoard board = f.readInFEN(initialFEN);
        ChessBoard board = new ChessBoard();

        GridPane gridPane = new GridPane();
        gridPane.add(board,1,1,8,8);

        this.gameScene = new Scene(gridPane,WIDTH,HEIGHT);


        stage.setResizable(false);
        stage.setTitle("dice chess");
        Image icon = new Image("gui/white_pawn.png");
        stage.getIcons().add(0,icon);
        stage.setScene(startMenu);
        stage.show();
    }

    public void startGame(int playerOne, int PlayerTwo){
        stage.setScene(gameScene);
    }
}
