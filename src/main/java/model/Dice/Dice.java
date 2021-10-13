package model.Dice;

import controller.GameRunner;
import model.pieces.ChessPiece;

import java.util.ArrayList;

/**
 * This class represents a die for the chess game, which determines the kind of piece that needs to be moved.
 */
public class Dice {

    /**
     * Private method to get the current GameRunner instance
     * @return  current GameRunner
     */
    private GameRunner gameRunner;
    public Dice(GameRunner gameRunner){
        this.gameRunner = gameRunner;
    }

    /**
     * Sees which pieces have a valid play and decides which piece will be rolled based on that.
     * @param   usablePieces - a list containing all the characters that represent pieces that have at least one valid move
     * @return  char - the character value representing the piece that will have to be moved in the next play
     */
    public char choosePiece(ArrayList<Character> usablePieces) {
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
    public char rollTheDice(){
        runValidMoves();
        ArrayList<Character> movablePieces = new ArrayList<>();
        ChessPiece[][] pieces = gameRunner.getBoard().getField();
        for(ChessPiece[] pieceArray : pieces){
            for(ChessPiece piece : pieceArray){
                if(piece!=null && piece.hasValidMove() && (gameRunner.getWhiteMove()==piece.isWhite())){
                    char charToAdd = piece.getPieceChar();
                    addCharToArray(movablePieces, charToAdd);
                }
            }
        }
        return choosePiece(movablePieces);
    }

    /**
     * Support method to add a piece to the set of movable pieces.
     * @see #rollTheDice()
     * @param movablePieces     set of current pieces that can be moved
     * @param charToAdd         piece to add
     */
    private void addCharToArray(ArrayList<Character> movablePieces, char charToAdd) {
        if(!movablePieces.contains(charToAdd)){
            movablePieces.add(charToAdd);
        }
    }

    /**
     * Support method to run all the valid moves.
     * @see #rollTheDice()
     */
    public void runValidMoves(){
        ChessPiece[][] pieces = gameRunner.getBoard().getField();

        for(ChessPiece[] piecesArray : pieces){
            for(ChessPiece piece : piecesArray){
                if(piece!=null){
                    if(gameRunner.getWhiteMove()){
                        if(piece.isWhite()) {
                            piece.validMoves();
                        }
                    }
                    else{
                        if(!piece.isWhite()){
                            piece.validMoves();
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
    public char firstMoveDiceRoll(){
        double r = Math.random();
        if(r<0.5){
            return'N';
        }
        else
            return'P';
    }
}
