package controller;

import java.util.ArrayList;

public class Dice {


    /**
     * sees which pieces have a valid play and decides which piece will be rolled based on that
     * @param usablePieces - a list containing all the characters that represent pieces that have at least one valid move
     * @return char - the character value representing the piece that will have to be moved in the next play
     */
    public char rollTheDice(ArrayList<Character> usablePieces) {
        int numberOfPieces = usablePieces.size();
        int random = (int)(Math.random()*numberOfPieces);
        return  usablePieces.get(random);

    }
}
