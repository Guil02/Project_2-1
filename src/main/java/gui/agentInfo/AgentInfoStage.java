package gui.agentInfo;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class represents the info window for all agents that a user can choose from.
 */
public class AgentInfoStage extends Stage {

    // Variables
    BorderPane root;
    VBox infoList; // List of all agents one can choose

    /**
     * Constructor
     */
    public AgentInfoStage() {
        this.setTitle("Agent Info");
        this.setWidth(800);
        this.setHeight(389);
        // Main content
        infoList = new VBox();
        infoList.setStyle("-fx-font: 14px \"Verdana\";");
        infoList.getChildren().add(new InfoHBox("Human", "Human input, no agent", false));
        infoList.getChildren().add(new InfoHBox("Search Agent", "Expectiminimax search", true));
        infoList.getChildren().add(new InfoHBox("Random Agent", "Performs random move", false));
        infoList.getChildren().add(new InfoHBox("TD Learning Agent", "TODO", true));
        infoList.getChildren().add(new InfoHBox("Take Agent", "Tries to take the most valuable piece in each round, \n performs random move if no piece can be taken.", false));
        infoList.getChildren().add(new InfoHBox("NN Agent", "Neural Network", true));
        infoList.getChildren().add(new InfoHBox("Cheating Agent", "Knows outcomes of the dice", false));
        // Set content
        root = new BorderPane();
        root.setCenter(infoList);
        this.setScene(new Scene(root));
    }
}

/**
 * Inner class for each row of the info window represented by a customized HBox.
 */
class InfoHBox extends HBox {

    // Variables
    Color left1 = Color.rgb(180,150,200);
    Color left2 = Color.rgb(200,150,180);
    Color right1 = Color.rgb(160,130,180);
    Color right2 = Color.rgb(180,130,160);

    /**
     * Constructor
     * @param name
     * @param description
     * @param alternateColour
     */
    public InfoHBox(String name, String description, boolean alternateColour) {
        StackPane leftStack = new StackPane();
        StackPane rightStack = new StackPane();
        // Left side (name)
        Rectangle rectLeft = new Rectangle();
        rectLeft.setWidth(150);
        rectLeft.setHeight(50);
        // Right side (description)
        Rectangle rectRight = new Rectangle();
        rectRight.setWidth(650);
        rectRight.setHeight(50);
        // Colour of rectangles
        if (!alternateColour) {
            rectLeft.setFill(left1);
            rectRight.setFill(right1);
        }
        else {
            rectLeft.setFill(left2);
            rectRight.setFill(right2);
        }
        leftStack.getChildren().addAll(rectLeft, new Text(name));
        rightStack.getChildren().addAll(rectRight, new Text(description));
        this.getChildren().addAll(leftStack, rightStack);
    }
}
