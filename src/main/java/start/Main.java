package start;

import javax.swing.JOptionPane;

import engine.GameEngineGraphical;
import model.DemineurGame;
import model.DemineurPainter;
import model.DemineurController;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		String[] choix = {"Facile", "Moyen", "Difficile"};
		int rep = JOptionPane.showOptionDialog(null, "Choisis la difficulté", "Démineur",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);

		if (rep == JOptionPane.CLOSED_OPTION) return;

		int nbMines;
		switch (rep) {
		case 0:  nbMines = 10; break;
		case 1:  nbMines = 40; break;
		default: nbMines = 75; break;
		}

		DemineurGame game = new DemineurGame(15, 25, nbMines);
		DemineurPainter painter = new DemineurPainter(game);
		DemineurController controller = new DemineurController();

		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();
	}

}