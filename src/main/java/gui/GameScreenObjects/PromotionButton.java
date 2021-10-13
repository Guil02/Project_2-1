package gui.GameScreenObjects;

import controller.GraphicsConnector;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PromotionButton extends Button {
    private PromotionDisplay promotionDisplay;
    private GraphicsConnector graphicsConnector;
    private static final double DIVIDER = 10.0;
    private final int type;

    public PromotionButton(PromotionDisplay promotionDisplay, GraphicsConnector graphicsConnector, int type) {
        this.promotionDisplay = promotionDisplay;
        this.graphicsConnector = graphicsConnector;
        this.type = type;
        setImage(graphicsConnector.getPromotionImage(type));
        setStyle("-fx-background-color: #59913a;");
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            promotionDisplay.setInvisible();
            ChessSpot.setPromotionLock(true);
            graphicsConnector.doPromotion(type);
            }
        });
    }


    public void setImage(String URL){
        Image image = new Image(URL,promotionDisplay.getWidthFromTop()/DIVIDER, promotionDisplay.getHeightFromTop()/DIVIDER, true, false);
        ImageView imageView = new ImageView(image);
        setGraphic(imageView);
    }

    public void updateGraphics(){
        setImage(graphicsConnector.getPromotionImage(type));
    }
}
