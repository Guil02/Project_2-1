package gui.SceneLayouts;

import gui.ChessGUI;
import gui.Menus.DisplayMenu;
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
    public static final double EMPTY_SPACE_PERCENTAGE = 0.35;
    private ChessGUI chessGUI;
    private int playerOne = 1;
    private int playerTwo = 1;

    public StartScreen(ChessGUI chessGUI) {
        this.chessGUI = chessGUI;
        setStyle("-fx-background-color: #336699;");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        VBox topLayerVbox = new VBox();


        HBox hBoxLabels = new HBox();
        hBoxLabels.setPadding(new Insets(15,12,15,12));
        hBoxLabels.setAlignment(Pos.CENTER);

        Label labelPlayerOne = new Label("Player 1");
        labelPlayerOne.setAlignment(Pos.CENTER);
        labelPlayerOne.setFont(new Font("Verdana", 30));
        labelPlayerOne.setMinSize(188,58);
        labelPlayerOne.setMaxSize(188,58);

        Label labelPlayerTwo = new Label("Player 2");
        labelPlayerTwo.setAlignment(Pos.CENTER);
        labelPlayerTwo.setFont(new Font("Verdana", 30));
        labelPlayerTwo.setMinSize(188,58);
        labelPlayerTwo.setMaxSize(188,58);

        hBoxLabels.getChildren().addAll(labelPlayerOne,labelPlayerTwo);

        HBox hBoxChoiceBox = new HBox();
        hBoxChoiceBox.setPadding(new Insets(15, 12, 15, 12));
        hBoxChoiceBox.setAlignment(Pos.CENTER);

        ChoiceBox<String> playerOneChoiceBox = new ChoiceBox<>();
        playerOneChoiceBox.setMinSize(188,58);
        playerOneChoiceBox.setMaxSize(188,58);
        playerOneChoiceBox.setStyle("-fx-font: 30px \"Verdana\";");
        playerOneChoiceBox.getItems().add("Human");
        playerOneChoiceBox.getItems().add("AI");
        playerOneChoiceBox.setOnAction(event -> {
            playerOne = playerOneChoiceBox.getSelectionModel().getSelectedIndex();
            System.out.println(playerOneChoiceBox.getSelectionModel().getSelectedItem());
        });

        ChoiceBox<String> playerTwoChoiceBox = new ChoiceBox<>();
        playerTwoChoiceBox.setMinSize(188,58);
        playerTwoChoiceBox.setMaxSize(188,58);
        playerTwoChoiceBox.setStyle("-fx-font: 30px \"Verdana\";");
        playerTwoChoiceBox.getItems().add("Human");
        playerTwoChoiceBox.getItems().add("AI");
        playerTwoChoiceBox.setOnAction(event -> {
            playerTwo = playerTwoChoiceBox.getSelectionModel().getSelectedIndex();
            System.out.println(playerTwoChoiceBox.getSelectionModel().getSelectedItem());
        });

        hBoxChoiceBox.getChildren().addAll(playerOneChoiceBox, playerTwoChoiceBox);

        Button confirmationButton = new Button("confirm");
        confirmationButton.setFont(new Font("Verdana", 30));
        confirmationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chessGUI.startGame(playerOne, playerTwo);
            }
        });

        //TODO adapt to make everything change size correctly.
        MenuBar menuBar = new MenuBar();
        DisplayMenu displayMenu = new DisplayMenu(chessGUI);
        menuBar.getMenus().addAll(displayMenu);

        Label emptySpace = new Label();
        emptySpace.setMinSize(chessGUI.getWidth()* EMPTY_SPACE_PERCENTAGE, chessGUI.getHeight()* EMPTY_SPACE_PERCENTAGE);
        emptySpace.setMaxSize(chessGUI.getWidth()* EMPTY_SPACE_PERCENTAGE, chessGUI.getHeight()* EMPTY_SPACE_PERCENTAGE);


        vBox.getChildren().addAll(hBoxLabels, hBoxChoiceBox, confirmationButton);
        topLayerVbox.getChildren().addAll(menuBar, emptySpace,vBox);
        setCenter(topLayerVbox);
    }
}
