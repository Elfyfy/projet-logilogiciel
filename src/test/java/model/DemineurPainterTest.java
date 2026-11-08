
package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;

import engine.Cmd;

public class DemineurPainterTest {

    // Vérifie la largeur de l'affichage
    @Test
    void testGetWidth() {
        DemineurGame jeu = new DemineurGame(5, 8, 3);
        DemineurPainter painter = new DemineurPainter(jeu);

        assertEquals(8 * 30, painter.getWidth());
    }

    // Vérifie la hauteur de l'affichage
    @Test
    void testGetHeight() {
        DemineurGame jeu = new DemineurGame(5, 8, 3);
        DemineurPainter painter = new DemineurPainter(jeu);

        assertEquals(185, painter.getHeight());
    }

    // Vérifie que le dessin fonctionne sur une image
    @Test
    void testDraw() {
        DemineurGame jeu = new DemineurGame(5, 5, 3);
        DemineurPainter painter = new DemineurPainter(jeu);

        BufferedImage image = new BufferedImage(
            painter.getWidth(),
            painter.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );

        assertDoesNotThrow(() -> painter.draw(image));
    }

    // Vérifie que le dessin fonctionne avec une case découverte
    @Test
    void testDrawCaseDecouverte() {
        DemineurGame jeu = new DemineurGame(3, 3, 0);
        DemineurPainter painter = new DemineurPainter(jeu);

        jeu.evolve(Cmd.REVEAL);

        BufferedImage image = new BufferedImage(
            painter.getWidth(),
            painter.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );

        assertDoesNotThrow(() -> painter.draw(image));
    }

    // Vérifie que le dessin fonctionne avec un drapeau
    @Test
    void testDrawDrapeau() {
        DemineurGame jeu = new DemineurGame(3, 3, 0);
        DemineurPainter painter = new DemineurPainter(jeu);

        jeu.evolve(Cmd.FLAG);

        BufferedImage image = new BufferedImage(
            painter.getWidth(),
            painter.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );

        assertDoesNotThrow(() -> painter.draw(image));
    }

    // Vérifie que le dessin fonctionne avec une mine découverte
    @Test
    void testDrawMineDecouverte() {
        DemineurGame jeu = new DemineurGame(1, 1, 1);
        DemineurPainter painter = new DemineurPainter(jeu);

        jeu.evolve(Cmd.REVEAL);

        BufferedImage image = new BufferedImage(
            painter.getWidth(),
            painter.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );

        assertDoesNotThrow(() -> painter.draw(image));
    }
}
