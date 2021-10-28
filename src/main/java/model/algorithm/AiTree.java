package model.algorithm;

import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import model.pieces.ChessPiece;

import java.util.ArrayList;

public class AiTree {
    public AiTree() {
    }

    public void createChildren(ChessTreeNode root, boolean doEvaluation, boolean maxIsWhite){
        if(root.getNodeType()== 1 || root.getNodeType() == 2){
            createChanceChildren(root, doEvaluation, maxIsWhite);
        }
        else if(root.getNodeType() == 3){
            if(root.getParent().getNodeType() == 1){
                createMinChildren(root, doEvaluation, maxIsWhite);
            }
            else if(root.getParent().getNodeType() == 2){
                createMaxChildren(root, doEvaluation, maxIsWhite);
            }
        }
    }

    private void createChanceChildren(ChessTreeNode root, boolean doEvaluation, boolean maxIsWhite){
        for (ChessPiece[] pieces: root.getBoard().getBoardModel()) {
            for(ChessPiece piece: pieces){
                if(piece != null && piece.isTurn(root.getBoard()) && root.getBoard().getMovablePiece()==piece.getPieceChar()){
                    boolean[][] validMoves = piece.validMoves(root.getBoard());
                    createChild(root, validMoves, piece, 3, doEvaluation, maxIsWhite);
                }
            }
        }
    }

    private void createMaxChildren(ChessTreeNode root, boolean doEvaluation, boolean maxIsWhite) {
        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());

        for (Character movablePiece : movablePieces) {
            createChanceChild(root, doEvaluation, movablePiece, 1, 1.0/movablePieces.size(), maxIsWhite);
        }

    }

    private void createMinChildren(ChessTreeNode root, boolean doEvaluation, boolean maxIsWhite) {
        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());

        for (Character movablePiece : movablePieces) {
            createChanceChild(root, doEvaluation, movablePiece, 2, 1.0/movablePieces.size(), maxIsWhite);
        }
    }

    private void createChanceChild(ChessTreeNode parent, boolean doEvaluation, char movablePiece, int nodeType, double probability, boolean maxIsWhite){
        Board copy = parent.getBoard().clone();
        copy.setMovablePiece(movablePiece);
        double value = 0;
        if(doEvaluation){
            value = staticBoardEvaluation(copy, maxIsWhite);
        }
        ChessTreeNode child = new ChessTreeNode(copy,value, parent, nodeType, probability, 0,0,0,0);
        parent.addChild(child);
    }

    private void createChild(ChessTreeNode parent, boolean[][] validMoves, ChessPiece piece, int nodeType, boolean doEvaluation, boolean maxIsWhite){

        for(int i = 0; i< validMoves.length; i++){
            for(int j = 0; j<validMoves[0].length; j++){
                if(validMoves[i][j]){
                    Board copy = parent.getBoard().clone();
                    BoardUpdater.movePiece(copy, piece.getX(), piece.getY(), i,j);

                    double value = 0;
                    if(doEvaluation){
                        value = staticBoardEvaluation(copy, maxIsWhite);
                    }

                    ChessTreeNode child = new ChessTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),i,j);
                    parent.addChild(child);
                }
            }
        }
    }

    private double staticBoardEvaluation(Board board, boolean maxIsWhite){
        double value = 0;
        boolean seenWhiteKing = false;
        boolean seenBlackKing = false;

        for(ChessPiece[] pieces: board.getBoardModel()){
            for(ChessPiece piece: pieces){
                if(piece != null){
                    if(maxIsWhite){
                        if(piece.isWhite()){
                            value += getPieceValue(piece.getPieceType());
                        }
                    }
                    else{
                        if(!piece.isWhite()){
                            value += getPieceValue(piece.getPieceType());
                        }
                    }

                    if(piece.getPieceChar() == 'K'){
                        seenWhiteKing = true;
                    }
                    if(piece.getPieceChar() == 'k'){
                        seenBlackKing = true;
                    }
                }
            }
        }
        if(maxIsWhite){
            if(!seenWhiteKing){
                return Double.MIN_VALUE;
            }
            else if(!seenBlackKing){
                return Double.MAX_VALUE;
            }
        }
        else {
            if(!seenBlackKing){
                return Double.MIN_VALUE;
            }
            else if(!seenWhiteKing){
                return Double.MAX_VALUE;
            }
        }
        return value;
    }

    private double staticBoardEvaluation2(Board board, boolean maxIsWhite){
        double value = 0;
        double toBeSubtracted = 0;
        boolean seenWhiteKing = false;
        boolean seenBlackKing = false;

        for(ChessPiece[] pieces: board.getBoardModel()){
            for(ChessPiece piece: pieces){
                if(piece != null){
                    if(maxIsWhite){
                        if(piece.isWhite()){
                            value += getPieceValue(piece.getPieceType());
                        }
                        else{
                            toBeSubtracted += getPieceValue(piece.getPieceType());
                        }
                    }
                    else{
                        if(!piece.isWhite()){
                            value += getPieceValue(piece.getPieceType());
                        }
                        else{
                            toBeSubtracted += getPieceValue(piece.getPieceType());
                        }
                    }

                    if(piece.getPieceChar() == 'K'){
                        seenWhiteKing = true;
                    }
                    if(piece.getPieceChar() == 'k'){
                        seenBlackKing = true;
                    }
                }
            }
        }
        if(maxIsWhite){
            if(!seenWhiteKing){
                return Double.MIN_VALUE;
            }
            else if(!seenBlackKing){
                return Double.MAX_VALUE;
            }
        }
        else {
            if(!seenBlackKing){
                return Double.MIN_VALUE;
            }
            else if(!seenWhiteKing){
                return Double.MAX_VALUE;
            }
        }
        return value - toBeSubtracted;
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
