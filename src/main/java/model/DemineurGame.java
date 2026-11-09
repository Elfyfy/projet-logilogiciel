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
	//non-testée correctement

	private boolean isDefined(int l,int c){return(l>-1)&&(c>-1)&&(l<lignes)&&(c<colonnes);}
	// vérifie que la case (l,c) est valide dans la grille de jeu
	//non-testée correctement

	private boolean changed(int[][][] verif){
		for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){if(verif[l][c][0]!=verif[l][c][1]){return true;}}
		};return false;} //vérifie si verif a été modifiée lors de l'étape de vérification courante
	//non-testée correctement

	private int KAround(int k,int l,int c,int[][][] verif){
		int count=0;
		for (int dl=-1;dl<2;dl++) {
			for (int dc=-1;dc<2;dc++) {
				if ((dl==0)&&(dc==0)) continue;
				int nl=l+dl;int nc=c+dc;
				if (isDefined(nl,nc)&&(verif[nl][nc][0]==k)){count++;}}}
		return count;} //nb d'élément(s) k autour de la case (i,j)
	//non-testée correctement
	private int UnkAround(int l,int c,int[][][] verif){return KAround(0,l,c,verif);}
	private int PndAround(int l,int c,int[][][] verif){return KAround(1,l,c,verif);}
//	private int SptAround(int l,int c,int[][][] verif){return KAround(2,l,c,verif);}

//	private void KBetween(int k,int l1,int c1,int l2,int c2,int[][][] verif){}
	private void sprint(int[][][] verif){
		System.out.print("\n");
		for(int i=0;i<lignes;i++){for(int j=0;j<colonnes;j++){
			System.out.print(verif[i][j][0]);
			System.out.print(verif[i][j][1]);
			System.out.print(verif[i][j][2]);
			System.out.print(" ");
		}
	System.out.print("\n");}
	}

	private void placerMines(int nbMines,int lDep,int cDep) {
		boolean keep=true;
		Random alea=new Random();
		int posees;int[][][] verif;	// 0:unknown, 1:pinned, 2:swept
		//verif[i][j]: [état de (i,j) à l'étape n; à l'étape n-1,nb de mines autour,a une mine]
		while(keep){
			posees=0;
			verif=new int[lignes][colonnes][4];
			verif[lDep][cDep][0]=2;
			while(posees<nbMines){
				int l=alea.nextInt(lignes);
				int c=alea.nextInt(colonnes);
				if((abs(l-lDep)<2)&&(abs(c-cDep)<2)) continue;//empêche de miner autour du départ
				if(verif[l][c][3]==0){
					verif[l][c][3]=1;
					posees++;
					for (int dl=-1;dl<2;dl++) {
						for (int dc=-1;dc<2;dc++) {
							if ((dl==0)&&(dc==0)) continue;
							int nl=l+dl;int nc=c+dc;
							if (isDefined(nl,nc)){verif[l+dl][c+dc][2]++;}}}}}
			while(changed(verif)){
				for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){verif[l][c][1]=verif[l][c][0];}}
				for(int l=0;l<lignes;l++){
					for(int c=0;c<colonnes;c++){
						if(verif[l][c][0]!=2)continue;
						//si nb pinned autour = nb mines indiquées, il ne reste que du vide, qu'on drague
						if(PndAround(l,c,verif)==verif[l][c][2]){
							for(int i=l-1;i<l+2;i++){ 
								for(int j=c-1;j<c+2;j++){
									if(isDefined(i,j)&&(verif[i][j][0]==0)){verif[i][j][0]=2;}}}}
						//si nb unknown autour = nb mines indiquées, il ne reste que les mines, qu'on pin
						if(UnkAround(l,c,verif)==verif[l][c][2]){
							for(int i=l-1;i<l+2;i++){ 
								for(int j=c-1;j<c+2;j++){
									if(isDefined(i,j)&&(verif[i][j][0]==0)){verif[i][j][0]=1;}}}} }} }
			keep=false; //s'il reste unknown quelque part, les mines sont mal disposées
			for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){if(verif[l][c][0]==0){keep=true;}}}
		if(keep==false){
			for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){
				if(verif[l][c][3]==1){grille[l][c]=new CaseMinee(c,l);}}} }
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