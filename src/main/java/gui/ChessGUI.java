package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessGUI extends Application {
    private static final String initialFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    public static final int width = 800;
    public static final int height = 800;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        FENReader f = new FENReader();
        ChessBoard board = f.readInFEN(initialFEN);
        System.out.println("white move "+board.isWhiteMove());
        System.out.println("black king "+board.isBlackKingCastling());
        System.out.println("black queen "+board.isBlackQueenCastling());
        System.out.println("white king "+board.isWhiteKingCastling());
        System.out.println("white queen "+board.isWhiteQueenCastling());


        Scene scene = new Scene(board, width, height);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
