package gui;

import controller.GraphicsConnector;

public class test {
    public static void main(String[] args) {
        ChessGUI chessGUI = new ChessGUI();
        GraphicsConnector graphicsConnector = new GraphicsConnector();
        chessGUI.launchGUI(graphicsConnector);
    }
}
