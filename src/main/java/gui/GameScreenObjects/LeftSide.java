package gui.GameScreenObjects;


import gui.SceneLayouts.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class LeftSide extends VBox {
    private Label labelOne;
    private Label labelTwo;
    private Label labelFirst;
    private Label labelSecond;
    private Label labelThird;
    private Timeline time;
    private int whiteTime = 600;
    private int blackTime = 600;
    private boolean isWhiteTurn = true;

    public LeftSide(GameScreen gameScreen) {
        labelOne = new Label(String.valueOf(blackTime));
        labelTwo = new Label(String.valueOf(whiteTime));
        labelOne.setFont(new Font("Verdana", 30));
        labelTwo.setFont(new Font("Verdana", 30));
        labelFirst = new Label();
        labelSecond = new Label();
        labelThird = new Label();
        labelFirst.setMinSize(gameScreen.getWidthFromChessGUI() * 0.15, gameScreen.getHeightFromChessGUI() * 0.7 / 5);
        labelFirst.setMaxSize(gameScreen.getWidthFromChessGUI() * 0.15, gameScreen.getHeightFromChessGUI() * 0.7 / 5);
        labelSecond.setMinSize(gameScreen.getWidthFromChessGUI() * 0.15, gameScreen.getHeightFromChessGUI() * 0.7 / 5);
        labelSecond.setMaxSize(gameScreen.getWidthFromChessGUI() * 0.15, gameScreen.getHeightFromChessGUI() * 0.7 / 5);
        labelThird.setMinSize(gameScreen.getWidthFromChessGUI() * 0.15, gameScreen.getHeightFromChessGUI() * 0.7 / 5);
        labelThird.setMaxSize(gameScreen.getWidthFromChessGUI() * 0.15, gameScreen.getHeightFromChessGUI() * 0.7 / 5);
        labelOne.setMinSize(gameScreen.getWidthFromChessGUI() * 0.15, gameScreen.getHeightFromChessGUI() * 0.7 / 5);
        labelOne.setMaxSize(gameScreen.getWidthFromChessGUI() * 0.15, gameScreen.getHeightFromChessGUI() * 0.7 / 5);
        labelTwo.setMinSize(gameScreen.getWidthFromChessGUI() * 0.15, gameScreen.getHeightFromChessGUI() * 0.7 / 5);
        labelTwo.setMaxSize(gameScreen.getWidthFromChessGUI() * 0.15, gameScreen.getHeightFromChessGUI() * 0.7 / 5);

        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                doTimeMagic();
            }
        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
        getChildren().addAll(labelOne, labelFirst, labelSecond, labelThird, labelTwo);
    }

    public void doTimeMagic() {
        if (isWhiteTurn) {
            whiteTime--;
            labelTwo.setText(String.valueOf(whiteTime));
        } else {
            blackTime--;
            labelOne.setText(String.valueOf(blackTime));
        }
    }

    public void changeTimer(boolean x) {
        isWhiteTurn = x;
    }
}
