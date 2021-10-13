package gui.PlayerSelection;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
/**
 * a class to design player menus styles
 */

public class PlayerSelectionChoiceBox extends HBox {
    private PlayerSelection playerSelection;
    private PlayerChoiceBox playerChoiceBoxOne;
    private PlayerChoiceBox playerChoiceBoxTwo;

    /**
     * @param playerSelection reference to the playerSelection it is in
     */
    public PlayerSelectionChoiceBox(PlayerSelection playerSelection){
        this.playerSelection = playerSelection;
        setPadding(new Insets(15, 12, 15, 12));
        setAlignment(Pos.CENTER);

        this.playerChoiceBoxOne = new PlayerChoiceBox(this,1);
        this.playerChoiceBoxTwo = new PlayerChoiceBox(this,2);

        getChildren().addAll(playerChoiceBoxOne, playerChoiceBoxTwo);
    }

    /**
     * @param playerNumber
     * @param player
     */
    public void setPlayer(int playerNumber, int player){
        playerSelection.setPlayer(playerNumber, player);
    }

    /**
     * @param size
     */
    public void updateGraphics(int size) {
        playerChoiceBoxOne.updateGraphics(size);
        playerChoiceBoxTwo.updateGraphics(size);
    }
}
