package controller;

import com.sun.source.tree.Tree;
import gui.ChessGUI;
import model.algorithm.AiTree;
import model.algorithm.ChessTreeNode;
import model.algorithm.Expectiminimax;
import model.algorithm.TreeNode;
import model.pieces.ChessPiece;

import java.util.concurrent.TimeUnit;

public class GameRunner {
    private Board board;
    ChessGUI chessGUI;
    GraphicsConnector graphicsConnector;
    private AiTree aiTree;
    private Expectiminimax expectiminimax;



    /**
     * Constructor
     */
    public GameRunner() {
        chessGUI = new ChessGUI();
        graphicsConnector = new GraphicsConnector(this);
        if(Board.GUI_ON){
            chessGUI.launchGUI(graphicsConnector);
        }
        else{
            init(0,0);
        }
    }

    /**
     * Initializes the game when it is started.
     */
    public void init(int playerOne, int playerTwo) {
        aiTree = new AiTree();
        expectiminimax = new Expectiminimax();
        board = new Board(this);
        board.setPlayers(playerOne, playerTwo);
        BoardUpdater.fillGameStart(board);
        graphicsConnector.setBoard(board);
        Dice.firstMoveDiceRoll(board);
    }

    @Override
    protected GameRunner clone(){
        try{
            return (GameRunner) super.clone();
        }
        catch(CloneNotSupportedException e){
            System.err.println("failed to make copy.");
        }
        return new GameRunner();
    }

    public void doAiMove(Board board, int aiType){
        if(aiType ==1){
            ruleBasedAgent(board);
        }
    }

    public void ruleBasedAgent(Board board){
        Board copy = board.clone();
        ChessTreeNode root = new ChessTreeNode(copy, 0, null, 1, 1, 0, 0, 0, 0);
        boolean maxIsWhite = board.getWhiteMove();
        aiTree.createChildren(root, false, maxIsWhite);

        for(TreeNode node: root.getChildren()){
            ChessTreeNode subNode = (ChessTreeNode) node;
            aiTree.createChildren(subNode, false, maxIsWhite);
        }

        for(TreeNode node: root.getChildren()){
            for(TreeNode node1: node.getChildren()){
                ChessTreeNode subNode = (ChessTreeNode) node1;
                aiTree.createChildren(subNode, false, maxIsWhite);
            }
        }

        for(TreeNode node: root.getChildren()){
            for(TreeNode node1: node.getChildren()){
                for(TreeNode node2: node1.getChildren()){
                    ChessTreeNode subNode = (ChessTreeNode) node2;
                    aiTree.createChildren(subNode, true, maxIsWhite);
                }
            }
        }

        double res = expectiminimax.expectiminimax(root, 10);
        System.out.println(res);
        double maxValue = Double.MIN_VALUE;
        ChessTreeNode maxNode = (ChessTreeNode) root.getChildren().get(0);
        for(TreeNode child: root.getChildren()){
            ChessTreeNode subChild = (ChessTreeNode) child;
            if(subChild.getValue()>=maxValue){
                if(subChild.getValue() == maxValue){
                    if(Math.random()<0.3){
                        maxValue = subChild.getValue();
                        maxNode = subChild;
                    }
                    continue;
                }
                maxValue = subChild.getValue();
                maxNode = subChild;
            }
        }

        printBoard(board.getBoardModel(), board);
        BoardUpdater.movePiece(board, maxNode.getxFrom(), maxNode.getyFrom(), maxNode.getxTo(), maxNode.getyTo());
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
