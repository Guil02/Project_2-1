package gui.GameScreenObjects;

import javafx.scene.input.DataFormat;

import java.io.Serializable;

/**
 * this class exists to be used as a movable item in chessSpot
 */
public class Piece implements Serializable {
    private static final DataFormat dataFormat = new DataFormat("Piece");
    private int x;
    private int y;


    /**
     * @param x x coordinate
     * @param y y coordinate
     */
    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return returns the data format
     */
    public static DataFormat getDataFormat() {
        return dataFormat;
    }

    /**
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return returns the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * @return returns the x coordinate
     */
    public int getX() {
        return x;
    }
}
