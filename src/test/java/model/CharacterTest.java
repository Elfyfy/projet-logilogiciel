
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import engine.Cmd;

public class CharacterTest {

    @Test
    void testConstructeur() {
        Character character = new Character(2, 5, null);

        assertEquals(2, character.getRow());
        assertEquals(5, character.getColumn());
        assertSame(DemineurPainter.spriteGilet, character.getSprites());
        assertEquals(Cmd.IDLE, character.getDir());
    }

    @Test
    void testGetRow() {
        Character character = new Character(3, 4, null);

        assertEquals(3, character.getRow());
    }

    @Test
    void testSetRow() {
        Character character = new Character(0, 0, null);

        character.setRow(7);

        assertEquals(7, character.getRow());
    }

    @Test
    void testGetColumn() {
        Character character = new Character(3, 6, null);

        assertEquals(6, character.getColumn());
    }

    @Test
    void testSetColumn() {
        Character character = new Character(0, 0, null);

        character.setColumn(8);

        assertEquals(8, character.getColumn());
    }

    @Test
    void testGetDir() {
        Character character = new Character(0, 0, null);

        assertEquals(Cmd.IDLE, character.getDir());
    }

    @Test
    void testSetDir() {
        Character character = new Character(0, 0, null);

        character.setDir(Cmd.IDLE);
        assertEquals(Cmd.IDLE, character.getDir());
    }

    @Test
    void testGetSprites() {
        Character character = new Character(0, 0, null);

        assertSame(DemineurPainter.spriteGilet, character.getSprites());
    }

    @Test
    void testSetSprites() {
        Character character = new Character(0, 0, null);

        character.setSprites(DemineurPainter.spriteFlag);

        assertSame(DemineurPainter.spriteFlag, character.getSprites());
    }
}
