package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;

import java.io.Serializable;

public class Piece implements Serializable {
    private static final DataFormat dataFormat = new DataFormat("Piece");
    private int x;
    private int y;


    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
