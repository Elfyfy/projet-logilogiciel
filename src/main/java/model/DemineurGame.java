package model;

import java.util.Random;

import engine.Cmd;
import engine.Game;

public class DemineurGame implements Game {
	public static final int REVELEE = 1;
	public static final int DRAPEAU = 2;

	private int lignes;
	private int colonnes;
	private boolean[][] mines;      
	private int[][] voisines;       
	private int[][] etats;         
	private int curseurLigne = 0;
	private int curseurColonne = 0;

	public DemineurGame(int lignes, int colonnes, int nbMines) {
		this.lignes = lignes;
		this.colonnes = colonnes;
		this.mines = new boolean[lignes][colonnes];
		this.voisines = new int[lignes][colonnes];
		this.etats = new int[lignes][colonnes];

		placerMines(nbMines);
		calculerVoisines();
	}

	/** Place nbMines mines à des positions aléatoires, sans doublon. */
	private void placerMines(int nbMines) {
		Random alea = new Random();
		int posees = 0;
		while (posees < nbMines) {
			int l = alea.nextInt(lignes);
			int c = alea.nextInt(colonnes);
			if (!mines[l][c]) {         
				mines[l][c] = true;
				posees++;
			}
		}
	}

	private void calculerVoisines() {
		for (int l = 0; l < lignes; l++) {
			for (int c = 0; c < colonnes; c++) {
				int compteur = 0;
				for (int dl = -1; dl <= 1; dl++) {
					for (int dc = -1; dc <= 1; dc++) {
						if (dl == 0 && dc == 0) {
							continue; 
						}
						int nl = l + dl;
						int nc = c + dc;
						boolean dansLaGrille = nl >= 0 && nl < lignes && nc >= 0 && nc < colonnes;
						if (dansLaGrille && mines[nl][nc]) {
							compteur++;
						}
					}
				}
				voisines[l][c] = compteur;
			}
		}
	}


	public int getLignes() { return lignes; }
	public int getColonnes() { return colonnes; }
	public int getEtat(int l, int c) { return etats[l][c]; }
	public boolean estMine(int l, int c) { return mines[l][c]; }
	public int getVoisines(int l, int c) { return voisines[l][c]; }
	public int getCurseurLigne() { return curseurLigne; }
	public int getCurseurColonne() { return curseurColonne; }


	@Override
	public void evolve(Cmd cmd) {
		switch (cmd) {
		case UP:    if (curseurLigne > 0) curseurLigne--; break;
		case DOWN:  if (curseurLigne < lignes - 1) curseurLigne++; break;
		case LEFT:  if (curseurColonne > 0) curseurColonne--; break;
		case RIGHT: if (curseurColonne < colonnes - 1) curseurColonne++; break;
		default: break;
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}