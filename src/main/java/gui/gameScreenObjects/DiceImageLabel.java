package gui.gameScreenObjects;

import controller.GraphicsConnector;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Image of the dice.
 */
public class DiceImageLabel extends Label {
    private DiceDisplay diceDisplay;
    private GraphicsConnector graphicsConnector;
    private static final double DIVIDER = 10.0;
    private int type;
    public DiceImageLabel(DiceDisplay diceDisplay, GraphicsConnector graphicsConnector, int type){
        this.diceDisplay = diceDisplay;
        this.graphicsConnector =graphicsConnector;
        this.type = type;
        setAlignment(Pos.CENTER);
        setImage(graphicsConnector.getDiceImage(type));
    }

    /**
     * Sets the image.
     * @param URL file paht
     */
    public void setImage(String URL){
        Image image = new Image(URL,diceDisplay.getWidthFromTop()/DIVIDER, diceDisplay.getHeightFromTop()/DIVIDER, false, false);
        ImageView imageView = new ImageView(image);
        setGraphic(imageView);
    }

    /**
     * Updates the graphics on the dice.
     */
    public void updateGraphics(){
        setImage(graphicsConnector.getDiceImage(type));
    }

    /**
     * Updates the number on the dice.
     */
    public void updateDice(){
        setImage(graphicsConnector.getDiceImage(type));
    }
}
