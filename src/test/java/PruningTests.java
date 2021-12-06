import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import model.algorithm.*;
import model.player.FirstAi;
import org.junit.jupiter.api.Test;
import utils.FenEvaluator;
import controller.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static model.algorithm.test.printBoard;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PruningTests {
    @Test
    /**
     * This method takes random chess board states and compares the best move for both
     * Expectiminimax with and without pruning.
     */
    public void pruningTest() {
        fenTest("8/5K1p/7B/Pb2k3/6P1/2p1Pp1P/2Rr2p1/Q4N2 w - - 0 1");
        fenTest("8/5pP1/Q1K4b/P1P2pP1/1p4p1/2r1R1p1/8/1k3b2 w - - 0 1");
        fenTest("Q1Nb2k1/P7/p7/2K4P/6nR/1P5P/6pp/B4r2 w - - 0 1");
        fenTest("2b5/K5B1/8/Pp4N1/2pP4/n4P2/1qpPk3/6nQ w - - 0 1");
        fenTest("N7/5p1p/p1b2k2/3P4/4n1P1/r1p2K2/1R1P4/B1R5 w - - 0 1");
        fenTest("2r5/1p1Pp1Q1/6P1/P6p/p1N5/K2kr3/3B1n2/6R1 w - - 0 1");

        //fenTest("r2r4/1R4p1/2p1P1pk/1Pp1P3/5R1n/8/1q5p/4K3 w - - 0 1");
    }

    public void fenTest(String fen) {
        FenEvaluator reader = new FenEvaluator();
        Board board = reader.read(fen);
        board.setPlayers(1, 1);
        board.setWhiteMove(true);
        Dice.rollTheDice(board);
        printBoard(board.getBoardModel(), board);
        boolean maxIsWhite = board.getWhiteMove();
        assertEquals(1, 1);
        ChessTreeNode root = new ChessTreeNode(board, 0, null, 1, 1, 0, 0, 0, 0, maxIsWhite);
        ChessTreeNode root2 = new ChessTreeNode(board, 0, null, 1, 1, 0, 0, 0, 0, maxIsWhite);
        AiTree aiTree = new AiTree();
        aiTree.createChildren(root, false, maxIsWhite);

        int ply = 4;

        // Execute the algorithms
        long start1 = System.currentTimeMillis();
        Expectiminimax expectiminimax = new Expectiminimax();
        expectiminimax.expectiminimax(root, (ply * 2) - 1);
        long end1 = System.currentTimeMillis();

        System.out.println("Time for normal Expectiminimax: " + (end1 - start1));

        long start2 = System.currentTimeMillis();
        ExpectiminimaxStar2 expectiminimaxStar2 = new ExpectiminimaxStar2(true);
        expectiminimaxStar2.expectiminimax(root2, (ply * 2) - 1);
        long end2 = System.currentTimeMillis();

        System.out.println("Time for Expectiminimax with P: " + (end2 - start2));

        double maxValue = Double.MIN_VALUE;
        ArrayList<ChessTreeNode> highestNodes = new ArrayList<>();
        ChessTreeNode maxNode = (ChessTreeNode) root.getChildren().get(0);
        highestNodes.add(maxNode);
        for (TreeNode child : root.getChildren()) {
            ChessTreeNode subChild = (ChessTreeNode) child;
            if (subChild.getValue() >= maxValue) {
                if (subChild.getValue() == maxValue) {
                    highestNodes.add(subChild);
                    continue;
                }
                highestNodes.clear();
                highestNodes.add(subChild);
                maxValue = subChild.getValue();
            }
        }

        double maxValue2 = Double.MIN_VALUE;
        ArrayList<ChessTreeNode> highestNodes2 = new ArrayList<>();
        ChessTreeNode maxNode2 = (ChessTreeNode) root.getChildren().get(0);
        highestNodes2.add(maxNode2);
        for (TreeNode child : root2.getChildren()) {
            ChessTreeNode subChild = (ChessTreeNode) child;
            if (subChild.getValue() >= maxValue2) {
                if (subChild.getValue() == maxValue2) {
                    highestNodes2.add(subChild);
                    continue;
                }
                highestNodes2.clear();
                highestNodes2.add(subChild);
                maxValue2 = subChild.getValue();
            }
        }

        ArrayList<int[]> moves1 = new ArrayList<>();
        int counter1 = 0;
        for (ChessTreeNode node : highestNodes) {
            int[] move = {node.getxFrom(), node.getyFrom(), node.getxTo(), node.getyTo()};
            moves1.add(move);
            System.out.println("Move1: " + Arrays.toString(move));
            counter1++;
        }

        ArrayList<int[]> moves2 = new ArrayList<>();
        int counter2 = 0;
        for (ChessTreeNode node : highestNodes2) {
            int[] move = {node.getxFrom(), node.getyFrom(), node.getxTo(), node.getyTo()};
            moves2.add(move);
            System.out.println("Move2: " + Arrays.toString(move));
            counter2++;
        }

        if (counter1 != counter2) {
            System.out.println("Different amount of equal moves!");
            return;
        }

        for (int i = 0; i < moves1.size(); i++) {
            System.out.println("Move 1: " + Arrays.toString(moves1.get(i)));
            System.out.println("Move 2: " + Arrays.toString(moves2.get(i)));
            for (int j = 0; j < 4; j++) {
                assertEquals(moves1.get(i)[j], moves2.get(i)[j]);
            }
        }
    }
}