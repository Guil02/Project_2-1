package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        Scene scene = new Scene(root, Color.GREEN);
        Image icon = new Image("gui/pawn.png");

        stage.getIcons().add(icon);
        stage.setTitle("chess");
        stage.setWidth(1000);
        stage.setHeight(1000);
        stage.setResizable(false);
        stage.setFullScreen(true);

        stage.setScene(scene);
        stage.show();
    }
}
