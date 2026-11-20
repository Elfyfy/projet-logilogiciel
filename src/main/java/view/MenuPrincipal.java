package view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import model.Commandes;

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

	// les trois écrans du menu
	private static final int ACCUEIL = 0;   // JOUER / PARAMÈTRES / QUITTER
	private static final int PARTIE = 1;    // taille et difficulté
	private static final int COMMANDES = 2; // choix des touches

	// on retient les derniers choix du joueur
	private static int niveauMemo = 0;
	private static int tailleMemo = 1;

	// Retourne les dimensions correspondant à une taille
	static int[] dimensionsPourTaille(int taille) {
		return TAILLES[taille].clone();
	}

	// Calcule le nombre de mines selon la taille et la difficulté
	static int calculerNbMines(int lignes, int colonnes, int niveau) {
		return Math.max(1, (int) (lignes * colonnes * TAUX[niveau]));
	}

	private int ecran = ACCUEIL;
	private int ligne = 0;                  // bouton sélectionné
	private int niveau = niveauMemo;
	private int taille = tailleMemo;
	private boolean lancer = false;
	private boolean attenteTouche = false;  // true quand on attend la nouvelle touche
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
				toucheAppuyee(e);
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
		int[] dimensions = dimensionsPourTaille(menu.taille);
		int lignes = dimensions[0];
		int colonnes = dimensions[1];
		int nbMines = calculerNbMines(lignes, colonnes, menu.niveau);
		return new Parametres(menu.niveau, lignes, colonnes, nbMines);
	}

	/** Le texte de chaque bouton, selon l'écran affiché. */
	private String[] boutons() {
		if (ecran == ACCUEIL) {
			return new String[] {"JOUER", "PARAMÈTRES", "QUITTER"};
		}

		if (ecran == PARTIE) {
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

		// écran des commandes : une ligne par action, puis RÉINITIALISER et RETOUR
		int n = Commandes.ACTIONS.length;
		String[] textes = new String[n + 2];
		for (int i = 0; i < n; i++) {
			String touche = Commandes.nomTouche(Commandes.getTouche(Commandes.ACTIONS[i]));
			if (attenteTouche && i == ligne) touche = "appuie sur une touche...";
			textes[i] = Commandes.NOMS[i] + " :  " + touche;
		}
		textes[n] = "RÉINITIALISER";
		textes[n + 1] = "RETOUR";
		return textes;
	}

	private void toucheAppuyee(KeyEvent e) {
		int touche = e.getKeyCode();

		// on attend la nouvelle touche d'une commande
		if (attenteTouche) {
			int id = Commandes.identifiant(e);
			if (touche == KeyEvent.VK_ESCAPE) {
				attenteTouche = false; // annuler
			} else if (id != 0) {
				Commandes.setTouche(Commandes.ACTIONS[ligne], id);
				attenteTouche = false;
			}
			return;
		}

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
			if (ecran == ACCUEIL) {
				dispose();
			} else {
				allerVers(ACCUEIL, 0);
			}
		}
	}

	private void allerVers(int nouvelEcran, int nouvelleLigne) {
		ecran = nouvelEcran;
		ligne = nouvelleLigne;
	}

	// change la taille ou la difficulté (uniquement sur l'écran PARTIE)
	private void changer(int sens) {
		if (ecran != PARTIE) return;
		if (ligne == 0) taille = (taille + sens + 3) % 3;
		if (ligne == 1) niveau = (niveau + sens + 3) % 3;
	}

	private void valider() {
		if (ecran == ACCUEIL) {
			if (ligne == 0) {
				allerVers(PARTIE, 2);      // JOUER -> réglages, curseur sur LANCER
			} else if (ligne == 1) {
				allerVers(COMMANDES, 0);   // PARAMÈTRES -> commandes
			} else {
				dispose();                 // QUITTER
			}

		} else if (ecran == PARTIE) {
			if (ligne == 2) {
				lancer = true;             // LANCER
				dispose();
			} else if (ligne == 3) {
				allerVers(ACCUEIL, 0);     // RETOUR
			} else {
				changer(1);
			}

		} else {
			int n = Commandes.ACTIONS.length;
			if (ligne < n) {
				attenteTouche = true;      // on va lire la nouvelle touche
			} else if (ligne == n) {
				Commandes.reinitialiser(); // RÉINITIALISER
			} else {
				allerVers(ACCUEIL, 1);     // RETOUR (curseur sur PARAMÈTRES)
			}
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

		// boutons : la place disponible est partagée entre tous les boutons
		String[] textes = boutons();
		int largeur = w / 2;
		int pas = Math.min(h / 9, (h * 6 / 10) / textes.length);
		int hauteur = pas * 3 / 4;
		g.setFont(new Font("SansSerif", Font.BOLD, h / 35));

		for (int i = 0; i < textes.length; i++) {
			int x = (w - largeur) / 2;
			int y = h * 3 / 10 + i * pas;

			g.setColor(i == ligne ? Color.ORANGE : new Color(50, 55, 60));
			g.fillRect(x, y, largeur, hauteur);

			g.setColor(i == ligne ? Color.BLACK : Color.WHITE);
			texteCentre(g, textes[i], w, y + hauteur / 2 + h / 90);
		}

		// aide en bas
		g.setFont(new Font("SansSerif", Font.PLAIN, h / 60));
		g.setColor(new Color(200, 190, 170));
		texteCentre(g, aide(), w, h - h / 30);
	}

	private String aide() {
		if (ecran == COMMANDES) {
			return attenteTouche
				? "Appuie sur la nouvelle touche      ECHAP : annuler"
				: "Z / S : naviguer      ESPACE : changer la touche      ECHAP : retour";
		}
		if (ecran == PARTIE) {
			return "Z / S : naviguer      Q / D : modifier      ESPACE : valider      ECHAP : retour";
		}
		return "Z / S : naviguer      ESPACE : valider      ECHAP : quitter";
	}

	private void texteCentre(Graphics g, String texte, int w, int y) {
		g.drawString(texte, (w - g.getFontMetrics().stringWidth(texte)) / 2, y);
	}
}