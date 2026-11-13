package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

public class Flammes {

	private static final int TAILLE_PIXEL = 8;
	private static final int NIVEAUX = 32;
	private static final int[] PALETTE = creerPalette();

	private final Random alea = new Random();
	private int colonnes;
	private int lignes;
	private int[] feu = new int[0];
	private BufferedImage image;
	private int[] pixels;
	private int finGauche;
	private int debutDroite;

	private static int[] creerPalette() {
		Color[] cles = {
			new Color(60, 0, 0), new Color(190, 20, 0), new Color(240, 90, 0),
			new Color(255, 170, 20), new Color(255, 235, 120)
		};
		int[] palette = new int[NIVEAUX];
		for (int i = 4; i < NIVEAUX; i++) {
			float pos = (i - 4) / (float) (NIVEAUX - 5) * (cles.length - 1);
			int k = Math.min((int) pos, cles.length - 2);
			float t = pos - k;
			Color a = cles[k];
			Color b = cles[k + 1];
			int r = (int) (a.getRed() + (b.getRed() - a.getRed()) * t);
			int g = (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t);
			int bl = (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t);
			palette[i] = 0xFF000000 | (r << 16) | (g << 8) | bl;
		}
		return palette;
	}

	public synchronized void avancer() {
		if (feu.length == 0) return;
		for (int c = 0; c < colonnes; c++) {
			boolean source = c < finGauche || c >= debutDroite;
			feu[(lignes - 1) * colonnes + c] = source ? NIVEAUX - 1 - alea.nextInt(6) : 0;
		}
		for (int l = 1; l < lignes; l++) {
			for (int c = 0; c < colonnes; c++) {
				int v = feu[l * colonnes + c];
				int dc = c + alea.nextInt(3) - 1;
				if (dc < 0 || dc >= colonnes) continue;
				feu[(l - 1) * colonnes + dc] = v == 0 ? 0 : Math.max(0, v - alea.nextInt(2));
			}
		}
	}

	public synchronized void dessiner(Graphics2D g, int largeur, int hauteur, int xJeu, int largeurJeu) {
		int col = (largeur + TAILLE_PIXEL - 1) / TAILLE_PIXEL;
		int lig = (hauteur + TAILLE_PIXEL - 1) / TAILLE_PIXEL;
		if (col != colonnes || lig != lignes) {
			colonnes = col;
			lignes = lig;
			feu = new int[col * lig];
			image = new BufferedImage(col, lig, BufferedImage.TYPE_INT_ARGB);
			pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		}
		finGauche = xJeu / TAILLE_PIXEL;
		debutDroite = (xJeu + largeurJeu + TAILLE_PIXEL - 1) / TAILLE_PIXEL;
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = PALETTE[feu[i]];
		}
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.drawImage(image, 0, 0, col * TAILLE_PIXEL, lig * TAILLE_PIXEL, null);
	}
}