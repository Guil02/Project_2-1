package utils;

import controller.Board;
import model.pieces.ChessPiece;

import java.util.ArrayList;

public class GABoardEncoding {
    private static final int evaluationSize = 25;
    private static final char[] pieceTypes = {'K', 'k', 'Q', 'q', 'R', 'r', 'B', 'b', 'N', 'n', 'P', 'p'};

    /**
     * @param board
     * @return
     */
    public int[] evaluate(Board board) {
        int[] evaluation = new int[evaluationSize];

        //sets the first value for whose move it is, 1 for white 0 for black
        evaluation[0] = board.getWhiteMove() ? 1 : 0;

        int filledIn = 1;
        for (int i = filledIn; i < filledIn + pieceTypes.length; i++) {
            //i-filledIn because we start from the index in filledIn
            evaluation[i] = countPiece(board, pieceTypes[i - filledIn]);
        }
        //12 more items have been filled in
        filledIn += pieceTypes.length;

        for (int i = filledIn; i < filledIn + pieceTypes.length; i++) {
            //i-filledIn because we start from the index in filledIn
            evaluation[i] = countPieceMobility(board, pieceTypes[i - filledIn]);
        }

        return evaluation;
    }

    /**
     * @param board - static board
     * @param piece - which piece is it? SENSITIVE TO CAPS
     * @return the amount of pieces of the specified type.
     */
    private int countPiece(Board board, char piece) {
        return board.getAmountOfPieces(piece);
    }

    private int countPieceMobility(Board board, char piece){
        Board copy = board.clone();
        copy.setWhiteMove(getWhiteOrBlack(piece));
        int count = 0;
        ArrayList<ChessPiece> pieces = copy.getPieces(piece);
        for(ChessPiece chessPiece: pieces){
            boolean[][] valid_moves = chessPiece.validMoves(copy);
            count += getAmountOfValidMoves(valid_moves);
        }
        return count;
    }

    private int getAmountOfValidMoves( boolean[][] valid_moves) {
        int count = 0;
        for(int i = 0; i< Board.getBoardSize(); i++){
            for(int j = 0; j< Board.getBoardSize(); j++){
                if(valid_moves[i][j]){
                    count++;
                }
            }
        }
        return count;
    }

    private boolean getWhiteOrBlack(char c){
        return switch (c) {
            case 'P', 'N', 'B', 'R', 'Q', 'K' -> true;
            case 'p', 'n', 'b', 'r', 'q', 'k' -> false;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

}
