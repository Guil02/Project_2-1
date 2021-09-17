package gui.PlayerSelection;

import gui.SceneLayouts.StartScreen;
import javafx.scene.layout.VBox;

public class PlayerSelection extends VBox {
    private StartScreen startScreen;
    public PlayerSelection(StartScreen startScreen){
        this.startScreen = startScreen;

        PlayerSelectionLabel playerSelectionLabel = new PlayerSelectionLabel();

        PlayerSelectionChoiceBox playerSelectionChoiceBox = new PlayerSelectionChoiceBox(this);

        getChildren().addAll(playerSelectionLabel);
    }

    public void setPlayer(int playerOne, int playerTwo){
        startScreen.setPlayer(playerOne, playerTwo);
    }
}
