
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class WallTest {

    @Test
    void testConstructeur() {
        Sprite[] sprites = new Sprite[2];

        Wall wall = new Wall(3, 5, sprites);

        assertEquals(3, wall.getRow());
        assertEquals(5, wall.getColumn());
        assertFalse(wall.getAccesible());
        assertTrue(wall.getUnknown());
        assertFalse(wall.getPinned());
        assertSame(sprites, wall.getSprites());
    }

    @Test
    void testConstructeurAvecAutresCoordonnees() {
        Wall wall = new Wall(0, 0, null);

        assertEquals(0, wall.getRow());
        assertEquals(0, wall.getColumn());
        assertFalse(wall.getAccesible());
        assertNull(wall.getSprites());
    }

    @Test
    void testSpritesVides() {
        Sprite[] sprites = new Sprite[0];

        Wall wall = new Wall(2, 4, sprites);

        assertSame(sprites, wall.getSprites());
        assertEquals(0, wall.getSprites().length);
    }

    @Test
    void testModifierCoordonnees() {
        Wall wall = new Wall(1, 2, null);

        wall.setRow(4);
        wall.setColumn(6);

        assertEquals(4, wall.getRow());
        assertEquals(6, wall.getColumn());
    }

    @Test
    void testModifierSprites() {
        Wall wall = new Wall(1, 2, null);
        Sprite[] nouveauxSprites = new Sprite[3];

        wall.setSprites(nouveauxSprites);

        assertSame(nouveauxSprites, wall.getSprites());
    }

    @Test
    void testModifierEtat() {
        Wall wall = new Wall(1, 2, null);

        wall.setUnknown(false);
        wall.setPinned(true);

        assertFalse(wall.getUnknown());
        assertTrue(wall.getPinned());

        // Un mur reste inaccessible.
        assertFalse(wall.getAccesible());
    }
}
