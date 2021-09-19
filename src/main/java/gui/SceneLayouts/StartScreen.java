package gui.SceneLayouts;

import gui.ChessGUI;
import gui.Menus.DisplayMenu;
import gui.PlayerSelection.PlayerSelection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StartScreen extends BorderPane {
    private ChessGUI chessGUI;
    private int playerOne = 1;
    private int playerTwo = 1;
    private PlayerSelection playerSelection;

    public StartScreen(ChessGUI chessGUI) {
        this.chessGUI = chessGUI;
        setStyle("-fx-background-color: #336699;");

        this.playerSelection = new PlayerSelection(this);

        Button confirmationButton = new Button("confirm");
        confirmationButton.setFont(new Font("Verdana", 30));
        confirmationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chessGUI.startGame(playerOne, playerTwo);
            }
        });

        MenuBar menuBar = new MenuBar();
        DisplayMenu displayMenu = new DisplayMenu(chessGUI);
        menuBar.getMenus().addAll(displayMenu);

        setTop(menuBar);

        setCenter(playerSelection);
    }

    public void setPlayer(int playerNumber, int player){
        if(playerNumber==1){
            this.playerOne = player;
        }
        else{
            this.playerTwo = player;
        }
    }

    public void startGame() {
        chessGUI.startGame(playerOne,playerTwo);
    }

    public void updateGraphics(){
        setMinSize(chessGUI.getWidth(),chessGUI.getHeight());
        setMaxSize(chessGUI.getWidth(),chessGUI.getHeight());
        playerSelection.updateGraphics(chessGUI.getWidth(), chessGUI.getHeight());
    }
}