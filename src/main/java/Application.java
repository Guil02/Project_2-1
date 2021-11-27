import controller.GameRunner;
import utils.Learner;

/**
 * Application class which contains the executable method to start the game.
 */
public class Application {
    private static final boolean LEARN = true;
    /**
     * Main method which gets executed upon start.
     * @param args
     */
    public static void main(String[] args) {
        if(LEARN){
            Learner learner = new Learner();
        }
        else{
            GameRunner gameRunner = new GameRunner();
        }
    }
}
