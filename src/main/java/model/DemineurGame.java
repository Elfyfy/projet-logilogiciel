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
	private Chronometre chronometre; // MODIF : créé dans initialiser()
	private Bomb[] bombes;
	private boolean isOver;
	private Character hero;

	// AJOUT : infos pour l'explosion
	private long tempsDefaite;
	private int ligneExplosion, colonneExplosion;

	// AJOUT : victoire
	private boolean isWon;
	private long tempsVictoire;
	private boolean retourMenu; // AJOUT : Échap -> retour au menu
	private boolean niveauSuivant;
	private boolean niveauSuivantPossible;

	public DemineurGame(int lignes, int colonnes, int nbMines) {
		this.lignes = lignes;
		this.colonnes = colonnes;
		this.nbMines = nbMines;
		initialiser(); // MODIF : tout le reste est déplacé dans initialiser()
	}

	// AJOUT : (ré)initialise la partie, utilisée au démarrage et pour rejouer
	private void initialiser() {
		this.grille = new Case[lignes][colonnes];
		this.curseurLigne = 0;
		this.curseurColonne = 0;
		this.chronometre = new Chronometre();
		this.bombes = new Bomb[1];
		this.bombes[0] = new Bomb(lignes,colonnes,null,1,null);
		this.isOver = false;
		this.isWon = false;       // AJOUT
		this.retourMenu = false;  // AJOUT
		Sprite[] sprites = new Sprite[1];
		this.hero = new Character(this.curseurLigne,this.curseurColonne,sprites);
		this.niveauSuivant = false;

		placerMines(nbMines,0,0); //pt de départ du joueur.euse
		creerCasesLibres();
		calculerVoisines();
	}

	// AJOUT
	public void rejouer() {
		initialiser();
	}

	private int abs(int z){if(z<0){return abs(-z);};return z;}
	// valeur absolue (on peut utiliser Math.abs())

	private boolean isDefined(int l,int c){return(l>-1)&&(c>-1)&&(l<lignes)&&(c<colonnes);}
	// vérifie que la case l,c est valide dans la grille de jeu

	private boolean changed(int[][][] verif){
		for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){
			if(verif[l][c][0]!=verif[l][c][1]){return true;}}
		};return false;} //vérifie si verif a été modifiée lors de l'étape de vérification courante

	private int KAround(int k,int l,int c,int[][][] verif){
		int count=0;
		for(int i=l-1;i<l+2;i++){for(int j=c-1;j<c+2;j++) {
				if((100*i+j!=100*l+c)&&isDefined(i,j)&&(verif[i][j][0]==k)){count++;}}}
		return count;} //nb d'élément(s) k autour de la case (l,c)
	//non-testée correctement

	private int UnkAround(int l,int c,int[][][] verif){return KAround(0,l,c,verif);}
	private int PndAround(int l,int c,int[][][] verif){return KAround(1,l,c,verif);}
//	private int SptAround(int l,int c,int[][][] verif){return KAround(2,l,c,verif);}

//	private void KBetween(int k,int l1,int c1,int l2,int c2,int[][][] verif){}
	private void sprint(int[][][] verif){
		System.out.print('\n');
		for(int l=0;l<lignes;l++){
				for(int c=0;c<colonnes;c++){
					System.out.print(verif[l][c][0]);}
				System.out.print('\n');}}

	private void placerMines(int nbMines,int lDep,int cDep) {
		boolean keep=true;Random alea=new Random();
		int posees;int[][][] verif;
		while(keep){
			posees=0;verif=new int[lignes][colonnes][4];boolean change=true;
			verif[lDep][cDep][0]=2;//démarrer par un zéro
			while(posees<nbMines){
				int l=alea.nextInt(lignes);int c=alea.nextInt(colonnes);
				if((abs(l-lDep)<2)&&(abs(c-cDep)<2)) continue;
				if(verif[l][c][3]==0){
					verif[l][c][3]=1;//présence d'une mine
					posees++;
					for(int i=l-1;i<l+2;i++){for(int j=c-1;j<c+2;j++){
						if((100*i+j!=100*l+c)&&isDefined(i,j)){verif[i][j][2]++;}}} } }
			while(change){
				//for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){verif[l][c][1]=verif[l][c][0];}}
				change=false;
				for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){
					if(verif[l][c][0]==2){
						if(PndAround(l,c,verif)==verif[l][c][2]){
							for(int i=l-1;i<l+2;i++){for(int j=c-1;j<c+2;j++){
								if(isDefined(i,j)&&(verif[i][j][0]==0)){
									verif[i][j][0]=2;change=true;}}} }
						else if(UnkAround(l,c,verif)==verif[l][c][2]){
							for(int i=l-1;i<l+2;i++){for(int j=c-1;j<c+2;j++){
								if(isDefined(i,j)&&(verif[i][j][0]==0)){
									verif[i][j][0]=1;change=true;}}} } }} } }
			keep=false; //s'il reste une case unknown, les mines sont mal placées, et on continue
			for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){if(verif[l][c][0]==0){keep=true;}}}
			if(keep==false){
				for(int l=0;l<lignes;l++){for(int c=0;c<colonnes;c++){
					if(verif[l][c][3]==1){grille[l][c]=new CaseMinee(c,l);}}} } } }

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
		if (cible.getDrapeau()) return; // AJOUT : une case avec drapeau ne se révèle pas
		if (cible instanceof CaseMinee) {
			declencherDefaite(l, c); // MODIF : remplace this.setIsOver()
		}
		else {
			if (cible.getDecouvert() || cible.getDrapeau()) return;

			chronometre.demarrer();

			cible.setDecouvert();

			if (cible instanceof CaseLibre && ((CaseLibre) cible).getNMines() == 0) {
				for (int dl = -1; dl <= 1; dl++) {
					for (int dc = -1; dc <= 1; dc++) {
						if (dl != 0 || dc != 0) decouvrir(l + dl, c + dc);
					}
				}
			}
		}
	}

	// AJOUT : défaite = on mémorise où et quand, puis on révèle toutes les mines
	private void declencherDefaite(int l, int c) {
		isOver = true;
		chronometre.arreter();
		tempsDefaite = System.currentTimeMillis();
		ligneExplosion = l;
		colonneExplosion = c;
		for (int i = 0; i < lignes; i++) {
			for (int j = 0; j < colonnes; j++) {
				if (grille[i][j] instanceof CaseMinee) grille[i][j].setDecouvert();
			}
		}
	}

	// AJOUT : victoire si toutes les cases non minées sont découvertes
	private void verifierVictoire() {
		for (int l = 0; l < lignes; l++) {
			for (int c = 0; c < colonnes; c++) {
				if (!(grille[l][c] instanceof CaseMinee) && !grille[l][c].getDecouvert()) {
					return; // il reste une case sûre à découvrir
				}
			}
		}
		isWon = true;
		chronometre.arreter();
		tempsVictoire = System.currentTimeMillis();
		// chronometre.arreter(); // à décommenter si Chronometre possède une méthode d'arrêt
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
	public Bomb[] getBomb() {return this.bombes;}
	public int getNbBombes() {return this.bombes.length;}
	public boolean getIsOver() {return this.isOver;}
	public Character getHero() {return this.hero;}
	// AJOUT
	public long getTempsDefaite() { return tempsDefaite; }
	public int getLigneExplosion() { return ligneExplosion; }
	public int getColonneExplosion() { return colonneExplosion; }
	public boolean getIsWon() { return isWon; }
	public long getTempsVictoire() { return tempsVictoire; }
	public boolean getRetourMenu() { return retourMenu; }
	public boolean getNiveauSuivant() { return niveauSuivant; }
	public boolean getNiveauSuivantPossible() { return niveauSuivantPossible; }
	public void setNiveauSuivantPossible(boolean b) { this.niveauSuivantPossible = b; }
	public void setIsOver() {this.isOver = true;}
	
	@Override
	public void evolve(Cmd cmd) {
		// MODIF : Échap marche à tout moment (partie en cours, défaite ou victoire)
		if (cmd == Cmd.MENU) {
			retourMenu = true;
			return;
		}

		if (isOver || isWon) {
		if (cmd == Cmd.REJOUER) rejouer();
		else if (cmd == Cmd.SUIVANT && isWon && niveauSuivantPossible) niveauSuivant = true;
		return;
}

		switch (cmd) {
		case UP:    if (curseurLigne > 0) curseurLigne--; break;
		case DOWN:  if (curseurLigne < lignes - 1) curseurLigne++; break;
		case LEFT:  if (curseurColonne > 0) curseurColonne--; break;
		case RIGHT: if (curseurColonne < colonnes - 1) curseurColonne++; break;
		case FLAG:
			Case cible = grille[curseurLigne][curseurColonne];
			if (!cible.getDecouvert()) cible.setDrapeau(); break;
		case REVEAL:
			decouvrir(curseurLigne, curseurColonne);
			if (!isOver) verifierVictoire(); // AJOUT
			break;
		default: break; 
		}
	}

	@Override
	public boolean isFinished() {
		if (retourMenu || niveauSuivant) return true;
		if (this.isOver || this.isWon) {
			return false; // MODIF : on reste affiché pour l'explosion / la victoire
		} else {
			for (int i = 0; i < this.bombes.length; i++) {
				Bomb bombe = this.bombes[i];
				if (!bombe.getBlown()) {return false;}
			} return true;
		}
		
	}
}