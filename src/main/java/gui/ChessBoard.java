package gui;

import controller.GraphicsConnector;
import javafx.scene.layout.GridPane;

/**
 * is the layout manager for the chess game itself
 */
public class ChessBoard extends GridPane {
    public static final int TOTAL_AMOUNT_OF_SPOTS = 63;
    private ChessSpot[] board = new ChessSpot[64];
    private GraphicsConnector graphicsConnector;
    private ChessGUI chessGUI;

    /**
     * a constructor that will create a 8x8 board for the chess game to take place on
     */
    public ChessBoard(GraphicsConnector graphicsConnector, ChessGUI chessGUI) {
        this.graphicsConnector = graphicsConnector;
        this.chessGUI = chessGUI;
        for(int i = 0; i<64; i++){
            int x = i%8;
            int y = (i-x)/8;
            ChessSpot spot = new ChessSpot(graphicsConnector,this,x,y);
            add(spot,x,y);
            board[i]=spot;
        }

    }

    /**
     * @return an array that contains all the spots on the board and can the easily be gone through.
     */
    public ChessSpot[] getBoard() {
        return board;
    }
    private boolean isTurned = false;

    /**
     * translates two-dimensional coordinates into the 1 dimensional coordinate that can then be used to access the array.
     *
     * @param x the x coordinate of the spot on the board you want to get returned.
     * @param y the y coordinate of the spot on the board you want to get returned.
     * @return the spot on the board that is located at the specified x and y coordinates.
     */
    public ChessSpot getChessSpot(int x, int y) {
        int index = y * 8 + x;
        if (isTurned) {
            return getBoard()[TOTAL_AMOUNT_OF_SPOTS - index];
        } else return getBoard()[index];
    }

    public void initializeBoard(){
        for(ChessSpot chessSpot : getBoard()){
            if(graphicsConnector.hasPiece(chessSpot.getX(), chessSpot.getY())){
                Piece piece = new Piece(chessSpot.getX(), chessSpot.getY());
                chessSpot.setPiece(piece);
            }
        }
    }

    public void updateGraphic(){
        for(ChessSpot chessSpot : getBoard()){
            chessSpot.updateGraphic();
        }
    }

    public ChessSpot getChessSpot(int oneDimension) {
        if (isTurned) {
            return getBoard()[TOTAL_AMOUNT_OF_SPOTS - oneDimension];
        } else return getBoard()[oneDimension];
    }

    public double getWidthFromChessGUI() {
        return chessGUI.getWidth();
    }

    public double getHeightFromChessGUI() {
        return chessGUI.getHeight();
    }

    public void turnBoard() {
        if (ChessGUI.TURN) {

            getChildren().clear();
            ChessSpot[] temp = new ChessSpot[64];
            for (int i = 0; i < board.length; i++) {
                temp[TOTAL_AMOUNT_OF_SPOTS - i] = board[i];
                int x = (TOTAL_AMOUNT_OF_SPOTS - i) % 8;
                int y = (TOTAL_AMOUNT_OF_SPOTS - i - x) / 8;
                add(board[i], x, y);
            }
            isTurned = !isTurned;
            board = temp;
        }
    }
}
