package gui.PlayerSelection;

import gui.SceneLayouts.StartScreen;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
/**
 * a class to change the first scene based on user's choices for sizes
 * and set the player choice and start the game
 */
public class PlayerSelection extends VBox {
    private StartScreen startScreen;
    private PlayerSelectionLabel playerSelectionLabel;
    private PlayerSelectionChoiceBox playerSelectionChoiceBox;
    private StartButton startButton;

    /**
     * @param startScreen reference to the start screen in which it is located
     */
    public PlayerSelection(StartScreen startScreen){
        this.startScreen = startScreen;
        setAlignment(Pos.CENTER);

        this.playerSelectionLabel = new PlayerSelectionLabel();
        this.playerSelectionChoiceBox = new PlayerSelectionChoiceBox(this);
        this.startButton = new StartButton(this);

        getChildren().addAll(playerSelectionLabel,playerSelectionChoiceBox,startButton);
    }

    /**
     * @param playerNumber number of the player
     * @param player type of player
     */
    public void setPlayer(int playerNumber, int player){
        startScreen.setPlayer(playerNumber, player);
    }

    /**
     * launches the game
     */
    public void startGame(){
        startScreen.startGame();
    }

    /**
     * @param width width of the screen
     * @param height height of the screen
     */
    public void updateGraphics(double width, double height) {
        setMinSize(width,height);
        setMaxSize(width,height);
        int size = getSize(width);
        playerSelectionLabel.updateGraphics(size);
        playerSelectionChoiceBox.updateGraphics(size);
        startButton.updateGraphics(size);

    }

    /**
     * @param width width of the screen
     * @return returns an int of the size
     */
    private int getSize(double width){
        int size;
        switch((int) width){
            case 200:
                size = 1;
                break;
            case 400:
                size = 2;
                break;
            case 600:
                size = 3;
                break;
            case 800:
                size = 4;
                break;
            case 1000:
                size = 5;
                break;
            default:
                size = 4;
                break;
        }
        return size;
    }
}
