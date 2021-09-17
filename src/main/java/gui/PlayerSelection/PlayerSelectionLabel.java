package gui.PlayerSelection;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class PlayerSelectionLabel extends HBox {
    public static final int DEFAULT_WIDTH = 188;
    public static final int DEFAULT_HEIGHT = 58;
    private Label labelPlayerOne;
        private Label labelPlayerTwo;
    public PlayerSelectionLabel() {
        this.labelPlayerOne = new Label("Player 1");
        this.labelPlayerTwo = new Label("Player 2");
        labelPlayerOne.setFont(new Font("Verdana", 30));
        labelPlayerTwo.setFont(new Font("Verdana", 30));

        labelPlayerOne.setMinSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        labelPlayerOne.setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        labelPlayerTwo.setMinSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        labelPlayerTwo.setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        labelPlayerOne.setAlignment(Pos.CENTER);
        labelPlayerTwo.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        getChildren().addAll(labelPlayerOne,labelPlayerTwo);
    }

    public void updateGraphics(int size) {
        switch(size){
            case 1:
                labelPlayerOne.setStyle("-fx-font: 7px \"Verdana\";");
                labelPlayerOne.setMinSize(DEFAULT_WIDTH*0.25, DEFAULT_HEIGHT*0.25);
                labelPlayerOne.setMaxSize(DEFAULT_WIDTH*0.25, DEFAULT_HEIGHT*0.25);

                labelPlayerTwo.setStyle("-fx-font: 7px \"Verdana\";");
                labelPlayerTwo.setMinSize(DEFAULT_WIDTH*0.25, DEFAULT_HEIGHT*0.25);
                labelPlayerTwo.setMaxSize(DEFAULT_WIDTH*0.25, DEFAULT_HEIGHT*0.25);
                break;
            case 2:
                labelPlayerOne.setStyle("-fx-font: 15px \"Verdana\";");
                labelPlayerOne.setMinSize(DEFAULT_WIDTH*0.5, DEFAULT_HEIGHT*0.5);
                labelPlayerOne.setMaxSize(DEFAULT_WIDTH*0.5, DEFAULT_HEIGHT*0.5);

                labelPlayerTwo.setStyle("-fx-font: 15px \"Verdana\";");
                labelPlayerTwo.setMinSize(DEFAULT_WIDTH*0.5, DEFAULT_HEIGHT*0.5);
                labelPlayerTwo.setMaxSize(DEFAULT_WIDTH*0.5, DEFAULT_HEIGHT*0.5);
                break;
            case 3:
                labelPlayerOne.setStyle("-fx-font: 22px \"Verdana\";");
                labelPlayerOne.setMinSize(DEFAULT_WIDTH*0.75, DEFAULT_HEIGHT*0.75);
                labelPlayerOne.setMaxSize(DEFAULT_WIDTH*0.75, DEFAULT_HEIGHT*0.75);

                labelPlayerTwo.setStyle("-fx-font: 22px \"Verdana\";");
                labelPlayerTwo.setMinSize(DEFAULT_WIDTH*0.75, DEFAULT_HEIGHT*0.75);
                labelPlayerTwo.setMaxSize(DEFAULT_WIDTH*0.75, DEFAULT_HEIGHT*0.75);
                break;
            case 4:
                labelPlayerOne.setStyle("-fx-font: 30px \"Verdana\";");
                labelPlayerOne.setMinSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
                labelPlayerOne.setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

                labelPlayerTwo.setStyle("-fx-font: 30px \"Verdana\";");
                labelPlayerTwo.setMinSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
                labelPlayerTwo.setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
                break;
            case 5:
                labelPlayerOne.setStyle("-fx-font: 37px \"Verdana\";");
                labelPlayerOne.setMinSize(DEFAULT_WIDTH*1.25, DEFAULT_HEIGHT*1.25);
                labelPlayerOne.setMaxSize(DEFAULT_WIDTH*1.25, DEFAULT_HEIGHT*1.25);

                labelPlayerTwo.setStyle("-fx-font: 37px \"Verdana\";");
                labelPlayerTwo.setMinSize(DEFAULT_WIDTH*1.25, DEFAULT_HEIGHT*1.25);
                labelPlayerTwo.setMaxSize(DEFAULT_WIDTH*1.25, DEFAULT_HEIGHT*1.25);
                break;
        }
    }
}
