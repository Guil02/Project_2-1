package model.player;

import controller.Board;
import gui.DebugWindow.DebugWindowStage;

public abstract class Player {

    Object pauseLock = DebugWindowStage.pauseLock;

    public Player() {
    }

    public void run(){
        while(!Board.hasMoved){

        }
    }

    public void pauseThread() throws InterruptedException {
        synchronized (pauseLock) {
            pauseLock.wait();
        }
    }
}
