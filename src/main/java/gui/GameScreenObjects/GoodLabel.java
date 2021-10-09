package gui.GameScreenObjects;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class GoodLabel extends Label {
    private LeftSide leftSide;
    public GoodLabel(LeftSide leftSide){
        this.leftSide = leftSide;
//        setStyle("-fx-font: 20px \"Verdana\";");
//        setMinSize(leftSide.getWidthFromChessGUI() * 0.15, leftSide.getHeightFromChessGUI() * 0.7 / 5);
//        setMaxSize(leftSide.getWidthFromChessGUI() * 0.15, leftSide.getHeightFromChessGUI() * 0.7 / 5);
        updateGraphics();
        setAlignment(Pos.CENTER);

    }

    public void updateGraphics(){

        setMinSize(leftSide.getWidthFromChessGUI() * 0.15, leftSide.getHeightFromChessGUI() * 0.7 / 5);
        setMaxSize(leftSide.getWidthFromChessGUI() * 0.15, leftSide.getHeightFromChessGUI() * 0.7 / 5);
        System.out.println(leftSide.getWidthFromChessGUI()*0.15);
        switch((int)leftSide.getWidthFromChessGUI()){
            case 200:
                System.out.println("200");
                setStyle("-fx-font: 4px \"Verdana\";");
                break;
            case 400:
                System.out.println("400");
                setStyle("-fx-font: 9px \"Verdana\";");
                break;
            case 600:
                System.out.println("600");
                setStyle("-fx-font: 13px \"Verdana\";");
                break;
            case 800:
                System.out.println("800");
                setStyle("-fx-font: 18px \"Verdana\";");
                break;
            case 1000:
                System.out.println("1000");
                setStyle("-fx-font: 23px \"Verdana\";");
                break;
            default:
                setStyle("-fx-font: 20px \"Verdana\";");
        }
    }
}
