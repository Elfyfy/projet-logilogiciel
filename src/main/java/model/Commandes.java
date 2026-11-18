package model;

import java.awt.event.KeyEvent;

import engine.Cmd;


public class Commandes {

	// les actions que le joueur peut configurer (dans cet ordre)
	public static final Cmd[] ACTIONS = {Cmd.UP, Cmd.DOWN, Cmd.LEFT, Cmd.RIGHT, Cmd.FLAG, Cmd.REVEAL};
	public static final String[] NOMS = {"HAUT", "BAS", "GAUCHE", "DROITE", "DRAPEAU", "CREUSER"};

	private static final int[] DEFAUT = {'z', 's', 'q', 'd', 'f', ' '};
	private static final int[] touches = DEFAUT.clone();

	private static int indice(Cmd action) {
		for (int i = 0; i < ACTIONS.length; i++) {
			if (ACTIONS[i] == action) return i;
		}
		return -1;
	}

	/** Transforme un événement clavier en identifiant de touche (0 si la touche n'est pas utilisable). */
	public static int identifiant(KeyEvent e) {
		char c = e.getKeyChar();
		if (c != KeyEvent.CHAR_UNDEFINED && !java.lang.Character.isISOControl(c)) {
			return java.lang.Character.toLowerCase(c);
		}
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN
				|| code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
			return -code;
		}
		return 0;
	}

	/** Touche actuellement associée à une action. */
	public static int getTouche(Cmd action) {
		return touches[indice(action)];
	}

	/** Associe une touche à une action. Si la touche servait déjà ailleurs, les deux actions s'échangent. */
	public static void setTouche(Cmd action, int touche) {
		int i = indice(action);
		for (int j = 0; j < touches.length; j++) {
			if (j != i && touches[j] == touche) {
				touches[j] = touches[i];
			}
		}
		touches[i] = touche;
	}

	/** Action associée à une touche, ou null si la touche ne fait rien. */
	public static Cmd getAction(int touche) {
		if (touche == 0) return null;
		for (int i = 0; i < touches.length; i++) {
			if (touches[i] == touche) return ACTIONS[i];
		}
		return null;
	}

	public static void reinitialiser() {
		for (int i = 0; i < touches.length; i++) {
			touches[i] = DEFAUT[i];
		}
	}

	/** Nom lisible d'une touche (pour l'affichage). */
	public static String nomTouche(int touche) {
		if (touche == -KeyEvent.VK_UP) return "FLÈCHE HAUT";
		if (touche == -KeyEvent.VK_DOWN) return "FLÈCHE BAS";
		if (touche == -KeyEvent.VK_LEFT) return "FLÈCHE GAUCHE";
		if (touche == -KeyEvent.VK_RIGHT) return "FLÈCHE DROITE";
		if (touche == ' ') return "ESPACE";
		return String.valueOf((char) java.lang.Character.toUpperCase(touche));
	}
}