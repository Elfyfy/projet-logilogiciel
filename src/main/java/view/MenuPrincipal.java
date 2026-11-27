package view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import model.Commandes;

public class MenuPrincipal extends JDialog {

	public static class Parametres {
		public final int niveau, taille, lignes, colonnes, nbMines;
		public final boolean pleinEcran; // mode d'affichage choisi dans le menu

		private Parametres(int niveau, int taille, int lignes, int colonnes, int nbMines, boolean pleinEcran) {
			this.pleinEcran = pleinEcran;
			this.niveau = niveau;
			this.taille = taille;
			this.lignes = lignes;
			this.colonnes = colonnes;
			this.nbMines = nbMines;
		}
	}

	private static final String[] NOMS_TAILLE = {"PETITE", "MOYENNE", "GRANDE"};
	private static final int[][] TAILLES = {{10, 15}, {15, 25}, {20, 35}};
	private static final String[] NOMS_NIVEAU = {"FACILE", "MOYEN", "DIFFICILE"};
	private static final double[] TAUX = {0.05, 0.15, 0.20};
	// les écrans du menu
	private static final int ACCUEIL = 0;    // JOUER / PARAMÈTRES / QUITTER
	private static final int PARTIE = 1;     // taille et difficulté
	private static final int COMMANDES = 2;  // choix des touches
	private static final int PARAMETRES = 3; // COMMANDES / AFFICHAGE / VOLUME
	private static final int AFFICHAGE = 4;  // fenêtré / plein écran
	private static final int VOLUME = 5;     // volume du son
	private static final int PAS_VOLUME = 10;

	// on retient les derniers choix du joueur
	private static int niveauMemo = 0;
	private static int tailleMemo = 1;
	private static boolean pleinEcranMemo = true;

	// taille de la fenêtre en mode fenêtré
	private static final int LARGEUR_FENETRE = 1000;
	private static final int HAUTEUR_FENETRE = 700;

	/** Dernier mode d'affichage choisi (true : plein écran, false : fenêtré). */
	public static boolean isPleinEcran() {
		return pleinEcranMemo;
	}

	// Retourne les dimensions correspondant à une taille
	public static final int[] dimensionsPourTaille(int taille) {
		return TAILLES[taille].clone();
	}

	// Calcule le nombre de mines selon la taille et la difficulté
	public static final int calculerNbMines(int lignes, int colonnes, int niveau) {
		return Math.max(1, (int) (lignes * colonnes * TAUX[niveau]));
	}

	private int ecran = ACCUEIL;
	private int ligne = 0;
	private int niveau = niveauMemo;
	private int taille = tailleMemo;
	private boolean pleinEcran = pleinEcranMemo;
	private boolean reconstruire = false;
	private boolean lancer = false;
	private boolean attenteTouche = false;
	private Image fond;

	private MenuPrincipal(MenuPrincipal precedent) {
		super((Frame) null, "Démineur", true);

		if (precedent != null) {
			this.ecran = precedent.ecran;
			this.ligne = precedent.ligne;
			this.niveau = precedent.niveau;
			this.taille = precedent.taille;
			this.pleinEcran = precedent.pleinEcran;
		}

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

		setContentPane(panneau);
		if (pleinEcran) {
			setUndecorated(true);
			setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		} else {
			Rectangle utile = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
			panneau.setPreferredSize(new Dimension(
					Math.min(LARGEUR_FENETRE, utile.width - 40),
					Math.min(HAUTEUR_FENETRE, utile.height - 60)));
			setResizable(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			pack();
			setLocationRelativeTo(null);
		}
	}

	public static Parametres afficher() {
		MenuPrincipal menu = new MenuPrincipal(null);
		menu.setVisible(true);
		while (menu.reconstruire) {
			menu = new MenuPrincipal(menu);
			menu.setVisible(true);
		}
		pleinEcranMemo = menu.pleinEcran;

		if (!menu.lancer) return null;
		niveauMemo = menu.niveau;
		tailleMemo = menu.taille;
		int[] dimensions = dimensionsPourTaille(menu.taille);
		int lignes = dimensions[0];
		int colonnes = dimensions[1];
		int nbMines = calculerNbMines(lignes, colonnes, menu.niveau);
		return new Parametres(menu.niveau, menu.taille, lignes, colonnes, nbMines, menu.pleinEcran);
	}

	private String[] boutons() {
		if (ecran == ACCUEIL) {
			return new String[] {"JOUER", "PARAMÈTRES", "QUITTER"};
		}

		if (ecran == PARAMETRES) {
			return new String[] {"COMMANDES", "AFFICHAGE", "VOLUME", "RETOUR"};
		}

		if (ecran == AFFICHAGE) {
			return new String[] {
				(pleinEcran ? "FENÊTRÉ" : "FENÊTRÉ  (actuel)"),
				(pleinEcran ? "PLEIN ÉCRAN  (actuel)" : "PLEIN ÉCRAN"),
				"RETOUR"
			};
		}

		if (ecran == VOLUME) {
			int v = engine.SoundManager.getVolume();
			return new String[] {"VOLUME :  < " + v + " % >", "RETOUR"};
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
		if (touche == KeyEvent.VK_M && !attenteTouche) {
			model.DemineurGame.basculerModerateur();
			return;
		}

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
			} else if (ecran == COMMANDES) {
				allerVers(PARAMETRES, 0);
			} else if (ecran == AFFICHAGE) {
				allerVers(PARAMETRES, 1);
			} else if (ecran == VOLUME) {
				allerVers(PARAMETRES, 2);
			} else if (ecran == PARAMETRES) {
				allerVers(ACCUEIL, 1);
			} else {
				allerVers(ACCUEIL, 0);
			}
		}
	}

	private void allerVers(int nouvelEcran, int nouvelleLigne) {
		ecran = nouvelEcran;
		ligne = nouvelleLigne;
	}

	private void definirAffichage(boolean plein) {
		if (plein == pleinEcran) return;
		pleinEcran = plein;
		reconstruire = true;
		dispose();
	}

	private void changer(int sens) {
		if (ecran == VOLUME && ligne == 0) {
			engine.SoundManager.setVolume(engine.SoundManager.getVolume() + sens * PAS_VOLUME);
			return;
		}
		if (ecran != PARTIE) return;
		if (ligne == 0) taille = (taille + sens + 3) % 3;
		if (ligne == 1) niveau = (niveau + sens + 3) % 3;
	}

	private void valider() {
		if (ecran == ACCUEIL) {
			if (ligne == 0) {
				allerVers(PARTIE, 2);      // JOUER -> réglages, curseur sur LANCER
			} else if (ligne == 1) {
				allerVers(PARAMETRES, 0);  // PARAMÈTRES -> sous-menu
			} else {
				dispose();                 // QUITTER
			}

		} else if (ecran == PARAMETRES) {
			if (ligne == 0) {
				allerVers(COMMANDES, 0);   // COMMANDES -> choix des touches
			} else if (ligne == 1) {
				allerVers(AFFICHAGE, pleinEcran ? 1 : 0); // curseur sur le mode actuel
			} else if (ligne == 2) {
				allerVers(VOLUME, 0);
			} else {
				allerVers(ACCUEIL, 1);     // RETOUR
			}

		} else if (ecran == AFFICHAGE) {
			if (ligne == 0) {
				definirAffichage(false);   // FENÊTRÉ
			} else if (ligne == 1) {
				definirAffichage(true);    // PLEIN ÉCRAN
			} else {
				allerVers(PARAMETRES, 1);  // RETOUR
			}

		} else if (ecran == VOLUME) {
			if (ligne == 0) {
				changer(1);
			} else {
				allerVers(PARAMETRES, 2);
			}

		} else if (ecran == PARTIE) {
			if (ligne == 2) {
				lancer = true;
				dispose();
			} else if (ligne == 3) {
				allerVers(ACCUEIL, 0);   
			} else {
				changer(1);
			}

		} else {
			int n = Commandes.ACTIONS.length;
			if (ligne < n) {
				attenteTouche = true;
			} else if (ligne == n) {
				Commandes.reinitialiser();
			} else {
				allerVers(PARAMETRES, 0);  
			}
		}
	}

	private void dessiner(Graphics g, int w, int h) {
		if (fond != null) {
			g.drawImage(fond, 0, 0, w, h, null);
		}
		g.setColor(new Color(0, 0, 0, 140));
		g.fillRect(0, 0, w, h);

		g.setFont(new Font("SansSerif", Font.BOLD, h / 10));
		g.setColor(Color.WHITE);
		texteCentre(g, "DÉMINEUR", w, h / 5);

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
		if (ecran == VOLUME) {
			return "Z / S : naviguer      Q / D : modifier le volume      ECHAP : retour";
		}
		if (ecran == AFFICHAGE) {
			return "Z / S : naviguer      ESPACE : choisir le mode      ECHAP : retour";
		}
		if (ecran == PARAMETRES) {
			return "Z / S : naviguer      ESPACE : valider      ECHAP : retour";
		}
		return "Z / S : naviguer      ESPACE : valider      ECHAP : quitter";
	}

	private void texteCentre(Graphics g, String texte, int w, int y) {
		g.drawString(texte, (w - g.getFontMetrics().stringWidth(texte)) / 2, y);
	}
}