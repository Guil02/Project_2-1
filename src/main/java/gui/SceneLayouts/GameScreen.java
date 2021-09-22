package gui.SceneLayouts;

import gui.GameScreenObjects.ChessBoard;
import gui.ChessGUI;
import gui.Menus.DisplayMenu;
import gui.Menus.HighlightMenu;
import gui.Menus.TurnMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GameScreen extends BorderPane {
    public static final double DIVIDER = 0.15;
    private ChessGUI chessGUI;
    private Label leftEmptySpace;
    private Label rightEmptySpace;
    private Label topEmptySpace;
    public GameScreen(ChessBoard chessBoard, ChessGUI chessGUI) {
        this.chessGUI = chessGUI;
        leftEmptySpace = new Label();
        rightEmptySpace = new Label();
        topEmptySpace = new Label();

        leftEmptySpace.setMinSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* DIVIDER);
        leftEmptySpace.setMaxSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* DIVIDER);
        rightEmptySpace.setMinSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* DIVIDER);
        rightEmptySpace.setMaxSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* DIVIDER);
        topEmptySpace.setMinSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* (DIVIDER-0.10));
        topEmptySpace.setMaxSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* (DIVIDER-0.10));

        setLeft(leftEmptySpace);
        setRight(rightEmptySpace);
        setCenter(chessBoard);
        setStyle("-fx-background-color: #59913a;");
        MenuBar menuBar = new MenuBar();
        DisplayMenu displayMenu = new DisplayMenu(chessGUI);
        HighlightMenu highlightMenu = new HighlightMenu();
        TurnMenu turnMenu = new TurnMenu();
        menuBar.getMenus().addAll(displayMenu, highlightMenu, turnMenu);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar,topEmptySpace);
        setTop(vBox);
    }

    public void updateGraphics(){
        leftEmptySpace.setMinSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* DIVIDER);
        leftEmptySpace.setMaxSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* DIVIDER);
        rightEmptySpace.setMinSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* DIVIDER);
        rightEmptySpace.setMaxSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* DIVIDER);
        topEmptySpace.setMinSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* (DIVIDER-0.10));
        topEmptySpace.setMaxSize(chessGUI.getWidth()* DIVIDER, chessGUI.getHeight()* (DIVIDER-0.10));
    }
}
