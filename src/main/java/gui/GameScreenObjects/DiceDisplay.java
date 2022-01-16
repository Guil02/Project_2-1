package gui.GameScreenObjects;

import controller.GraphicsConnector;
import gui.SceneLayouts.GameScreen;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Part of the GUI that displays the dice roll.
 */
public class DiceDisplay extends VBox {

    // Variables
    public static final double DIVIDER_HEIGHT = 0.7;
    private GameScreen gameScreen;
    private DiceImageLabel diceImageLabelOne;
    private DiceImageLabel diceImageLabelTwo;
    private static final double DIVIDER_WIDTH = 0.15;

    /**
     * Constructor
     * @param gameScreen game screen instance
     * @param graphicsConnector attached GraphicsConnector
     */
    public DiceDisplay(GameScreen gameScreen, GraphicsConnector graphicsConnector) {
        this.gameScreen = gameScreen;
        diceImageLabelOne = new DiceImageLabel(this, graphicsConnector,1);
        diceImageLabelTwo = new DiceImageLabel(this, graphicsConnector,2);
        setMinSize(getWidthFromTop()* DIVIDER_WIDTH, getHeightFromTop()* DIVIDER_HEIGHT);
        setMaxSize(getWidthFromTop()* DIVIDER_WIDTH, getHeightFromTop()* DIVIDER_HEIGHT);
        setAlignment(Pos.CENTER);
        getChildren().addAll(diceImageLabelOne, diceImageLabelTwo);
    }

    /**
     * @return width
     */
    public double getWidthFromTop(){
        return gameScreen.getWidthFromChessGUI();
    }

    /**
     * @return height
     */
    public double getHeightFromTop() {
        return gameScreen.getHeightFromChessGUI();
    }

    /**
     * Updates the graphics.
     */
    public void updateGraphics(){
        setMinSize(getWidthFromTop()* DIVIDER_WIDTH, getHeightFromTop()* DIVIDER_HEIGHT);
        setMaxSize(getWidthFromTop()* DIVIDER_WIDTH, getHeightFromTop()* DIVIDER_HEIGHT);
        diceImageLabelOne.updateGraphics();
        diceImageLabelTwo.updateGraphics();
    }

    /**
     * Updates the dice.
     */
    public void updateDice(){
        diceImageLabelOne.updateDice();
        diceImageLabelTwo.updateDice();
    }

    /**
     * Hides the dice.
     */
    public void hideDice() {
        setVisible(false);
    }
}
