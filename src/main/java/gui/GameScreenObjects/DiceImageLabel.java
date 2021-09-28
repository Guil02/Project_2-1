package gui.GameScreenObjects;

import controller.GraphicsConnector;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    public void setImage(String URL){
        System.out.println(URL);
        Image image = new Image(URL,diceDisplay.getWidthFromTop()/DIVIDER, diceDisplay.getHeightFromTop()/DIVIDER, false, false);
        ImageView imageView = new ImageView(image);
        setGraphic(imageView);
    }

    public void updateGraphics(){
        setImage(graphicsConnector.getDiceImage(type));
    }

    public void updateDice(){
        setImage(graphicsConnector.getDiceImage(type));
    }
}
