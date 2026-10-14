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
				crayon.setColor(Color.LIGHT_GRAY);
				crayon.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
				crayon.setColor(Color.BLACK);
				String texte = "";

				if (jeu.getEtat(l, c) == DemineurGame.DRAPEAU) {
					texte = "F";
				} else if (jeu.estMine(l, c)) {
					texte = "*";
				} else if (jeu.getVoisines(l, c) > 0) {
					texte = String.valueOf(jeu.getVoisines(l, c));
				}
				crayon.drawString(texte, x + 10, y + 21);
				crayon.drawRect(x, y, TAILLE_CASE, TAILLE_CASE);
				if (l == jeu.getCurseurLigne() && c == jeu.getCurseurColonne()) {
					crayon.setColor(Color.YELLOW);
					crayon.drawRect(x + 1, y + 1, TAILLE_CASE - 2, TAILLE_CASE - 2);
				}
			}
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