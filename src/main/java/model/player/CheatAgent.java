package model.player;

import controller.Board;
import model.algorithm.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class that represents an agent that can "cheat".
 * It has the capability of performing any move and is
 * not dependent on the dice roll.
 */
public class CheatAgent extends Player {
    private CheatAiTree cheatAiTree = new CheatAiTree();
    private ExpectiminimaxStar2 expectiminimaxStar2 = new ExpectiminimaxStar2(true);
    private boolean cheatIsWhite;
    private ChessCheatAiTreeNode maxima;
    private Board board;
    private boolean firstTurn = true;
    private static final int ply = 2;

    /**
     * Runs the agent.
     * @param board
     */
    public void runAgent(Board board){
        Board copy = board.clone();
        boolean maxIsWhite = board.getWhiteMove();
        ChessCheatAiTreeNode root;
        if (maxIsWhite) {
            root = new ChessCheatAiTreeNode(copy, 0, null, 1, 1, 0, 0, 0, 0, maxIsWhite);
        } else {
            root = new ChessCheatAiTreeNode(copy, 0, null, 2, 1, 0, 0, 0, 0, maxIsWhite);
        }
        double res = expectiminimaxStar2.expectiminimaxWithStar2(root, (ply * 2) - 1, (ply * 2) - 1);
        double maxValue;

        if(maxIsWhite){
            maxValue = Double.NEGATIVE_INFINITY;
        }
        else {
            maxValue = Double.POSITIVE_INFINITY;
        }
        ArrayList<ChessCheatAiTreeNode> highestNodes = new ArrayList<>();
        ChessCheatAiTreeNode maxNode = (ChessCheatAiTreeNode) root.getChildren().get(0);
        highestNodes.add(maxNode);
        for (TreeNode child : root.getChildren()) {
            ChessCheatAiTreeNode subChild = (ChessCheatAiTreeNode) child;
            if (maxIsWhite) {

                if (subChild.getValue() >= maxValue) {
                    if (subChild.getValue() == maxValue) {
                        highestNodes.add(subChild);
                        continue;
                    }
                    highestNodes.clear();
                    highestNodes.add(subChild);
                    maxValue = subChild.getValue();
                }
            } else {
                if (subChild.getValue() <= maxValue) {
                    if (subChild.getValue() == maxValue) {
                        highestNodes.add(subChild);
                        continue;
                    }
                    highestNodes.clear();
                    highestNodes.add(subChild);
                    maxValue = subChild.getValue();
                }
            }
        }
        Random rand = new Random();
        maxNode = highestNodes.get(rand.nextInt(highestNodes.size()));
        maxima = maxNode;
    }

    /**
     * Get maxima from the search tree.
     * @return
     */
    public ChessCheatAiTreeNode getMaxima() {
        return maxima;
    }

    /**
     * Gets the numerical value of a piece type.
     * @param pieceType
     * @return
     */
    public int getPieceType(char pieceType) {
        switch (pieceType) {
            case 'n', 'N':
                return 2;
            case 'b', 'B':
                return 3;
            case 'r', 'R':
                return 4;
            case 'q', 'Q':
                return 5;
        }
        return 0;
    }
}
