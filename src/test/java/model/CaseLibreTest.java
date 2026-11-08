
package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaseLibreTest {

    // Vérifie l'initialisation du constructeur
    @Test
    void testConstructeur() {
        CaseLibre c = new CaseLibre(2, 3, "case.png");

        assertEquals(0, c.getNMines());
        assertFalse(c.getDecouvert());
        assertTrue(c.getAccessible());
        assertFalse(c.getDrapeau());
        assertEquals(2, c.getCoordX());
        assertEquals(3, c.getCoordY());
        assertEquals("case.png", c.getSprite());
    }

    // Vérifie la modification du nombre de mines voisines
    @Test
    void testSetNMines() {
        CaseLibre c = new CaseLibre(0, 0, "case.png");

        c.setNMines(3);

        assertEquals(3, c.getNMines());
    }

    // Vérifie qu'une case libre peut avoir zéro mine voisine
    @Test
    void testAucuneMineVoisine() {
        CaseLibre c = new CaseLibre(0, 0, "case.png");

        assertEquals(0, c.getNMines());
    }

    // Vérifie que les méthodes héritées fonctionnent
    @Test
    void testMethodesHeritees() {
        CaseLibre c = new CaseLibre(1, 1, "case.png");

        c.setDecouvert();
        c.setDrapeau();
        c.setAccessible(false);

        assertTrue(c.getDecouvert());
        assertTrue(c.getDrapeau());
        assertFalse(c.getAccessible());
    }
}
