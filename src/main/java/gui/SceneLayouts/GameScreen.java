package gui.SceneLayouts;

import controller.GraphicsConnector;
import gui.ChessGUI;
import gui.GameScreenObjects.ChessBoard;
import gui.GameScreenObjects.DiceDisplay;
import gui.GameScreenObjects.LeftSide;
import gui.Menus.DisplayMenu;
import gui.Menus.HighlightMenu;
import gui.Menus.TurnMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GameScreen extends BorderPane {
    public static final double DIVIDER = 0.15;
    private final LeftSide leftSide;
    private ChessGUI chessGUI;
    private Label leftEmptySpace;
    private Label rightEmptySpace;
    private Label topEmptySpace;
    private DiceDisplay diceDisplay;
    public GameScreen(ChessBoard chessBoard, ChessGUI chessGUI, GraphicsConnector graphicsConnector) {
        this.chessGUI = chessGUI;
        leftEmptySpace = new Label();
        rightEmptySpace = new Label();
        topEmptySpace = new Label();

       // leftEmptySpace.setMinSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * DIVIDER);
       // leftEmptySpace.setMaxSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * DIVIDER);
//        rightEmptySpace.setMinSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * DIVIDER);
//        rightEmptySpace.setMaxSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * DIVIDER);
        topEmptySpace.setMinSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER - 0.10));
        topEmptySpace.setMaxSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER - 0.10));

        diceDisplay = new DiceDisplay(this, graphicsConnector);
        leftSide = new LeftSide(this);
        setLeft(leftSide);
        setRight(diceDisplay);
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

    public void updateGraphics(double width, double height) {
        leftEmptySpace.setMinSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * DIVIDER);
        leftEmptySpace.setMaxSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * DIVIDER);
        rightEmptySpace.setMinSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * DIVIDER);
        rightEmptySpace.setMaxSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * DIVIDER);
        topEmptySpace.setMinSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER - 0.10));
        topEmptySpace.setMaxSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER - 0.10));

        int size = getSize(width);
        diceDisplay.updateGraphics(size);

    }

    public double getWidthFromChessGUI() {
        return chessGUI.getWidth();
    }

    public double getHeightFromChessGUI() {
        return chessGUI.getHeight();
    }

    private int getSize(double width) {
        int size;
        switch ((int) width) {
            case 200:
                size = 1;
                break;
            case 400:
                size = 2;
                break;
            case 600:
                size = 3;
                break;
            case 800:
                size = 4;
                break;
            case 1000:
                size = 5;
                break;
            default:
                size = 4;
                break;
        }
        return size;
    }
    public void changeTimer(){
        leftSide.changeTimer();
    }
}
