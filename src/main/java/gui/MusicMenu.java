package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MusicMenu extends Menu {
    public MusicMenu(ChessGUI chessGUI){
        setText("music control");
        MenuItem play = new MenuItem("play");
        MenuItem pause = new MenuItem("pause");

        setStyle("-fx-font: 10px \"Verdana\";");
        play.setStyle("-fx-font: 10px \"Verdana\";");
        pause.setStyle("-fx-font: 10px \"Verdana\";");

        EventHandler<ActionEvent> playEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chessGUI.getMediaPlayer().play();
            }
        };
        EventHandler<ActionEvent> pauseEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chessGUI.getMediaPlayer().pause();
            }
        };
        play.setOnAction(playEvent);
        pause.setOnAction(pauseEvent);

        getItems().addAll(play,pause);

    }
}
