package model.player;

import config.Config;
import controller.Board;
import controller.BoardUpdater;
import javafx.application.Platform;
import model.algorithm.*;
import model.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.Random;

public class SearchAgent extends Player {
    private ExpectiminimaxStar2 expectiminimaxStar2 = new ExpectiminimaxStar2(true);
    private ChessTreeNode maxima;
    private Board board;
    private static final int ply = 1;

    public SearchAgent(Board board) {
        this.board = board;
    }

    public void runAgent(Board board) {
        Board copy = board.clone();
        boolean maxIsWhite = board.getWhiteMove();
        ChessTreeNode root;
        if(maxIsWhite){
            root = new ChessTreeNode(copy, 0, null, 1, 1, 0, 0, 0, 0);
        }
        else{
            root = new ChessTreeNode(copy, 0, null, 2, 1, 0, 0, 0, 0);
        }
        double res = expectiminimaxStar2.expectiminimaxWithStar2(root, (ply*2)-1, (ply*2)-1);
        double maxValue;
        if(maxIsWhite){
            maxValue = Double.NEGATIVE_INFINITY;
        }
        else{
            maxValue = Double.POSITIVE_INFINITY;
        }
        ArrayList<ChessTreeNode> highestNodes = new ArrayList<>();
        ChessTreeNode maxNode = (ChessTreeNode) root.getChildren().get(0);
        highestNodes.add(maxNode);
        for (TreeNode child : root.getChildren()) {
            ChessTreeNode subChild = (ChessTreeNode) child;
            if(maxIsWhite){

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
            else{
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

    public static double evaluation(Board board){
        double value = 0;
        double enemyPiecesOnBoardValue = 0;
        boolean seenWhiteKing = false;
        boolean seenBlackKing = false;
//        int distanceWhiteKingFromBackRow = 0;
//        int distanceBlackKingFromBackRow = 0;

        for(ChessPiece[] pieces: board.getBoardModel()){
            for(ChessPiece piece: pieces){
                if(piece != null){
                    if(piece.isWhite()){
                        value += getPieceValue(piece.getPieceType());
                    }
                    else{
                        enemyPiecesOnBoardValue += getPieceValue(piece.getPieceType());
                    }

                    if(piece.getPieceChar() == 'K'){
//                        distanceWhiteKingFromBackRow = 7-piece.getY();
                        seenWhiteKing = true;
                    }
                    if(piece.getPieceChar() == 'k'){
//                        distanceBlackKingFromBackRow = piece.getY();
                        seenBlackKing = true;
                    }
                }
            }
        }
        if(!seenWhiteKing){
            return -100;
        }
        else if(!seenBlackKing){
            return 100;
        }
        return value - enemyPiecesOnBoardValue + getRandomElement();
    }

    private static double getPieceValue(int pieceType){
        return switch (pieceType) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 3;
            case 4 -> 5;
            case 5 -> 9;
            case 6 -> 0;
            default -> 0;
        };
    }
    private static double getRandomElement(){
        return Math.random()*10-5;
    }
}
