
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MineTest {

    @Test
    void testConstructeur() {
        Mine mine = new Mine(2, 3);

        assertEquals(2, mine.getRow());
        assertEquals(3, mine.getColumn());
    }

    @Test
    void testSprites() {
        Mine mine = new Mine(0, 0);

        assertNotNull(mine.getSprites());
        assertEquals(1, mine.getSprites().length);
        assertSame(DemineurPainter.spriteMine, mine.getSprites()[0]);
    }

    @Test
    void testPositionsDifferentes() {
        Mine mine = new Mine(5, 8);

        assertEquals(5, mine.getRow());
        assertEquals(8, mine.getColumn());
    }

    @Test
    void testDeuxMines() {
        Mine mine1 = new Mine(1, 2);
        Mine mine2 = new Mine(3, 4);

        assertEquals(1, mine1.getRow());
        assertEquals(2, mine1.getColumn());

        assertEquals(3, mine2.getRow());
        assertEquals(4, mine2.getColumn());

        assertNotSame(mine1, mine2);
        assertNotSame(mine1.getSprites(), mine2.getSprites());
    }
}
