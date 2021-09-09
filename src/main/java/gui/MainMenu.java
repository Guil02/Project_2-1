package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainMenu extends BorderPane {
    private ChessGUI chessGUI;
    private int playerOne = 1;
    private int playerTwo = 1;

    public MainMenu(ChessGUI chessGUI) {
        this.chessGUI = chessGUI;
        setStyle("-fx-background-color: #336699;");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        setCenter(vBox);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setAlignment(Pos.CENTER);

        ChoiceBox<String> playerOneChoiceBox = new ChoiceBox<>();
        playerOneChoiceBox.setStyle("-fx-font: 30px \"Verdana\";");
        playerOneChoiceBox.getItems().add("Human");
        playerOneChoiceBox.getItems().add("AI");
        playerOneChoiceBox.setOnAction(event -> {
            playerOne = playerOneChoiceBox.getSelectionModel().getSelectedIndex();
            System.out.println(playerOneChoiceBox.getSelectionModel().getSelectedItem());
        });

        ChoiceBox<String> playerTwoChoiceBox = new ChoiceBox<>();
        playerTwoChoiceBox.setStyle("-fx-font: 30px \"Verdana\";");
        playerTwoChoiceBox.getItems().add("Human");
        playerTwoChoiceBox.getItems().add("AI");
        playerTwoChoiceBox.setOnAction(event -> {
            playerTwo = playerTwoChoiceBox.getSelectionModel().getSelectedIndex();
            System.out.println(playerTwoChoiceBox.getSelectionModel().getSelectedItem());
        });

        hBox.getChildren().addAll(playerOneChoiceBox, playerTwoChoiceBox);

        Button confirmationButton = new Button("confirm");
        confirmationButton.setFont(new Font("Verdana", 30));
        confirmationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chessGUI.startGame(playerOne, playerTwo);
            }
        });

        vBox.getChildren().addAll(hBox, confirmationButton);
    }
}
