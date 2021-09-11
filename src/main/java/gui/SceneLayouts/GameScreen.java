package gui.SceneLayouts;

import gui.ChessBoard;
import gui.ChessGUI;
import gui.Menus.DisplayMenu;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class GameScreen extends BorderPane {
    public GameScreen(ChessBoard chessBoard, ChessGUI chessGUI) {
        setCenter(chessBoard);
        setStyle("-fx-background-color: #59913a;");
        Label label = new Label("hi");
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font("Verdana",30));
        setTop(label);
        MenuBar menuBar = new MenuBar();
        DisplayMenu displayMenu = new DisplayMenu(chessGUI);
        menuBar.getMenus().add(displayMenu);
        setTop(menuBar);
    }
}
