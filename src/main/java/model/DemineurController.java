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
		// MODIF : REJOUER et MENU sont consommées une seule fois, comme FLAG et REVEAL
		if (commande == Cmd.FLAG || commande == Cmd.REVEAL
				|| commande == Cmd.REJOUER || commande == Cmd.MENU) {
			this.commandeEnCours = Cmd.IDLE;
		}
		return commande;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.commandeEnCours = Cmd.MENU;
			return;
		}
		Cmd action = Commandes.getAction(Commandes.identifiant(e));
		if (action != null) {
			this.commandeEnCours = action;
		} else if (e.getKeyCode() == KeyEvent.VK_R) {
			this.commandeEnCours = Cmd.REJOUER; // AJOUT : R = rejouer
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// ne pas écraser une commande pas encore lue par le moteur (boucle de 100 ms)
		if (this.commandeEnCours != Cmd.MENU && this.commandeEnCours != Cmd.REJOUER) {
			this.commandeEnCours = Cmd.IDLE;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}