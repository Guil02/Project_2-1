package gui.DebugWindow;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DebugWindowStage extends Stage {
    private int plyCount;
    private Text plyCountText;

    public DebugWindowStage() {

        this.setTitle("Debug Info");
        this.setHeight(400);
        this.setWidth(400);

        BorderPane root = new BorderPane();
        plyCount = 0;
        plyCountText = new Text("0");
        root.setCenter(plyCountText);

        this.setScene(new Scene(root));
    }

    public void updateInformation() {
        plyCount++;
        plyCountText.setText(Integer.toString(plyCount));
    }
}
