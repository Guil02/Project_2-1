package gui.PlayerSelection;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class PlayerSelectionLabel extends HBox {

    public PlayerSelectionLabel() {
        Label labelPlayerOne = new Label("Player 1");
        Label labelPlayerTwo = new Label("Player 2");
        labelPlayerOne.setFont(new Font("Verdana", 30));
        labelPlayerTwo.setFont(new Font("Verdana", 30));

        labelPlayerOne.setMinSize(188,58);
        labelPlayerOne.setMaxSize(188,58);
        labelPlayerTwo.setMinSize(188,58);
        labelPlayerTwo.setMaxSize(188,58);

        labelPlayerOne.setAlignment(Pos.CENTER);
        labelPlayerTwo.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        getChildren().addAll(labelPlayerOne,labelPlayerTwo);
    }
}
