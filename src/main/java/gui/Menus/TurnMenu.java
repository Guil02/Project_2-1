package gui.Menus;

import gui.ChessGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
/**
 * a class to define turns based on user's choices in the begining of the game
 */

public class TurnMenu extends Menu {
    public TurnMenu(){
        setText("turn settings");
        MenuItem m1 = new MenuItem("on");
        MenuItem m2 = new MenuItem("off");

        setStyle("-fx-font: 10px \"Verdana\";");
        m1.setStyle("-fx-font: 10px \"Verdana\";");
        m2.setStyle("-fx-font: 10px \"Verdana\";");

        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChessGUI.TURN=true;
            }
        };

        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChessGUI.TURN=false;
            }
        };

        m1.setOnAction(event1);
        m2.setOnAction(event2);
        getItems().addAll(m1,m2);
    }
}
