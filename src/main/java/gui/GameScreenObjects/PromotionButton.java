package gui.GameScreenObjects;

import controller.GraphicsConnector;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the button that shows up during a promotion.
 */
public class PromotionButton extends Button {

    // Variables
    private PromotionDisplay promotionDisplay;
    private GraphicsConnector graphicsConnector;
    private static final double DIVIDER = 10.0;
    private final int type;

    /**
     * Constructor
     * @param promotionDisplay display
     * @param graphicsConnector attached GraphicsConnector
     * @param type type code
     */
    public PromotionButton(PromotionDisplay promotionDisplay, GraphicsConnector graphicsConnector, int type) {
        this.promotionDisplay = promotionDisplay;
        this.graphicsConnector = graphicsConnector;
        this.type = type;
        setImage(graphicsConnector.getPromotionImage(type));
        setStyle("-fx-background-color: #59913a;");
        setOnAction(event -> {
            promotionDisplay.setInvisible();
            ChessSpot.setPromotionLock(true);
            graphicsConnector.doPromotion(type);
        });
    }

    /**
     * Sets an image.
     * @param URL image path
     */
    public void setImage(String URL){
        Image image = new Image(URL,promotionDisplay.getWidthFromTop()/DIVIDER, promotionDisplay.getHeightFromTop()/DIVIDER, true, false);
        ImageView imageView = new ImageView(image);
        setGraphic(imageView);
    }

    /**
     * Updates the graphics.
     */
    public void updateGraphics(){
        setImage(graphicsConnector.getPromotionImage(type));
    }
}
