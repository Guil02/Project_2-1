package utils;

//transforms a 2d notation of the chess board into a 1d notation
public class Transform {
    public static boolean[] transform(boolean[][] validMoves) {
        boolean[] validMoves1D = new boolean[64];
        int xBoard = 0;
        int yBoard = 0;
        for (int i = 0; i < validMoves1D.length; i++) {

            validMoves1D[i] = validMoves[xBoard][yBoard];
            xBoard++;
            if ((i + 1) % 8 == 0) {
                yBoard++;
                xBoard = 0;
            }
        }
        return validMoves1D;
    }

    public static boolean[] transformTry(boolean[][] validmoves){
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
