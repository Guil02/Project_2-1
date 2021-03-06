package gui.gameScreenObjects;

import controller.GraphicsConnector;
import gui.sceneLayouts.GameScreen;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Display that shows up during promotion.
 */
public class PromotionDisplay extends HBox {

    // Variables
    private GraphicsConnector graphicsConnector;
    private GameScreen gameScreen;
    public static final double DIVIDER_HEIGHT = 0.15;
    private static final double DIVIDER_WIDTH = 0.7;
    PromotionButton p1;
    PromotionButton p2;
    PromotionButton p3;
    PromotionButton p4;

    /**
     * Constructor
     * @param graphicsConnector attached GraphicsConnector
     * @param gameScreen current GameScreen instance
     */
    public PromotionDisplay(GraphicsConnector graphicsConnector, GameScreen gameScreen) {
        this.graphicsConnector = graphicsConnector;
        this.gameScreen = gameScreen;
        setMinSize(getWidthFromTop()* DIVIDER_WIDTH, getHeightFromTop()* DIVIDER_HEIGHT);
        setMaxSize(getWidthFromTop()* DIVIDER_WIDTH, getHeightFromTop()* DIVIDER_HEIGHT);
        setAlignment(Pos.CENTER);
        p1 = new PromotionButton(this, graphicsConnector, 1);
        p2 = new PromotionButton(this, graphicsConnector, 2);
        p3 = new PromotionButton(this, graphicsConnector, 3);
        p4 = new PromotionButton(this, graphicsConnector, 4);
        getChildren().addAll(p1, p2, p3, p4);
        setInvisible();
    }

    /**
     * Gets with from ChessGUI.
     * @return width
     */
    public double getWidthFromTop(){
        return gameScreen.getWidthFromChessGUI();
    }

    /**
     * Gets height from ChessGUI.
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
        p1.updateGraphics();
        p2.updateGraphics();
        p3.updateGraphics();
        p4.updateGraphics();
    }

    /**
     * Shows the promotion dialog.
     */
    public void showPromotionDialog() {
        p1.setVisible(true);
        p2.setVisible(true);
        p3.setVisible(true);
        p4.setVisible(true);
        p1.updateGraphics();
        p2.updateGraphics();
        p3.updateGraphics();
        p4.updateGraphics();
    }

    /**
     * Sets the dialog invisible.
     */
    public void setInvisible(){
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);
    }
}
