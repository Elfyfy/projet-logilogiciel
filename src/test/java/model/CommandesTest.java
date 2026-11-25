
package model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Canvas;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import engine.Cmd;

public class CommandesTest {

    // Réinitialise les touches avant chaque test
    @BeforeEach
    void setUp() {
        Commandes.reinitialiser();
    }

    @Test
    void testTouchesParDefaut() {
        assertEquals('z', Commandes.getTouche(Cmd.UP));
        assertEquals('s', Commandes.getTouche(Cmd.DOWN));
        assertEquals('q', Commandes.getTouche(Cmd.LEFT));
        assertEquals('d', Commandes.getTouche(Cmd.RIGHT));
        assertEquals('f', Commandes.getTouche(Cmd.FLAG));
        assertEquals(' ', Commandes.getTouche(Cmd.REVEAL));
    }

    @Test
    void testIdentifiantCaractere() {
        KeyEvent event = new KeyEvent(
            new Canvas(), KeyEvent.KEY_PRESSED, 0, 0,
            KeyEvent.VK_A, 'A'
        );

        assertEquals('a', Commandes.identifiant(event));
    }

    @Test
    void testIdentifiantEspace() {
        KeyEvent event = new KeyEvent(
            new Canvas(), KeyEvent.KEY_PRESSED, 0, 0,
            KeyEvent.VK_SPACE, ' '
        );

        assertEquals(' ', Commandes.identifiant(event));
    }

    @Test
    void testIdentifiantToucheDirectionnelle() {
        KeyEvent event = new KeyEvent(
            new Canvas(), KeyEvent.KEY_PRESSED, 0, 0,
            KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED
        );

        assertEquals(-KeyEvent.VK_UP, Commandes.identifiant(event));
    }

    @Test
    void testIdentifiantAutresFleches() {
        KeyEvent down = new KeyEvent(
            new Canvas(), KeyEvent.KEY_PRESSED, 0, 0,
            KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED
        );

        KeyEvent left = new KeyEvent(
            new Canvas(), KeyEvent.KEY_PRESSED, 0, 0,
            KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED
        );

        KeyEvent right = new KeyEvent(
            new Canvas(), KeyEvent.KEY_PRESSED, 0, 0,
            KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED
        );

        assertEquals(-KeyEvent.VK_DOWN, Commandes.identifiant(down));
        assertEquals(-KeyEvent.VK_LEFT, Commandes.identifiant(left));
        assertEquals(-KeyEvent.VK_RIGHT, Commandes.identifiant(right));
    }

    @Test
    void testIdentifiantToucheInutilisable() {
        KeyEvent event = new KeyEvent(
            new Canvas(), KeyEvent.KEY_PRESSED, 0, 0,
            KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED
        );

        assertEquals(0, Commandes.identifiant(event));
    }

    @Test
    void testGetTouche() {
        assertEquals('z', Commandes.getTouche(Cmd.UP));
        assertEquals('f', Commandes.getTouche(Cmd.FLAG));
    }

    @Test
    void testSetTouche() {
        Commandes.setTouche(Cmd.UP, 'w');

        assertEquals('w', Commandes.getTouche(Cmd.UP));
    }

    @Test
    void testSetToucheEchange() {
        // La touche 's' appartient initialement à DOWN
        Commandes.setTouche(Cmd.UP, 's');

        assertEquals('s', Commandes.getTouche(Cmd.UP));
        assertEquals('z', Commandes.getTouche(Cmd.DOWN));
    }

    @Test
    void testGetAction() {
        assertEquals(Cmd.UP, Commandes.getAction('z'));
        assertEquals(Cmd.DOWN, Commandes.getAction('s'));
        assertEquals(Cmd.LEFT, Commandes.getAction('q'));
        assertEquals(Cmd.RIGHT, Commandes.getAction('d'));
        assertEquals(Cmd.FLAG, Commandes.getAction('f'));
        assertEquals(Cmd.REVEAL, Commandes.getAction(' '));
    }

    @Test
    void testGetActionToucheInconnue() {
        assertNull(Commandes.getAction('x'));
        assertNull(Commandes.getAction(0));
    }

    @Test
    void testGetActionApresModification() {
        Commandes.setTouche(Cmd.UP, 'w');

        assertEquals(Cmd.UP, Commandes.getAction('w'));
        assertNull(Commandes.getAction('z'));
    }

    @Test
    void testReinitialiser() {
        Commandes.setTouche(Cmd.UP, 'w');
        Commandes.setTouche(Cmd.DOWN, 'x');

        Commandes.reinitialiser();

        assertEquals('z', Commandes.getTouche(Cmd.UP));
        assertEquals('s', Commandes.getTouche(Cmd.DOWN));
        assertEquals('q', Commandes.getTouche(Cmd.LEFT));
        assertEquals('d', Commandes.getTouche(Cmd.RIGHT));
        assertEquals('f', Commandes.getTouche(Cmd.FLAG));
        assertEquals(' ', Commandes.getTouche(Cmd.REVEAL));
    }

    @Test
    void testNomToucheFleches() {
        assertEquals("FLÈCHE HAUT",
            Commandes.nomTouche(-KeyEvent.VK_UP));

        assertEquals("FLÈCHE BAS",
            Commandes.nomTouche(-KeyEvent.VK_DOWN));

        assertEquals("FLÈCHE GAUCHE",
            Commandes.nomTouche(-KeyEvent.VK_LEFT));

        assertEquals("FLÈCHE DROITE",
            Commandes.nomTouche(-KeyEvent.VK_RIGHT));
    }

    @Test
    void testNomToucheEspace() {
        assertEquals("ESPACE", Commandes.nomTouche(' '));
    }

    @Test
    void testNomToucheLettre() {
        assertEquals("A", Commandes.nomTouche('a'));
        assertEquals("Z", Commandes.nomTouche('z'));
    }
}
