package model;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

	// Palette plomberie
	private static final Color TUYAU_CLAIR = new Color(150, 160, 170);
	private static final Color TUYAU_SOMBRE = new Color(80, 88, 98);
	private static final Color EAU = new Color(70, 160, 230);
	private static final Color EAU_CLAIRE = new Color(190, 230, 255);

	// Palette du plombier
	private static final Color ROUGE_PLOMBIER = new Color(210, 40, 40);
	private static final Color ROUGE_SOMBRE = new Color(150, 20, 20);
	private static final Color PEAU = new Color(240, 190, 150);
	private static final Color SALOPETTE = new Color(40, 90, 200);
	private static final Color SALOPETTE_SOMBRE = new Color(25, 60, 140);
	private static final Color MOUSTACHE = new Color(70, 40, 20);

	private DemineurGame jeu;
	private Theme theme;
	private Flammes flammes;
	private final Confettis confettis = new Confettis();

	// MODIF : suivi des plots pour animer leur pose
	private static final long DUREE_POSE = 600;
	private boolean[][] etatPlots;
	private long[][] tempsPose;

		// Gardé car Mine.java et MineTest.java l'utilisent. Le jeu ne l'affiche plus.
	public static final Sprite spriteMine = new Sprite("/images/mine.png",TAILLE_CASE - 6);
	public static final Sprite spriteGilet = new Sprite("/images/gilet.png",TAILLE_CASE - 4);
	public static final Sprite spriteFlag = new Sprite("/images/flag.png",TAILLE_CASE - 6);
	

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
		long maintenant = System.currentTimeMillis(); // MODIF : horloge des animations
		majEtatPlots(maintenant);
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		crayon.setFont(new Font("SansSerif", Font.BOLD, 16));

		// Fond de l'en-tête
		crayon.setColor(theme.entete);
		crayon.fillRect(0, 0, getWidth(), HAUTEUR_HEADER);
		dessinerTuyauEntete(crayon, maintenant);

		// Police
		crayon.setColor(Color.WHITE);
		crayon.setFont(new Font("SansSerif", Font.BOLD, 14));

		// Chronomètre à gauche
		String temps = jeu.getChronometre().getTempsFormate();
		crayon.drawString("Temps : " + temps, 10, 23);

		// Compteur de plots à droite
		String drapeaux = "Plots : " + jeu.getNbDrapeaux()
				+ "/" + jeu.getNbMines();

		int largeurTexte = crayon.getFontMetrics().stringWidth(drapeaux);

		crayon.drawString(drapeaux, getWidth() - largeurTexte - 10, 23);

		for (int l = 0; l < jeu.getLignes(); l++) {
			for (int c = 0; c < jeu.getColonnes(); c++) {

				int x = c * TAILLE_CASE;
				int y = HAUTEUR_HEADER + l * TAILLE_CASE;
				Case case_ = jeu.getCase(l, c);
				int decalage = l * 7 + c * 13; // MODIF : desynchronise les animations des cases
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
					// Rivets aux quatre coins, comme une plaque metallique
					crayon.setColor(theme.caseSombre);
					crayon.fillOval(x + 5, y + 5, 3, 3);
					crayon.fillOval(x + TAILLE_CASE - 8, y + 5, 3, 3);
					crayon.fillOval(x + 5, y + TAILLE_CASE - 8, 3, 3);
					crayon.fillOval(x + TAILLE_CASE - 8, y + TAILLE_CASE - 8, 3, 3);
				}
				crayon.setColor(case_.getDecouvert() ? new Color(225, 232, 240) : theme.bordure);
				String texte = "";

				if (case_.getDecouvert()) {
					if (case_ instanceof CaseMinee) {
						dessinerFuite(crayon, x, y, maintenant, decalage); // MODIF : fuite animee
					} else if (case_ instanceof CaseLibre) {
						int n = ((CaseLibre) case_).getNMines();
						if (n > 0) texte = String.valueOf(n);
					}
				} else if (case_.getDrapeau()) {
					dessinerPlot(crayon, x, y, maintenant, decalage, tempsPose[l][c]); // MODIF : plot avec gyrophare + animation de pose
				}
				crayon.drawString(texte, x + 10, y + 21);
				crayon.setColor(theme.entete);
				crayon.drawRect(x, y, TAILLE_CASE, TAILLE_CASE);
			}
		}

	
		// MODIF : le curseur est maintenant un vrai plombier dessiné (aucune image).
		// Dessiné en dernier : il passe par-dessus les plots
		int xc = jeu.getCurseurColonne() * TAILLE_CASE;
		int yc = HAUTEUR_HEADER + jeu.getCurseurLigne() * TAILLE_CASE;
		int lc = jeu.getCurseurLigne();
		int cc = jeu.getCurseurColonne();
		Case caseCurseur = jeu.getCase(lc, cc);
		boolean surPlot = !caseCurseur.getDecouvert() && caseCurseur.getDrapeau();
		if (surPlot) {
			// MODIF : le plombier devient fantomatique et le plot est redessine PAR-DESSUS,
			// pour qu'on le voie meme quand le perso est dessus
			crayon.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
			dessinerPlombier(crayon, xc, yc, maintenant);
			crayon.setComposite(AlphaComposite.SrcOver);
			dessinerPlot(crayon, xc, yc, maintenant, lc * 7 + cc * 13, tempsPose[lc][cc]);
		} else {
			dessinerPlombier(crayon, xc, yc, maintenant);
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

	// MODIF : plombier dessine a la main (casquette, moustache, salopette, cle a molette)
	private void dessinerPlombier(Graphics2D g, int x, int y, long maintenant) {
		// Cadre de selection de la case qui pulse doucement
		int alphaCadre = 140 + (int) (80 * Math.sin(maintenant / 250.0));
		g.setColor(new Color(255, 220, 0, Math.max(60, Math.min(255, alphaCadre))));
		g.drawRect(x + 1, y + 1, TAILLE_CASE - 2, TAILLE_CASE - 2);

		// Petit rebond de respiration
		int yBase = y;
		y += (int) Math.round(Math.sin(maintenant / 180.0));

		// Ombre au sol
		g.setColor(new Color(0, 0, 0, 80));
		g.fillOval(x + 7, yBase + 26, 16, 4);

		// Bottes
		g.setColor(new Color(90, 55, 30));
		g.fillRoundRect(x + 8, y + 25, 6, 4, 2, 2);
		g.fillRoundRect(x + 16, y + 25, 6, 4, 2, 2);

		// Jambes de la salopette
		g.setColor(SALOPETTE);
		g.fillRect(x + 9, y + 19, 12, 7);
		g.setColor(SALOPETTE_SOMBRE);
		g.drawLine(x + 15, y + 22, x + 15, y + 25);

		// Manches de la chemise rouge
		g.setColor(ROUGE_PLOMBIER);
		g.fillRoundRect(x + 6, y + 15, 18, 6, 5, 5);

		// Plastron de la salopette + bretelles + boutons
		g.setColor(SALOPETTE);
		g.fillRect(x + 10, y + 17, 10, 4);
		g.fillRect(x + 10, y + 15, 2, 3);
		g.fillRect(x + 18, y + 15, 2, 3);
		g.setColor(new Color(255, 215, 80));
		g.fillRect(x + 11, y + 17, 2, 2);
		g.fillRect(x + 17, y + 17, 2, 2);

		// Cle a molette tenue dans la main droite
		java.awt.Stroke ancien = g.getStroke();
		g.setStroke(new BasicStroke(2f));
		g.setColor(new Color(190, 195, 205));
		g.drawLine(x + 24, y + 19, x + 27, y + 10);
		g.setStroke(ancien);
		g.fillOval(x + 24, y + 6, 6, 6);
		g.setColor(new Color(60, 66, 76));
		g.fillRect(x + 26, y + 6, 2, 3); // machoire de la cle

		// Gants blancs
		g.setColor(Color.WHITE);
		g.fillOval(x + 4, y + 17, 5, 5);
		g.fillOval(x + 22, y + 17, 5, 5);

		// Visage
		g.setColor(PEAU);
		g.fillOval(x + 9, y + 6, 12, 11);
		g.setColor(new Color(220, 150, 120));
		g.fillOval(x + 14, y + 11, 3, 3); // nez
		g.setColor(Color.BLACK);
		g.fillRect(x + 12, y + 10, 2, 2); // yeux
		g.fillRect(x + 17, y + 10, 2, 2);
		g.setColor(MOUSTACHE);
		g.fillRoundRect(x + 11, y + 14, 9, 3, 3, 3); // moustache

		// Casquette
		g.setColor(ROUGE_PLOMBIER);
		g.fillArc(x + 8, y + 1, 14, 12, 0, 180);
		g.setColor(ROUGE_SOMBRE);
		g.fillRect(x + 9, y + 6, 12, 2); // visiere
		g.setColor(Color.WHITE);
		g.fillOval(x + 13, y + 3, 5, 5);
		g.setColor(ROUGE_PLOMBIER);
		g.fillOval(x + 14, y + 4, 3, 3);
	}

	// MODIF : en-tete en forme de tuyau, avec de l'eau qui coule (lueurs qui defilent)
	private void dessinerTuyauEntete(Graphics2D g, long maintenant) {
		int w = getWidth();
		g.setColor(TUYAU_CLAIR);
		g.fillRect(0, 2, w, 3);
		g.setColor(TUYAU_SOMBRE);
		g.fillRect(0, HAUTEUR_HEADER - 5, w, 3);

		// Lueurs bleues qui avancent de gauche a droite
		int decal = (int) ((maintenant / 25) % 40);
		g.setColor(new Color(EAU_CLAIRE.getRed(), EAU_CLAIRE.getGreen(), EAU_CLAIRE.getBlue(), 150));
		for (int i = -40 + decal; i < w; i += 40) {
			g.fillRect(i, 3, 14, 1);
		}
		g.setColor(new Color(EAU.getRed(), EAU.getGreen(), EAU.getBlue(), 120));
		for (int i = -40 + decal + 20; i < w; i += 40) {
			g.fillRect(i, HAUTEUR_HEADER - 4, 10, 1);
		}

		// Brides aux extremites
		g.setColor(TUYAU_CLAIR);
		g.fillRect(0, 0, 6, HAUTEUR_HEADER);
		g.fillRect(w - 6, 0, 6, HAUTEUR_HEADER);
		g.setColor(TUYAU_SOMBRE);
		g.drawRect(0, 0, 6, HAUTEUR_HEADER - 1);
		g.drawRect(w - 7, 0, 6, HAUTEUR_HEADER - 1);
	}

	// MODIF : plot de chantier avec un gyrophare qui clignote
	// MODIF : detecte les plots qui viennent d'etre poses (pour l'animation)
	private void majEtatPlots(long maintenant) {
		int nl = jeu.getLignes();
		int nc = jeu.getColonnes();
		boolean init = etatPlots == null || etatPlots.length != nl || etatPlots[0].length != nc;
		if (init) {
			etatPlots = new boolean[nl][nc];
			tempsPose = new long[nl][nc];
		}
		for (int l = 0; l < nl; l++) {
			for (int c = 0; c < nc; c++) {
				Case k = jeu.getCase(l, c);
				boolean pose = !k.getDecouvert() && k.getDrapeau();
				if (pose && !etatPlots[l][c] && !init) tempsPose[l][c] = maintenant;
				if (!pose) tempsPose[l][c] = 0;
				etatPlots[l][c] = pose;
			}
		}
	}

	// MODIF : le plot tombe du ciel, rebondit, projette une onde et des etincelles, gyrophare allume
	private void dessinerPlot(Graphics2D g, int x, int y, long maintenant, int decalage, long tPose) {
		long ecoule = tPose > 0 ? maintenant - tPose : DUREE_POSE;
		boolean anime = ecoule < DUREE_POSE;
		float p = anime ? ecoule / (float) DUREE_POSE : 1f;

		int dy = 0;
		if (anime) {
			if (p < 0.55f) {
				float q = p / 0.55f;
				dy = -(int) ((1 - q * q) * 28); // chute acceleree
			} else {
				float q = (p - 0.55f) / 0.45f;
				dy = -(int) (Math.sin(q * Math.PI) * 5); // petit rebond
			}
			// Ombre au sol : grandit quand le plot approche
			int w = 20 + dy / 3;
			g.setColor(new Color(0, 0, 0, 100));
			g.fillOval(x + 15 - w / 2, y + 23, w, 4);
		}

		g.translate(0, dy);
		dessinerPlotCorps(g, x, y, maintenant, decalage, anime);
		g.translate(0, -dy);

		// Impact : onde jaune + etincelles
		if (anime && p >= 0.55f) {
			float q = (p - 0.55f) / 0.45f;
			int r = 4 + (int) (q * 10);
			int alpha = Math.max(0, (int) (220 * (1 - q)));
			java.awt.Stroke ancien = g.getStroke();
			g.setStroke(new BasicStroke(2f));
			g.setColor(new Color(255, 230, 80, alpha));
			g.drawOval(x + 15 - r, y + 24 - r / 3, 2 * r, 2 * r / 3);
			g.setStroke(ancien);
			for (int k = 0; k < 4; k++) {
				int dx = (k < 2 ? -1 : 1) * (2 + (int) (q * (6 + k * 2)));
				int dyy = -(int) (Math.sin(q * Math.PI) * (5 + k * 2));
				g.setColor(new Color(255, 220, 90, alpha));
				g.fillRect(x + 15 + dx, y + 24 + dyy, 2, 2);
			}
		}
	}

	private void dessinerPlotCorps(Graphics2D g, int x, int y, long maintenant, int decalage, boolean forceAllume) {
		g.setColor(new Color(60, 60, 60));
		g.fillRect(x + 5, y + 22, 20, 4); // base
		g.setColor(new Color(255, 120, 0));
		g.fillPolygon(new int[] {x + 9, x + 21, x + 17, x + 13},
				new int[] {y + 22, y + 22, y + 8, y + 8}, 4); // cone
		g.setColor(Color.WHITE);
		g.fillPolygon(new int[] {x + 11, x + 19, x + 18, x + 12},
				new int[] {y + 17, y + 17, y + 13, y + 13}, 4); // bande blanche

		// Gyrophare : clignote toutes les 400 ms
		boolean allume = forceAllume || ((maintenant / 400) + decalage) % 2 == 0;
		if (allume) {
			g.setColor(new Color(255, 160, 0, 90));
			g.fillOval(x + 8, y + 1, 14, 12); // halo
			g.setColor(new Color(255, 230, 80));
		} else {
			g.setColor(new Color(140, 90, 20));
		}
		g.fillOval(x + 12, y + 3, 6, 6);
	}

	// MODIF : fuite = tuyau fissure, goutte qui tombe et flaque qui pulse
	private void dessinerFuite(Graphics2D g, int x, int y, long maintenant, int decalage) {
		// Flaque en bas, sa largeur varie doucement
		double pulse = Math.sin((maintenant + decalage * 97L) / 250.0);
		int largeurFlaque = (int) (16 + 3 * pulse);
		g.setColor(new Color(EAU.getRed(), EAU.getGreen(), EAU.getBlue(), 170));
		g.fillOval(x + TAILLE_CASE / 2 - largeurFlaque / 2, y + 22, largeurFlaque, 5);

		// Tuyau en haut de la case
		g.setColor(TUYAU_SOMBRE);
		g.fillRect(x + 3, y + 4, TAILLE_CASE - 6, 6);
		g.setColor(TUYAU_CLAIR);
		g.fillRect(x + 3, y + 4, TAILLE_CASE - 6, 2);
		g.setColor(Color.BLACK);
		g.drawLine(x + 14, y + 4, x + 16, y + 9); // fissure
		g.drawLine(x + 16, y + 9, x + 15, y + 10);

		// Goutte qui tombe de la fissure vers la flaque (cycle de 900 ms)
		float p = ((maintenant + decalage * 137L) % 900) / 900f;
		int yGoutte = y + 11 + (int) (p * 11);
		int taille = 5 - (int) (p * 2);
		g.setColor(EAU);
		g.fillOval(x + 15 - taille / 2, yGoutte, taille, taille + 2);
		g.setColor(EAU_CLAIRE);
		g.fillOval(x + 14, yGoutte + 1, 1, 2); // reflet

		// Fil d'eau entre la fissure et la goutte
		g.setColor(new Color(EAU.getRed(), EAU.getGreen(), EAU.getBlue(), 110));
		g.drawLine(x + 15, y + 10, x + 15, yGoutte);
	}

	/** Jet d'eau qui part de la fuite touchee, puis menu "Partie perdue". */
	private void dessinerDefaite(Graphics2D g) {
		long ecoule = System.currentTimeMillis() - jeu.getTempsDefaite();
		dessinerExplosion(g, ecoule);
		if (ecoule > DUREE_EXPLOSION) {
			dessinerMenuFin(g, "INONDATION !", new Color(110, 190, 255), new Color(70, 160, 230),
					"Partie perdue", "R : rejouer", "Échap : menu");
		}
	}

	private void dessinerVictoire(Graphics2D g) {
    long ecoule = System.currentTimeMillis() - jeu.getTempsVictoire();
    confettis.dessiner(g, ecoule, getWidth(), getHeight());
    if (ecoule > DELAI_MENU_VICTOIRE) {
        String temps = "Temps : " + jeu.getChronometre().getTempsFormate();
        if (jeu.getNiveauSeul()) {
            dessinerMenuFin(g, "TOUT EST RÉPARÉ !", new Color(255, 215, 80), new Color(240, 200, 60),
                    temps, "R : rejouer", "Échap : menu");
        } else if (jeu.getNiveauSuivantPossible()) {
            dessinerMenuFin(g, "TOUT EST RÉPARÉ !", new Color(255, 215, 80), new Color(240, 200, 60),
                    temps, "Entrée : niveau suivant", "R : rejouer", "Échap : menu");
        } else {
            dessinerMenuFin(g, "TOUS LES NIVEAUX !", new Color(255, 215, 80), new Color(240, 200, 60),
                    temps, "R : rejouer", "Échap : menu");
        }
    }
}

	// Jet d'eau qui part de la case piégée (memes durees et memes tailles que l'explosion)
	private void dessinerExplosion(Graphics2D g, long ecoule) {
		float p = Math.min(1f, ecoule / (float) DUREE_EXPLOSION);
		int cx = jeu.getColonneExplosion() * TAILLE_CASE + TAILLE_CASE / 2;
		int cy = HAUTEUR_HEADER + jeu.getLigneExplosion() * TAILLE_CASE + TAILLE_CASE / 2;
		int rMax = Math.max(getWidth(), getHeight());
		int alpha = (int) (200 * (1 - p * 0.6f));

		Color[] couleurs = {
			new Color(30, 90, 170, alpha),
			new Color(60, 150, 230, alpha),
			new Color(140, 210, 255, alpha),
			new Color(255, 255, 255, alpha)
		};
		float[] echelles = {1.0f, 0.7f, 0.45f, 0.2f};

		for (int i = 0; i < couleurs.length; i++) {
			int r = (int) (rMax * p * echelles[i]);
			g.setColor(couleurs[i]);
			g.fillOval(cx - r, cy - r, 2 * r, 2 * r);
		}

		// Gouttes projetees
		g.setColor(new Color(200, 235, 255, alpha));
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

	private void texteCentre(Graphics2D g, String texte, int y) {
		int largeur = g.getFontMetrics().stringWidth(texte);
		g.drawString(texte, (getWidth() - largeur) / 2, y);
	}

	@Override
	public void dessinerFond(Graphics2D g, int largeurEcran, int hauteurEcran, int xJeu, int largeurJeu) {
		// MODIF : decor de salle des machines (mur de briques, tuyaux, vannes, manometre)
		dessinerDecor(g, largeurEcran, hauteurEcran, xJeu, largeurJeu);
		if (flammes != null) flammes.dessiner(g, largeurEcran, hauteurEcran, xJeu, largeurJeu);
	}

	// MODIF : decor de fond dans le theme plomberie
	private void dessinerDecor(Graphics2D g, int W, int H, int xJeu, int largeurJeu) {
		long t = System.currentTimeMillis();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Mur sombre degrade
		g.setPaint(new GradientPaint(0, 0, new Color(26, 34, 46), 0, H, new Color(12, 18, 28)));
		g.fillRect(0, 0, W, H);

		// Briques
		int bh = 20, bw = 42;
		for (int row = 0; row * bh < H; row++) {
			int offset = (row % 2 == 0) ? 0 : bw / 2;
			int col = 0;
			for (int x = -offset; x < W; x += bw, col++) {
				int v = (row * 31 + col * 17) % 7;
				g.setColor(new Color(32 + v, 42 + v, 56 + v));
				g.fillRect(x + 1, row * bh + 1, bw - 2, bh - 2);
			}
		}

		// Tuyaux horizontaux
		int nbH = 3;
		int th = 14;
		int[] yTuyaux = new int[nbH];
		for (int i = 0; i < nbH; i++) {
			yTuyaux[i] = H * (i + 1) / (nbH + 1) - th / 2;
		}

		// Tuyaux verticaux dans les marges laterales (si assez de place)
		int margeG = xJeu;
		int margeD = W - (xJeu + largeurJeu);
		int[] xTuyaux = new int[2];
		int nbV = 0;
		if (margeG >= 30) xTuyaux[nbV++] = margeG / 2 - 8;
		if (margeD >= 30) xTuyaux[nbV++] = xJeu + largeurJeu + margeD / 2 - 8;

		for (int i = 0; i < nbV; i++) {
			dessinerTuyauVertical(g, xTuyaux[i], H, 16, t, i);
		}
		for (int i = 0; i < nbH; i++) {
			dessinerTuyauHorizontal(g, 0, yTuyaux[i], W, th, t, i);
		}

		// Vannes rouges aux croisements
		for (int i = 0; i < nbV; i++) {
			for (int j = 0; j < nbH; j++) {
				if (j % 2 == 0) {
					dessinerVanne(g, xTuyaux[i] + 8, yTuyaux[j] + th / 2, t, i + j);
				}
			}
		}

		// Manometre dans la marge gauche (sinon en haut a gauche)
		int gx = margeG >= 50 ? margeG / 2 : 30;
		int gy = H - 50;
		dessinerManometre(g, gx, gy, t);

		// Bulles qui montent dans les marges
		g.setColor(new Color(EAU_CLAIRE.getRed(), EAU_CLAIRE.getGreen(), EAU_CLAIRE.getBlue(), 90));
		for (int i = 0; i < 14; i++) {
			int bx = (i * 83 + 20) % Math.max(1, W);
			if (bx > xJeu - 4 && bx < xJeu + largeurJeu + 4) continue;
			float p = ((t / 20 + i * 61) % 400) / 400f;
			int by = H - (int) (p * H);
			int r = 3 + (i % 3);
			g.drawOval(bx + (int) (Math.sin((t / 400.0) + i) * 4), by, r, r);
		}
	}

	private void dessinerTuyauHorizontal(Graphics2D g, int x, int y, int w, int h, long t, int idx) {
		g.setColor(TUYAU_SOMBRE);
		g.fillRect(x, y, w, h);
		g.setColor(TUYAU_CLAIR);
		g.fillRect(x, y + 2, w, 3);
		g.setColor(new Color(30, 36, 44));
		g.fillRect(x, y + h - 2, w, 2);

		// Eau qui circule (sens alterne selon le tuyau)
		int decal = (int) ((t / 20) % 70);
		if (idx % 2 == 1) decal = 70 - decal;
		g.setColor(new Color(EAU.getRed(), EAU.getGreen(), EAU.getBlue(), 150));
		for (int i = -70 + decal; i < w; i += 70) {
			g.fillRoundRect(i, y + h / 2 - 1, 28, 4, 4, 4);
		}

		// Brides + gouttes
		for (int bx = 60 + idx * 25; bx < w; bx += 180) {
			g.setColor(TUYAU_CLAIR);
			g.fillRect(bx, y - 3, 8, h + 6);
			g.setColor(TUYAU_SOMBRE);
			g.drawRect(bx, y - 3, 8, h + 6);
			g.fillOval(bx + 2, y - 1, 3, 3); // boulon

			float p = ((t + idx * 330L + bx * 7L) % 1500) / 1500f;
			int yg = y + h + 3 + (int) (p * 40);
			g.setColor(new Color(EAU.getRed(), EAU.getGreen(), EAU.getBlue(), (int) (200 * (1 - p))));
			g.fillOval(bx + 2, yg, 4, 6);
		}
	}

	private void dessinerTuyauVertical(Graphics2D g, int x, int h, int w, long t, int idx) {
		g.setColor(TUYAU_SOMBRE);
		g.fillRect(x, 0, w, h);
		g.setColor(TUYAU_CLAIR);
		g.fillRect(x + 2, 0, 3, h);
		g.setColor(new Color(30, 36, 44));
		g.fillRect(x + w - 2, 0, 2, h);

		int decal = (int) ((t / 20) % 70);
		g.setColor(new Color(EAU.getRed(), EAU.getGreen(), EAU.getBlue(), 150));
		for (int i = -70 + decal; i < h; i += 70) {
			g.fillRoundRect(x + w / 2 - 1, i, 4, 28, 4, 4);
		}

		for (int by = 40 + idx * 30; by < h; by += 150) {
			g.setColor(TUYAU_CLAIR);
			g.fillRect(x - 3, by, w + 6, 8);
			g.setColor(TUYAU_SOMBRE);
			g.drawRect(x - 3, by, w + 6, 8);
		}
	}

	private void dessinerVanne(Graphics2D g, int cx, int cy, long t, int idx) {
		g.setColor(new Color(60, 66, 76));
		g.fillOval(cx - 9, cy - 9, 18, 18);
		g.setColor(ROUGE_PLOMBIER);
		java.awt.Stroke ancien = g.getStroke();
		g.setStroke(new BasicStroke(2f));
		g.drawOval(cx - 8, cy - 8, 16, 16);
		double a = t / 1500.0 * (idx % 2 == 0 ? 1 : -1);
		for (int k = 0; k < 3; k++) {
			double ang = a + k * Math.PI / 3;
			g.drawLine(cx - (int) (Math.cos(ang) * 8), cy - (int) (Math.sin(ang) * 8),
					cx + (int) (Math.cos(ang) * 8), cy + (int) (Math.sin(ang) * 8));
		}
		g.setStroke(ancien);
		g.setColor(TUYAU_CLAIR);
		g.fillOval(cx - 2, cy - 2, 4, 4);
	}

	private void dessinerManometre(Graphics2D g, int cx, int cy, long t) {
		g.setColor(TUYAU_SOMBRE);
		g.fillOval(cx - 17, cy - 17, 34, 34);
		g.setColor(new Color(230, 235, 240));
		g.fillOval(cx - 14, cy - 14, 28, 28);

		// Zone rouge
		g.setColor(new Color(210, 50, 50));
		g.fillArc(cx - 12, cy - 12, 24, 24, 0, 40);
		g.setColor(new Color(60, 160, 80));
		g.fillArc(cx - 12, cy - 12, 24, 24, 90, 50);

		// Aiguille qui tremble
		double ang = Math.toRadians(200 - (50 + 40 * Math.sin(t / 700.0) + 3 * Math.sin(t / 60.0)));
		g.setColor(new Color(30, 30, 30));
		g.drawLine(cx, cy, cx + (int) (Math.cos(ang) * 11), cy - (int) (Math.sin(ang) * 11));
		g.fillOval(cx - 2, cy - 2, 4, 4);
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