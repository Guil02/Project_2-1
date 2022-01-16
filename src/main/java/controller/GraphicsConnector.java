package controller;

import gui.ChessGUI;
import model.pieces.*;
import model.pieces.ChessPiece;

/**
 * This class is used as an exchange point for the GUI and the back end.
 * Relevant data is passed forth and back to separate concerns.
 */
public class GraphicsConnector {

    // Variables
    private Board board;
    private GameRunner gamerunner;
    private ChessGUI chessGUI;
    private boolean isWhite;
    private int x;
    private int y;

    /**
     * Constructor
     * @param gameRunner
     */
    public GraphicsConnector(GameRunner gameRunner) {
        this.gamerunner = gameRunner;
    }

    /**
     * Returns an array of the 1 dimensional coordinates of all the fields to which the piece that is
     * located on the provided x and y coordinate can move to.
     *
     * @param x x coordinate of a piece
     * @param y y coordinate of a piece
     * @return an array that contains all the 1 dimensional coordinates of the spots a piece can move to
     */
    public boolean[] getMoveAbleSpots(int x, int y){
        ChessPiece[][] piecesArray = board.getBoardModel();
        ChessPiece piece = piecesArray[x][y];
        boolean[][] validMoves = piece.validMoves(board);

        boolean[] canMoveToField = new boolean[64];
        int tempInt = 0;
        for (int i = 0; i < validMoves[0].length; i++) {
            for (int j = 0; j < validMoves.length; j++) {
                canMoveToField[tempInt++] = validMoves[j][i];
            }
        }
        char movablePiece = board.getMovablePiece();
        boolean movable = board.getCharOffField(x, y)==movablePiece;
        if(!movable){
            return new boolean[64];
        }
        return canMoveToField;
    }

    /**
     * Method that receives an int for the players where:
     * 0 = human
     * 1 = AI #1
     * etc.
     *
     * @param player1 the player with the white pieces
     * @param player2 the player with the black pieces
     */
    public void setPlayers(int player1, int player2){
        board.setPlayers(player1, player2);
    }

    /**
     * @return true if the player that has to move is a human player
     */
    public boolean isHumanPlayer() {
        if(board.getWhiteMove()){
            return board.getPlayer1() == 0;
        }
        else{
            return board.getPlayer2() == 0;
        }
    }

    /**
     * Method that receives the initial and target position of a piece so that
     * it can be moved like that in the back end of the game this way the front end does not have
     * to deal with the actual moving of the pieces and only the displaying.
     *
     * @param initialX the initial x coordinate of the piece that is moved
     * @param initialY the initial y coordinate of the piece that is moved
     * @param finalX the final x coordinate of the piece that is moved
     * @param finalY the final y coordinate of the piece that is moved
     */
    public void doMove(int initialX, int initialY, int finalX, int finalY){
        BoardUpdater.movePiece(board, initialX, initialY, finalX, finalY);
        gamerunner.debugWindowStage.incrementPlyCount();
    }

    /**
     * Method to return the url of the image of the piece located at that spot
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
        char field = board.getCharOffField(x,y);
        return switch (field) {
            case 'b' -> "gui/bB.png";
            case 'k' -> "gui/bK.png";
            case 'n' -> "gui/bN.png";
            case 'p' -> "gui/bP.png";
            case 'q' -> "gui/bQ.png";
            case 'r' -> "gui/bR.png";
            case 'B' -> "gui/wB.png";
            case 'K' -> "gui/wK.png";
            case 'N' -> "gui/wN.png";
            case 'P' -> "gui/wP.png";
            case 'Q' -> "gui/wQ.png";
            case 'R' -> "gui/wR.png";
            default -> "gui/error_cross.png";
        };
    }

    /**
     * Method to check for me if the move attempted is legal.
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
        ChessPiece[][] piecesArray = board.getBoardModel();
        ChessPiece piece = piecesArray[initialX][initialY];
        boolean[][] validMoves = piece.validMoves(board);
        char movablePiece = board.getMovablePiece();
        boolean movable = board.getCharOffField(initialX, initialY)==movablePiece;
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
        char field = board.getCharOffField(x,y);
        return field != '-';
    }

    /**
     * Setter of a board.
     * @param board desired board
     */
    public void setBoard(Board board) {
        this.board = board;
        board.setGraphicsConnector(this);
    }

    /**
     * Initializes both players.
     * @param playerOne player one
     * @param playerTwo player two
     */
    public void init(int playerOne, int playerTwo){
        gamerunner.init(playerOne, playerTwo);
   }

    /**
     * Checks, if it is the turn of a certain piece at a target position.
     * @param x     target x-position
     * @param y     target y-position
     * @return      true if it is the pieces' turn
     */
    public boolean isTurn(int x, int y){
        ChessPiece[][] piecesArray = board.getBoardModel();
        ChessPiece piece = piecesArray[x][y];
        return piece.isTurn(board);
    }

    /**
     * @return white or black based on the turn
     */
    public boolean whoTurn(){
        return !board.getWhiteMove();
    }


    /**
     * Returns the image file of a certain dice roll.
     * @param type  input condition
     * @return file name of the dice image
     */
    public String getDiceImage(int type){
        if(type == 1) {
            switch (board.getMovablePiece()) {
                case 'K':
                    return "gui/wK.png";
                case 'Q':
                    return "gui/wQ.png";
                case 'R':
                    return "gui/wR.png";
                case 'B':
                    return "gui/wB.png";
                case 'N':
                    return "gui/wN.png";
                case 'P':
                    return "gui/wP.png";
                case 'k':
                    return "gui/bK.png";
                case 'q':
                    return "gui/bQ.png";
                case 'r':
                    return "gui/bR.png";
                case 'b':
                    return "gui/bB.png";
                case 'n':
                    return "gui/bN.png";
                case 'p':
                    return "gui/bP.png";
            }
        }
        else {
            switch (board.getMovablePiece()) {
                case 'K':
                case 'k':
                    return "gui/dice_six.png";
                case 'Q':
                case 'q':
                    return "gui/dice_five.png";
                case 'R':
                case 'r':
                    return "gui/dice_four.png";
                case 'B':
                case 'b':
                    return "gui/dice_three.png";
                case 'N':
                case 'n':
                    return "gui/dice_two.png";
                case 'P':
                case 'p':
                    return "gui/dice_one.png";
            }
        }
        return "gui/error_cross.png";
    }

    /**
     * Sets the chessGUI to a given instance
     * @param chessGUI chessGUI instance
     */
    public void setChessGUI(ChessGUI chessGUI) {
        this.chessGUI = chessGUI;
    }

    /**
     * Gets the path to the image file for a promotion in the game.
     * @param type of piece
     * @return url of piece - used for promotion
     */
    public String getPromotionImage(int type) {
        switch(type){
            case 1:
                if(whoTurn()){
                    return "gui/wN.png";
                }
                else{
                    return "gui/bN.png";
                }
            case 2:
                if(whoTurn()){
                    return "gui/wB.png";
                }
                else{
                    return "gui/bB.png";
                }
            case 3:
                if(whoTurn()){
                    return "gui/wR.png";
                }
                else{
                    return "gui/bR.png";
                }
            case 4:
                if(whoTurn()){
                    return "gui/wQ.png";
                }
                else{
                    return "gui/bQ.png";
                }
            default:
                return "gui/error_cross.png";
        }
    }

    /**
     * Starts the dialog for a promotion choice.
     * @param isWhite - turn boolean
     * @param boardModel - board moodel
     * @param x x-pos
     * @param y y-pos
     */
    public void startPromotionDialog(boolean isWhite, Board boardModel, int x, int y){
        chessGUI.launchPromotionDialog();
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
    }

    /**
     * Carries out the promotion - makes the new piece.
     * @param type of piece
     */
    public void doPromotion(int type) {
        switch (type){
            case 1:
                BoardUpdater.doPromotion(board, new KnightPiece(this.isWhite, this.x, this.y));
                break;
            case 2:
                BoardUpdater.doPromotion(board, new BishopPiece(this.isWhite, this.x, this.y));
                break;
            case 3:
                BoardUpdater.doPromotion(board, new RookPiece(this.isWhite, this.x, this.y));
                break;
            case 4:
                BoardUpdater.doPromotion(board, new QueenPiece(this.isWhite, this.x, this.y));
                break;
        }
    }

    /**
     * Update images
     */
    public void updateImages() {
        chessGUI.updateImages();
    }

    /**
     * Changes turn to the other player.
     */
    public void changeTurn(){
        chessGUI.updateDisplaySize(chessGUI.getHeight());
    }

    /**
     * Defines a winning player.
     * @param white true if white won, false if black won
     */
    public void setWin(boolean white){
        chessGUI.setWin(white);
    }

    @Override
    protected GraphicsConnector clone(){
        try{
            return (GraphicsConnector) super.clone();
        }
        catch(CloneNotSupportedException e){
            System.err.println("could not make clone.");
        }
        return new GraphicsConnector(new GameRunner());
    }

    /**
     * Launches a possible agent player.
     */
    public void launchAI() {
        board.checkAi();
    }
}
