package gui;

import gui.Pieces.Piece;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

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
        setMinSize(100,100);
        setMaxSize(100,100);
        setAlignment(Pos.CENTER);
        setBackgroundColor();

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
            setGraphic(piece.getImage());
        }
    }

    public Piece getPiece() {
        return piece;
    }
}
