import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import model.algorithm.*;
import model.player.FirstAi;
import org.junit.jupiter.api.Test;
import utils.FenEvaluator;
import controller.*;

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
        AiTree aiTree = new AiTree();
        aiTree.createChildren(root, false, maxIsWhite);
        for(TreeNode node: root.getChildren()){
            ChessTreeNode subNode = (ChessTreeNode) node;
            aiTree.createChildren(subNode, true, maxIsWhite);
        }
        Expectiminimax expectiminimax = new Expectiminimax();
        ExpectiminimaxStar2 expectiminimaxStar2 = new ExpectiminimaxStar2(true);
        System.out.println("initializing expectiminimax");
        double res = expectiminimax.expectiminimax(root, 1);
        System.out.println("getting first value");
        System.out.println("First value :" + res);
        System.out.println("initializing expectiminimax with pruning");
        double res2 = expectiminimaxStar2.expectiminimax(root,1);
        System.out.print("Without pruning "+res+" With pruning "+res2+"for piece "+ board.getMovablePiece());
        assertEquals(res,res2);
    }
}
