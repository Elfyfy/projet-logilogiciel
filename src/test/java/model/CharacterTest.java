
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import engine.Cmd;

public class CharacterTest {

    @Test
    void testConstructeur() {
        Sprite[] sprites = new Sprite[4];

        Character character = new Character(2, 5, sprites);

        assertEquals(2, character.getRow());
        assertEquals(5, character.getColumn());
        assertSame(sprites, character.getSprites());
        assertEquals(Cmd.IDLE, character.getDir());
    }

    @Test
    void testGetRow() {
        Character character = new Character(3, 4, new Sprite[0]);

        assertEquals(3, character.getRow());
    }

    @Test
    void testSetRow() {
        Character character = new Character(0, 0, new Sprite[0]);

        character.setRow(7);

        assertEquals(7, character.getRow());
    }

    @Test
    void testGetColumn() {
        Character character = new Character(3, 6, new Sprite[0]);

        assertEquals(6, character.getColumn());
    }

    @Test
    void testSetColumn() {
        Character character = new Character(0, 0, new Sprite[0]);

        character.setColumn(8);

        assertEquals(8, character.getColumn());
    }

    @Test
    void testGetDir() {
        Character character = new Character(0, 0, new Sprite[0]);

        assertEquals(Cmd.IDLE, character.getDir());
    }

    @Test
    void testSetDir() {
        Character character = new Character(0, 0, new Sprite[0]);

        character.setDir(Cmd.IDLE);
        assertEquals(Cmd.IDLE, character.getDir());
    }

    @Test
    void testGetSprites() {
        Sprite[] sprites = new Sprite[4];

        Character character = new Character(0, 0, sprites);

        assertSame(sprites, character.getSprites());
    }

    @Test
    void testSetSprites() {
        Character character = new Character(0, 0, new Sprite[0]);
        Sprite[] newSprites = new Sprite[6];

        character.setSprites(newSprites);

        assertSame(newSprites, character.getSprites());
    }
}
