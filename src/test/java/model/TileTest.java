
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TileTest {

    @Test
    void testConstructeur() {
        Tile tile = new Tile(2, 3, true);

        assertEquals(2, tile.getRow());
        assertEquals(3, tile.getColumn());
        assertTrue(tile.getAccesible());
        assertTrue(tile.getUnknown());
        assertFalse(tile.getPinned());
        assertNull(tile.getSprites());
    }

    @Test
    void testConstructeurInaccessible() {
        Tile tile = new Tile(1, 4, false);

        assertFalse(tile.getAccesible());
    }

    @Test
    void testGetRow() {
        Tile tile = new Tile(5, 0, true);

        assertEquals(5, tile.getRow());
    }

    @Test
    void testSetRow() {
        Tile tile = new Tile(0, 0, true);

        tile.setRow(7);

        assertEquals(7, tile.getRow());
    }

    @Test
    void testGetColumn() {
        Tile tile = new Tile(0, 6, true);

        assertEquals(6, tile.getColumn());
    }

    @Test
    void testSetColumn() {
        Tile tile = new Tile(0, 0, true);

        tile.setColumn(8);

        assertEquals(8, tile.getColumn());
    }

    @Test
    void testGetAccesible() {
        Tile tile = new Tile(0, 0, true);

        assertTrue(tile.getAccesible());

        Tile tile2 = new Tile(0, 0, false);

        assertFalse(tile2.getAccesible());
    }

    @Test
    void testGetUnknown() {
        Tile tile = new Tile(0, 0, true);

        assertTrue(tile.getUnknown());
    }

    @Test
    void testSetUnknown() {
        Tile tile = new Tile(0, 0, true);

        tile.setUnknown(false);

        assertFalse(tile.getUnknown());

        tile.setUnknown(true);

        assertTrue(tile.getUnknown());
    }

    @Test
    void testGetPinned() {
        Tile tile = new Tile(0, 0, true);

        assertFalse(tile.getPinned());
    }

    @Test
    void testSetPinned() {
        Tile tile = new Tile(0, 0, true);

        tile.setPinned(true);

        assertTrue(tile.getPinned());

        tile.setPinned(false);

        assertFalse(tile.getPinned());
    }

    @Test
    void testGetSprites() {
        Tile tile = new Tile(0, 0, true);

        assertNull(tile.getSprites());
    }

    @Test
    void testSetSprites() {
        Tile tile = new Tile(0, 0, true);
        Sprite[] sprites = new Sprite[4];

        tile.setSprites(sprites);

        assertSame(sprites, tile.getSprites());
    }

    @Test
    void testSetSpritesPlusieursFois() {
        Tile tile = new Tile(0, 0, true);

        Sprite[] sprites1 = new Sprite[2];
        Sprite[] sprites2 = new Sprite[4];

        tile.setSprites(sprites1);
        assertSame(sprites1, tile.getSprites());

        tile.setSprites(sprites2);
        assertSame(sprites2, tile.getSprites());
    }

    @Test
    void testModifierPlusieursAttributs() {
        Tile tile = new Tile(1, 2, true);

        tile.setRow(5);
        tile.setColumn(6);
        tile.setUnknown(false);
        tile.setPinned(true);

        assertEquals(5, tile.getRow());
        assertEquals(6, tile.getColumn());
        assertFalse(tile.getUnknown());
        assertTrue(tile.getPinned());

        // L'accessibilité ne doit pas changer
        assertTrue(tile.getAccesible());
    }
}
