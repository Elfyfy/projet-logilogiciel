package engine;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class GraphicalInterface {

	private DrawingPanel panel;
	private JFrame frame;        // AJOUT : on garde la fenêtre pour pouvoir la fermer
	private GraphicsDevice ecran; // AJOUT

	public GraphicalInterface(GamePainter gamePainter, GameController gameController) {
		this.frame = new JFrame();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setUndecorated(true);

		this.panel = new DrawingPanel(gamePainter);
		this.frame.setContentPane(this.panel);

		this.panel.addKeyListener(gameController);
		// MODIF : l'ancien KeyListener qui faisait System.exit(0) sur Échap est supprimé,
		// Échap est maintenant gérée par le contrôleur (Cmd.MENU)

		this.ecran = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if (this.ecran.isFullScreenSupported()) {
			this.ecran.setFullScreenWindow(this.frame);
		} else {
			this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.frame.setVisible(true);
		}

		this.frame.getContentPane().setFocusable(true);
		this.frame.getContentPane().requestFocus();
	}

	public void paint() {
		this.panel.drawGame();
	}

	// AJOUT : ferme la fenêtre de jeu (pour revenir au menu)
	public void close() {
		if (this.ecran.getFullScreenWindow() == this.frame) {
			this.ecran.setFullScreenWindow(null);
		}
		this.frame.dispose();
	}

}