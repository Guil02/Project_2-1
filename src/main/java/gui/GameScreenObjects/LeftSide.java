package gui.GameScreenObjects;


import gui.SceneLayouts.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
        labelOne.setText("10:00");
        labelTwo = new GoodLabel(this);
        labelTwo.setText("10:00");
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
            labelTwo.setText(timeClean(whiteTime));
            labelFirst.setText("");
            labelThird.setText("White's turn!");
        } else {
            blackTime--;
            labelOne.setText(timeClean(blackTime));
            labelThird.setText("");
            labelFirst.setText("Black's turn!");
        }
    }
    public String timeClean(int a){
        StringBuilder string = new StringBuilder();
        int minutes = (a/60);
        int seconds = a%60;
        string.append(minutes);
        string.append(":");
        string.append(seconds);
        return string.toString();
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
