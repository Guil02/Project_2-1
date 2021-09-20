package gui.SceneLayouts;

import gui.ChessGUI;
import gui.Menus.DisplayMenu;
import gui.Menus.HighlightMenu;
import gui.PlayerSelection.PlayerSelection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
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

        MenuBar menuBar = new MenuBar();
        DisplayMenu displayMenu = new DisplayMenu(chessGUI);
        HighlightMenu highlightMenu = new HighlightMenu();
        menuBar.getMenus().addAll(displayMenu, highlightMenu);

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
