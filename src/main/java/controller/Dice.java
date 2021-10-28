package controller;

import model.pieces.ChessPiece;

import java.util.ArrayList;

/**
 * This class represents a die for the chess game, which determines the kind of piece that needs to be moved.
 */
public class Dice {
    /**
     * Sees which pieces have a valid play and decides which piece will be rolled based on that.
     * @param   usablePieces - a list containing all the characters that represent pieces that have at least one valid move
     * @return  char - the character value representing the piece that will have to be moved in the next play
     */
    public static char choosePiece(ArrayList<Character> usablePieces) {
        if(usablePieces.size()!=0){
            int numberOfPieces = usablePieces.size();
            int random = (int)(Math.random()*numberOfPieces);
            return usablePieces.get(random);
        }
        else return '.';
    }

    /**
     * Executes the dice roll and checks if the result has a possible move.
     * @return  char - the character of the kind of piece that can be moved
     */
    public static void rollTheDice(Board board){
        runValidMoves(board);
        ArrayList<Character> movablePieces = new ArrayList<>();
        for(ChessPiece[] pieceArray : board.getBoardModel()){
            for(ChessPiece piece : pieceArray){
                if(piece!=null && piece.hasValidMove() && (board.getWhiteMove()==piece.isWhite())){
                    char charToAdd = piece.getPieceChar();
                    addCharToArray(board, movablePieces, charToAdd);
                }
            }
        }
        board.setMovablePiece(Dice.choosePiece(movablePieces));
    }

    public static ArrayList<Character> getMovablePieces(Board board){
        runValidMoves(board);
        ArrayList<Character> movablePieces = new ArrayList<>();
        for(ChessPiece[] pieceArray : board.getBoardModel()){
            for(ChessPiece piece : pieceArray){
                if(piece!=null && piece.hasValidMove() && (board.getWhiteMove()==piece.isWhite())){
                    char charToAdd = piece.getPieceChar();
                    addCharToArray(board, movablePieces, charToAdd);
                }
            }
        }
        return movablePieces;
    }

    /**
     * Support method to add a piece to the set of movable pieces.
     * @param movablePieces     set of current pieces that can be moved
     * @param charToAdd         piece to add
     */
    private static void addCharToArray(Board board, ArrayList<Character> movablePieces, char charToAdd) {
        if(!movablePieces.contains(charToAdd)){
            movablePieces.add(charToAdd);
        }
    }

    /**
     * Support method to run all the valid moves.
     */
    public static void runValidMoves(Board board){
        for(ChessPiece[] piecesArray : board.getBoardModel()){
            for(ChessPiece piece : piecesArray){
                if(piece!=null){
                    if(board.getWhiteMove()){
                        if(piece.isWhite()) {
                            piece.validMoves(board);
                        }
                    }
                    else{
                        if(!piece.isWhite()){
                            piece.validMoves(board);
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the kind of piece that can be moved in the first move.
     * Only knight and pawn are possible options.
     * @return  char - kind of piece to move
     */
    public static void firstMoveDiceRoll(Board board){
        double r = Math.random();
        if(r<0.5){
            board.setMovablePiece('N');
        }
        else
            board.setMovablePiece('P');
    }
}
