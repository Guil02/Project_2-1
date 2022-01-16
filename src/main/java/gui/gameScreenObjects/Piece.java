package gui.gameScreenObjects;

import javafx.scene.input.DataFormat;

import java.io.Serializable;

/**
 * This class is used as a movable item in ChessSpot.
 */
public class Piece implements Serializable {

    // Variables
    private static final DataFormat dataFormat = new DataFormat("Piece");
    private int x;
    private int y;

    /**
     * Constructor
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return data format
     */
    public static DataFormat getDataFormat() {
        return dataFormat;
    }

    /**
     * Sets both x- and y-coordinates.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * @return x-coordinate
     */
    public int getX() {
        return x;
    }
}
