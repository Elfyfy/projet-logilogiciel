package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import engine.GamePainter;

public class DemineurPainter implements GamePainter {

<<<<<<< HEAD
	private static final int TAILLE_CASE = 50;
=======
	private static final int TAILLE_CASE = 30;
	private static final int HAUTEUR_HEADER = 35;
>>>>>>> origin/main

	private DemineurGame jeu;
	private BufferedImage imgDrapeau;
	private BufferedImage imgGilet;

	public DemineurPainter(DemineurGame jeu) {
		this.jeu = jeu;
		imgDrapeau = chargerImage("/images/flag.png");
		imgGilet = chargerImage("/images/gilet.png");
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
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setFont(new Font("SansSerif", Font.BOLD, 16));

		// Fond de l'en-tête
		crayon.setColor(new Color(20, 26, 36));
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

<<<<<<< HEAD
				if (case_.getDrapeau()) {
					if (imgDrapeau != null) {
						crayon.drawImage(imgDrapeau, x + 3, y + 3,
								TAILLE_CASE - 6, TAILLE_CASE - 6, null);
					} else {
						texte = "F";
					}
				} else if (case_ instanceof CaseMinee) {
					texte = case_.getSprite();
				} else if (case_ instanceof CaseLibre) {
					int n = ((CaseLibre) case_).getNMines();
					if (n > 0) texte = String.valueOf(n);
=======
					crayon.setColor(new Color(30, 38, 50));
					crayon.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
					crayon.setColor(melanger(new Color(62, 76, 96), new Color(170, 40, 40), t));
					crayon.fillRect(x + 2, y + 2, TAILLE_CASE - 4, TAILLE_CASE - 4);
					crayon.setColor(melanger(new Color(82, 98, 120), new Color(215, 85, 75), t));
					crayon.fillRect(x + 2, y + 2, TAILLE_CASE - 4, 2);
					crayon.fillRect(x + 2, y + 2, 2, TAILLE_CASE - 4);
					crayon.setColor(melanger(new Color(46, 57, 74), new Color(110, 22, 22), t));
					crayon.fillRect(x + 2, y + TAILLE_CASE - 4, TAILLE_CASE - 4, 2);
					crayon.fillRect(x + TAILLE_CASE - 4, y + 2, 2, TAILLE_CASE - 4);
				} else {
					crayon.setColor(new Color(30, 38, 50));
					crayon.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
					crayon.setColor(new Color(78, 122, 134));
					crayon.fillRect(x + 2, y + 2, TAILLE_CASE - 4, TAILLE_CASE - 4);
					crayon.setColor(new Color(108, 156, 168));
					crayon.fillRect(x + 2, y + 2, TAILLE_CASE - 4, 2);
					crayon.fillRect(x + 2, y + 2, 2, TAILLE_CASE - 4);
					crayon.setColor(new Color(54, 88, 98));
					crayon.fillRect(x + 2, y + TAILLE_CASE - 4, TAILLE_CASE - 4, 2);
					crayon.fillRect(x + TAILLE_CASE - 4, y + 2, 2, TAILLE_CASE - 4);
				}
				crayon.setColor(case_.getDecouvert() ? new Color(225, 232, 240) : new Color(30, 38, 50));
				String texte = "";

								if (case_.getDecouvert()) {
					if (case_ instanceof CaseMinee) {
						texte = case_.getSprite();
					} else if (case_ instanceof CaseLibre) {
						int n = ((CaseLibre) case_).getNMines();
						if (n > 0) texte = String.valueOf(n);
					}
				} else if (case_.getDrapeau()) {
					texte = "F";
>>>>>>> origin/main
				}
				crayon.drawString(texte, x + 10, y + 21);
				crayon.setColor(new Color(20, 26, 36));
				crayon.drawRect(x, y, TAILLE_CASE, TAILLE_CASE);
			}
		}

		// Curseur dessiné en dernier : il passe par-dessus les drapeaux
		int xc = jeu.getCurseurColonne() * TAILLE_CASE;
		int yc = jeu.getCurseurLigne() * TAILLE_CASE;
		if (imgGilet != null) {
			crayon.drawImage(imgGilet, xc + 2, yc + 2,
					TAILLE_CASE - 4, TAILLE_CASE - 4, null);
		} else {
			// secours : l'ancien contour jaune
			crayon.setColor(Color.YELLOW);
			crayon.drawRect(xc + 1, yc + 1, TAILLE_CASE - 2, TAILLE_CASE - 2);
		}
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