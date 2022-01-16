package gui.GameScreenObjects;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * GoodLabel
 */
public class GoodLabel extends Label {
    private LeftSide leftSide;
    public GoodLabel(LeftSide leftSide){
        this.leftSide = leftSide;
        updateGraphics();
        setAlignment(Pos.CENTER);
    }

    /**
     * Updates graphics.
     */
    public void updateGraphics(){
        setMinSize(leftSide.getWidthFromChessGUI() * 0.15, leftSide.getHeightFromChessGUI() * 0.7 / 5);
        setMaxSize(leftSide.getWidthFromChessGUI() * 0.15, leftSide.getHeightFromChessGUI() * 0.7 / 5);
        switch((int)leftSide.getWidthFromChessGUI()){
            case 200:
                setStyle("-fx-font: 4px \"Verdana\";");
                break;
            case 400:
                setStyle("-fx-font: 9px \"Verdana\";");
                break;
            case 600:
                setStyle("-fx-font: 13px \"Verdana\";");
                break;
            case 800:
                setStyle("-fx-font: 18px \"Verdana\";");
                break;
            case 1000:
                setStyle("-fx-font: 23px \"Verdana\";");
                break;
            default:
                setStyle("-fx-font: 16px \"Verdana\";");
        }
    }
}
