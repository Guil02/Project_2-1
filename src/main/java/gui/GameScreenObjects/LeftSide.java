package gui.GameScreenObjects;


import gui.SceneLayouts.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
    private GameScreen gameScreen;

    public LeftSide(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        labelOne = new GoodLabel(this);
        labelOne.setText(String.valueOf(blackTime));
        labelTwo = new GoodLabel(this);
        labelTwo.setText(String.valueOf(whiteTime));
        labelFirst = new GoodLabel(this);
        labelSecond = new GoodLabel(this);
        labelThird = new GoodLabel(this);

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
            labelFirst.setText("");
            labelThird.setText("White's turn!");
        } else {
            blackTime--;
            labelOne.setText(String.valueOf(blackTime));
            labelThird.setText("");
            labelFirst.setText("Black's turn!");
        }
    }

    public double getWidthFromChessGUI() {
        return gameScreen.getWidthFromChessGUI();
    }


    public void changeTimer() {
        isWhiteTurn = !isWhiteTurn;
    }

    public double getHeightFromChessGUI() {
        return gameScreen.getHeightFromChessGUI();
    }
}
