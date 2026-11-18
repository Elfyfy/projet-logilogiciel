package view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class MenuPrincipal extends JDialog {

	public static class Parametres {
		public final int niveau, lignes, colonnes, nbMines;

		private Parametres(int niveau, int lignes, int colonnes, int nbMines) {
			this.niveau = niveau;
			this.lignes = lignes;
			this.colonnes = colonnes;
			this.nbMines = nbMines;
		}
	}

	private static final String[] NOMS_TAILLE = {"PETITE", "MOYENNE", "GRANDE"};
	private static final int[][] TAILLES = {{10, 15}, {15, 25}, {20, 35}};
	private static final String[] NOMS_NIVEAU = {"FACILE", "MOYEN", "DIFFICILE"};
	private static final double[] TAUX = {0.05, 0.15, 0.20};

	// on retient les derniers choix du joueur
	private static int niveauMemo = 0;
	private static int tailleMemo = 1;

	private boolean ecranReglages = false; // false = JOUER/QUITTER, true = réglages
	private int ligne = 0;                 // bouton sélectionné
	private int niveau = niveauMemo;
	private int taille = tailleMemo;
	private boolean lancer = false;
	private Image fond;

	private MenuPrincipal() {
		super((Frame) null, "Démineur", true);

		URL url = MenuPrincipal.class.getResource("/images/menu_fond.png");
		if (url != null) fond = new ImageIcon(url).getImage();

		JPanel panneau = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				dessiner(g, getWidth(), getHeight());
			}
		};
		panneau.setFocusable(true);
		panneau.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				toucheAppuyee(e.getKeyCode());
				panneau.repaint();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				panneau.requestFocusInWindow();
			}
		});

		// plein écran : fenêtre sans bordure de la taille de l'écran
		setUndecorated(true);
		setContentPane(panneau);
		setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	}

	/** Affiche le menu. Renvoie les paramètres de la partie, ou null si on quitte. */
	public static Parametres afficher() {
		MenuPrincipal menu = new MenuPrincipal();
		menu.setVisible(true); // bloque jusqu'à dispose()

		if (!menu.lancer) return null;
		niveauMemo = menu.niveau;
		tailleMemo = menu.taille;
		int lignes = TAILLES[menu.taille][0];
		int colonnes = TAILLES[menu.taille][1];
		int nbMines = Math.max(1, (int) (lignes * colonnes * TAUX[menu.niveau]));
		return new Parametres(menu.niveau, lignes, colonnes, nbMines);
	}

	/** Le texte de chaque bouton, selon l'écran affiché. */
	private String[] boutons() {
		if (!ecranReglages) {
			return new String[] {"JOUER", "QUITTER"};
		}
		int l = TAILLES[taille][0];
		int c = TAILLES[taille][1];
		int mines = Math.max(1, (int) (l * c * TAUX[niveau]));
		return new String[] {
			"TAILLE :  < " + NOMS_TAILLE[taille] + " >  (" + l + " x " + c + ")",
			"DIFFICULTÉ :  < " + NOMS_NIVEAU[niveau] + " >  (" + mines + " mines)",
			"LANCER",
			"RETOUR"
		};
	}

	private void toucheAppuyee(int touche) {
		int n = boutons().length;

		if (touche == KeyEvent.VK_Z || touche == KeyEvent.VK_UP) {
			ligne = (ligne + n - 1) % n;
		} else if (touche == KeyEvent.VK_S || touche == KeyEvent.VK_DOWN) {
			ligne = (ligne + 1) % n;
		} else if (touche == KeyEvent.VK_Q || touche == KeyEvent.VK_LEFT) {
			changer(-1);
		} else if (touche == KeyEvent.VK_D || touche == KeyEvent.VK_RIGHT) {
			changer(1);
		} else if (touche == KeyEvent.VK_SPACE || touche == KeyEvent.VK_ENTER) {
			valider();
		} else if (touche == KeyEvent.VK_ESCAPE) {
			if (ecranReglages) {
				ecranReglages = false;
				ligne = 0;
			} else {
				dispose();
			}
		}
	}

	// change la taille ou la difficulté (uniquement sur l'écran des réglages)
	private void changer(int sens) {
		if (!ecranReglages) return;
		if (ligne == 0) taille = (taille + sens + 3) % 3;
		if (ligne == 1) niveau = (niveau + sens + 3) % 3;
	}

	private void valider() {
		if (!ecranReglages) {
			if (ligne == 0) {          // JOUER -> on passe aux réglages
				ecranReglages = true;
				ligne = 2;             // le curseur est sur LANCER
			} else {                   // QUITTER
				dispose();
			}
		} else if (ligne == 2) {       // LANCER
			lancer = true;
			dispose();
		} else if (ligne == 3) {       // RETOUR
			ecranReglages = false;
			ligne = 0;
		} else {
			changer(1);
		}
	}

	private void dessiner(Graphics g, int w, int h) {
		// fond : l'image étirée sur tout l'écran, puis un voile sombre
		if (fond != null) {
			g.drawImage(fond, 0, 0, w, h, null);
		}
		g.setColor(new Color(0, 0, 0, 140));
		g.fillRect(0, 0, w, h);

		// titre
		g.setFont(new Font("SansSerif", Font.BOLD, h / 10));
		g.setColor(Color.WHITE);
		texteCentre(g, "DÉMINEUR", w, h / 5);

		// boutons
		String[] textes = boutons();
		int largeur = w / 2;
		int hauteur = h / 12;
		g.setFont(new Font("SansSerif", Font.BOLD, h / 35));

		for (int i = 0; i < textes.length; i++) {
			int x = (w - largeur) / 2;
			int y = h * 2 / 5 + i * (hauteur + hauteur / 3);

			g.setColor(i == ligne ? Color.ORANGE : new Color(50, 55, 60));
			g.fillRect(x, y, largeur, hauteur);

			g.setColor(i == ligne ? Color.BLACK : Color.WHITE);
			texteCentre(g, textes[i], w, y + hauteur / 2 + h / 90);
		}
	}

	private void texteCentre(Graphics g, String texte, int w, int y) {
		g.drawString(texte, (w - g.getFontMetrics().stringWidth(texte)) / 2, y);
	}
}