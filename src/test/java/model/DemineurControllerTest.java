
package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Canvas;
import java.awt.event.KeyEvent;

import engine.Cmd;

public class DemineurControllerTest {

    // Crée un événement clavier
    private KeyEvent creerEvenement(char touche) {
        return new KeyEvent(
            new Canvas(),
            KeyEvent.KEY_PRESSED,
            System.currentTimeMillis(),
            0,
            KeyEvent.VK_UNDEFINED,
            touche
        );
    }

    // Vérifie la commande initiale
    @Test
    void testCommandeInitiale() {
        DemineurController controller = new DemineurController();

        assertEquals(Cmd.IDLE, controller.getCommand());
    }

    // Vérifie les déplacements
    @Test
    void testTouchesDeplacement() {
        DemineurController controller = new DemineurController();

        controller.keyPressed(creerEvenement('z'));
        assertEquals(Cmd.UP, controller.getCommand());

        controller.keyPressed(creerEvenement('s'));
        assertEquals(Cmd.DOWN, controller.getCommand());

        controller.keyPressed(creerEvenement('q'));
        assertEquals(Cmd.LEFT, controller.getCommand());

        controller.keyPressed(creerEvenement('d'));
        assertEquals(Cmd.RIGHT, controller.getCommand());
    }

    // Vérifie la pose de drapeau
    @Test
    void testToucheDrapeau() {
        DemineurController controller = new DemineurController();

        controller.keyPressed(creerEvenement('f'));

        assertEquals(Cmd.FLAG, controller.getCommand());
    }

    // Vérifie la découverte d'une case
    @Test
    void testToucheReveler() {
        DemineurController controller = new DemineurController();

        controller.keyPressed(creerEvenement(' '));

        assertEquals(Cmd.REVEAL, controller.getCommand());
    }

    // Vérifie le relâchement d'une touche
    @Test
    void testKeyReleased() {
        DemineurController controller = new DemineurController();

        controller.keyPressed(creerEvenement('z'));
        controller.keyReleased(creerEvenement('z'));

        assertEquals(Cmd.IDLE, controller.getCommand());
    }

    // Vérifie qu'une touche inconnue ne change pas la commande
    @Test
    void testToucheInconnue() {
        DemineurController controller = new DemineurController();

        controller.keyPressed(creerEvenement('x'));

        assertEquals(Cmd.IDLE, controller.getCommand());
    }

    // Vérifie que keyTyped ne modifie pas la commande
    @Test
    void testKeyTyped() {
        DemineurController controller = new DemineurController();

        controller.keyPressed(creerEvenement('z'));
        controller.keyTyped(creerEvenement('z'));

        assertEquals(Cmd.UP, controller.getCommand());
    }
}
