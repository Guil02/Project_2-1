package gui;

import controller.GraphicsConnector;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.util.ArrayList;

/**
 * the class that represents every single square on the chess board.
 */
public class ChessSpot extends Label {
    private static final double DIVIDER = 8.0;
    public static final double PERCENTAGE_FILLED = 0.7;
    private double width;
    private double height;
    private Piece piece;
    private int x;
    private int y;
    private ChessBoard board;
    private GraphicsConnector graphicsConnector;
    private static final String color1 = "-fx-background-color: #4a4b3e;";
    private static final String color2 = "-fx-background-color: #2f7244;";
    private static final String attackColor = "-fx-background-color: #bd2b2b;";
    private static final String moveColor = "-fx-background-color: #23a94d;";


    /**
     * @param board the board that represents the chessboard on which this spot is located
     * @param x the x coordinate at which it is located on the chess board.
     * @param y the y coordinate at which it is located on the chess board.
     */
    public ChessSpot(GraphicsConnector graphicsConnector, ChessBoard board, int x, int y) {
        this.graphicsConnector=graphicsConnector;
        this.board = board;
        width = board.getWidthFromChessGUI()* PERCENTAGE_FILLED;
        height = board.getHeightFromChessGUI()* PERCENTAGE_FILLED;
        this.x = x;
        this.y = y;
        setMinSize(width / DIVIDER, height / DIVIDER);
        setMaxSize(width / DIVIDER, height / DIVIDER);
        setAlignment(Pos.CENTER);
        setBackgroundColor();
        setOnDragDetected(this::onDragDetected);
        setOnDragOver(this::onDragOver);
        setOnDragDropped(this::onDragDropped);
        setOnDragDone(this::onDragDone);

    }

    /**
     * method that sets the colors of the chessboard, the color can be changed
     * by changing the 'color1' and 'color2' variables this will then change
     * the color of the squares of the chessboard
     */
    public void setBackgroundColor(){
        if((x+y)%2==0){
            setStyle(color1);
        }
        else{
            setStyle(color2);
        }
    }

    public void setAttackColor(){
        setStyle(attackColor);
    }

    public void setMoveColor(){
        setStyle(moveColor);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece == null) {
            setGraphic(null);
        } else {
            Image image = new Image(graphicsConnector.getImage(x, y), width /DIVIDER, height /DIVIDER, false, false);
            setGraphic(new ImageView(image));
        }
    }

    /**
     * updates the size of everything when the display size has changed
     */
    public void updateGraphic(){
        width = board.getWidthFromChessGUI()* PERCENTAGE_FILLED;
        height = board.getHeightFromChessGUI()* PERCENTAGE_FILLED;
        setMinSize(width / DIVIDER, height / DIVIDER);
        setMaxSize(width / DIVIDER, height / DIVIDER);
        if (piece == null) {
            setGraphic(null);
        } else {
            Image image = new Image(graphicsConnector.getImage(x, y), width /DIVIDER, height /DIVIDER, false, false);
            setGraphic(new ImageView(image));
        }
    }

    public Piece getPiece() {
        return piece;
    }

    /**
     * the method that dictates what happens when a click and drag behaviour is detected
     * will color all the spots the piece can move to either an attacking color or a simple
     * moveable color.
     *
     * @param e a mouse event
     */
    public void onDragDetected(MouseEvent e){
        if(piece!=null) {
            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            Image image = new Image(graphicsConnector.getImage(piece.getX(), piece.getY()), 100, 100, false, false);
            dragboard.setDragView(image);

            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.put(Piece.getDataFormat(), piece);
            dragboard.setContent(clipboardContent);
            ArrayList<ChessSpot> moveAbleFields = getMoveAbleSpotsArrayList();
            for (ChessSpot chessSpot : moveAbleFields) {
                if (chessSpot.getPiece() != null) {
                    chessSpot.setAttackColor();
                } else {
                    chessSpot.setMoveColor();
                }
            }
            e.consume();
        }
    }

    /**
     * a method that dictates what happens when a piece is dragged over a chessSpot,
     * specifically it says that it is possible to drag a piece over it.
     *
     * @param e a mouse event
     */
    public void onDragOver(DragEvent e){
        if (e.getDragboard().hasContent(Piece.getDataFormat())) {
            e.acceptTransferModes(TransferMode.MOVE);
        }
        e.consume();
    }

    /**
     * a method that dictates what happens when a drag is released above a chessSpot,
     * and then decides how everything should be handled.
     *
     * @param e a mouse event
     */
    public void onDragDropped(DragEvent e) {
        Dragboard dragboard = e.getDragboard();

        // check if the drag board was actually dragging a piece
        if (dragboard.hasContent(Piece.getDataFormat())) {

            Piece piece = (Piece) dragboard.getContent(Piece.getDataFormat());
            ChessSpot originalSpot = board.getChessSpot(piece.getX(), piece.getY());
            Piece actualPiece = originalSpot.getPiece();

            if (graphicsConnector.canMove(piece.getX(), piece.getY(), x, y)) {

                originalSpot.setPiece(null);
                actualPiece.setXY(x, y);
                setPiece(actualPiece);
                graphicsConnector.doMove(piece.getX(), piece.getY(), x, y);

            }

            e.consume();
        }
    }

    /**
     * a method what dictates what happens when there is not dragging going on anymore.
     * it will recolor all the spots to their original color.
     *
     * @param e a mouse event
     */
    public void onDragDone(DragEvent e){
        Dragboard dragboard = e.getDragboard();
        if(dragboard.hasContent(Piece.getDataFormat())){
            for (ChessSpot chessSpot : board.getBoard()) {
                chessSpot.setBackgroundColor();
            }
            e.consume();
        }
    }


    /**
     * @return returns an arraylist of all the possible moves for the piece that is located
     * on the spot represented by this object.
     */
    public ArrayList<ChessSpot> getMoveAbleSpotsArrayList(){
        boolean[] availableMovements = graphicsConnector.getMoveAbleSpots(x,y);
        ArrayList<ChessSpot> spots = new ArrayList<>();
        for(int i = 0; i<availableMovements.length; i++){
            if(availableMovements[i]){
                spots.add(board.getChessSpot(i));
            }
        }
        return spots;
    }
}
