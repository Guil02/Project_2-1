package gui;

import controller.GraphicsConnector;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.*;

import java.util.ArrayList;

/**
 * the class that represents every single square on the chess board.
 */
public class ChessSpot extends Label {
    private Piece piece;
    private int x;
    private int y;
    private ChessBoard board;
    private static final String color1 = "-fx-background-color: #4a4b3e;";
    private static final String color2 = "-fx-background-color: #2f7244;";
    private static final String attackColor = "-fx-background-color: #bd2b2b;";
    private static final String moveColor = "-fx-background-color: #23a94d;";


    /**
     * @param board the board that represents the chessboard on which this spot is located
     * @param x the x coordinate at which it is located on the chess board.
     * @param y the y coordinate at which it is located on the chess board.
     */
    public ChessSpot(ChessBoard board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        setMinSize(ChessGUI.WIDTH /8.0,ChessGUI.HEIGHT /8.0);
        setMaxSize(ChessGUI.WIDTH /8.0,ChessGUI.HEIGHT /8.0);
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

    public void setPiece(Piece piece){
        this.piece = piece;
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
        if(piece!=null){
            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            dragboard.setDragView(piece.getImage());

            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.put(Piece.getDataFormat(),piece);
            dragboard.setContent(clipboardContent);
            ArrayList<ChessSpot> moveAbleFields = GraphicsConnector.getMoveAbleSpots(x,y); //TODO NEEDS TO BE ADAPTED TO A BACK END METHOD THAT RETURNS ALL AVAILABLE POSITIONS TO WHICH THE PIECE CAN MOVE.
            for(ChessSpot chessSpot : moveAbleFields){
                if(chessSpot.getPiece()!=null){
                    chessSpot.setAttackColor();
                }
                else{
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
    public void onDragDropped(DragEvent e){ //TODO update to the new UML version
        Dragboard dragboard = e.getDragboard();
        if(dragboard.hasContent(Piece.getDataFormat())){
            Piece piece = (Piece) dragboard.getContent(Piece.getDataFormat());
            ChessSpot originalSpot = board.getChessSpot(piece.getX(),piece.getY());
            Piece actualPiece = originalSpot.getPiece();
            originalSpot.setPiece(null);
            actualPiece.setXY(x,y);
            setPiece(actualPiece);
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
            Piece piece = (Piece) dragboard.getContent(Piece.getDataFormat());
            ArrayList<ChessSpot> moveAbleFields = GraphicsConnector.getMoveAbleSpots(x,y); //TODO NEEDS TO BE ADAPTED TO A BACK END METHOD THAT RETURNS ALL AVAILABLE POSITIONS TO WHICH THE PIECE CAN MOVE.
            for(ChessSpot chessSpot : moveAbleFields){
                chessSpot.setBackgroundColor();
            }
            e.consume();
        }
    }
}
