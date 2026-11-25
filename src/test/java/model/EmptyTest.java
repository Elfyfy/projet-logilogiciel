
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EmptyTest {

    @Test
    void testConstructeur() {
        Sprite[] sprites = new Sprite[4];

        Empty empty = new Empty(2, 3, sprites);

        assertEquals(0, empty.getNbMine());
        assertSame(sprites, empty.getSprites());
    }

    @Test
    void testGetNbMine() {
        Empty empty = new Empty(0, 0, new Sprite[0]);

        assertEquals(0, empty.getNbMine());
    }

    @Test
    void testSetNbMine() {
        Empty empty = new Empty(0, 0, new Sprite[0]);

        empty.setNbMine(3);

        assertEquals(3, empty.getNbMine());
    }

    @Test
    void testSetNbMinePlusieursValeurs() {
        Empty empty = new Empty(0, 0, new Sprite[0]);

        empty.setNbMine(5);
        assertEquals(5, empty.getNbMine());

        empty.setNbMine(8);
        assertEquals(8, empty.getNbMine());

        empty.setNbMine(0);
        assertEquals(0, empty.getNbMine());
    }

    @Test
    void testSetNbMineValeurNegative() {
        Empty empty = new Empty(0, 0, new Sprite[0]);

        empty.setNbMine(-1);

        assertEquals(-1, empty.getNbMine());
    }
}
