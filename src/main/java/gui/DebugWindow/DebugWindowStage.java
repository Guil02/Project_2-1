package gui.DebugWindow;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    public DebugWindowStage() {

        this.setTitle("Debug Info");
        this.setHeight(400);
        this.setWidth(400);
        isOnPause = false; // Not on pause for agents by default

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
        bottomRow.setStyle("-fx-background-color: #557799;");
        playPauseButton = new Button("Pause");
        playPauseButton.setOnAction(e -> {
            if (!isOnPause) {
                playPauseButton.setText("Play");
                stepButton.setDisable(false);
                isOnPause = true;
                // TODO: implement pause functionality
            }
            else {
                playPauseButton.setText("Pause");
                stepButton.setDisable(true);
                isOnPause = false;
                // TODO: implement play functionality
            }
        });
        stepButton = new Button("Step");
        stepButton.setOnAction(e -> {

        });
        stepButton.setDisable(true);
        speedSlider = new Slider();
        speedSlider.setMin(0);
        speedSlider.setMax(1000);
        speedSlider.setValue(1000);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.valueProperty().addListener(e -> {
            delayMS = (int) speedSlider.getValue();
        });
        bottomRow.getChildren().addAll(playPauseButton, stepButton, new Text("Delay:"), speedSlider);
        root.setBottom(bottomRow);
    }

    public void incrementPlyCount() {
        plyCount++;
        plyCountText.setText(Integer.toString(plyCount));
    }
}
