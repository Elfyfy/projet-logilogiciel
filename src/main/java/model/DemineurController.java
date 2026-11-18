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
		if (commande == Cmd.FLAG || commande == Cmd.REVEAL) {
			this.commandeEnCours = Cmd.IDLE;
		}
		return commande;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// les touches viennent de la classe Commandes (modifiables depuis le menu)
		Cmd action = Commandes.getAction(Commandes.identifiant(e));
		if (action != null) {
			this.commandeEnCours = action;
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