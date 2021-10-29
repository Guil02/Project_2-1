package gui.Menus;

import gui.ChessGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class StartAIButton extends Menu {
    public StartAIButton(ChessGUI chessGUI) {
        MenuItem m1 = new MenuItem("start");
        setStyle("-fx-font: 10px \"Verdana\";");
        m1.setStyle("-fx-font: 10px \"Verdana\";");
        setText("start ai");
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
