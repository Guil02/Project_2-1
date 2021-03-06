package model.algorithm;

import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import model.pieces.ChessPiece;
import utils.NodeEnum;

import java.util.ArrayList;

public class CheatAiTree extends AiTree{

    public CheatAiTree(){

    }
    public void createChildren(ChessCheatAiTreeNode root, boolean cheatIsWhite , boolean doEvaluation) {

        if (root.getNodeType() == 1) {
            createMaxChildren(root, doEvaluation, cheatIsWhite);
        } else if (root.getNodeType() == 2) {
            createMinChildren(root, doEvaluation,  cheatIsWhite);
        } else {
            createChanceChildren(root, doEvaluation);
        }

    }
    public void createMaxChildren(ChessCheatAiTreeNode root, boolean doEvaluation, boolean cheatIsWhite){
        for (ChessPiece[] pieces: root.getBoard().getBoardModel()) {
            for(ChessPiece piece: pieces){
                if(cheatIsWhite){
                    if(piece != null && piece.isTurn(root.getBoard())){
                        boolean[][] validMoves = piece.validMoves(root.getBoard());
                        createChild(root, validMoves, piece, 3, doEvaluation);
                    }
                }
                else{
                    if(piece != null && piece.isTurn(root.getBoard())&&root.getBoard().getMovablePiece()==piece.getPieceChar()){
                        boolean[][] validMoves = piece.validMoves(root.getBoard());
                        createChild(root, validMoves, piece, 2, doEvaluation);
                    }
                }
            }
        }
    }

    public void createMinChildren(ChessCheatAiTreeNode root, boolean doEvaluation, boolean cheatIsWhite){
        for (ChessPiece[] pieces: root.getBoard().getBoardModel()) {
            for(ChessPiece piece: pieces){
                if(!cheatIsWhite){
                    if(piece != null && piece.isTurn(root.getBoard())){
                        boolean[][] validMoves = piece.validMoves(root.getBoard());
                        createChild(root, validMoves, piece, 3, doEvaluation);
                    }
                }
                else{
                    if(piece != null && piece.isTurn(root.getBoard())&&root.getBoard().getMovablePiece()==piece.getPieceChar()){
                        boolean[][] validMoves = piece.validMoves(root.getBoard());
                        createChild(root, validMoves, piece, 1, doEvaluation);
                    }
                }
            }
        }
    }

    private void createChanceChildren(ChessCheatAiTreeNode root, boolean doEvaluation){
        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());
        for (Character movablePiece : movablePieces) {
            if(root.getParent().getNodeType()== NodeEnum.MAX_NODE.getId()){
                createChanceChild(root, doEvaluation, movablePiece, NodeEnum.MIN_NODE.getId(), 1.0/movablePieces.size());
            }
            else{
                createChanceChild(root, doEvaluation, movablePiece, NodeEnum.MAX_NODE.getId(), 1.0/movablePieces.size());
            }
        }
    }

    public void createChanceChild(ChessCheatAiTreeNode parent, boolean doEvaluation, char movablePiece, int nodeType, double probability){
        Board copy = parent.getBoard().clone();
        copy.setMovablePiece(movablePiece);
        double value = 0;
        if(doEvaluation){
            value = staticBoardEvaluation(copy);
        }
        ChessCheatAiTreeNode child = new ChessCheatAiTreeNode(copy,value, parent, nodeType, probability, 0,0,0,0, parent.isCheatIsWhite());
        parent.addChild(child);
    }
    private void createChild(ChessCheatAiTreeNode parent, boolean[][] validMoves, ChessPiece piece, int nodeType, boolean doEvaluation){

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
                    if(doEvaluation){
                        value = staticBoardEvaluation(copy);
                    }

                    ChessCheatAiTreeNode child = new ChessCheatAiTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),i,j, parent.isCheatIsWhite());
                    parent.addChild(child);
                }
            }
        }
    }
    private void createPromotionChild(ChessCheatAiTreeNode parent, ChessPiece piece, int xTo, int yTo, int pieceType, boolean doEvaluation, int nodeType){
        Board copy = parent.getBoard().clone();
        BoardUpdater.removePiece(copy, piece.getX(), piece.getY());
        ChessPiece promoted = BoardUpdater.createPiece(piece.isWhite(), xTo, yTo, pieceType);
        BoardUpdater.addPiece(copy, promoted);

        double value = 0;
        if(doEvaluation){
            value = staticBoardEvaluation(copy);
        }

        ChessCheatAiTreeNode child = new ChessCheatAiTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),xTo,yTo, parent.isCheatIsWhite());
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

    private double staticBoardEvaluation2(Board board, boolean maxIsWhite){
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
                return Double.NEGATIVE_INFINITY;
            }
            else if(!seenBlackKing){
                return Double.MAX_VALUE;
            }
        }
        else {
            if(!seenBlackKing){
                return Double.NEGATIVE_INFINITY;
            }
            else if(!seenWhiteKing){
                return Double.MAX_VALUE;
            }
        }
        return value;
    }

    double staticBoardEvaluation(Board board){
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
    private double getRandomElement(){
        return Math.random()*10-5;
    }
}
