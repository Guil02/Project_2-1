package gui.PlayerSelection;

import gui.SceneLayouts.StartScreen;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class PlayerSelection extends VBox {
    private StartScreen startScreen;
    public PlayerSelection(StartScreen startScreen){
        this.startScreen = startScreen;
        setAlignment(Pos.CENTER);

        PlayerSelectionLabel playerSelectionLabel = new PlayerSelectionLabel();
        PlayerSelectionChoiceBox playerSelectionChoiceBox = new PlayerSelectionChoiceBox(this);
        StartButton startButton = new StartButton(this);

        getChildren().addAll(playerSelectionLabel,playerSelectionChoiceBox,startButton);
    }

    public void setPlayer(int playerNumber, int player){
        startScreen.setPlayer(playerNumber, player);
    }

    public void startGame(){
        startScreen.startGame();
    }
}
