package model.player;

import config.Config;
import controller.Board;
import controller.BoardUpdater;
import gui.debugWindow.DebugWindowStage;
import javafx.application.Platform;
import model.algorithm.TreeNode;

/**
 * Abstract class that represents any player of a chess match.
 */
public abstract class Player {

    // Variables
    Object pauseLock = DebugWindowStage.pauseLock;
    TreeNode maxima;

    public void run(){
        while(!Board.hasMoved){

        }
    }

    public TreeNode getMaxima() {
        return maxima;
    }

    public void launch(Board board){
            new Thread(() -> {
                try {
                    // Stop if game is on pause
                    if (DebugWindowStage.isOnPause) {
                        pauseThread();
                    }

                    runAgent(board);
                    TreeNode move = getMaxima();
                    if (move.isDoPromotion()) {
                        board.storeMove();
                        BoardUpdater.runPromotion(board, move.getBoard(), move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
                        if (Config.GUI_ON) {
                            Platform.runLater(
                                    new Thread(board::launchGuiUpdate)
                            );
                        }
                    } else {
                        BoardUpdater.movePiece(board, move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
                        if (Config.GUI_ON) {
                            Platform.runLater(
                                    new Thread(board::launchGuiUpdate)
                            );
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Piece might already have been moved due to glitch in the threading");
                    e.printStackTrace();
                }
            }).start();
    }

    public abstract void runAgent(Board board);

    public void pauseThread() throws InterruptedException {
        synchronized (pauseLock) {
            pauseLock.wait();
        }
    }
}
