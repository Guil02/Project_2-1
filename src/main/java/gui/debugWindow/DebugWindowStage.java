package gui.debugWindow;

import controller.GameRunner;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class represents the debug window next to the game.
 */
public class DebugWindowStage extends Stage {
    private int plyCount;
    private Text plyCountText;
    BorderPane root;
    GridPane gridPane;
    private Button playPauseButton;
    private Button stepButton;
    private HBox bottomRow;
    private Slider speedSlider; // Slider to set delay in ms for an agent move
    public static boolean isOnPause;
    public static int delayMS = 200;
    public static final Object pauseLock = new Object();
    private GameRunner gameRunner;

    /**
     * Constructor
     */
    public DebugWindowStage(GameRunner gr) {
        this.gameRunner = gr;

        this.setTitle("Debug Info");
        this.setHeight(300);
        this.setWidth(500);
        isOnPause = false; // Not on pause for agents by default
        setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.setX((screenBounds.getWidth() / 2) - 900);
        this.setY((screenBounds.getHeight() / 2) - 300);

        initComponents();
        this.setScene(new Scene(root));
    }

    /**
     * Init all the components on the grid.
     */
    public void initComponents() {
        // Layouts
        root = new BorderPane();
        gridPane = new GridPane();
        bottomRow = new HBox();
        Font defaultFont = new Font("Verdana", 20);
        // Ply count
        plyCount = 0;
        plyCountText = new Text("0");
        plyCountText.setFont(defaultFont);
        // Player codes
        int playerOneCode = this.gameRunner.getBoard().getPlayer1();
        int playerTwoCode = this.gameRunner.getBoard().getPlayer2();
        // Center grid
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(0, 10, 0, 10));
        Text plyCountTextLeft = new Text("Ply count:");
        plyCountTextLeft.setFont(defaultFont);
        gridPane.add(plyCountTextLeft, 0, 0);
        gridPane.add(plyCountText, 1, 0);
        Text playerOneText = new Text("Player 1 code:");
        playerOneText.setFont(defaultFont);
        gridPane.add(playerOneText, 0, 1);
        Text playerOneCodeText = new Text(Integer.toString(playerOneCode));
        playerOneCodeText.setFont(defaultFont);
        gridPane.add(playerOneCodeText, 1, 1);
        Text playerTwoText = new Text("Player 2 code:");
        playerTwoText.setFont(defaultFont);
        gridPane.add(playerTwoText, 0, 2);
        Text playerTwoCodeText = new Text(Integer.toString(playerTwoCode));
        playerTwoCodeText.setFont(defaultFont);
        gridPane.add(playerTwoCodeText, 1, 2);
        root.setCenter(gridPane);
        // Bottom row
        bottomRow.setPadding(new Insets(10, 10, 10, 10));
        bottomRow.setSpacing(10);
        bottomRow.setStyle("-fx-background-color: #7799aa;");
        playPauseButton = new Button("Pause");
        playPauseButton.setFont(new Font("Verdana", 11)); //TODO change font
        playPauseButton.setOnAction(e -> {
            if (!isOnPause) { // When "Pause" is clicked
                playPauseButton.setText("Play");
                stepButton.setDisable(false);
                isOnPause = true;
            }
            else { // When "Play" is clicked
                playPauseButton.setText("Pause");
                stepButton.setDisable(true);
                isOnPause = false;
                interruptSleep();
            }
        });
        stepButton = new Button("Step");
        stepButton.setFont(new Font("Verdana", 11)); //TODO change font
        stepButton.setOnAction(e -> {
            interruptSleep();
        });
        stepButton.setDisable(true);
        speedSlider = new Slider();
        speedSlider.setStyle("-fx-font: 12px 'Verdana';");
        speedSlider.setPrefWidth(250);
        speedSlider.setMin(0);
        speedSlider.setMax(1000);
        speedSlider.setValue(200);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.setMajorTickUnit(200);
        speedSlider.valueProperty().addListener(e -> {
            delayMS = (int) speedSlider.getValue();
        });
        Text delayText = new Text("Delay (ms)");
        delayText.setFont(new Font("Verdana", 12));
        bottomRow.getChildren().addAll(playPauseButton, stepButton, delayText, speedSlider);
        root.setBottom(bottomRow);
    }

    /**
     * This method lets the game continue when it's paused.
     * It interrupts all sleep methods in threads that are not the GUI.
     */
    public void interruptSleep() {
        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }

    /**
     * Increments the internal counter by one.
     */
    public void incrementPlyCount() {
        plyCount++;
        plyCountText.setText(Integer.toString(plyCount));
    }
}
