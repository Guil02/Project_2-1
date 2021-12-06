import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import model.algorithm.*;
import model.player.FirstAi;
import org.junit.jupiter.api.Test;
import utils.FenEvaluator;
import controller.*;

import java.util.ArrayList;
import java.util.HashMap;

import static model.algorithm.test.printBoard;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PruningTests {
    @Test
    public void fenTest(){
        String fen = "8/5K1p/7B/Pb2k3/6P1/2p1Pp1P/2Rr2p1/Q4N2 w - - 0 1";
        FenEvaluator reader = new FenEvaluator();
        Board board = reader.read(fen);
        board.setPlayers(1,1);
        board.setWhiteMove(true);
        Dice.rollTheDice(board);
        printBoard(board.getBoardModel(), board);
        boolean maxIsWhite = board.getWhiteMove();
        assertEquals(1,1);
        ChessTreeNode root = new ChessTreeNode(board, 0, null, 1, 1, 0,0,0,0, maxIsWhite);
        ChessTreeNode root2 = new ChessTreeNode(board, 0, null, 1, 1, 0,0,0,0, maxIsWhite);
        AiTree aiTree = new AiTree();
        aiTree.createChildren(root, false, maxIsWhite);
//        for(TreeNode node: root.getChildren()){
//            ChessTreeNode subNode = (ChessTreeNode) node;
//            aiTree.createChildren(subNode, true, maxIsWhite);
//        }
        Expectiminimax expectiminimax = new Expectiminimax();
        ExpectiminimaxStar2 expectiminimaxStar2 = new ExpectiminimaxStar2(true);
        System.out.println("initializing expectiminimax");
        double res = expectiminimax.expectiminimax(root, 1);
        System.out.println("getting first value");
        System.out.println("First value :" + res);
        System.out.println("initializing expectiminimax with pruning");
        double res2 = expectiminimaxStar2.expectiminimax(root2,1);
        System.out.println("Without pruning "+res+" With pruning "+res2+"for piece "+ board.getMovablePiece());

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
        for (TreeNode child : root.getChildren()) {
            ChessTreeNode subChild = (ChessTreeNode) child;
            if (subChild.getValue() >= maxValue) {
                if (subChild.getValue() == maxValue) {
                    highestNodes2.add(subChild);
                    continue;
                }
                highestNodes2.clear();
                highestNodes2.add(subChild);
                maxValue2 = subChild.getValue();
            }
        }
        for(ChessTreeNode node: highestNodes){
            System.out.println("xto: "+node.getxTo()+"\nyto: "+node.getyTo());
        }
        for(ChessTreeNode node: highestNodes2){
            System.out.println("xto: "+node.getxTo()+"\nyto: "+node.getyTo());
        }
        assertEquals(res,res2);
    }
}

class vector{
    private int x;
    private int y;

    public vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        vector v = (vector) obj;
        return v.getX()==x && v.getY()==y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
