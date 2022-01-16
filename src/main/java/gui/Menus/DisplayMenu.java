package gui.Menus;

import gui.ChessGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * A class for menus which appear in the first scene of the game and handles user events.
 */
public class DisplayMenu extends Menu {

    /**
     * Constructor
     * @param chessGUI reference to the chessGUI in which it is located
     */
    public DisplayMenu(ChessGUI chessGUI){
        setText("Display Options");
        MenuItem m1 = new MenuItem("200x200");
        MenuItem m2 = new MenuItem("400x400");
        MenuItem m3 = new MenuItem("600x600");
        MenuItem m4 = new MenuItem("800x800");
        MenuItem m5 = new MenuItem("1000x1000");
        // Style
        setStyle("-fx-font: 10px \"Verdana\";");
        m1.setStyle("-fx-font: 10px \"Verdana\";");
        m2.setStyle("-fx-font: 10px \"Verdana\";");
        m3.setStyle("-fx-font: 10px \"Verdana\";");
        m4.setStyle("-fx-font: 10px \"Verdana\";");
        m5.setStyle("-fx-font: 10px \"Verdana\";");
        // Event handling
        EventHandler<ActionEvent> event1 = event -> chessGUI.updateDisplaySize(200);
        EventHandler<ActionEvent> event2 = event -> chessGUI.updateDisplaySize(400);
        EventHandler<ActionEvent> event3 = event -> chessGUI.updateDisplaySize(600);
        EventHandler<ActionEvent> event4 = event -> chessGUI.updateDisplaySize(800);
        EventHandler<ActionEvent> event5 = event -> chessGUI.updateDisplaySize(1000);
        m1.setOnAction(event1);
        m2.setOnAction(event2);
        m3.setOnAction(event3);
        m4.setOnAction(event4);
        m5.setOnAction(event5);
        // Add contents
        getItems().addAll(m1,m2,m3,m4,m5);
    }
}
