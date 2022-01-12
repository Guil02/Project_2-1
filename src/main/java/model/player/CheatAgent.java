package model.player;

import config.Config;
import controller.Board;
import controller.BoardUpdater;
import javafx.application.Platform;
import model.algorithm.*;
import model.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.Random;

public class CheatAgent extends Player {
    private CheatAiTree cheatAiTree = new CheatAiTree();
    private Expectiminimax expectiminimax = new Expectiminimax();
    private boolean cheatIsWhite;
    private ChessCheatAiTreeNode maxima;
    private Board board;
    private boolean firstTurn = true;
    private static final int ply = 2;

    public CheatAgent() {
    }

    public void launch(Board board) {
        if(firstTurn){
            cheatIsWhite = board.getWhiteMove();
            firstTurn = false;
        }
        new Thread(() -> {
            try {
                Thread.sleep(50);
                cheatAgent(board);
                ChessCheatAiTreeNode move = getMaxima();
                char movablePieceChar = board.getMovablePiece();
                if (move.isDoPromotion()) {
                    board.storeMove();
                    BoardUpdater.runPromotion(board, move.getBoard(), move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
                    if (Config.GUI_ON) {
                        Platform.runLater(
                                new Thread(board::launchGuiUpdate)
                        );
                    }
                } else {
//                    if(!Board.GUI_ON) printBoard(board.getBoardModel(), board);
                    BoardUpdater.movePiece(board, move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
                    if (Config.GUI_ON) {
                        Platform.runLater(
                                new Thread(board::launchGuiUpdate)
                        );
                    }
                }
            }
            catch (Exception e) {
                System.err.println("Piece might already have been moved due to glitch in the threading");
                e.printStackTrace();
            }
        }).start();

    }

    public void runAgent(Board board){

    }

    public void cheatAgent(Board board) {
//        System.out.println(board.getWhiteMove());
        Board copy = board.clone();
        boolean maxIsWhite = board.getWhiteMove();
        ChessCheatAiTreeNode root;
        if (maxIsWhite) {
            root = new ChessCheatAiTreeNode(copy, 0, null, 1, 1, 0, 0, 0, 0, maxIsWhite);
        } else {
            root = new ChessCheatAiTreeNode(copy, 0, null, 2, 1, 0, 0, 0, 0, maxIsWhite);
        }
        double res = expectiminimax.expectiminimax(root, (ply * 2) - 1, (ply * 2) - 1);
        double maxValue;

        if(maxIsWhite){
            maxValue = Double.NEGATIVE_INFINITY;
        }
        else{
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
//        System.out.println(highestNodes.size());
        maxNode = highestNodes.get(rand.nextInt(highestNodes.size()));

//        System.out.println("move from: x=" + maxNode.getxFrom() + " y=" + maxNode.getyFrom() + " to: x=" + maxNode.getxTo() + " y=" + maxNode.getyTo());
//        printBoard(board.getBoardModel(), board);
        maxima = maxNode;
    }

    public ChessCheatAiTreeNode getMaxima() {
        return maxima;
    }

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
