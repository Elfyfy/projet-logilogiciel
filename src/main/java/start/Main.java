package start;

import engine.GameEngineGraphical;
import model.DemineurGame;
import model.DemineurPainter;
import model.DemineurController;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		int lignes = 15;
		int colonnes = 25;

		int nbMines = MenuDifficulte.choisirNbMines(lignes, colonnes);
		if (nbMines < 0) return;

		DemineurGame game = new DemineurGame(lignes, colonnes, nbMines);
		DemineurPainter painter = new DemineurPainter(game);
		DemineurController controller = new DemineurController();

		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();
	}

}