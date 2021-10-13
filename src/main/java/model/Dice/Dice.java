package model.Dice;

import controller.GameRunner;
import model.pieces.ChessPiece;

import java.util.ArrayList;

public class Dice {
    private GameRunner gameRunner;
    public Dice(GameRunner gameRunner){
        this.gameRunner = gameRunner;
    }
    /**
     * sees which pieces have a valid play and decides which piece will be rolled based on that
     * @param usablePieces - a list containing all the characters that represent pieces that have at least one valid move
     * @return char - the character value representing the piece that will have to be moved in the next play
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
    * adds pieces that can be moved to the arraylist
     * @return char - the character value of the movable piece
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


    private void addCharToArray(ArrayList<Character> movablePieces, char charToAdd) {
        if(!movablePieces.contains(charToAdd)){
            movablePieces.add(charToAdd);
        }
    }

    /**
     * sets the hasValidMove boolean in all the pieces
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
    * dice roll for the first move, because only n or p can be moved and it will take some initializing to be able to run the other methods
    * @char - n or p
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