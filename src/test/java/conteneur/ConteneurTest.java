package conteneur;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

public class ConteneurTest {

    // =========================
    // TESTS DE AJOUTER()
    // =========================

    // RIGHT 1 : Ajouter un élément
    @Test
    void testAjouterUnElement() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);

        c.ajouter("A");

        assertTrue(c.contient("A"));
        assertEquals(1, c.taille());
    }

    // RIGHT 2 : Ajouter deux éléments différents
    @Test
    void testAjouterDeuxElements() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);

        c.ajouter("A");
        c.ajouter("B");

        assertTrue(c.contient("A"));
        assertTrue(c.contient("B"));
        assertEquals(2, c.taille());
    }

    // BOUNDARY 1 : Ajouter null
    @Test
    void testAjouterNull() {
        Conteneur c = new ConteneurImpl(5);

        assertThrows(ErreurConteneur.class, () -> c.ajouter(null));
    }

    // BOUNDARY 2 : Ajouter deux fois le même objet
    @Test
    void testAjouterMemeObjet() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);
        Object a = new String("A");

        c.ajouter(a);

        assertThrows(ErreurConteneur.class, () -> c.ajouter(a));
    }

    // BOUNDARY 3 : Ajouter deux objets égaux
    @Test
    void testAjouterObjetsEgaux() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);

        String a = new String("A");
        String b = new String("A");

        c.ajouter(a);

        assertThrows(ErreurConteneur.class, () -> c.ajouter(b));
    }

    // BOUNDARY 4 : Ajouter deux éléments dans une capacité de 2
    @Test
    void testAjouterCapaciteDeux() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(2);

        c.ajouter("A");
        c.ajouter("B");

        assertEquals(2, c.taille());
    }

    // BOUNDARY 5 : Ajouter deux éléments dans une capacité de 1
    @Test
    void testAjouterCapaciteUn() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(1);

        c.ajouter("A");

        assertThrows(ErreurConteneur.class, () -> c.ajouter("B"));
    }

    // CROSSCHECK : Comparaison avec HashSet
    @Test
    void testAjouterCrosscheck() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);
        HashSet<Object> set = new HashSet<>();

        String[] elements = {"A", "B", "C", "D"};

        for (String element : elements) {
            c.ajouter(element);
            set.add(element);
        }

        for (Object element : set) {
            assertTrue(c.contient(element));
        }
    }


    // =========================
    // TESTS DE RETIRER()
    // =========================

    // RIGHT : Retirer le dernier élément
    @Test
    void testRetirerDernierElement() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);

        c.ajouter("A");
        c.ajouter("B");

        c.retirer("B");

        assertFalse(c.contient("B"));
        assertTrue(c.contient("A"));
        assertEquals(1, c.taille());
    }

    // RIGHT : Retirer puis ajouter un nouvel élément
    @Test
    void testRetirerPuisAjouter() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);

        c.ajouter("A");
        c.ajouter("B");

        c.retirer("B");
        c.ajouter("C");

        assertTrue(c.contient("A"));
        assertTrue(c.contient("C"));
        assertFalse(c.contient("B"));
        assertEquals(2, c.taille());
    }

    // BOUNDARY : Retirer un élément absent
    @Test
    void testRetirerElementAbsent() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);

        c.ajouter("A");

        assertThrows(ErreurConteneur.class, () -> c.retirer("B"));
    }

    // BOUNDARY : Retirer un élément absent dans un conteneur plein
    @Test
    void testRetirerElementAbsentConteneurPlein() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(2);

        c.ajouter("A");
        c.ajouter("B");

        assertThrows(ErreurConteneur.class, () -> c.retirer("C"));
    }

    // CROSSCHECK : Retirer un objet égal mais différent
    @Test
    void testRetirerObjetEgal() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);

        String a = new String("A");
        String b = new String("A");

        c.ajouter(a);
        c.retirer(b);

        assertFalse(c.contient(a));
        assertEquals(0, c.taille());
    }

    // RIGHT : Retirer le premier élément
    @Test
    void testRetirerPremierElement() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);

        c.ajouter("A");
        c.ajouter("B");
        c.ajouter("C");

        c.retirer("A");

        assertFalse(c.contient("A"));
        assertTrue(c.contient("B"));
        assertTrue(c.contient("C"));
        assertEquals(2, c.taille());
    }


    // =========================
    // TESTS DE REDIMENSIONNER()
    // =========================

    // RIGHT 1 : Redimensionner un conteneur vide
    @Test
    void testRedimensionnerVide() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(2);

        c.redimensionner(5);

        assertEquals(5, c.capacite());
        assertTrue(c.estVide());
    }

    // RIGHT 2 : Redimensionner avec un élément
    @Test
    void testRedimensionnerUnElement() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(2);

        c.ajouter("A");
        c.redimensionner(4);

        assertEquals(4, c.capacite());
        assertTrue(c.contient("A"));
        assertEquals(1, c.taille());
    }

    // RIGHT 3 : Redimensionner avec plusieurs éléments
    @Test
    void testRedimensionnerPlusieursElements() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(3);

        c.ajouter("A");
        c.ajouter("B");
        c.ajouter("C");

        c.redimensionner(6);

        assertEquals(6, c.capacite());
        assertTrue(c.contient("A"));
        assertTrue(c.contient("B"));
        assertTrue(c.contient("C"));
        assertEquals(3, c.taille());
    }

    // BOUNDARY 1 : Nouvelle capacité égale à la taille
    @Test
    void testRedimensionnerCapaciteEgaleTaille() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);

        c.ajouter("A");
        c.ajouter("B");

        c.redimensionner(2);

        assertEquals(2, c.capacite());
        assertTrue(c.contient("A"));
        assertTrue(c.contient("B"));
    }

    // BOUNDARY 2 : Nouvelle capacité inférieure à la taille
    @Test
    void testRedimensionnerCapaciteInferieureTaille() throws ErreurConteneur {
        Conteneur c = new ConteneurImpl(5);

        c.ajouter("A");
        c.ajouter("B");

        assertThrows(ErreurConteneur.class, () -> c.redimensionner(1));
    }
}