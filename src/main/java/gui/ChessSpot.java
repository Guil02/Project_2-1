package gui;

import gui.Pieces.Piece;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.*;

public class ChessSpot extends Label {
    private Piece piece;
    private int x;
    private int y;
    private ChessBoard board;
    private static final String color1 = "-fx-background-color: #4a4b3e;";
    private static final String color2 = "-fx-background-color: #2f7244;";




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

    }

    public void setBackgroundColor(){
        if((x+y)%2==0){
            setStyle(color1);
        }
        else{
            setStyle(color2);
        }
    }

    public void setPiece(Piece piece){
        this.piece=piece;
        if(piece==null){
            setGraphic(null);
        }
        else{
            setGraphic(piece.getImageView());
        }
    }

    public Piece getPiece() {
        return piece;
    }

    public void onDragDetected(MouseEvent e){
        if(piece!= null && piece.canMove()){
            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            dragboard.setDragView(piece.getImage());

            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.put(Piece.dataFormatPiece,piece);
            dragboard.setContent(clipboardContent);
            e.consume();
        }
    }

    public void onDragOver(DragEvent e){
        if (e.getDragboard().hasContent(Piece.dataFormatPiece)) {
            e.acceptTransferModes(TransferMode.MOVE);
        }
        e.consume();
    }

    public void onDragDropped(DragEvent e){
        Dragboard dragboard = e.getDragboard();
        if(dragboard.hasContent(Piece.dataFormatPiece)){
            Piece piece = (Piece) dragboard.getContent(Piece.dataFormatPiece);
            ChessSpot originalSpot = board.getChessSpot(piece.getX(),piece.getY());
            Piece actualPiece = originalSpot.getPiece();
            originalSpot.setPiece(null);
            actualPiece.setXY(x,y);
            setPiece(actualPiece);
        }
    }
}
