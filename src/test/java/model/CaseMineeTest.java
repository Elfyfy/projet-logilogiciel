
package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaseMineeTest {

    // Vérifie l'initialisation du constructeur
    @Test
    void testConstructeur() {
        CaseMinee c = new CaseMinee(2, 3);

        assertFalse(c.getDecouvert());
        assertTrue(c.getAccessible());
        assertFalse(c.getDrapeau());
        assertEquals(2, c.getCoordX());
        assertEquals(3, c.getCoordY());
        assertEquals("*", c.getSprite());
    }

    // Vérifie que les méthodes héritées fonctionnent
    @Test
    void testMethodesHeritees() {
        CaseMinee c = new CaseMinee(0, 0);

        c.setDecouvert();
        c.setDrapeau();
        c.setAccessible(false);

        assertTrue(c.getDecouvert());
        assertTrue(c.getDrapeau());
        assertFalse(c.getAccessible());
    }

    // Vérifie que le sprite peut être modifié
    @Test
    void testModificationSprite() {
        CaseMinee c = new CaseMinee(0, 0);

        c.setSprite("mine.png");

        assertEquals("mine.png", c.getSprite());
    }
}
