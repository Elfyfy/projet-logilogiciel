package model;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.GamePainter;

public class DemineurPainter implements GamePainter {

	private static final int TAILLE_CASE = 30;
	private static final int HAUTEUR_HEADER = 35;

	// Menu de fin (commun a la victoire et a la defaite)
	private static final long DUREE_EXPLOSION = 900;
	private static final long DELAI_MENU_VICTOIRE = 700;
	private static final Font POLICE_TITRE_MENU = new Font("SansSerif", Font.BOLD, 22);
	private static final Font POLICE_MENU = new Font("SansSerif", Font.PLAIN, 14);
	private static final Color FOND_MENU = new Color(30, 38, 50);
	private static final Color VOILE_SOMBRE = new Color(0, 0, 0, 170);

	private DemineurGame jeu;
	private Theme theme;
	private Flammes flammes;
	private final Confettis confettis = new Confettis();

	// Initialisation des Sprites (charges et redimensionnes une seule fois) :
	public static final Sprite spriteFlag = new Sprite("/images/flag.png",TAILLE_CASE - 6);
	public static final Sprite spriteGilet = new Sprite("/images/gilet.png",TAILLE_CASE - 4);
	public static final Sprite spriteMine = new Sprite("/images/mine.png",TAILLE_CASE - 6);

	public DemineurPainter(DemineurGame jeu) {
		this(jeu, Theme.pour(0));
	}

	public DemineurPainter(DemineurGame jeu, Theme theme) {
		this.jeu = jeu;
		this.theme = theme;
		this.flammes = theme.flammes ? new Flammes() : null;
	}

	@Override
	public void draw(BufferedImage im) {
		if (flammes != null) flammes.avancer();
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setFont(new Font("SansSerif", Font.BOLD, 16));

		// Fond de l'en-tête
		crayon.setColor(theme.entete);
		crayon.fillRect(0, 0, getWidth(), HAUTEUR_HEADER);

		// Police
		crayon.setColor(Color.WHITE);
		crayon.setFont(new Font("SansSerif", Font.BOLD, 14));

		// Chronomètre à gauche
		String temps = jeu.getChronometre().getTempsFormate();
		crayon.drawString("Temps : " + temps, 10, 23);

		// Compteur de drapeaux à droite
		String drapeaux = "Drapeaux : " + jeu.getNbDrapeaux()
				+ "/" + jeu.getNbMines();

		int largeurTexte = crayon.getFontMetrics().stringWidth(drapeaux);

		crayon.drawString(drapeaux, getWidth() - largeurTexte - 10, 23);

		for (int l = 0; l < jeu.getLignes(); l++) {
			for (int c = 0; c < jeu.getColonnes(); c++) {

				int x = c * TAILLE_CASE;
				int y = HAUTEUR_HEADER + l * TAILLE_CASE;
				Case case_ = jeu.getCase(l, c);
				if (case_.getDecouvert()) {int voisines = 0;
					if (case_ instanceof CaseLibre) {
						voisines = ((CaseLibre) case_).getNMines();
					}
					float t = Math.min(voisines, 4) / 4f;

					crayon.setColor(theme.bordure);
					crayon.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
					crayon.setColor(melanger(theme.videBase, theme.dangerBase, t));
					crayon.fillRect(x + 2, y + 2, TAILLE_CASE - 4, TAILLE_CASE - 4);
					crayon.setColor(melanger(theme.videClair, theme.dangerClair, t));
					crayon.fillRect(x + 2, y + 2, TAILLE_CASE - 4, 2);
					crayon.fillRect(x + 2, y + 2, 2, TAILLE_CASE - 4);
					crayon.setColor(melanger(theme.videSombre, theme.dangerSombre, t));
					crayon.fillRect(x + 2, y + TAILLE_CASE - 4, TAILLE_CASE - 4, 2);
					crayon.fillRect(x + TAILLE_CASE - 4, y + 2, 2, TAILLE_CASE - 4);
				} else {
					crayon.setColor(theme.bordure);
					crayon.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
					crayon.setColor(theme.caseBase);
					crayon.fillRect(x + 2, y + 2, TAILLE_CASE - 4, TAILLE_CASE - 4);
					crayon.setColor(theme.caseClair);
					crayon.fillRect(x + 2, y + 2, TAILLE_CASE - 4, 2);
					crayon.fillRect(x + 2, y + 2, 2, TAILLE_CASE - 4);
					crayon.setColor(theme.caseSombre);
					crayon.fillRect(x + 2, y + TAILLE_CASE - 4, TAILLE_CASE - 4, 2);
					crayon.fillRect(x + TAILLE_CASE - 4, y + 2, 2, TAILLE_CASE - 4);
				}
				crayon.setColor(case_.getDecouvert() ? new Color(225, 232, 240) : theme.bordure);
				String texte = "";

				if (case_.getDecouvert()) {
					   if (case_ instanceof CaseMinee) {
       						if (spriteMine.getImage() != null) {
           						crayon.drawImage(spriteMine.getImage(), x + 3, y + 3, null);
       						} else {
           						texte = case_.getSprite();
      			 }
   
					} else if (case_ instanceof CaseLibre) {
						int n = ((CaseLibre) case_).getNMines();
						if (n > 0) texte = String.valueOf(n);
					}
				} else if (case_.getDrapeau()) {
					if (spriteFlag.getImage() != null) {
						crayon.drawImage(spriteFlag.getImage(), x + 3, y + 3, null);
					} else {
						texte = "F";
					}
				}
				crayon.drawString(texte, x + 10, y + 21);
				crayon.setColor(theme.entete);
				crayon.drawRect(x, y, TAILLE_CASE, TAILLE_CASE);
			}
		}

	
		// Curseur dessiné en dernier : il passe par-dessus les drapeaux
		int xc = jeu.getCurseurColonne() * TAILLE_CASE;
		int yc = HAUTEUR_HEADER + jeu.getCurseurLigne() * TAILLE_CASE;
		if (spriteGilet.getImage() != null) {
		crayon.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
		crayon.drawImage(spriteGilet.getImage(), xc + 2, yc + 2, null);
		crayon.setComposite(AlphaComposite.SrcOver);
		} else {
			crayon.setColor(Color.YELLOW);
			crayon.drawRect(xc + 1, yc + 1, TAILLE_CASE - 2, TAILLE_CASE - 2);
		}

		// Fin de partie : explosion puis menu (defaite) ou menu (victoire)
		if (jeu.getIsOver()) {
			dessinerDefaite(crayon);
		}
		if (jeu.getIsWon()) {
			dessinerVictoire(crayon);
		}

		crayon.dispose();
	}

	/** Explosion qui part de la mine touchee, puis menu "Partie perdue". */
	private void dessinerDefaite(Graphics2D g) {
		long ecoule = System.currentTimeMillis() - jeu.getTempsDefaite();
		dessinerExplosion(g, ecoule);
		if (ecoule > DUREE_EXPLOSION) {
			dessinerMenuFin(g, "BOOM !", new Color(255, 140, 0), new Color(215, 85, 75),
					"Partie perdue", "R : rejouer", "Échap : menu");
		}
	}

	private void dessinerVictoire(Graphics2D g) {
		long ecoule = System.currentTimeMillis() - jeu.getTempsVictoire();
		confettis.dessiner(g, ecoule, getWidth(), getHeight());
		if (ecoule > DELAI_MENU_VICTOIRE) {
			dessinerMenuFin(g, "VICTOIRE !", new Color(255, 215, 80), new Color(240, 200, 60),
					"Temps : " + jeu.getChronometre().getTempsFormate(), "R : rejouer", "Échap : menu");
		}
	}

	// AJOUT : explosion qui part de la case piégée
	private void dessinerExplosion(Graphics2D g, long ecoule) {
		float p = Math.min(1f, ecoule / (float) DUREE_EXPLOSION);
		int cx = jeu.getColonneExplosion() * TAILLE_CASE + TAILLE_CASE / 2;
		int cy = HAUTEUR_HEADER + jeu.getLigneExplosion() * TAILLE_CASE + TAILLE_CASE / 2;
		int rMax = Math.max(getWidth(), getHeight());
		int alpha = (int) (200 * (1 - p * 0.6f));

		Color[] couleurs = {
			new Color(200, 40, 20, alpha),
			new Color(255, 140, 0, alpha),
			new Color(255, 230, 80, alpha),
			new Color(255, 255, 255, alpha)
		};
		float[] echelles = {1.0f, 0.7f, 0.45f, 0.2f};

		for (int i = 0; i < couleurs.length; i++) {
			int r = (int) (rMax * p * echelles[i]);
			g.setColor(couleurs[i]);
			g.fillOval(cx - r, cy - r, 2 * r, 2 * r);
		}

		// Étincelles
		g.setColor(new Color(255, 220, 100, alpha));
		for (int i = 0; i < 12; i++) {
			double angle = i * Math.PI / 6;
			int r1 = (int) (rMax * p * 0.5);
			int r2 = (int) (rMax * p * 0.9);
			g.drawLine(cx + (int) (Math.cos(angle) * r1), cy + (int) (Math.sin(angle) * r1),
					cx + (int) (Math.cos(angle) * r2), cy + (int) (Math.sin(angle) * r2));
		}
	}

	/** Assombrit le jeu et affiche une fenetre avec un titre et quelques lignes de texte. */
	private void dessinerMenuFin(Graphics2D g, String titre, Color couleurTitre, Color couleurCadre, String... lignes) {
		g.setColor(VOILE_SOMBRE);
		g.fillRect(0, 0, getWidth(), getHeight());

		int largeur = Math.min(getWidth() - 20, 260);
		int hauteur = 70 + 25 * lignes.length;
		int x = (getWidth() - largeur) / 2;
		int y = (getHeight() - hauteur) / 2;

		g.setColor(FOND_MENU);
		g.fillRoundRect(x, y, largeur, hauteur, 16, 16);
		g.setColor(couleurCadre);
		g.drawRoundRect(x, y, largeur, hauteur, 16, 16);

		g.setFont(POLICE_TITRE_MENU);
		g.setColor(couleurTitre);
		texteCentre(g, titre, y + 40);

		g.setFont(POLICE_MENU);
		g.setColor(Color.WHITE);
		for (int i = 0; i < lignes.length; i++) {
			texteCentre(g, lignes[i], y + 68 + 25 * i);
		}
	}

	// AJOUT
	private void texteCentre(Graphics2D g, String texte, int y) {
		int largeur = g.getFontMetrics().stringWidth(texte);
		g.drawString(texte, (getWidth() - largeur) / 2, y);
	}

	@Override
	public void dessinerFond(Graphics2D g, int largeurEcran, int hauteurEcran, int xJeu, int largeurJeu) {
		if (flammes != null) flammes.dessiner(g, largeurEcran, hauteurEcran, xJeu, largeurJeu);
	}

	private Color melanger(Color a, Color b, float t) {
		int r = (int) (a.getRed() + (b.getRed() - a.getRed()) * t);
		int g = (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t);
		int bl = (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t);
		return new Color(r, g, bl);
	}
	@Override
	public int getWidth() {
		return jeu.getColonnes() * TAILLE_CASE;
	}

	@Override
	public int getHeight() {
		return jeu.getLignes() * TAILLE_CASE + HAUTEUR_HEADER;
	}

}