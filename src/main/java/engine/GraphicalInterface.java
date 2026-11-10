package engine;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class GraphicalInterface {

	private DrawingPanel panel;

	public GraphicalInterface(GamePainter gamePainter, GameController gameController) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setUndecorated(true);

		this.panel = new DrawingPanel(gamePainter);
		f.setContentPane(this.panel);

		this.panel.addKeyListener(gameController);
		this.panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
			}
		});

		GraphicsDevice ecran = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if (ecran.isFullScreenSupported()) {
			ecran.setFullScreenWindow(f);
		} else {
			f.setExtendedState(JFrame.MAXIMIZED_BOTH);
			f.setVisible(true);
		}

		f.getContentPane().setFocusable(true);
		f.getContentPane().requestFocus();
	}

	public void paint() {
		this.panel.drawGame();
	}

}