import controller.GameRunner;
import model.Board;
import model.BoardUpdater;
import model.pieces.ChessPiece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ValidMovesTest {

//    //TODO: fails, pawn method isn't correct and I think i know why: you say if index_v = 1, but that does not hold when you have a black pawn;
//    @Test
//    public void validMovesBlackPawnStartingPos(){
//        Board board = new Board();
//        BoardUpdater boardUpdater = new BoardUpdater(board);
//        boardUpdater.fillGameStart();
//        ChessPiece piece = board.getField()[1][0];
//
//        boolean[][] validMoves = piece.validMoves();
//        boolean[][] test = new boolean[8][8];
//        for(int i=0; i<test.length; i++){
//            for(int j=0; j<test.length; j++){
//                test[i][j] = false;
//            }
//        }
//        test[2][0] = true;
//        test[3][0] = true;
//        assertArrayEquals(test, validMoves);
//    }


//    //
//    @Test
//    public void validMovesWhitePawnStartingPos(){
//        Board board = new Board();
//        BoardUpdater boardUpdater = new BoardUpdater(board);
//        boardUpdater.fillGameStart();
//        ChessPiece piece = board.getField()[6][0];
//
//        boolean[][] validMoves = piece.validMoves();
//        boolean[][] test = new boolean[8][8];
//        for(int i=0; i<test.length; i++){
//            for(int j=0; j<test.length; j++){
//                test[i][j] = false;
//            }
//        }
//        test[5][0] = true;
//        test[4][0] = true;
//        assertArrayEquals(test, validMoves);
//

    @Test
    public void validMovesWhiteBishopStartingPosLeftPawnRemoved() {
        GameRunner gameRunner = new GameRunner();
        Board board = new Board(gameRunner);
        BoardUpdater boardUpdater = new BoardUpdater(board);
        boardUpdater.fillGameStart();
        ChessPiece piece = board.getField()[2][7];
        boardUpdater.removePiece(1,6);
        boolean[][] validMoves = piece.validMoves();
        boolean[][] test = new boolean[8][8];
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test.length; j++) {
                test[i][j] = false;
            }
        }
        test[1][6] = true;
        test[0][5] = true;
        assertArrayEquals(test, validMoves);
    }

    @Test
    public void validMovesWhiteBishopStartingPos() {
        GameRunner gameRunner = new GameRunner();
        Board board = new Board(gameRunner);
        BoardUpdater boardUpdater = new BoardUpdater(board);
        boardUpdater.fillGameStart();
        ChessPiece piece = board.getField()[2][7];
        boolean[][] validMoves = piece.validMoves();
        boolean[][] test = new boolean[8][8];
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test.length; j++) {
                test[i][j] = false;
            }
        }
        assertArrayEquals(test, validMoves);
    }
}
