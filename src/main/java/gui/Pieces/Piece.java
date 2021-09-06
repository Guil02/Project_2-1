package gui.Pieces;

import gui.ChessGUI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Piece {
    private int x;
    private int y;
    private Image image;
    public Piece(String color, String name) {
        x=-1;
        y=-1;
        String url = "gui/"+color + "_" + name + ".png";
        image = new Image(url);
    }

    public ImageView getImage(){
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(ChessGUI.width/8);
        imageView.setFitHeight(ChessGUI.height/8);
        return imageView;
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
}
