package controller;

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
        int numberOfPieces = usablePieces.size();
        int random = (int)(Math.random()*numberOfPieces);
        return usablePieces.get(random);
    }

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
        System.out.println(movablePieces);
        return choosePiece(movablePieces);
    }

    private void addCharToArray(ArrayList<Character> movablePieces, char charToAdd) {
        if(!movablePieces.contains(charToAdd)){
            movablePieces.add(charToAdd);
        }
    }

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

    public char firstMoveDiceRoll(){
        double r = Math.random();
        if(r<0.5){
            return'N';
        }
        else
            return'P';
    }
}
