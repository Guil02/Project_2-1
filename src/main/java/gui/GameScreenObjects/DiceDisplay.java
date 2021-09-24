package gui.GameScreenObjects;

import controller.GraphicsConnector;
import gui.SceneLayouts.GameScreen;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class DiceDisplay extends VBox {
    public static final double DIVIDER_HEIGHT = 0.7;
    private GameScreen gameScreen;
    private DiceImageLabel diceImageLabelOne;
    private DiceImageLabel diceImageLabelTwo;
    private static final double DIVIDER_WIDTH = 0.15;

    public DiceDisplay(GameScreen gameScreen, GraphicsConnector graphicsConnector) {
        this.gameScreen = gameScreen;
        diceImageLabelOne = new DiceImageLabel(this, graphicsConnector,1);
        diceImageLabelTwo = new DiceImageLabel(this, graphicsConnector,2);
        setMinSize(getWidthFromTop()* DIVIDER_WIDTH, getHeightFromTop()* DIVIDER_HEIGHT);
        setMaxSize(getWidthFromTop()* DIVIDER_WIDTH, getHeightFromTop()* DIVIDER_HEIGHT);
        setAlignment(Pos.CENTER);
        getChildren().addAll(diceImageLabelOne, diceImageLabelTwo);

    }

    public double getWidthFromTop(){
        return gameScreen.getWidthFromChessGUI();
    }

    public double getHeightFromTop() {
        return gameScreen.getHeightFromChessGUI();
    }

    public void updateGraphics(){
        setMinSize(getWidthFromTop()/ DIVIDER_WIDTH, getHeightFromTop()* DIVIDER_HEIGHT);
        setMaxSize(getWidthFromTop()/ DIVIDER_WIDTH, getHeightFromTop()* DIVIDER_HEIGHT);
        diceImageLabelOne.updateGraphics();
        diceImageLabelTwo.updateGraphics();
    }
}
