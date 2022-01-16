package gui.sceneLayouts;

import controller.GraphicsConnector;
import gui.ChessGUI;
import gui.gameScreenObjects.ChessBoard;
import gui.gameScreenObjects.DiceDisplay;
import gui.gameScreenObjects.LeftSide;
import gui.gameScreenObjects.PromotionDisplay;
import gui.menus.DisplayMenu;
import gui.menus.HighlightMenu;
import gui.menus.StartAIButton;
import gui.menus.TurnMenu;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
/**
 * a class for designing the game scene which contains
 * the chess bord,the dice and 2 timers
 */
public class GameScreen extends BorderPane {
    public static final double DIVIDER = 0.15;
    private final LeftSide leftSide;
    private final ChessGUI chessGUI;
    private final Label topEmptySpace;
    private final Label bottomLeftEmptySpace;
    private final DiceDisplay diceDisplay;
    private final PromotionDisplay promotionDisplay;

    /**
     * @param chessBoard
     * @param chessGUI
     * @param graphicsConnector
     */
    public GameScreen(ChessBoard chessBoard, ChessGUI chessGUI, GraphicsConnector graphicsConnector) {
        this.chessGUI = chessGUI;
        topEmptySpace = new Label();
        bottomLeftEmptySpace = new Label();

        topEmptySpace.setMinSize(chessGUI.getWidth(), chessGUI.getHeight() * (DIVIDER - 0.10));
        topEmptySpace.setMaxSize(chessGUI.getWidth(), chessGUI.getHeight() * (DIVIDER - 0.10));
        bottomLeftEmptySpace.setMinSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER));
        bottomLeftEmptySpace.setMaxSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER));

        diceDisplay = new DiceDisplay(this, graphicsConnector);
        leftSide = new LeftSide(this);
        promotionDisplay = new PromotionDisplay(graphicsConnector, this);
        setLeft(leftSide);
        setRight(diceDisplay);
        setCenter(chessBoard);
        setStyle("-fx-background-color: #59913a;");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(bottomLeftEmptySpace, promotionDisplay);
        setBottom(hBox);

        MenuBar menuBar = new MenuBar();
        DisplayMenu displayMenu = new DisplayMenu(chessGUI);
        HighlightMenu highlightMenu = new HighlightMenu();
        TurnMenu turnMenu = new TurnMenu();
        StartAIButton st = new StartAIButton(chessGUI);
        menuBar.getMenus().addAll(displayMenu, highlightMenu, turnMenu, st);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar,topEmptySpace);
        setTop(vBox);
    }

    /**
     *
     */
    public void launchPromotionDialog() {
        promotionDisplay.showPromotionDialog();
    }

    /**
     * @param width
     * @param height
     */
    public void updateGraphics(double width, double height) {
        topEmptySpace.setMinSize(chessGUI.getWidth(), chessGUI.getHeight() * (DIVIDER - 0.10));
        topEmptySpace.setMaxSize(chessGUI.getWidth(), chessGUI.getHeight() * (DIVIDER - 0.10));
        bottomLeftEmptySpace.setMinSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER));
        bottomLeftEmptySpace.setMaxSize(chessGUI.getWidth() * DIVIDER, chessGUI.getHeight() * (DIVIDER));
        leftSide.updateGraphics();
        diceDisplay.updateGraphics();
        updateLabelGraphic();

    }

    /**
     * @return
     */
    public double getWidthFromChessGUI() {
        return chessGUI.getWidth();
    }

    /**
     * @return
     */
    public double getHeightFromChessGUI() {
        return chessGUI.getHeight();
    }


    /**
     *
     */
    public void changeTimer(){
        leftSide.changeTimer();
    }

    /**
     *
     */
    public void updateDice(){
        diceDisplay.updateDice();
    }

    /**
     * @param white
     */
    public void setWin(boolean white) {
        topEmptySpace.setAlignment(Pos.CENTER);
        updateLabelGraphic();
        stopTime();
        hideDice();
        if(white){
            topEmptySpace.setText("white has won!");
        }
        else{
            topEmptySpace.setText("black has won!");
        }
    }

    /**
     *
     */
    private void hideDice() {
        diceDisplay.hideDice();
    }

    /**
     *
     */
    public void stopTime(){
        leftSide.stopTime();
    }

    public void endGame(boolean white) {
        chessGUI.setWin(white);
    }

    public void updateLabelGraphic(){
        int width = (int) getWidthFromChessGUI();
        switch (width){
            case 200:
                topEmptySpace.setFont(new Font("Verdana", 6));
            case 400:
                topEmptySpace.setFont(new Font("Verdana", 11));
                break;
            case 600:
                topEmptySpace.setFont(new Font("Verdana", 17));
                break;
            case 800:
                topEmptySpace.setFont(new Font("Verdana", 22));
                break;
            case 1000:
                topEmptySpace.setFont(new Font("Verdana", 28));
            default:
                topEmptySpace.setFont(new Font("Verdana", 20));
        }
    }
}
