package gui.menus;

import gui.ChessGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * This class represents the Start AI button.
 */
public class StartAIButton extends Menu {
    public StartAIButton(ChessGUI chessGUI) {
        MenuItem m1 = new MenuItem("Start");
        setStyle("-fx-font: 10px \"Verdana\";");
        m1.setStyle("-fx-font: 10px \"Verdana\";");
        setText("Start AI");
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chessGUI.launchAI();
            }
        };
        m1.setOnAction(event1);
        getItems().addAll(m1);
    }
}
