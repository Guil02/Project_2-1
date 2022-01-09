package gui.DebugWindow;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    public static int delayMS = 1000;
    public static final Object pauseLock = new Object();

    /**
     * Constructor
     */
    public DebugWindowStage() {

        this.setTitle("Debug Info");
        this.setHeight(400);
        this.setWidth(400);
        isOnPause = false; // Not on pause for agents by default
        setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.setX((screenBounds.getWidth() / 2) - 800);
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

        // Ply count
        plyCount = 0;
        plyCountText = new Text("0");

        // Center grid
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(0, 10, 0, 10));
        gridPane.add(new Text("Ply count:"), 0, 0);
        gridPane.add(plyCountText, 1, 0);
        root.setCenter(gridPane);

        // Bottom row
        bottomRow.setPadding(new Insets(10, 10, 10, 10));
        bottomRow.setSpacing(10);
        bottomRow.setStyle("-fx-background-color: #7799aa;");
        playPauseButton = new Button("Pause");
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
        stepButton.setOnAction(e -> {
            interruptSleep();
        });
        stepButton.setDisable(true);
        speedSlider = new Slider();
        speedSlider.setMin(0);
        speedSlider.setMax(1000);
        speedSlider.setValue(1000);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.setMajorTickUnit(200);
        speedSlider.valueProperty().addListener(e -> {
            delayMS = (int) speedSlider.getValue();
        });
        bottomRow.getChildren().addAll(playPauseButton, stepButton, new Text("Delay:"), speedSlider);
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
