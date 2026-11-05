package model;

public class Chronometre {

    private long debut;
    private long tempsEcoule;
    private boolean enCours;

    public Chronometre() {
        debut = 0;
        tempsEcoule = 0;
        enCours = false;
    }

    public void demarrer() {
        if (!enCours) {
            debut = System.currentTimeMillis();
            enCours = true;
        }
    }

    public void arreter() {
        if (enCours) {
            tempsEcoule += System.currentTimeMillis() - debut;
            enCours = false;
        }
    }

    public void reinitialiser() {
        debut = 0;
        tempsEcoule = 0;
        enCours = false;
    }

    public long getTempsSecondes() {
        long temps = tempsEcoule;

        if (enCours) {
            temps += System.currentTimeMillis() - debut;
        }

        return temps / 1000;
    }

    public String getTempsFormate() {
        long secondes = getTempsSecondes();

        long minutes = secondes / 60;
        long resteSecondes = secondes % 60;

        return String.format("%02d:%02d", minutes, resteSecondes);
    }

    public boolean estEnCours() {
        return enCours;
    }
}