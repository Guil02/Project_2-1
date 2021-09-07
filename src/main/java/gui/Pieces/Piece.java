package gui.Pieces;

import gui.ChessGUI;
import gui.ChessSpot;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;

import java.io.Serializable;

public abstract class Piece implements Serializable {
    public static final DataFormat dataFormatPiece = new DataFormat("gui.Pieces");
    private int x;
    private int y;
    private final String name;
    private final String color;
    public Piece(String color, String name, int x, int y) {
        this.name = name;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public ImageView getImageView(){
        ImageView imageView = new ImageView(getImage());
        imageView.setFitWidth(ChessGUI.width/8.0);
        imageView.setFitHeight(ChessGUI.height/8.0);
        return imageView;
    }

    public Image getImage(){
        String url = "gui/"+color + "_" + name + ".png";
        Image image = new Image(url, ChessGUI.width/8.0,ChessGUI.height/8.0, false, false);
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean canMove(){
        //TODO implement the ability to move
        return true;
    }

    public void move(ChessSpot chessSpot){
        chessSpot.setPiece(this);

    }
}
