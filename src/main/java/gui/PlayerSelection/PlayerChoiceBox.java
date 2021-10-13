package gui.PlayerSelection;
import javafx.scene.control.ChoiceBox;

/**
 * a class to design menus styles based on the chosen size
 */
public class PlayerChoiceBox extends ChoiceBox<String> {
    public static final int DEFAULT_WIDTH = 188;
    public static final int DEFAULT_HEIGHT = 58;
    private PlayerSelectionChoiceBox playerSelectionChoiceBox;
    private int number;

    /**
     * @param playerSelectionChoiceBox reference to the playerSelectionChoiceBox it is in
     * @param number the number of the player
     */
    public PlayerChoiceBox(PlayerSelectionChoiceBox playerSelectionChoiceBox, int number){
        this.playerSelectionChoiceBox = playerSelectionChoiceBox;
        this.number = number;
        setMinSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setStyle("-fx-font: 30px \"Verdana\";");
        getItems().add("Human");
        getItems().add("AI");
        setOnAction(event -> {
            int selectedIndex = getSelectionModel().getSelectedIndex();
            playerSelectionChoiceBox.setPlayer(number, selectedIndex);
        });
    }

    /**
     * @param size the size to which it needs to be changed
     */
    public void updateGraphics(int size) {
        switch(size){
            case 1:
                setStyle("-fx-font: 7px \"Verdana\";");
                setMinSize(DEFAULT_WIDTH*0.25, DEFAULT_HEIGHT*0.25);
                setMaxSize(DEFAULT_WIDTH*0.25, DEFAULT_HEIGHT*0.25);
                break;
            case 2:
                setStyle("-fx-font: 15px \"Verdana\";");
                setMinSize(DEFAULT_WIDTH*0.5, DEFAULT_HEIGHT*0.5);
                setMaxSize(DEFAULT_WIDTH*0.5, DEFAULT_HEIGHT*0.5);
                break;
            case 3:
                setStyle("-fx-font: 22px \"Verdana\";");
                setMinSize(DEFAULT_WIDTH*0.75, DEFAULT_HEIGHT*0.75);
                setMaxSize(DEFAULT_WIDTH*0.75, DEFAULT_HEIGHT*0.75);
                break;
            case 4:
                setStyle("-fx-font: 30px \"Verdana\";");
                setMinSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
                setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
                break;
            case 5:
                setStyle("-fx-font: 37px \"Verdana\";");
                setMinSize(DEFAULT_WIDTH*1.25, DEFAULT_HEIGHT*1.25);
                setMaxSize(DEFAULT_WIDTH*1.25, DEFAULT_HEIGHT*1.25);
                break;
        }
    }
}
