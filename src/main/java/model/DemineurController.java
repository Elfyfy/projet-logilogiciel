package model;

import java.awt.event.KeyEvent;

import engine.Cmd;
import engine.GameController;

public class DemineurController implements GameController {


	private Cmd commandeEnCours;
	
	public DemineurController() {
		this.commandeEnCours = Cmd.IDLE;
	}

	public Cmd getCommand() {
		return this.commandeEnCours;
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