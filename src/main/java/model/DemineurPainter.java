package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import engine.GamePainter;

public class DemineurPainter implements GamePainter {

	private static final int TAILLE_CASE = 30;
	private static final int HAUTEUR_HEADER = 35;

	private DemineurGame jeu;
	private Theme theme;
	private Flammes flammes;
	private BufferedImage imgDrapeau;
	private BufferedImage imgGilet;
	private BufferedImage imgMine;

	public DemineurPainter(DemineurGame jeu) {
		this(jeu, Theme.pour(0));
	}

	public DemineurPainter(DemineurGame jeu, Theme theme) {
		this.jeu = jeu;
		this.theme = theme;
		this.flammes = theme.flammes ? new Flammes() : null;
		imgDrapeau = redimensionner(chargerImage("/images/flag.png"), TAILLE_CASE - 6);
		imgGilet = redimensionner(chargerImage("/images/gilet.png"), TAILLE_CASE - 4);
		imgMine = redimensionner(chargerImage("/images/mine.png"), TAILLE_CASE - 6);
	}

	private BufferedImage redimensionner(BufferedImage source, int taille) {
		if (source == null) return null;
		BufferedImage res = new BufferedImage(taille, taille, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = res.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(source, 0, 0, taille, taille, null);
		g.dispose();
		return res;
	}

	private BufferedImage chargerImage(String chemin) {
		try {
			return ImageIO.read(getClass().getResource(chemin));
		} catch (Exception e) {
			System.err.println("Impossible de charger " + chemin);
			return null;
		}
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
       						if (imgMine != null) {
           						crayon.drawImage(imgMine, x + 3, y + 3, null);
       						} else {
           						texte = case_.getSprite();
      			 }
   
					} else if (case_ instanceof CaseLibre) {
						int n = ((CaseLibre) case_).getNMines();
						if (n > 0) texte = String.valueOf(n);
					}
				} else if (case_.getDrapeau()) {
					if (imgDrapeau != null) {
						crayon.drawImage(imgDrapeau, x + 3, y + 3, null);
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
		if (imgGilet != null) {
			crayon.drawImage(imgGilet, xc + 2, yc + 2, null);
		} else {
			// secours : l'ancien contour jaune
			crayon.setColor(Color.YELLOW);
			crayon.drawRect(xc + 1, yc + 1, TAILLE_CASE - 2, TAILLE_CASE - 2);
		}
		crayon.dispose();
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