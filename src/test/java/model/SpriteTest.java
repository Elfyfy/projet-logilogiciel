
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SpriteTest {

    @Test
    void testConstructeur() {
        Sprite sprite = new Sprite("/images/test.png", 32);

        assertNotNull(sprite.getImage());
        assertEquals(32, sprite.getImage().getWidth());
        assertEquals(32, sprite.getImage().getHeight());
    }

    @Test
    void testGetImage() {
        Sprite sprite = new Sprite("/images/test.png", 64);

        assertNotNull(sprite.getImage());
    }

    @Test
    void testLoadImage() {
        Sprite sprite = new Sprite("/images/test.png", 32);

        sprite.loadImage("/images/test.png");

        assertNotNull(sprite.getImage());
    }

    @Test
    void testRedimension() {
        Sprite sprite = new Sprite("/images/test.png", 32);

        sprite.redimension(64);

        assertNotNull(sprite.getImage());
        assertEquals(64, sprite.getImage().getWidth());
        assertEquals(64, sprite.getImage().getHeight());
    }

    @Test
    void testRedimensionPlusieursFois() {
        Sprite sprite = new Sprite("/images/test.png", 32);

        sprite.redimension(64);
        assertEquals(64, sprite.getImage().getWidth());

        sprite.redimension(128);
        assertEquals(128, sprite.getImage().getWidth());

        sprite.redimension(16);
        assertEquals(16, sprite.getImage().getWidth());
    }

    @Test
    void testDimensionsImageCarree() {
        Sprite sprite = new Sprite("/images/test.png", 50);

        assertEquals(
            sprite.getImage().getWidth(),
            sprite.getImage().getHeight()
        );
    }
}
