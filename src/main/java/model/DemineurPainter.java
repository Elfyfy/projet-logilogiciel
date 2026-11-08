package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import engine.GamePainter;

public class DemineurPainter implements GamePainter {

	private static final int TAILLE_CASE = 50;

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

		for (int l = 0; l < jeu.getLignes(); l++) {
			for (int c = 0; c < jeu.getColonnes(); c++) {

				int x = c * TAILLE_CASE;
				int y = l * TAILLE_CASE;
				crayon.setColor(Color.LIGHT_GRAY);
				crayon.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
				crayon.setColor(Color.BLACK);
				String texte = "";
				Case case_ = jeu.getCase(l, c);

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
				}
				crayon.drawString(texte, x + 10, y + 21);
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

	@Override
	public int getWidth() {
		return jeu.getColonnes() * TAILLE_CASE;
	}

	@Override
	public int getHeight() {
		return jeu.getLignes() * TAILLE_CASE;
	}

}