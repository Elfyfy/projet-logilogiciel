
package model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

public class ConfettisTest {

    @Test
    void testConstructeurParDefaut() {
        Confettis confettis = new Confettis();

        assertNotNull(confettis);
    }

    @Test
    void testConstructeurAvecNombre() {
        Confettis confettis = new Confettis(100);

        assertNotNull(confettis);
    }

    @Test
    void testConstructeurAvecZero() {
        Confettis confettis = new Confettis(0);

        assertNotNull(confettis);
    }

    @Test
    void testDessiner() {
        Confettis confettis = new Confettis(70);

        BufferedImage image = new BufferedImage(
            800, 600, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() ->
            confettis.dessiner(g, 1000, 800, 600)
        );

        g.dispose();
    }

    @Test
    void testDessinerAvecTempsZero() {
        Confettis confettis = new Confettis(70);

        BufferedImage image = new BufferedImage(
            800, 600, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() ->
            confettis.dessiner(g, 0, 800, 600)
        );

        g.dispose();
    }

    @Test
    void testDessinerAvecAucunConfetti() {
        Confettis confettis = new Confettis(0);

        BufferedImage image = new BufferedImage(
            800, 600, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() ->
            confettis.dessiner(g, 5000, 800, 600)
        );

        g.dispose();
    }

    @Test
    void testDessinerAvecDimensionsNulles() {
        Confettis confettis = new Confettis(70);

        BufferedImage image = new BufferedImage(
            800, 600, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() ->
            confettis.dessiner(g, 1000, 0, 0)
        );

        g.dispose();
    }

    @Test
    void testDeterminisme() {
        Confettis confettis1 = new Confettis(70);
        Confettis confettis2 = new Confettis(70);

        BufferedImage image1 = new BufferedImage(
            800, 600, BufferedImage.TYPE_INT_ARGB
        );

        BufferedImage image2 = new BufferedImage(
            800, 600, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g1 = image1.createGraphics();
        Graphics2D g2 = image2.createGraphics();

        confettis1.dessiner(g1, 3000, 800, 600);
        confettis2.dessiner(g2, 3000, 800, 600);

        g1.dispose();
        g2.dispose();

        for (int x = 0; x < 800; x++) {
            for (int y = 0; y < 600; y++) {
                assertEquals(
                    image1.getRGB(x, y),
                    image2.getRGB(x, y)
                );
            }
        }
    }
}
