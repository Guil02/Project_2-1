package gui.PlayerSelection;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class StartButton extends Button {
    public StartButton(PlayerSelection playerSelection){
        setText("start game");
        setFont(new Font("Verdana", 30));
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playerSelection.startGame();
            }
        });
    }
}
