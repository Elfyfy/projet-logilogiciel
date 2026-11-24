package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;


public class Confettis {

	private static final Color[] PALETTE = {
		new Color(255, 200, 40), new Color(255, 120, 30), new Color(80, 200, 120),
		new Color(90, 170, 255), new Color(235, 90, 150), Color.WHITE
	};

	private final int nb;
	private final float[] x;         // position horizontale (0..1 de la largeur)
	private final float[] vitesse;   // vitesse de chute (px/s)
	private final float[] decalage;  // départ décalé (0..1 de la hauteur)
	private final float[] phase;     // balancement / rotation
	private final int[] taille;
	private final Color[] couleur;

	public Confettis() {
		this(70);
	}

	public Confettis(int nb) {
		this.nb = nb;
		this.x = new float[nb];
		this.vitesse = new float[nb];
		this.decalage = new float[nb];
		this.phase = new float[nb];
		this.taille = new int[nb];
		this.couleur = new Color[nb];

		Random alea = new Random(42);
		for (int i = 0; i < nb; i++) {
			x[i] = alea.nextFloat();
			vitesse[i] = 70 + alea.nextFloat() * 110;
			decalage[i] = alea.nextFloat();
			phase[i] = alea.nextFloat() * 6.28f;
			taille[i] = 4 + alea.nextInt(4);
			couleur[i] = PALETTE[alea.nextInt(PALETTE.length)];
		}
	}

	/**
	 * Dessine les confettis, qui tombent en boucle.
	 *
	 * @param g       crayon
	 * @param ecoule  temps écoulé (ms) depuis le début de l'animation
	 * @param largeur largeur de la zone de jeu
	 * @param hauteur hauteur de la zone de jeu
	 */
	public void dessiner(Graphics2D g, long ecoule, int largeur, int hauteur) {
		float s = ecoule / 1000f;
		for (int i = 0; i < nb; i++) {
			float d = s * vitesse[i] - decalage[i] * hauteur;
			if (d < 0) continue; // pas encore entré dans l'écran
			float py = (d % (hauteur + 20)) - 10;
			float px = x[i] * largeur + (float) Math.sin(s * 2.5 + phase[i]) * 12;

			Graphics2D c = (Graphics2D) g.create();
			c.translate(px, py);
			c.rotate(s * 4 + phase[i]);
			c.setColor(couleur[i]);
			c.fillRect(-taille[i] / 2, -taille[i] / 4, taille[i], Math.max(2, taille[i] / 2));
			c.dispose();
		}
	}
}