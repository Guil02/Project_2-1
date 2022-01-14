package utils;

import controller.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GABoardEncodingTest {

    @Test
    public void testEvaluate1(){
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -";
        GABoardEncoding encoding = new GABoardEncoding();
        Board board = FenEvaluator.read(fen);
        int[] actual = encoding.evaluate(board);
        int[] expected = {1,1,1,1,1,2,2,2,2,2,2,8,8,0,0,0,0,0,0,0,0,4,4,16,16};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testEvaluate2(){
        String fen = "r3k3/1p4p1/2bp1N2/p3BQR1/7P/n1p5/P1PPPNKP/2R5 b  -";
        GABoardEncoding encoding = new GABoardEncoding();
        Board board = FenEvaluator.read(fen);
        int[] actual = encoding.evaluate(board);
        int[] expected = {0,1,1,1,0,2,1,1,1,2,1,6,5,6,5,11,0,12,5,5,7,14,4,7,8};
        assertArrayEquals(expected, actual);
    }

}