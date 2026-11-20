
package view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MenuPrincipalTest {

    // TESTS DES DIMENSIONS

    @Test
    void testDimensionsPetite() {
        int[] dimensions = MenuPrincipal.dimensionsPourTaille(0);

        assertEquals(10, dimensions[0]);
        assertEquals(15, dimensions[1]);
    }

    @Test
    void testDimensionsMoyenne() {
        int[] dimensions = MenuPrincipal.dimensionsPourTaille(1);

        assertEquals(15, dimensions[0]);
        assertEquals(25, dimensions[1]);
    }

    @Test
    void testDimensionsGrande() {
        int[] dimensions = MenuPrincipal.dimensionsPourTaille(2);

        assertEquals(20, dimensions[0]);
        assertEquals(35, dimensions[1]);
    }

    @Test
    void testModificationTableauDimensions() {
        int[] dimensions = MenuPrincipal.dimensionsPourTaille(0);

        dimensions[0] = 100;

        // Vérifie que le tableau original n'est pas modifié
        assertEquals(10, MenuPrincipal.dimensionsPourTaille(0)[0]);
    }

    // TESTS DU NOMBRE DE MINES

    @Test
    void testNombreMinesPetiteFacile() {
        assertEquals(7, MenuPrincipal.calculerNbMines(10, 15, 0));
    }

    @Test
    void testNombreMinesPetiteMoyen() {
        assertEquals(22, MenuPrincipal.calculerNbMines(10, 15, 1));
    }

    @Test
    void testNombreMinesPetiteDifficile() {
        assertEquals(30, MenuPrincipal.calculerNbMines(10, 15, 2));
    }

    @Test
    void testNombreMinesMoyenneFacile() {
        assertEquals(18, MenuPrincipal.calculerNbMines(15, 25, 0));
    }

    @Test
    void testNombreMinesMoyenneMoyen() {
        assertEquals(56, MenuPrincipal.calculerNbMines(15, 25, 1));
    }

    @Test
    void testNombreMinesMoyenneDifficile() {
        assertEquals(75, MenuPrincipal.calculerNbMines(15, 25, 2));
    }

    @Test
    void testNombreMinesGrandeFacile() {
        assertEquals(35, MenuPrincipal.calculerNbMines(20, 35, 0));
    }

    @Test
    void testNombreMinesGrandeMoyen() {
        assertEquals(105, MenuPrincipal.calculerNbMines(20, 35, 1));
    }

    @Test
    void testNombreMinesGrandeDifficile() {
        assertEquals(140, MenuPrincipal.calculerNbMines(20, 35, 2));
    }

    @Test
    void testMinimumUneMine() {
        assertEquals(1, MenuPrincipal.calculerNbMines(1, 1, 0));
    }
}
