package start;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MenuDifficulte extends JDialog {

	private static final int LARGEUR = 750;
	private static final int HAUTEUR = 450;
	private static final int TAILLE_CASE = 30;

	private static final String[] NOMS = {"FACILE", "MOYEN", "DIFFICILE"};
	private static final int[] MINES = {10, 40, 75};
	private static final Color[] COULEURS = {
		new Color(86, 168, 120), new Color(206, 156, 64), new Color(196, 66, 60)
	};

	private int choix = 0;
	private int resultat = -1;
	private boolean clignote = true;
	private BufferedImage fond = creerFond();
	private Timer minuteur;

	private MenuDifficulte() {
		super((Frame) null, "Démineur", true);

		JPanel panneau = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				dessiner((Graphics2D) g);
			}
		};
		panneau.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
		panneau.setFocusable(true);

		panneau.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
                    case 'z':
                        choix = (choix + 2) % 3;
                    break;
                    case 's':
                        choix = (choix + 1) % 3;
                    break;
                    case ' ':
                        resultat = choix;
                        dispose();
                    break;
                    case 27:
                        dispose();
                    break;
                }
				panneau.repaint();
			}
		});

		minuteur = new Timer(400, e -> {
			clignote = !clignote;
			panneau.repaint();
		});
		minuteur.start();

		setContentPane(panneau);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	public static int choisirNbMines() {
		MenuDifficulte menu = new MenuDifficulte();
		menu.setVisible(true);
		menu.minuteur.stop();
		return menu.resultat < 0 ? -1 : MINES[menu.resultat];
	}

	private Rectangle bouton(int i) {
		return new Rectangle((LARGEUR - 320) / 2, 190 + i * 80, 320, 60);
	}

	private BufferedImage creerFond() {
		BufferedImage im = new BufferedImage(LARGEUR, HAUTEUR, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = im.createGraphics();
		for (int y = 0; y < HAUTEUR; y += TAILLE_CASE) {
			for (int x = 0; x < LARGEUR; x += TAILLE_CASE) {
				g.setColor(new Color(30, 38, 50));
				g.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
				g.setColor(new Color(78, 122, 134));
				g.fillRect(x + 2, y + 2, TAILLE_CASE - 4, TAILLE_CASE - 4);
				g.setColor(new Color(108, 156, 168));
				g.fillRect(x + 2, y + 2, TAILLE_CASE - 4, 2);
				g.fillRect(x + 2, y + 2, 2, TAILLE_CASE - 4);
				g.setColor(new Color(54, 88, 98));
				g.fillRect(x + 2, y + TAILLE_CASE - 4, TAILLE_CASE - 4, 2);
				g.fillRect(x + TAILLE_CASE - 4, y + 2, 2, TAILLE_CASE - 4);
			}
		}
		g.setColor(new Color(14, 18, 26, 215));
		g.fillRect(0, 0, LARGEUR, HAUTEUR);
		g.dispose();
		return im;
	}

	private void texteCentre(Graphics2D g, String texte, int y) {
		FontMetrics fm = g.getFontMetrics();
		g.drawString(texte, (LARGEUR - fm.stringWidth(texte)) / 2, y);
	}

	private void dessiner(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(fond, 0, 0, null);

		g.setFont(new Font("SansSerif", Font.BOLD, 68));
		g.setColor(new Color(8, 12, 18));
		texteCentre(g, "DÉMINEUR", 109);
		g.setColor(new Color(225, 232, 240));
		texteCentre(g, "DÉMINEUR", 105);

		g.setColor(new Color(108, 156, 168));
		g.fillRect((LARGEUR - 360) / 2, 124, 360, 3);

		g.setFont(new Font("SansSerif", Font.BOLD, 16));
		g.setColor(new Color(150, 190, 200));
		texteCentre(g, "CHOISIS TA DIFFICULTÉ", 156);

		for (int i = 0; i < 3; i++) {
			Rectangle r = bouton(i);
			boolean actif = i == choix;
			Color base = actif ? COULEURS[i] : new Color(54, 88, 98);

			g.setColor(new Color(10, 14, 20));
			g.fillRect(r.x + 4, r.y + 4, r.width, r.height);
			g.setColor(new Color(30, 38, 50));
			g.fillRect(r.x, r.y, r.width, r.height);
			g.setColor(base);
			g.fillRect(r.x + 3, r.y + 3, r.width - 6, r.height - 6);
			g.setColor(base.brighter());
			g.fillRect(r.x + 3, r.y + 3, r.width - 6, 3);
			g.fillRect(r.x + 3, r.y + 3, 3, r.height - 6);
			g.setColor(base.darker());
			g.fillRect(r.x + 3, r.y + r.height - 6, r.width - 6, 3);
			g.fillRect(r.x + r.width - 6, r.y + 3, 3, r.height - 6);

			if (actif) {
				g.setColor(clignote ? Color.YELLOW : new Color(190, 160, 0));
				g.drawRect(r.x - 2, r.y - 2, r.width + 3, r.height + 3);
				int cy = r.y + r.height / 2;
				g.fillPolygon(new Polygon(
					new int[] {r.x - 30, r.x - 30, r.x - 14},
					new int[] {cy - 10, cy + 10, cy}, 3));
			}

			g.setColor(actif ? new Color(20, 26, 36) : new Color(225, 232, 240));
			g.setFont(new Font("SansSerif", Font.BOLD, 24));
			g.drawString(NOMS[i], r.x + 24, r.y + 39);
			g.setFont(new Font("SansSerif", Font.BOLD, 14));
			String mines = MINES[i] + " MINES";
			g.drawString(mines, r.x + r.width - 24 - g.getFontMetrics().stringWidth(mines), r.y + 36);
		}

		g.setFont(new Font("SansSerif", Font.PLAIN, 12));
		g.setColor(new Color(130, 160, 170));
		texteCentre(g, "Z / S : naviguer      ESPACE : valider      ECHAP : quitter", 440);
	}
}