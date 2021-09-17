package gui.PlayerSelection;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class PlayerSelectionChoiceBox extends HBox {
    private PlayerSelection playerSelection;
    public PlayerSelectionChoiceBox(PlayerSelection playerSelection){
        this.playerSelection = playerSelection;
        setPadding(new Insets(15, 12, 15, 12));
        setAlignment(Pos.CENTER);

        PlayerChoiceBox playerChoiceBoxOne = new PlayerChoiceBox(this,1);
        PlayerChoiceBox playerChoiceBoxTwo = new PlayerChoiceBox(this,2);

        getChildren().addAll(playerChoiceBoxOne, playerChoiceBoxTwo);
    }

    public void setPlayer(int playerNumber, int player){
        playerSelection.setPlayer(playerNumber, player);
    }
}
