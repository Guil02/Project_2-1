package model.algorithm;

import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import model.pieces.ChessPiece;
import utils.NodeEnum;

import java.util.ArrayList;

public class TreeBuilder {
    public void createChildren(TreeNode root, boolean doEvaluation){
        if(root.getNodeType()== NodeEnum.MAX_NODE.getId() || root.getNodeType() == NodeEnum.MIN_NODE.getId()){
            createChance(root, doEvaluation);
        }
        else if(root.getNodeType() == NodeEnum.CHANCE_NODE.getId()){
            if(root.getParent().getNodeType() == NodeEnum.MAX_NODE.getId()){
                createMaxOrMin(root, doEvaluation, false);
            }
            else if(root.getParent().getNodeType() == NodeEnum.MIN_NODE.getId()){
                createMaxOrMin(root, doEvaluation, true);
            }
        }
    }


    private void createMaxOrMin(TreeNode parent, boolean doEvaluation, boolean max) {
        ArrayList<Character> movablePieces = Dice.getMovablePieces(parent.getBoard());

        for (Character movablePiece : movablePieces) {
            if(max){
                createCharChild(parent, doEvaluation, movablePiece, NodeEnum.MAX_NODE.getId(), 1.0/movablePieces.size());
            }
            else{
                createCharChild(parent, doEvaluation, movablePiece, NodeEnum.MIN_NODE.getId(), 1.0/movablePieces.size());
            }
        }
    }

    private void createCharChild(TreeNode parent, boolean doEvaluation, Character movablePiece, int nodeType, double probability) {
        Board copy = parent.getBoard().clone();
        copy.setMovablePiece(movablePiece);
        double value = 0;
        TreeNode child = childCreate(copy, value, parent, nodeType, probability, 0,0,0,0);
        if(doEvaluation){
            child.evaluate();
        }
        parent.addChild(child);
    }

    private void createChance(TreeNode root, boolean doEvaluation) {
        for (ChessPiece[] pieces: root.getBoard().getBoardModel()) {
            for(ChessPiece piece: pieces){
                if(piece != null && piece.isTurn(root.getBoard()) && root.getBoard().getMovablePiece()==piece.getPieceChar()){
                    boolean[][] validMoves = piece.validMoves(root.getBoard());
                    createChild(root, validMoves, piece, NodeEnum.CHANCE_NODE.getId(), doEvaluation);
                }
            }
        }
    }

    private void createChild(TreeNode parent, boolean[][] validMoves, ChessPiece piece, int nodeType, boolean doEvaluation){

        for(int i = 0; i< validMoves.length; i++){
            for(int j = 0; j<validMoves[0].length; j++){
                if(validMoves[i][j]){
                    if(isPromotion(piece, j)){
                        createPromotionChild(parent, piece, i, j, 2, doEvaluation, nodeType);
                        createPromotionChild(parent, piece, i, j, 3, doEvaluation, nodeType);
                        createPromotionChild(parent, piece, i, j, 4, doEvaluation, nodeType);
                        createPromotionChild(parent, piece, i, j, 5, doEvaluation, nodeType);
                        continue;
                    }

                    Board copy = parent.getBoard().clone();
                    BoardUpdater.movePiece(copy, piece.getX(), piece.getY(), i,j);

                    double value = 0;
                    double probability = 1;
                    TreeNode child = childCreate(copy, value, parent, nodeType, probability, piece.getX(), piece.getY(), i, j);
                    if(doEvaluation){
                        child.evaluate();
                    }

                    parent.addChild(child);
                }
            }
        }
    }

    private TreeNode childCreate(Board board, double value, TreeNode parent, int nodeType, double probability, int xFrom, int yFrom, int xTo, int yTo){
        switch(parent.getObjectType()){
            case SEARCH_NODE:
                return new ChessTreeNode(board, value, parent, nodeType, probability, xFrom, yFrom,xTo,yTo);
            case NN_NODE:
                return new NNTreeNode(board, value, parent, nodeType, probability, xFrom, yFrom, xTo, yTo, ((NNTreeNode) parent).getNnAgent());
            case TD_NODE:
                return new TDTreeNode(board, value, parent, nodeType, probability, xFrom, yFrom, xTo, yTo);
            case CHEAT_NODE:
                //TODO: REMOVE THE MAXISWHITE PART AFTER CHEAT AI IS DONE.
                return new ChessCheatAiTreeNode(board, value, parent, nodeType, probability, xFrom, yFrom, xTo, yTo, true);
            default:
                return null;
        }
    }

    private void createPromotionChild(TreeNode parent, ChessPiece piece, int xTo, int yTo, int pieceType, boolean doEvaluation, int nodeType) {
        Board copy = parent.getBoard().clone();
        BoardUpdater.removePiece(copy, piece.getX(), piece.getY());
        ChessPiece promoted = BoardUpdater.createPiece(piece.isWhite(), xTo, yTo, pieceType);
        BoardUpdater.addPiece(copy, promoted);


        double value = 0;
        TreeNode child = new ChessTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),xTo,yTo);
        if(doEvaluation){
            child.evaluate();
        }
        child.setDoPromotion(true);
        parent.addChild(child);
    }

    private boolean isPromotion(ChessPiece piece, int yTo){
        if(piece.getPieceType()==1){
            if(piece.isWhite()&&yTo==0){
                return true;
            }
            else if(!piece.isWhite()&&yTo==7){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}
