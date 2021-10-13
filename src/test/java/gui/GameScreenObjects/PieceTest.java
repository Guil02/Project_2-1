package gui.GameScreenObjects;

import javafx.scene.input.DataFormat;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {
    @Test
    public void testGetDataFormat(){
        String expected = "Piece";

        DataFormat actualItem = Piece.getDataFormat();
        Set<String> set = actualItem.getIdentifiers();
        String actual = "";
        for (String s : set) {
            actual = s;
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testGetX(){
        Piece piece = new Piece(3,4);
        int expected = 3;
        int actual = piece.getX();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetY(){
        Piece piece = new Piece(3,4);
        int expected = 4;
        int actual = piece.getY();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetXY(){
        Vector<Integer> expected = new Vector<>();
        expected.add(5);
        expected.add(6);

        Piece piece = new Piece(3,4);
        piece.setXY(5,6);
        Vector<Integer> actual = new Vector<>();
        actual.add(piece.getX());
        actual.add(piece.getY());

        assertEquals(expected,actual);
    }
}