package gui;

import controller.GraphicsConnector;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.*;

import java.util.ArrayList;

public class ChessSpot extends Label {
    private Piece piece;
    private int x;
    private int y;
    private ChessBoard board;
    private static final String color1 = "-fx-background-color: #4a4b3e;";
    private static final String color2 = "-fx-background-color: #2f7244;";
    private static final String attackColor = "-fx-background-color: #bd2b2b;";
    private static final String moveColor = "-fx-background-color: #23a94d;";




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

    public void onDragOver(DragEvent e){
        if (e.getDragboard().hasContent(Piece.getDataFormat())) {
            e.acceptTransferModes(TransferMode.MOVE);
        }
        e.consume();
    }

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
