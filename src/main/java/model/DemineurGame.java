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
	private int nbMines; 
	private Chronometre chronometre = new Chronometre();

	public DemineurGame(int lignes, int colonnes, int nbMines) {
		this.lignes = lignes;
		this.colonnes = colonnes;
		this.grille = new Case[lignes][colonnes];
		this.nbMines = nbMines;

		placerMines(nbMines,0,0); //pt de départ du joueur.euse
		creerCasesLibres();
		calculerVoisines();
	}

	private int abs(int z){if(z<0){return abs(-z);};return z;}
	// valeur absolue

	private boolean isDefined(int l,int c){return(l>-1)&&(c>-1)&&(l<lignes)&&(c<colonnes);}
	// vérifie que la case l,c est valide dans la grille de jeu

	private boolean changed(int[][][] verif){
		for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){if(verif[l][c][0]!=verif[l][c][1]){return true;}}
		};return false;} //vérifie si verif a été modifiée lors de l'étape de vérification courante

	private int KAround(int k,int l,int c,int[][][] verif){
		int count=0;
		for (int dl = -1; dl <= 1; dl++) {
			for (int dc = -1; dc <= 1; dc++) {
				if (dl == 0 && dc == 0) continue;
				int nl = l + dl;
				int nc = c + dc;
				if (isDefined(nl,nc) && (verif[nl][nc][0]==k)) {count++;}}}
		return count;} //nb d'élément(s) k autour de la case (i,j)

	private void placerMines(int nbMines,int lDep,int cDep) {
		boolean keep=true;
		while (keep) {
			Random alea = new Random();
			int posees = 0;
			int[][][] verif=new int[lignes][colonnes][3];
			// 0:unknown, 1:pinned, 2:swept
			//verif[i][j]: [état de la case (i,j) à l'étape de vérification n; à l'étape n-1,nb de mines autour]
			verif[lDep][cDep][0]=2;
			while (posees < nbMines) {
				int l = alea.nextInt(lignes);
				int c = alea.nextInt(colonnes);
				if((abs(l-lDep)<2)&&(abs(c-cDep)<2)) continue;
				if (grille[l][c] == null) {
					grille[l][c] = new CaseMinee(c, l);
					posees++;
					verif[l][c][2]++;}}
			while (changed(verif)){
				for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){verif[l][c][1]=verif[l][c][0];}}
				for(int l=0;l<lignes;l++){
					for(int c=0;c<colonnes;c++){
						////si nb pinned = nb mines indiquées, on peut draguer ce qui est draguable
						if(KAround(1,l,c,verif)==verif[l][c][2]){
							for(int i=l-1;i<l+2;i++){ 
								for(int j=c-1;j<c+2;j++){
									if(isDefined(i,j)&&(verif[i][j][0]==0)){verif[i][j][0]=2;}}}}
						////si nb unknown = nb mines indiquées, on peut tout pin
						if(KAround(0,l,c,verif)==verif[l][c][2]){
							for(int i=l-1;i<l+2;i++){ 
								for(int j=c-1;j<c+2;j++){
									if(isDefined(i,j)&&(verif[i][j][0]==0)){verif[i][j][0]=1;}}}} }} }
			keep=false; //s'il reste une case unknown, les mines sont mal placées, et on continue
			for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){if(verif[l][c][0]==0){keep=true;}}}
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
						if (isDefined(nl,nc) && grille[nl][nc] instanceof CaseMinee) {
							compteur++;
						}
					}
				}
				((CaseLibre) grille[l][c]).setNMines(compteur);
			}
		}
	}

	private void calculerFlag() {
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
	private void decouvrir(int l, int c) {
	if (l < 0 || l >= lignes || c < 0 || c >= colonnes) return;
	Case cible = grille[l][c];
	if (cible.getDecouvert() || cible.getDrapeau()) return;

	chronometre.demarrer();

	cible.setDecouvert();

	if (cible instanceof CaseLibre && ((CaseLibre) cible).getNMines() == 0) {
		for (int dl = -1; dl <= 1; dl++) {
			for (int dc = -1; dc <= 1; dc++) {
				if (dl != 0 || dc != 0) decouvrir(l + dl, c + dc);}
			}
		}
	}

	private int NbDrapeaux() {
		int compteur = 0;
		for (int l = 0; l < lignes; l++) {
			for (int c = 0; c < colonnes; c++) {
				if (grille[l][c].getDrapeau()) {
					compteur++;
				}
			}
		}
		return compteur;
	}

	// Getters
	public int getLignes() { return lignes; }
	public int getColonnes() { return colonnes; }
	public Case getCase(int l, int c) { return grille[l][c]; }
	public int getCurseurLigne() { return curseurLigne; }
	public int getCurseurColonne() { return curseurColonne; }
	public Chronometre getChronometre() { return chronometre; }
	public int getNbMines() { return nbMines; }
	public int getMinesRestantes() { return nbMines - getNbDrapeaux(); }
	public int getNbDrapeaux() { return NbDrapeaux(); }

	
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
		case REVEAL:
		decouvrir(curseurLigne, curseurColonne);
		break;
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}