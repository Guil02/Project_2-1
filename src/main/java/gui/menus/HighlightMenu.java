package gui.menus;

import gui.ChessGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Menu for the highlight property.
 * (shows moves while a piece is dragged)
 */
public class HighlightMenu extends Menu {
    /**
     * Constructor
     */
    public HighlightMenu(){
        setText("Highlighting Setting");
        MenuItem m1 = new MenuItem("on");
        MenuItem m2 = new MenuItem("off");
        // Style
        setStyle("-fx-font: 10px \"Verdana\";");
        m1.setStyle("-fx-font: 10px \"Verdana\";");
        m2.setStyle("-fx-font: 10px \"Verdana\";");
        // Event handling
        EventHandler<ActionEvent> event1 = event -> ChessGUI.COLOR=true;
        EventHandler<ActionEvent> event2 = event -> ChessGUI.COLOR=false;
        m1.setOnAction(event1);
        m2.setOnAction(event2);
        // Add content
        getItems().addAll(m1,m2);
    }
}
