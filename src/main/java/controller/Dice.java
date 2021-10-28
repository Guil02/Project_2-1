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
}
