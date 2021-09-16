import org.junit.jupiter.api.Test;
import utils.Transform;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;

public class UtilTests {
    @Test
    public void TransformTest(){
        boolean[][] twoDimension = new boolean[8][8];
        boolean[] oneDimension = new boolean[64];

        for (boolean[] row: twoDimension)
            Arrays.fill(row, false);

        Arrays.fill(oneDimension, false);;

        twoDimension[0][4] = true;
        oneDimension[4] = true;

        twoDimension[1][2] = true;
        oneDimension[10] = true;

        twoDimension[4][5] = true;
        oneDimension[37] = true;

        twoDimension[7][7] = true;
        oneDimension[63] = true;


        boolean[] transformed = Transform.transform(twoDimension);

        assertArrayEquals(transformed, oneDimension);
    }

}
