package gui.PlayerSelection;

import javafx.scene.control.ChoiceBox;

public class PlayerChoiceBox extends ChoiceBox<String> {
    private PlayerSelectionChoiceBox playerSelectionChoiceBox;
    private int number;
    public PlayerChoiceBox(PlayerSelectionChoiceBox playerSelectionChoiceBox, int number){
        this.playerSelectionChoiceBox = playerSelectionChoiceBox;
        this.number = number;
        setMinSize(188,58);
        setMaxSize(188,58);
        setStyle("-fx-font: 30px \"Verdana\";");
        getItems().add("Human");
        getItems().add("AI");
        setOnAction(event -> {
            int selectedIndex = getSelectionModel().getSelectedIndex();
            playerSelectionChoiceBox.setPlayer(number, selectedIndex);
        });
    }
}
