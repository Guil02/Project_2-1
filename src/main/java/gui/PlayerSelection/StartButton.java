package gui.PlayerSelection;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
/**
 * a class to change the first scene to the game scene based on user's choices
 */
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

    public void updateGraphics(int size) {
        switch(size){
            case 1:
                setFont(new Font("Verdana", 7.5));
                break;
            case 2:
                setFont(new Font("Verdana", 15));
                break;
            case 3:
                setFont(new Font("Verdana", 22.5));
                break;
            case 4:
                setFont(new Font("Verdana", 30));
                break;
            case 5:
                setFont(new Font("Verdana", 37.5));
                break;
            default:
                setFont(new Font("Verdana", 30));
                break;
        }
    }
}
