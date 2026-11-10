package model;

import java.awt.event.KeyEvent;

import engine.Cmd;
import engine.GameController;

public class DemineurController implements GameController {


	private volatile Cmd commandeEnCours;
	
	public DemineurController() {
		this.commandeEnCours = Cmd.IDLE;
	}

	public Cmd getCommand() {
    Cmd commande = this.commandeEnCours;

    if (commande == Cmd.FLAG || commande == Cmd.REVEAL) {
        this.commandeEnCours = Cmd.IDLE;
    }

    return commande;
}

	@Override
	public void keyPressed(KeyEvent e) {

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