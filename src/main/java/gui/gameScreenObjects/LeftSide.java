package gui.gameScreenObjects;


import gui.sceneLayouts.GameScreen;
import javafx.animation.Timeline;
import javafx.scene.layout.VBox;


public class LeftSide extends VBox {
    private GoodLabel labelOne;
    private GoodLabel labelTwo;
    private GoodLabel labelFirst;
    private GoodLabel labelSecond;
    private GoodLabel labelThird;
    private Timeline time;
    private int whiteTime = 600;
    private int blackTime = 600;
    private boolean isWhiteTurn = true;
    private GameScreen gameScreen;

    public LeftSide(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        labelOne = new GoodLabel(this);
//        labelOne.setText(timeClean(blackTime));
        labelTwo = new GoodLabel(this);
//        labelTwo.setText(timeClean(whiteTime));
        labelFirst = new GoodLabel(this);
        labelSecond = new GoodLabel(this);
        labelThird = new GoodLabel(this);

//        time = new Timeline();
//        time.setCycleCount(Timeline.INDEFINITE);
//        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                doTimeMagic();
//            }
//        });
//        time.getKeyFrames().add(frame);
//        time.playFromStart();
        getChildren().addAll(labelOne, labelFirst, labelSecond, labelThird, labelTwo);
    }

    public void doTimeMagic() {
        if(whiteTime==1){
//            gameScreen.endGame(false);
        }
        if(blackTime==1){
//            gameScreen.endGame(true);
        }
        if (isWhiteTurn) {
//            whiteTime--;
//            labelTwo.setText(timeClean(whiteTime));
//            labelFirst.setText("");
//            labelThird.setText("White's turn!");
        } else {
//            blackTime--;
//            labelOne.setText(timeClean(blackTime));
//            labelThird.setText("");
//            labelFirst.setText("Black's turn!");
        }
    }
    public String timeClean(int a){
        StringBuilder string = new StringBuilder();
        int minutes = (a/60);
        int seconds = a%60;
        string.append(minutes);
        string.append(":");
        if(seconds<10){
            string.append("0");
        }
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

    public void stopTime(){
//        time.stop();
        labelFirst.setText("");
        labelThird.setText("");
    }

    public void updateGraphics(){
        setMinSize(getWidthFromChessGUI() * 0.15, getHeightFromChessGUI() * 0.7);
        setMaxSize(getWidthFromChessGUI() * 0.15, getHeightFromChessGUI() * 0.7);
        labelFirst.updateGraphics();
        labelSecond.updateGraphics();
        labelThird.updateGraphics();
        labelOne.updateGraphics();
        labelTwo.updateGraphics();
        doTimeMagic();
    }
}