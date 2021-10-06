package gui.SceneLayouts;

import controller.GraphicsConnector;
import gui.ChessGUI;
import gui.GameScreenObjects.ChessBoard;
import gui.GameScreenObjects.DiceDisplay;
import gui.GameScreenObjects.LeftSide;
import gui.GameScreenObjects.PromotionDisplay;
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
    private final ChessGUI chessGUI;
    private final Label topEmptySpace;
    private final DiceDisplay diceDisplay;
    private final PromotionDisplay promotionDisplay;
    public GameScreen(ChessBoard chessBoard, ChessGUI chessGUI, GraphicsConnector graphicsConnector) {
        this.chessGUI = chessGUI;
        topEmptySpace = new Label();

        topEmptySpace.setMinSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER - 0.10));
        topEmptySpace.setMaxSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER - 0.10));

        diceDisplay = new DiceDisplay(this, graphicsConnector);
        leftSide = new LeftSide(this);
        promotionDisplay = new PromotionDisplay(graphicsConnector, this);
        setLeft(leftSide);
        setRight(diceDisplay);
        setBottom(promotionDisplay);
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

    public void launchPromotionDialog() {
        promotionDisplay.showPromotionDialog();
    }

    public void updateGraphics(double width, double height) {
        topEmptySpace.setMinSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER - 0.10));
        topEmptySpace.setMaxSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER - 0.10));

        diceDisplay.updateGraphics();


    }

    public double getWidthFromChessGUI() {
        return chessGUI.getWidth();
    }

    public double getHeightFromChessGUI() {
        return chessGUI.getHeight();
    }


    public void changeTimer(){
        leftSide.changeTimer();
    }

    public void updateDice(){
        diceDisplay.updateDice();
    }
}
