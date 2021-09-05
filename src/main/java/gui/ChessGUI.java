package gui;

import gui.Pieces.King;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessGUI extends Application {
    private static final String initialFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FENReader f = new FENReader();
        ChessBoard board = f.readInFEN(initialFEN);

        Scene scene = new Scene(board, 800, 800);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
