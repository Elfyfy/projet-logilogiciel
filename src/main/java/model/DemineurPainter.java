package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.GamePainter;

public class DemineurPainter implements GamePainter {

	private static final int TAILLE_CASE = 30;

	private DemineurGame jeu;

	public DemineurPainter(DemineurGame jeu) {
		this.jeu = jeu;
	}

	@Override
	public void draw(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setFont(new Font("SansSerif", Font.BOLD, 16));

		for (int l = 0; l < jeu.getLignes(); l++) {
			for (int c = 0; c < jeu.getColonnes(); c++) {

				int x = c * TAILLE_CASE;
				int y = l * TAILLE_CASE;
				Case case_ = jeu.getCase(l, c);
				if (case_.getDecouvert()) {int voisines = 0;
					if (case_ instanceof CaseLibre) {
						voisines = ((CaseLibre) case_).getNMines();
					}
					float t = Math.min(voisines, 4) / 4f;

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
				}
				crayon.drawString(texte, x + 10, y + 21);
				crayon.setColor(new Color(20, 26, 36));
				crayon.drawRect(x, y, TAILLE_CASE, TAILLE_CASE);
				if (l == jeu.getCurseurLigne() && c == jeu.getCurseurColonne()) {
					crayon.setColor(Color.YELLOW);
					crayon.drawRect(x + 1, y + 1, TAILLE_CASE - 2, TAILLE_CASE - 2);
				}
			}
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
		return jeu.getLignes() * TAILLE_CASE;
	}

}