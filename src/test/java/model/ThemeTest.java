
package model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

public class ThemeTest {

    @Test
    void testThemeFacile() {
        Theme theme = Theme.FACILE;

        assertEquals(new Color(18, 32, 26), theme.entete);
        assertEquals(new Color(26, 44, 36), theme.bordure);

        assertEquals(new Color(72, 142, 104), theme.caseBase);
        assertEquals(new Color(104, 182, 138), theme.caseClair);
        assertEquals(new Color(48, 100, 74), theme.caseSombre);

        assertEquals(new Color(58, 86, 72), theme.videBase);
        assertEquals(new Color(80, 110, 94), theme.videClair);
        assertEquals(new Color(42, 66, 54), theme.videSombre);

        assertEquals(new Color(170, 40, 40), theme.dangerBase);
        assertEquals(new Color(215, 85, 75), theme.dangerClair);
        assertEquals(new Color(110, 22, 22), theme.dangerSombre);

        assertFalse(theme.flammes);
    }

    @Test
    void testThemeMoyen() {
        Theme theme = Theme.MOYEN;

        assertEquals(new Color(36, 28, 16), theme.entete);
        assertEquals(new Color(48, 38, 22), theme.bordure);

        assertEquals(new Color(150, 118, 62), theme.caseBase);
        assertEquals(new Color(190, 154, 92), theme.caseClair);
        assertEquals(new Color(108, 82, 40), theme.caseSombre);

        assertEquals(new Color(88, 72, 50), theme.videBase);
        assertEquals(new Color(112, 94, 68), theme.videClair);
        assertEquals(new Color(66, 54, 36), theme.videSombre);

        assertEquals(new Color(170, 40, 40), theme.dangerBase);
        assertEquals(new Color(215, 85, 75), theme.dangerClair);
        assertEquals(new Color(110, 22, 22), theme.dangerSombre);

        assertFalse(theme.flammes);
    }

    @Test
    void testThemeDifficile() {
        Theme theme = Theme.DIFFICILE;

        assertEquals(new Color(40, 6, 8), theme.entete);
        assertEquals(new Color(64, 8, 10), theme.bordure);

        assertEquals(new Color(210, 28, 28), theme.caseBase);
        assertEquals(new Color(250, 70, 55), theme.caseClair);
        assertEquals(new Color(140, 14, 16), theme.caseSombre);

        assertEquals(new Color(104, 28, 30), theme.videBase);
        assertEquals(new Color(136, 46, 46), theme.videClair);
        assertEquals(new Color(76, 18, 20), theme.videSombre);

        assertEquals(new Color(200, 160, 40), theme.dangerBase);
        assertEquals(new Color(235, 200, 90), theme.dangerClair);
        assertEquals(new Color(130, 100, 20), theme.dangerSombre);

        assertTrue(theme.flammes);
    }

    @Test
    void testPourNiveauFacile() {
        assertSame(Theme.FACILE, Theme.pour(0));
    }

    @Test
    void testPourNiveauMoyen() {
        assertSame(Theme.MOYEN, Theme.pour(1));
    }

    @Test
    void testPourNiveauDifficile() {
        assertSame(Theme.DIFFICILE, Theme.pour(2));
    }

    @Test
    void testPourNiveauNegatif() {
        assertSame(Theme.FACILE, Theme.pour(-1));
    }

    @Test
    void testPourNiveauSuperieur() {
        assertSame(Theme.DIFFICILE, Theme.pour(3));
        assertSame(Theme.DIFFICILE, Theme.pour(10));
        assertSame(Theme.DIFFICILE, Theme.pour(100));
    }

    @Test
    void testPourBornes() {
        assertSame(Theme.FACILE, Theme.pour(Integer.MIN_VALUE));
        assertSame(Theme.DIFFICILE, Theme.pour(Integer.MAX_VALUE));
    }

    @Test
    void testThemesDifferents() {
        assertNotSame(Theme.FACILE, Theme.MOYEN);
        assertNotSame(Theme.MOYEN, Theme.DIFFICILE);
        assertNotSame(Theme.FACILE, Theme.DIFFICILE);
    }
}
