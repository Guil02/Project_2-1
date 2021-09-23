package gui.GameScreenObjects;

import controller.GraphicsConnector;
import gui.SceneLayouts.GameScreen;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class DiceDisplay extends VBox {
    private GameScreen gameScreen;
    private DiceImageLabel diceImageLabelOne;
    private DiceImageLabel diceImageLabelTwo;
    private static final double DIVIDER  = 0.15;

    public DiceDisplay(GameScreen gameScreen, GraphicsConnector graphicsConnector) {
        this.gameScreen = gameScreen;
        diceImageLabelOne = new DiceImageLabel(this, graphicsConnector,1);
        diceImageLabelTwo = new DiceImageLabel(this, graphicsConnector,2);
        setMinSize(getWidthFromTop()*DIVIDER, getHeightFromTop());
        setMaxSize(getWidthFromTop()*DIVIDER, getHeightFromTop());
        setAlignment(Pos.CENTER);
        getChildren().addAll(diceImageLabelOne, diceImageLabelTwo);

    }

    public double getWidthFromTop(){
        return gameScreen.getWidthFromChessGUI();
    }

    public double getHeightFromTop() {
        return gameScreen.getHeightFromChessGUI();
    }

    public void updateGraphics(int size){
        setMinSize(getWidthFromTop()/DIVIDER, getHeightFromTop());
        setMaxSize(getWidthFromTop()/DIVIDER, getHeightFromTop() );
        diceImageLabelOne.updateGraphics(size);
    }
}
