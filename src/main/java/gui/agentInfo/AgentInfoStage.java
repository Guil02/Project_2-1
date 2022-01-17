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
        infoList.getChildren().add(new InfoHBox("Human", "Human input, no agent!", false));
        infoList.getChildren().add(new InfoHBox("Simple Search Agent", "Uses an expectiminimax-search-tree of a certain depth to select a move.", true));
        infoList.getChildren().add(new InfoHBox("Random Agent", "Simply performs a random move, without any strategy.", false));
        infoList.getChildren().add(new InfoHBox("Greedy Agent", "Tries to take the most valuable piece in each round, performs random move if no piece can be taken.", true));
        infoList.getChildren().add(new InfoHBox("NN Agent", "Based on the expectiminimax-search-tree, it uses a neural network to adapt weights of the board evaluation.", false));
        infoList.getChildren().add(new InfoHBox("Cheating Agent", "It is basically a simple search agent but is not dependent on the roll of a dice and can move any piece each round.", true));
        infoList.getChildren().add(new InfoHBox("GA Agent", "Based on the expectiminimax-search-tree, it uses a genetic algorithm to improve the weights of the board evaluation.", false));
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
