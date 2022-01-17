import controller.Board;
import controller.Dice;
import model.algorithm.*;
import model.pieces.ChessPiece;
import org.junit.jupiter.api.Test;
import utils.FenEvaluator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PruningTests {
    @Test
    /**
     * This method takes random chess board states and compares the best move for both
     * Expectiminimax with and without pruning.
     */
    public void pruningTest() {
        for(int i=0;i<10;i++) {
            fenTest("8/5K1p/7B/Pb2k3/6P1/2p1Pp1P/2Rr2p1/Q4N2 w - - 0 1");
            fenTest("8/5pP1/Q1K4b/P1P2pP1/1p4p1/2r1R1p1/8/1k3b2 w - - 0 1");
            fenTest("Q1Nb2k1/P7/p7/2K4P/6nR/1P5P/6pp/B4r2 w - - 0 1");
            fenTest("2b5/K5B1/8/Pp4N1/2pP4/n4P2/1qpPk3/6nQ w - - 0 1");
            fenTest("N7/5p1p/p1b2k2/3P4/4n1P1/r1p2K2/1R1P4/B1R5 w - - 0 1");
            fenTest("2r5/1p1Pp1Q1/6P1/P6p/p1N5/K2kr3/3B1n2/6R1 w - - 0 1");
        }
        //fenTest("r2r4/1R4p1/2p1P1pk/1Pp1P3/5R1n/8/1q5p/4K3 w - - 0 1");
    }

    @Test
    public void runSpeedComparison() {
        int ply = 4;
        int testAmount = 100;

        File inputData = new File("src/main/resources/utils/games.txt");
        ArrayList<long[]> results = new ArrayList<>();
        // Collect the data
        try {
            Scanner scanner = new Scanner(inputData);
            for (int i = 0; i < testAmount; i++) {
                String currentFen = scanner.nextLine();
                results.add(speedComparison(currentFen, ply));
                System.out.println("State " + (i+1) + " of " + testAmount + " passed. - " + results.get(i)[0] + "\t" + results.get(i)[1]);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }
        // Do the maths
        double sumNoPruning = 0;
        double sumWithPruning = 0;

        if (results.size() > 0) {
            for (long[] element : results) {
                sumNoPruning += element[0];
                sumWithPruning += element[1];
            }

            // Checks if pruning is actually faster than normal expectiminimax
            assertTrue(sumNoPruning > sumWithPruning);

            double percentage = 100 - ((sumWithPruning/sumNoPruning)*100);
            // Print results
            System.out.println("Total sum without pruning: \t" + sumNoPruning);
            System.out.println("Total sum with pruning \t" + sumWithPruning);
            System.out.println("Time saving: " + percentage + " % at ply " + ply);

        }
        else {
            System.out.println("No results!");
        }
    }

    /**
     * Performs the speed comparison for one move on a specified board
     * @return array of time durations for [0] without pruning and [1] with pruning
     */
    public long[] speedComparison(String fen, int ply) {
        FenEvaluator fenEvaluator = new FenEvaluator();
        Board board = fenEvaluator.read(fen);
        board.setWhiteMove(true);
        Dice.rollTheDice(board);

        // Measure time without pruning
        ChessTreeNode rootNoPruning = new ChessTreeNode(board, 0, null, 1, 1, 0, 0, 0, 0);
        Expectiminimax expectiminimax = new Expectiminimax();
        long startTimeNoPruning = System.currentTimeMillis();
        expectiminimax.expectiminimax(rootNoPruning, (ply * 2) - 1, (ply*2)-1);
        long endTimeNoPruning = System.currentTimeMillis();
        long totalTimeNoPruning = endTimeNoPruning - startTimeNoPruning;
        // Measure time with pruning
        ExpectiminimaxStar2 expectiminimaxStar2 = new ExpectiminimaxStar2(true);
        ChessTreeNode rootWithPruning = new ChessTreeNode(board, 0, null, 1, 1, 0, 0, 0, 0);
        long startTimeWithPruning = System.currentTimeMillis();
        expectiminimaxStar2.expectiminimaxWithStar2(rootWithPruning, (ply * 2) - 1, (ply*2)-1);
        long endTimeWithPruning = System.currentTimeMillis();
        long totalTimeWithPruning = endTimeWithPruning - startTimeWithPruning;
        // Return results
        return new long[]{totalTimeNoPruning, totalTimeWithPruning};
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
        ChessTreeNode root = new ChessTreeNode(board, 0, null, 1, 1, 0, 0, 0, 0);
        ChessTreeNode root2 = new ChessTreeNode(board, 0, null, 1, 1, 0, 0, 0, 0);
        AiTree aiTree = new AiTree();
        aiTree.createChildren(root, false, maxIsWhite);
        aiTree.setRandomness(false);

        int ply = 3;

        // Execute the algorithms
        long start1 = System.currentTimeMillis();
        Expectiminimax expectiminimax = new Expectiminimax();
        expectiminimax.expectiminimax(root, (ply * 2) - 1, (ply*2)-1);
        long end1 = System.currentTimeMillis();

        System.out.println("Time for normal Expectiminimax: " + (end1 - start1));

        long start2 = System.currentTimeMillis();
        ExpectiminimaxStar2 expectiminimaxStar2 = new ExpectiminimaxStar2(true);
        expectiminimaxStar2.expectiminimaxWithStar2(root2, (ply * 2) - 1, (ply*2)-1);
        long end2 = System.currentTimeMillis();

        System.out.println("Time for Expectiminimax with P: " + (end2 - start2));

        double maxValue = Double.NEGATIVE_INFINITY;
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

        double maxValue2 = Double.NEGATIVE_INFINITY;
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

    public static void printBoard(ChessPiece[][] boardModel, Board board) {
        System.out.println("--- Board State ---\n");
        for(int i = 0; i < boardModel[0].length; i++) {
            for (int j = 0; j < boardModel.length; j++) {
                System.out.print("[ " + board.getCharOffField(j,i) + " ] ");
                // System.out.print("[ " + j + " " + i + " ] ");
            }
            System.out.println();
        }
    }
}