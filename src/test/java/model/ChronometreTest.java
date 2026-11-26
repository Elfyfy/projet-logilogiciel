
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ChronometreTest {

    @Test
    public void testChronometreInitialiseAZero() {
        Chronometre chrono = new Chronometre();

        assertEquals(0, chrono.getTempsSecondes());
        assertEquals("00:00", chrono.getTempsFormate());
        assertFalse(chrono.estEnCours());
    }

    @Test
    public void testDemarrerChronometre() throws InterruptedException {
        Chronometre chrono = new Chronometre();

        chrono.demarrer();

        assertTrue(chrono.estEnCours());

        Thread.sleep(1100);

        assertTrue(chrono.getTempsSecondes() >= 1);
    }

    @Test
    public void testArreterChronometre() throws InterruptedException {
        Chronometre chrono = new Chronometre();

        chrono.demarrer();
        Thread.sleep(1100);

        chrono.arreter();

        assertFalse(chrono.estEnCours());

        long tempsArrete = chrono.getTempsSecondes();

        Thread.sleep(1100);

        // Le temps ne doit plus augmenter
        assertEquals(tempsArrete, chrono.getTempsSecondes());
    }

    @Test
    public void testReinitialiserChronometre() throws InterruptedException {
        Chronometre chrono = new Chronometre();

        chrono.demarrer();
        Thread.sleep(1100);

        chrono.reinitialiser();

        assertEquals(0, chrono.getTempsSecondes());
        assertEquals("00:00", chrono.getTempsFormate());
        assertFalse(chrono.estEnCours());
    }

    @Test
    public void testDemarrerDeuxFoisNeReinitialisePas() throws InterruptedException {
        Chronometre chrono = new Chronometre();

        chrono.demarrer();
        Thread.sleep(1100);

        chrono.demarrer();
        Thread.sleep(1100);

        assertTrue(chrono.getTempsSecondes() >= 2);
    }
}
