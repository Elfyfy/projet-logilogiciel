
package model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

public class FlammesTest {

    @Test
    void testAvancerAvantInitialisation() {
        Flammes flammes = new Flammes();

        // Aucun feu n'est encore initialisé
        assertDoesNotThrow(() -> flammes.avancer());
    }

    @Test
    void testDessiner() {
        Flammes flammes = new Flammes();

        BufferedImage image = new BufferedImage(
            800, 600, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() ->
            flammes.dessiner(g, 800, 600, 200, 400)
        );

        g.dispose();
    }

    @Test
    void testAvancerApresInitialisation() {
        Flammes flammes = new Flammes();

        BufferedImage image = new BufferedImage(
            800, 600, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        flammes.dessiner(g, 800, 600, 200, 400);

        assertDoesNotThrow(() -> flammes.avancer());

        g.dispose();
    }

    @Test
    void testPlusieursAvancements() {
        Flammes flammes = new Flammes();

        BufferedImage image = new BufferedImage(
            800, 600, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        flammes.dessiner(g, 800, 600, 200, 400);

        for (int i = 0; i < 100; i++) {
            assertDoesNotThrow(() -> flammes.avancer());
        }

        g.dispose();
    }

    @Test
    void testDessinerAvecDimensionsDifferentes() {
        Flammes flammes = new Flammes();

        BufferedImage image = new BufferedImage(
            800, 600, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        // Première initialisation
        flammes.dessiner(g, 800, 600, 200, 400);

        // Changement de dimensions
        assertDoesNotThrow(() ->
            flammes.dessiner(g, 400, 300, 100, 200)
        );

        g.dispose();
    }

    @Test
    void testDessinerSansZoneDeJeu() {
        Flammes flammes = new Flammes();

        BufferedImage image = new BufferedImage(
            800, 600, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        flammes.dessiner(g, 800, 600, 0, 0);

        assertDoesNotThrow(() -> flammes.avancer());

        g.dispose();
    }

    @Test
    void testPropagationDesFlammes() {
        Flammes flammes = new Flammes();

        BufferedImage image = new BufferedImage(
            80, 80, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        // Toute la largeur est une source de flammes
        flammes.dessiner(g, 80, 80, 0, 0);

        flammes.avancer();

        // On dessine les flammes après leur avancement
        assertDoesNotThrow(() ->
            flammes.dessiner(g, 80, 80, 0, 0)
        );

        // Vérifie qu'une flamme est visible en bas à gauche
        assertNotEquals(0, image.getRGB(0, 72));

        g.dispose();
    }

    @Test
    void testAvancerEtDessinerPlusieursFois() {
        Flammes flammes = new Flammes();

        BufferedImage image = new BufferedImage(
            400, 300, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        for (int i = 0; i < 50; i++) {
            flammes.dessiner(g, 400, 300, 100, 200);
            flammes.avancer();
        }

        assertDoesNotThrow(() ->
            flammes.dessiner(g, 400, 300, 100, 200)
        );

        g.dispose();
    }
}
