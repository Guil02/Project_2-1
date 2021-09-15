import model.Board;
import model.BoardUpdater;
import model.pieces.Piece;
import org.junit.jupiter.api.Test;
import utils.Transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;

public class UtilTests {
    @Test
    public void TransformTest(){
        boolean[][] twoDimension = new boolean[8][8];
        for(int i=0; i<twoDimension.length; i++){
            for(int j=0; j<twoDimension.length; j++){
                twoDimension[i][j] = false;
            }
        }
        twoDimension[0][7] = true;
//        twoDimension[4][3] = true;
//        twoDimension[7][7] = true;
        boolean[] actual = Transform.transformTry(twoDimension);
        boolean[] ex = new boolean[64];
        Arrays.fill(ex, false);
        ex[7] = true;
//        expected[27] = true;
//        expected[63] = true;

        assertEquals(ex, actual);
    }

}
