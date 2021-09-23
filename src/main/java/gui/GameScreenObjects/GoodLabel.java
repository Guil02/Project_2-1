package gui.GameScreenObjects;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class GoodLabel extends Label {

    public GoodLabel(LeftSide leftSide){
        setFont(new Font("Verdana", 20));
        setMinSize(leftSide.getWidthFromChessGUI() * 0.15, leftSide.getHeightFromChessGUI() * 0.7 / 5);
        setMaxSize(leftSide.getWidthFromChessGUI() * 0.15, leftSide.getHeightFromChessGUI() * 0.7 / 5);

    }
}
