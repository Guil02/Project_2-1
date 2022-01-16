package gui.menus;

import gui.ChessGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
/**
 * a class to change menus highlight based on user's choice
 */
public class HighlightMenu extends Menu {
    /**
     *
     */
    public HighlightMenu(){
        setText("Highlighting Setting");
        MenuItem m1 = new MenuItem("on");
        MenuItem m2 = new MenuItem("off");

        setStyle("-fx-font: 10px \"Verdana\";");
        m1.setStyle("-fx-font: 10px \"Verdana\";");
        m2.setStyle("-fx-font: 10px \"Verdana\";");

        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChessGUI.COLOR=true;
            }
        };

        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChessGUI.COLOR=false;
            }
        };

        m1.setOnAction(event1);
        m2.setOnAction(event2);
        getItems().addAll(m1,m2);
    }
}
