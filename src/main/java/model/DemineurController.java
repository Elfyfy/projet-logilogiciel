package model;

import java.awt.event.KeyEvent;

import engine.Cmd;
import engine.GameController;

public class DemineurController implements GameController {


	private volatile Cmd commandeEnCours;
	
	public DemineurController() {
		this.commandeEnCours = Cmd.IDLE;
	}

	@Override
	public Cmd getCommand() {
		Cmd commande = this.commandeEnCours;
		// MODIF : REJOUER est aussi consommée une seule fois
		if (commande == Cmd.FLAG || commande == Cmd.REVEAL || commande == Cmd.REJOUER) {
			this.commandeEnCours = Cmd.IDLE;
		}
		return commande;
}

	@Override
	public void keyPressed(KeyEvent e) {

		// AJOUT : Échap quitte le jeu
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}

		switch (e.getKeyChar()) {
		case 'z':
			this.commandeEnCours = Cmd.UP;
			break;
		case 's':
			this.commandeEnCours = Cmd.DOWN;
			break;
		case 'q':
			this.commandeEnCours = Cmd.LEFT;
			break;
		case 'd':
			this.commandeEnCours = Cmd.RIGHT;
			break;
		case 'f':
			this.commandeEnCours = Cmd.FLAG;
			break;
		case ' ':
			this.commandeEnCours = Cmd.REVEAL;
			break;
		case 'r': // AJOUT
			this.commandeEnCours = Cmd.REJOUER;
			break;
			}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.commandeEnCours = Cmd.IDLE;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}