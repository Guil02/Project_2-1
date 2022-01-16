package gui.gameScreenObjects;


import gui.sceneLayouts.GameScreen;
import javafx.animation.Timeline;
import javafx.scene.layout.VBox;

/**
 * Left side of the GUI.
 */
public class LeftSide extends VBox {
    private GoodLabel labelOne;
    private GoodLabel labelTwo;
    private GoodLabel labelFirst;
    private GoodLabel labelSecond;
    private GoodLabel labelThird;
    private Timeline time;
    private int whiteTime = 600;
    private int blackTime = 600;
    private boolean isWhiteTurn = true;
    private GameScreen gameScreen;

    /**
     * Constructor
     * @param gameScreen
     */
    public LeftSide(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        labelOne = new GoodLabel(this);
        labelTwo = new GoodLabel(this);
        labelFirst = new GoodLabel(this);
        labelSecond = new GoodLabel(this);
        labelThird = new GoodLabel(this);
        getChildren().addAll(labelOne, labelFirst, labelSecond, labelThird, labelTwo);
    }

    /**
     * Gets the width from the whole GUI.
     * @return GUI width
     */
    public double getWidthFromChessGUI() {
        return gameScreen.getWidthFromChessGUI();
    }

    /**
     * Gets the height from the whole GUI.
     * @return GUI height
     */
    public double getHeightFromChessGUI() {
        return gameScreen.getHeightFromChessGUI();
    }

    /**
     * Changes timer.
     */
    public void changeTimer() {
        isWhiteTurn = !isWhiteTurn;
    }

    /**
     * Stops the time.
     */
    public void stopTime(){
        labelFirst.setText("");
        labelThird.setText("");
    }

    /**
     * Updates the graphics.
     */
    public void updateGraphics(){
        setMinSize(getWidthFromChessGUI() * 0.15, getHeightFromChessGUI() * 0.7);
        setMaxSize(getWidthFromChessGUI() * 0.15, getHeightFromChessGUI() * 0.7);
        labelFirst.updateGraphics();
        labelSecond.updateGraphics();
        labelThird.updateGraphics();
        labelOne.updateGraphics();
        labelTwo.updateGraphics();
    }
}
