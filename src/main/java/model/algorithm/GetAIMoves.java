package model.algorithm;

import controller.Board;
import controller.BoardUpdater;
import model.pieces.ChessPiece;

public class GetAIMoves {
    public GetAIMoves() {
        int i = 63;
        int x = i%8;
        int y = (i-x)/8;
    }

    public ChessTreeNode createChildren(ChessTreeNode root, boolean doEvaluation){
        Board board = root.getBoard();
        for (ChessPiece[] pieces: board.getBoardModel()) {
            for(ChessPiece piece: pieces){
                if(piece != null && piece.isTurn(board) && board.getMovablePiece()==piece.getPieceChar()){
                    boolean[][] validMoves = piece.validMoves(board);
                    createChild(root, validMoves, piece, getNodeType(root), doEvaluation);
                }
            }
        }
        return root;
    }

    public double staticBoardEvaluation(Board board){
        double value = 0;

        for(ChessPiece[] pieces: board.getBoardModel()){
            for(ChessPiece piece: pieces){
                if(piece != null){
                    value += getPieceValue(piece.getPieceType());
                }
            }
        }

        return value;
    }

    public double getPieceValue(int pieceType){
        switch (pieceType){
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 3;
            case 4:
                return 5;
            case 5:
                return 9;
            case 6:
                return 0;
            default:
                return 0;
        }
    }

    public int getNodeType(ChessTreeNode node){
        if(node.getNodeType()==1 || node.getNodeType() == 2){
            return 3;
        }
        else if(node.getNodeType()==3){
            if(node.getParent().getNodeType() == 1){
                return 2;
            }
            else if(node.getParent().getNodeType()==2){
                return 1;
            }
        }
        return 1;
    }

    public void createChild(ChessTreeNode parent, boolean[][] validMoves, ChessPiece piece, int nodeType, boolean doEvaluation){
        for(int i = 0; i<Board.getBoardSize(); i++){
            for(int j = 0; j<Board.getBoardSize(); j++){
                if(validMoves[i][j]){
                    Board copy = parent.getBoard().clone();
                    BoardUpdater.movePiece(copy, piece.getX(), piece.getY(), i,j);
                    double value = 0;
                    if(doEvaluation){
                        value = staticBoardEvaluation(copy);
                    }
                    ChessTreeNode child = new ChessTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),i,j, parent.isMaxIsWhite());
                    parent.addChild(child);
                }
            }
        }
    }
}
