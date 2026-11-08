
package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaseTest {

    // Vérifie l'initialisation du constructeur
    @Test
    void testConstructeur() {
        Case c = new Case(true, 2, 3, "case.png");

        assertFalse(c.getDecouvert());
        assertTrue(c.getAccessible());
        assertFalse(c.getDrapeau());
        assertEquals(2, c.getCoordX());
        assertEquals(3, c.getCoordY());
        assertEquals("case.png", c.getSprite());
    }

    // Vérifie que la case peut être découverte
    @Test
    void testSetDecouvert() {
        Case c = new Case(true, 0, 0, "case.png");

        c.setDecouvert();

        assertTrue(c.getDecouvert());
    }

    // Vérifie la modification de l'accessibilité
    @Test
    void testSetAccessible() {
        Case c = new Case(true, 0, 0, "case.png");

        c.setAccessible(false);

        assertFalse(c.getAccessible());

        c.setAccessible(true);

        assertTrue(c.getAccessible());
    }

    // Vérifie que le drapeau peut être posé et retiré
    @Test
    void testSetDrapeau() {
        Case c = new Case(true, 0, 0, "case.png");

        assertFalse(c.getDrapeau());

        c.setDrapeau();
        assertTrue(c.getDrapeau());

        c.setDrapeau();
        assertFalse(c.getDrapeau());
    }

    // Vérifie la modification des coordonnées
    @Test
    void testSetCoordonnees() {
        Case c = new Case(true, 0, 0, "case.png");

        c.setCoordX(4);
        c.setCoordY(5);

        assertEquals(4, c.getCoordX());
        assertEquals(5, c.getCoordY());
    }

    // Vérifie la modification du sprite
    @Test
    void testSetSprite() {
        Case c = new Case(true, 0, 0, "case.png");

        c.setSprite("mine.png");

        assertEquals("mine.png", c.getSprite());
    }
}
