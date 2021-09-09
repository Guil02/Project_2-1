package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;

import java.io.Serializable;

public class Piece implements Serializable {
    private static final DataFormat dataFormat = new DataFormat("Piece");
    private Image image;
    private int x;
    private int y;


    public Piece() {
    }

    public void setXY(int x, int y) {
        this.x = this.x;
        this.y = this.y;
    }

    public static DataFormat getDataFormat() {
        return dataFormat;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return new ImageView(image);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
