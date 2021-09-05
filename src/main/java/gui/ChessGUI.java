package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ChessBoard board = new ChessBoard();
        Scene scene = new Scene(board, 800, 800);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
