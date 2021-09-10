package controller;

import gui.ChessSpot;

import java.util.ArrayList;

public class GraphicsConnector {
    public GraphicsConnector() {
    }

    /**
     * I want this method to return me an arraylist (can be another data structure)
     * of the 1 dimensional coordinates of all the fields to which the piece that is
     * located on the provided x and y coordinate can move to.
     *
     * @param x x coordinate of a piece
     * @param y y coordinate of a piece
     * @return an arraylist that contains all the 1 dimensional coordinates of the spots a piece can move to.
     */
    public ArrayList<Integer> getMoveAbleSpots(int x, int y){
        return new ArrayList<>();
    }

    /**
     * I want this to be a method that receives an int for the players where:
     * 0 = human
     * 1 = AI #1
     * etc.
     *
     * @param player1 the player with the white pieces
     * @param player2 the player with the black pieces
     */
    public void setPlayers(int player1, int player2){

    }

    /**
     * I want this to be a method that receives the initial and target position of a piece so that
     * it can be moved like that in the back end of the game this way the front end does not have
     * to deal with the actual moving of the pieces and only the displaying.
     *
     * @param initialX the initial x coordinate of the piece that is moved
     * @param initialY the initial y coordinate of the piece that is moved
     * @param finalX the final x coordinate of the piece that is moved
     * @param finalY the final y coordinate of the piece that is moved
     */
    public void doMove(int initialX, int initialY, int finalX, int finalY){

    }


    /**
     * I want this method to return the url of the image of the piece located at that spot
     * so say it is a white king, you return "gui/white_king.png".
     * this String can easily automatically be build by using string concatenation.
     * e.g.: String URL = "gui/"+color+"_"+pieceName+".png";
     * where color would be "white" or "black".
     * and pieceName "king", "queen" etc.
     *
     * @param x x coordinate of the piece
     * @param y y coordinate of the piece
     * @return the URL of the image.
     */
    public String getImage(int x, int y){
        return null;
    }
}
