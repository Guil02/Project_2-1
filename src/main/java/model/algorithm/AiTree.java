package model.algorithm;

import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import model.pieces.ChessPiece;

import java.util.ArrayList;

public class AiTree {
    public AiTree() {
    }

    public void createChildren(ChessTreeNode root, boolean doEvaluation){
        if(root.getNodeType()== 1 || root.getNodeType() == 2){
            createChanceChildren(root, doEvaluation);
        }
        else if(root.getNodeType() == 3){
            if(root.getParent().getNodeType() == 1){
                createMinChildren(root, doEvaluation);
            }
            else if(root.getParent().getNodeType() == 2){
                createMaxChildren(root, doEvaluation);
            }
        }
    }

    private void createMaxChildren(ChessTreeNode root, boolean doEvaluation) {
        for (ChessPiece[] pieces: root.getBoard().getBoardModel()) {
            for(ChessPiece piece: pieces){
                if(piece != null && piece.isTurn(root.getBoard()) && root.getBoard().getMovablePiece()==piece.getPieceChar()){
                    boolean[][] validMoves = piece.validMoves(root.getBoard());
                    createChild(root, validMoves, piece, 1, doEvaluation);
                }
            }
        }
    }

    private void createMinChildren(ChessTreeNode root, boolean doEvaluation) {
        for (ChessPiece[] pieces: root.getBoard().getBoardModel()) {
            for(ChessPiece piece: pieces){
                if(piece != null && piece.isTurn(root.getBoard()) && root.getBoard().getMovablePiece()==piece.getPieceChar()){
                    boolean[][] validMoves = piece.validMoves(root.getBoard());
                    createChild(root, validMoves, piece, 2, doEvaluation);
                }
            }
        }
    }

    private void createChanceChildren(ChessTreeNode root, boolean doEvaluation){
        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());

        for (Character movablePiece : movablePieces) {
            createChanceChild(root, doEvaluation, movablePiece);
        }
    }

    private void createChanceChild(ChessTreeNode root, boolean doEvaluation, char movablePiece){
        for (ChessPiece[] pieces: root.getBoard().getBoardModel()) {
            for(ChessPiece piece: pieces){
                if(piece != null && piece.isTurn(root.getBoard()) && movablePiece==piece.getPieceChar()){
                    boolean[][] validMoves = piece.validMoves(root.getBoard());
                    createChild(root, validMoves, piece, 3, doEvaluation);
                }
            }
        }
    }

    private void createChild(ChessTreeNode parent, boolean[][] validMoves, ChessPiece piece, int nodeType, boolean doEvaluation){

        for(int i = 0; i< validMoves.length; i++){
            for(int j = 0; j<validMoves[0].length; j++){
                if(validMoves[i][j]){
                    Board copy = parent.getBoard().clone();
                    BoardUpdater.movePiece(copy, piece.getX(), piece.getY(), i,j);

                    double value = 0;
                    if(doEvaluation){
                        value = staticBoardEvaluation(copy);
                    }

                    ChessTreeNode child = new ChessTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),i,j);
                    parent.addChild(child);
                }
            }
        }
    }

    private double staticBoardEvaluation(Board board){
        double value = 0;
        boolean seenFriendlyKing = false;
        boolean seenEnemyKing = false;

        for(ChessPiece[] pieces: board.getBoardModel()){
            for(ChessPiece piece: pieces){
                if(piece != null){
                    if(piece.isTurn(board)){
                        if(piece.getPieceType() == 6){
                            seenFriendlyKing  = true;
                        }
                        value += getPieceValue(piece.getPieceType());
                    }
                    else if(piece.getPieceType() == 6){
                        seenEnemyKing = true;
                    }
                }
            }
        }
        if(!seenFriendlyKing){
            return Double.MIN_VALUE;
        }
        if(!seenEnemyKing){
            return Double.MAX_VALUE;
        }
        return value;
    }

    private double getPieceValue(int pieceType){
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
}
