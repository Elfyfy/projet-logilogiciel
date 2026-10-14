package model;

import java.util.Random;

import engine.Cmd;
import engine.Game;

public class DemineurGame implements Game {

	private int lignes;
	private int colonnes;
	private Case[][] grille;
	private int curseurLigne = 0;
	private int curseurColonne = 0;

	public DemineurGame(int lignes, int colonnes, int nbMines) {
		this.lignes = lignes;
		this.colonnes = colonnes;
		this.grille = new Case[lignes][colonnes];

		placerMines(nbMines);
		creerCasesLibres();
		calculerVoisines();
	}

	private void placerMines(int nbMines) {
		Random alea = new Random();
		int posees = 0;
		while (posees < nbMines) {
			int l = alea.nextInt(lignes);
			int c = alea.nextInt(colonnes);
			if (grille[l][c] == null) {
				grille[l][c] = new CaseMinee(c, l);
				posees++;
			}
		}
	}

	// Toutes les cases encore vides deviennent des CaseLibre
	private void creerCasesLibres() {
		for (int l = 0; l < lignes; l++) {
			for (int c = 0; c < colonnes; c++) {
				if (grille[l][c] == null) {
					grille[l][c] = new CaseLibre(c, l, "");
				}
			}
		}
	}

	private void calculerVoisines() {
		for (int l = 0; l < lignes; l++) {
			for (int c = 0; c < colonnes; c++) {
				if (!(grille[l][c] instanceof CaseLibre)) continue;

				int compteur = 0;
				for (int dl = -1; dl <= 1; dl++) {
					for (int dc = -1; dc <= 1; dc++) {
						if (dl == 0 && dc == 0) continue;
						int nl = l + dl;
						int nc = c + dc;
						boolean dansLaGrille = nl >= 0 && nl < lignes && nc >= 0 && nc < colonnes;
						if (dansLaGrille && grille[nl][nc] instanceof CaseMinee) {
							compteur++;
						}
					}
				}
				((CaseLibre) grille[l][c]).setNMines(compteur);
			}
		}
	}

	// Getters
	public int getLignes() { return lignes; }
	public int getColonnes() { return colonnes; }
	public Case getCase(int l, int c) { return grille[l][c]; }
	public int getCurseurLigne() { return curseurLigne; }
	public int getCurseurColonne() { return curseurColonne; }

	@Override
	public void evolve(Cmd cmd) {
		switch (cmd) {
		case UP:    if (curseurLigne > 0) curseurLigne--; break;
		case DOWN:  if (curseurLigne < lignes - 1) curseurLigne++; break;
		case LEFT:  if (curseurColonne > 0) curseurColonne--; break;
		case RIGHT: if (curseurColonne < colonnes - 1) curseurColonne++; break;
		case FLAG:
			grille[curseurLigne][curseurColonne].setDrapeau();
			break;
		default: break;
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}