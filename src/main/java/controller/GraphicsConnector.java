package controller;


import utils.Transform;
import model.Board;
import model.BoardUpdater;
import model.pieces.ChessPiece;

public class GraphicsConnector {

    private final GameRunner gameRunner;
    private Board board;
    private BoardUpdater boardUpdater;

    public GraphicsConnector(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
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
    public boolean[] getMoveAbleSpots(int x, int y){
        ChessPiece[][] piecesArray = board.getField();
        ChessPiece piece = piecesArray[x][y];
        boolean[][] validMoves = piece.validMoves();

        boolean[] temp = new boolean[64];
        int tempInt = 0;
        for (int i = 0; i < validMoves[0].length; i++) {
            for (int j = 0; j < validMoves.length; j++) {
                temp[tempInt++] = validMoves[j][i];
            }
        }
        return temp;
//        return Transform.transformBooleanToOneDimension(validMoves);
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
        boardUpdater.movePiece(initialX, initialY, finalX, finalY);
        System.out.println(gameRunner.getMovablePiece());
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
     * @return the URL of the image. return null if the spot is empty
     */
    public String getImage(int x, int y){
        char field = board.getCharOfField(x,y);
        switch(field){
            case 'b':
                return "gui/black_bishop.png";
            case 'k':
                return "gui/black_king.png";
            case 'n':
                return "gui/black_knight.png";
            case 'p':
                return "gui/black_pawn.png";
            case 'q':
                return "gui/black_queen.png";
            case 'r':
                return "gui/black_rook.png";
            case 'B':
                return "gui/white_bishop.png";
            case 'K':
                return "gui/white_king.png";
            case 'N':
                return "gui/white_knight.png";
            case 'P':
                return "gui/white_pawn.png";
            case 'Q':
                return "gui/white_queen.png";
            case 'R':
                return "gui/white_rook.png";
        }
        return "NO PIECE FOUND";
    }

    /**
     * I want this method to check for me if the move attempted is legal.
     * the piece will start in the position (initialX, initialY) with this the piece
     * can be identified. Then it tries to move to (finalX, finalY).
     *
     * @param initialX initial x coordinate of piece
     * @param initialY initial y coordinate of piece
     * @param finalX final x coordinate of piece
     * @param finalY final y coordinate of piece
     * @return returns whether the move that is attempted is legal.
     */
    public boolean canMove(int initialX, int initialY, int finalX, int finalY){
        ChessPiece[][] piecesArray = board.getField();
        ChessPiece piece = piecesArray[initialX][initialY];
        boolean[][] validMoves = piece.validMoves();

        char movablePiece = gameRunner.getMovablePiece();
        boolean movable = board.getCharOfField(initialX, initialY)==movablePiece;
        if(!movable){
            return false;
        }
        return validMoves[finalX][finalY];
    }


    /**
     * checks whether there is a piece locate on the field with the provided coordinates.
     *
     * @param x x coordinate of a field
     * @param y y coordinate of a field
     * @return whether there is a piece on that field
     */
    public boolean hasPiece(int x, int y){
        char field = board.getCharOfField(x,y);

        return Character.compare(field, '-') != 0;
    }

    public char[] getStartingPositions(){
        char[][] arrayOfPositions = new char[8][8];
        for (int i = 0; i < arrayOfPositions.length; i++) {
            for (int j = 0; j < arrayOfPositions.length; j++) {
                arrayOfPositions[i][j] = board.getCharOfField(i, j);
            }
        }

        return Transform.transformCharToOneDimension(arrayOfPositions);
    }

    public void init(){
        gameRunner.init();
    }

    public void initConnector(){
        board = gameRunner.getBoard();
        boardUpdater = gameRunner.getBoardUpdater();
    }

    public boolean isTurn(int x, int y){
        ChessPiece[][] piecesArray = board.getField();
        ChessPiece piece = piecesArray[x][y];
        return piece.isTurn();
    }
    //TODO add correct method that gets the correct image for the correct dice throw
    public String getDiceImage(int type){
        if(type ==1){
            return "gui/white_king.png";
        }
        else{
            return "gui/dice_six.png";
        }
    }
}
