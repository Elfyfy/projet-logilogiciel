
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BombTest {

    @Test
    void testConstructor() {
        Sprite[] sprites = new Sprite[4];
        int[][] shape = {
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
        };

        Bomb bomb = new Bomb(2, 3, sprites, 5, shape);

        assertEquals(5, bomb.getFuse());
        assertSame(shape, bomb.getShape());
        assertFalse(bomb.getActive());
        assertFalse(bomb.getBlown());
    }

    @Test
    void testGetFuse() {
        Bomb bomb = new Bomb(0, 0, new Sprite[0], 10, new int[0][0]);

        assertEquals(10, bomb.getFuse());
    }

    @Test
    void testSetFuse() {
        Bomb bomb = new Bomb(0, 0, new Sprite[0], 5, new int[0][0]);

        bomb.setFuse(8);

        assertEquals(8, bomb.getFuse());
    }

    @Test
    void testGetShape() {
        int[][] shape = {
            {1, 0},
            {0, 1}
        };

        Bomb bomb = new Bomb(0, 0, new Sprite[0], 5, shape);

        assertSame(shape, bomb.getShape());
    }

    @Test
    void testSetShape() {
        Bomb bomb = new Bomb(0, 0, new Sprite[0], 5, new int[0][0]);

        int[][] newShape = {
            {1, 1},
            {1, 0}
        };

        bomb.setShape(newShape);

        assertSame(newShape, bomb.getShape());
    }

    @Test
    void testGetActive() {
        Bomb bomb = new Bomb(0, 0, new Sprite[0], 5, new int[0][0]);

        assertFalse(bomb.getActive());
    }

    @Test
    void testSetActive() {
        Bomb bomb = new Bomb(0, 0, new Sprite[0], 5, new int[0][0]);

        bomb.setActive(true);

        assertTrue(bomb.getActive());

        bomb.setActive(false);

        assertFalse(bomb.getActive());
    }

    @Test
    void testGetBlown() {
        Bomb bomb = new Bomb(0, 0, new Sprite[0], 5, new int[0][0]);

        assertFalse(bomb.getBlown());
    }

    @Test
    void testSetBlown() {
        Bomb bomb = new Bomb(0, 0, new Sprite[0], 5, new int[0][0]);

        bomb.setBlown(true);

        assertTrue(bomb.getBlown());

        bomb.setBlown(false);

        assertFalse(bomb.getBlown());
    }
}
