package engine;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GraphicalInterface {

	// marge (en pixels) autour du plateau en mode fenêtré
	private static final int MARGE_FENETRE = 80;

	private DrawingPanel panel;
	private JFrame frame;
	private GraphicsDevice ecran;
	private final boolean pleinEcran;                 // AJOUT : mode d'affichage choisi dans le menu
	private volatile boolean fermee = false;          // AJOUT : true si le joueur ferme la fenêtre (croix)

	/** Ancien constructeur : plein écran par défaut. */
	public GraphicalInterface(GamePainter gamePainter, GameController gameController) {
		this(gamePainter, gameController, true);
	}

	public GraphicalInterface(GamePainter gamePainter, GameController gameController, boolean pleinEcran) {
		this.pleinEcran = pleinEcran;
		this.ecran = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		this.frame = new JFrame("Démineur");
		this.panel = new DrawingPanel(gamePainter);
		this.frame.setContentPane(this.panel);
		this.panel.addKeyListener(gameController);
		// Échap est gérée par le contrôleur (Cmd.MENU)

		// la croix de la fenêtre ne tue plus l'appli : elle termine la partie et ramène au menu
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fermee = true;
			}

			@Override
			public void windowActivated(WindowEvent e) {
				panel.requestFocusInWindow(); // le clavier reste utilisable après un alt-tab
			}
		});

		this.panel.setFocusable(true);

		if (pleinEcran) {
			this.frame.setUndecorated(true);
			if (this.ecran.isFullScreenSupported()) {
				this.ecran.setFullScreenWindow(this.frame);
			} else {
				this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				this.frame.setVisible(true);
			}
		} else {
			// fenêtre normale (barre de titre, redimensionnable) à la taille du plateau + une marge
			Rectangle utile = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
			int largeur = Math.min(gamePainter.getWidth() + MARGE_FENETRE, utile.width - 40);
			int hauteur = Math.min(gamePainter.getHeight() + MARGE_FENETRE, utile.height - 60);
			this.panel.setPreferredSize(new Dimension(largeur, hauteur));
			this.frame.setResizable(true);
			this.frame.pack();
			this.frame.setLocationRelativeTo(null);
			this.frame.setVisible(true);
		}

		this.panel.requestFocusInWindow();
		SwingUtilities.invokeLater(() -> this.panel.requestFocusInWindow());
	}

	public void paint() {
		this.panel.drawGame();
	}

	/** true si le joueur a fermé la fenêtre avec la croix (mode fenêtré). */
	public boolean isClosed() {
		return this.fermee;
	}

	public boolean isPleinEcran() {
		return this.pleinEcran;
	}

	// ferme la fenêtre de jeu (pour revenir au menu)
	public void close() {
		if (this.ecran.getFullScreenWindow() == this.frame) {
			this.ecran.setFullScreenWindow(null);
		}
		this.frame.dispose();
	}

}
