package utils;

//transforms a 2d notation of the chess board into a 1d notation
public class Transform {

    public static boolean[] transform(boolean[][] validmoves){
        boolean[] array = new boolean[64];
        int index = 0;
        for (int i = 0; i < validmoves.length; i++) {
            // tiny change 1: proper dimensions
            for (int j = 0; j < validmoves[i].length; j++) {
                // tiny change 2: actually store the values
                array[index] = validmoves[i][j];
                index++;
            }
        }
        return array;
    }
}
