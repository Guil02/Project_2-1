package model.player;

import controller.Board;
import controller.BoardUpdater;
import javafx.application.Platform;
import javafx.concurrent.Task;
import model.algorithm.AiTree;
import model.algorithm.ChessTreeNode;
import model.algorithm.Expectiminimax;
import model.algorithm.TreeNode;
import model.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.Random;

public class FirstAi extends Player {
    private AiTree aiTree = new AiTree();
    private Expectiminimax expectiminimax = new Expectiminimax();
    private ChessTreeNode maxima;
    private Board board;

    public FirstAi(Board board) {
        this.board = board;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        ruleBasedAgent(board);
    }

    public void launch(Board board){
        if(Board.GUI_ON && false){

            System.out.println(Thread.currentThread().getName());
            Platform.runLater(

            new Thread(() -> {
                try{
                    System.out.println(Thread.currentThread().getName());
//                    Thread.sleep(1000);
                    ruleBasedAgent(board);
                    ChessTreeNode move = getMaxima();
                    if(move.isDoPromotion()){
                        System.err.println("---------------------------------------------------------------------");
                        boolean isWhite = board.getPieceOffField(move.getxFrom(), move.getyFrom()).isWhite();
                        int pieceType = getPieceType(move.getBoard().getCharOffField(move.getxTo(), move.getyTo()));
                        System.out.println(move.getxTo()+" "+ move.getyTo());
                        printBoard(board.getBoardModel(), board);
                        ChessPiece promoted = BoardUpdater.createPiece(isWhite, move.getxTo(), move.getyTo(), pieceType);
                        BoardUpdater.removePiece(board, move.getxFrom(), move.getyFrom());
                        BoardUpdater.addPiece(board, promoted);
                        if(!BoardUpdater.containsKing(board, !isWhite)){
                            if(isWhite){
                                if(Board.GUI_ON && board.isOriginal()){
                                    board.getGraphicsConnector().setWin(true);
                                }
                                board.setGameOver(true);
                            }
                            else{
                                if(Board.GUI_ON && board.isOriginal()){
                                    board.getGraphicsConnector().setWin(false);
                                }
                                board.setGameOver(true);
                            }
                        }
                        board.changeTurn();
                    }
                    else{
                        BoardUpdater.movePiece(board, move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }));
        }
        else{
            new Thread(() -> {
                try{
                    Thread.sleep(50);
                    ruleBasedAgent(board);
                    ChessTreeNode move = getMaxima();
                    if(move.isDoPromotion()){
//                        System.err.println("---------------------------------------------------------------------");
                        boolean isWhite = board.getPieceOffField(move.getxFrom(), move.getyFrom()).isWhite();
                        int pieceType = getPieceType(move.getBoard().getCharOffField(move.getxTo(), move.getyTo()));
//                        System.out.println(move.getxTo()+" "+ move.getyTo());
//                        printBoard(board.getBoardModel(), board);
                        ChessPiece promoted = BoardUpdater.createPiece(isWhite, move.getxTo(), move.getyTo(), pieceType);
                        BoardUpdater.removePiece(board, move.getxFrom(), move.getyFrom());
                        BoardUpdater.addPiece(board, promoted);
                        if(!BoardUpdater.containsKing(board, !isWhite)){
                            if(isWhite){
                                if(Board.GUI_ON && board.isOriginal()){
                                    Platform.runLater(
                                        new Thread(()->{
                                            board.getGraphicsConnector().setWin(true);
                                        })
                                    );
                                }
                                board.setGameOver(true);
                            }
                            else{
                                if(Board.GUI_ON && board.isOriginal()){
                                    Platform.runLater(
                                            new Thread(()->{
                                                board.getGraphicsConnector().setWin(false);
                                            })
                                    );
                                }
                                board.setGameOver(true);
                            }
                        }
                        Platform.runLater(
                            new Thread(board::launchGuiUpdate)
                        );
                        board.changeTurn();
                    }
                    else{
                        BoardUpdater.movePiece(board, move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
                        if(Board.GUI_ON){
                            Platform.runLater(
                                    new Thread(board::launchGuiUpdate)
                            );
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void ruleBasedAgent(Board board) {
//        System.out.println(board.getWhiteMove());
        Board copy = board.clone();
        ChessTreeNode root = new ChessTreeNode(copy, 0, null, 1, 1, 0, 0, 0, 0);
        boolean maxIsWhite = board.getWhiteMove();
        aiTree.createChildren(root, false, maxIsWhite);

        for (TreeNode node : root.getChildren()) {
            ChessTreeNode subNode = (ChessTreeNode) node;
            aiTree.createChildren(subNode, false, maxIsWhite);
        }

        for (TreeNode node : root.getChildren()) {
            for (TreeNode node1 : node.getChildren()) {
                ChessTreeNode subNode = (ChessTreeNode) node1;
                aiTree.createChildren(subNode, false, maxIsWhite);
            }
        }

        for (TreeNode node : root.getChildren()) {
            for (TreeNode node1 : node.getChildren()) {
                for (TreeNode node2 : node1.getChildren()) {
                    ChessTreeNode subNode = (ChessTreeNode) node2;
                    aiTree.createChildren(subNode, true, maxIsWhite);
                }
            }
        }
//
//        for (TreeNode node : root.getChildren()) {
//            for (TreeNode node1 : node.getChildren()) {
//                for (TreeNode node2 : node1.getChildren()) {
//                    for(TreeNode node3: node2.getChildren()){
//                        ChessTreeNode subNode = (ChessTreeNode) node3;
//                        aiTree.createChildren(subNode, false, maxIsWhite);
//                    }
//                }
//            }
//        }
//
//        for (TreeNode node : root.getChildren()) {
//            for (TreeNode node1 : node.getChildren()) {
//                for (TreeNode node2 : node1.getChildren()) {
//                    for(TreeNode node3: node2.getChildren()){
//                        for(TreeNode node4: node3.getChildren()){
//                            ChessTreeNode subNode = (ChessTreeNode) node4;
//                            aiTree.createChildren(subNode, false, maxIsWhite);
//                        }
//                    }
//                }
//            }
//        }
//
//        for (TreeNode node : root.getChildren()) {
//            for (TreeNode node1 : node.getChildren()) {
//                for (TreeNode node2 : node1.getChildren()) {
//                    for(TreeNode node3: node2.getChildren()){
//                        for(TreeNode node4: node3.getChildren()){
//                            for(TreeNode node5: node4.getChildren()){
//                                ChessTreeNode subNode = (ChessTreeNode) node5;
//                                aiTree.createChildren(subNode, false, maxIsWhite);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        for (TreeNode node : root.getChildren()) {
//            for (TreeNode node1 : node.getChildren()) {
//                for (TreeNode node2 : node1.getChildren()) {
//                    for(TreeNode node3: node2.getChildren()){
//                        for(TreeNode node4: node3.getChildren()){
//                            for(TreeNode node5: node4.getChildren()){
//                                for(TreeNode node6: node5.getChildren()){
//                                    ChessTreeNode subNode = (ChessTreeNode) node6;
//                                    aiTree.createChildren(subNode, true, maxIsWhite);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
        double res = expectiminimax.expectiminimax(root, 10);
//        System.out.println(res);
        double maxValue = Double.MIN_VALUE;
        ArrayList<ChessTreeNode> highestNodes = new ArrayList<>();
        ChessTreeNode maxNode = (ChessTreeNode) root.getChildren().get(0);
        highestNodes.add(maxNode);
        for (TreeNode child : root.getChildren()) {
            ChessTreeNode subChild = (ChessTreeNode) child;
            if (subChild.getValue() >= maxValue) {
                if (subChild.getValue() == maxValue) {
                    highestNodes.add(subChild);
//                    if (Math.random() < 0.5) {
//                        maxValue = subChild.getValue();
//                        maxNode = subChild;
//                    }
                    continue;
                }
                highestNodes.clear();
                highestNodes.add(subChild);
                maxValue = subChild.getValue();
//                maxNode = subChild;
            }
        }
        Random rand = new Random();
//        System.out.println(highestNodes.size());
        maxNode = highestNodes.get(rand.nextInt(highestNodes.size()));

        System.out.println("move from: x=" + maxNode.getxFrom() + " y=" + maxNode.getyFrom() + " to: x=" + maxNode.getxTo() + " y=" + maxNode.getyTo());
        printBoard(board.getBoardModel(), board);
        maxima = maxNode;
    }

    public ChessTreeNode getMaxima() {
        return maxima;
    }

    public int getPieceType(char pieceType){
        switch(pieceType){
            case 'n','N':
                return 2;
            case 'b','B':
                return 3;
            case 'r','R':
                return 4;
            case 'q','Q':
                return 5;
        }
        return 0;
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
