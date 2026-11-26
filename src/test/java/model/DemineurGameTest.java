
package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import engine.Cmd;

public class DemineurGameTest {

    // Vérifie les dimensions de la grille
    @Test
    void testDimensionsGrille() {
        DemineurGame jeu = new DemineurGame(5, 8, 10);

        assertEquals(5, jeu.getLignes());
        assertEquals(8, jeu.getColonnes());
    }

    // Vérifie que toutes les cases sont correctement créées
    @Test
    void testCreationCases() {
        DemineurGame jeu = new DemineurGame(5, 5, 5);

        for (int l = 0; l < jeu.getLignes(); l++) {
            for (int c = 0; c < jeu.getColonnes(); c++) {
                assertNotNull(jeu.getCase(l, c));
                assertEquals(c, jeu.getCase(l, c).getCoordX());
                assertEquals(l, jeu.getCase(l, c).getCoordY());
            }
        }
    }

    // Vérifie que le nombre de mines est respecté
    @Test
    void testNombreMines() {
        int nbMines = 8;
        DemineurGame jeu = new DemineurGame(5, 5, nbMines);

        int compteur = 0;

        for (int l = 0; l < jeu.getLignes(); l++) {
            for (int c = 0; c < jeu.getColonnes(); c++) {
                if (jeu.getCase(l, c) instanceof CaseMinee) {
                    compteur++;
                }
            }
        }

        assertEquals(nbMines, compteur);
    }

    // Vérifie que le nombre de mines voisines est correct
    @Test
    void testCalculMinesVoisines() {
        DemineurGame jeu = new DemineurGame(5, 5, 6);

        for (int l = 0; l < jeu.getLignes(); l++) {
            for (int c = 0; c < jeu.getColonnes(); c++) {

                Case actuelle = jeu.getCase(l, c);

                if (actuelle instanceof CaseLibre) {
                    int attendu = 0;

                    for (int dl = -1; dl <= 1; dl++) {
                        for (int dc = -1; dc <= 1; dc++) {

                            if (dl == 0 && dc == 0) {
                                continue;
                            }

                            int nl = l + dl;
                            int nc = c + dc;

                            if (nl >= 0 && nl < jeu.getLignes()
                                    && nc >= 0 && nc < jeu.getColonnes()
                                    && jeu.getCase(nl, nc) instanceof CaseMinee) {
                                attendu++;
                            }
                        }
                    }

                    assertEquals(attendu,
                            ((CaseLibre) actuelle).getNMines());
                }
            }
        }
    }

    // Vérifie la position initiale du curseur
    @Test
    void testPositionInitialeCurseur() {
        DemineurGame jeu = new DemineurGame(5, 5, 3);

        assertEquals(0, jeu.getCurseurLigne());
        assertEquals(0, jeu.getCurseurColonne());
    }

    // Vérifie les déplacements du curseur
    @Test
    void testDeplacementsCurseur() {
        DemineurGame jeu = new DemineurGame(5, 5, 3);

        jeu.evolve(Cmd.DOWN);
        assertEquals(1, jeu.getCurseurLigne());

        jeu.evolve(Cmd.RIGHT);
        assertEquals(1, jeu.getCurseurColonne());

        jeu.evolve(Cmd.UP);
        assertEquals(0, jeu.getCurseurLigne());

        jeu.evolve(Cmd.LEFT);
        assertEquals(0, jeu.getCurseurColonne());
    }

    // Vérifie que le curseur ne sort pas de la grille
    @Test
    void testLimitesDeplacement() {
        DemineurGame jeu = new DemineurGame(5, 5, 3);

        jeu.evolve(Cmd.UP);
        jeu.evolve(Cmd.LEFT);

        assertEquals(0, jeu.getCurseurLigne());
        assertEquals(0, jeu.getCurseurColonne());

        for (int i = 0; i < 10; i++) {
            jeu.evolve(Cmd.DOWN);
            jeu.evolve(Cmd.RIGHT);
        }

        assertEquals(4, jeu.getCurseurLigne());
        assertEquals(4, jeu.getCurseurColonne());
    }

    // Vérifie la pose d'un drapeau
    @Test
    void testPoserDrapeau() {
        DemineurGame jeu = new DemineurGame(5, 5, 3);

        Case c = jeu.getCase(0, 0);

        jeu.evolve(Cmd.FLAG);

        assertTrue(c.getDrapeau());
    }

    // Vérifie qu'une case vide peut être découverte
    @Test
    void testDecouvrirCaseSansMine() {
        DemineurGame jeu = new DemineurGame(3, 3, 0);

        jeu.evolve(Cmd.REVEAL);

        assertTrue(jeu.getCase(0, 0).getDecouvert());
    }

    // Vérifie la découverte automatique des cases voisines
    @Test
    void testDecouverteAutomatique() {
        DemineurGame jeu = new DemineurGame(3, 3, 0);

        jeu.evolve(Cmd.REVEAL);

        for (int l = 0; l < 3; l++) {
            for (int c = 0; c < 3; c++) {
                assertTrue(jeu.getCase(l, c).getDecouvert());
            }
        }
    }

    // Vérifie qu'une case avec un drapeau ne peut pas être découverte
    @Test
    void testDrapeauEmpecheDecouverte() {
        DemineurGame jeu = new DemineurGame(3, 3, 0);

        Case c = jeu.getCase(0, 0);

        jeu.evolve(Cmd.FLAG);
        jeu.evolve(Cmd.REVEAL);

        assertTrue(c.getDrapeau());
        assertFalse(c.getDecouvert());
    }

    // Vérifie qu'une mine peut être découverte
    @Test
    void testDecouvrirUneMine() {
        DemineurGame jeu = new DemineurGame(1, 1, 1);

        jeu.evolve(Cmd.REVEAL);

        assertTrue(jeu.getCase(0, 0) instanceof CaseMinee);
        assertTrue(jeu.getCase(0, 0).getDecouvert());
    }
}
