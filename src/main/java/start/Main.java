package start;

import engine.GameEngineGraphical;
import model.DemineurGame;
import model.DemineurPainter;
import model.DemineurController;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		int nbMines = MenuDifficulte.choisirNbMines();
		if (nbMines < 0) return;

		DemineurGame game = new DemineurGame(15, 25, nbMines);
		DemineurPainter painter = new DemineurPainter(game);
		DemineurController controller = new DemineurController();

		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();
	}

}