package model.player;

import controller.Board;

/**
 * This agent has no strategy. It just picks random moves based on the roll of the dice.
 */
public class BaselineAgent extends Player{
    public void launch(Board board) {
        System.gc();
        new Thread(() -> {
            try{

            }
            catch(Exception e) {
                System.err.println("Piece might already have been moved due to glitch in the threading");
                e.printStackTrace();
            }
        }).start();
    }
}
