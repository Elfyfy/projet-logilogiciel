package start;

import engine.GameEngineGraphical;
import model.DemineurGame;
import model.DemineurPainter;
import model.DemineurController;
import model.Theme;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		int lignes = 15;
		int colonnes = 25;

		int niveau = view.MenuDifficulte.choisirNiveau(lignes, colonnes);
		if (niveau < 0) return;
		int nbMines = view.MenuDifficulte.nbMines(niveau, lignes, colonnes);

		DemineurGame game = new DemineurGame(lignes, colonnes, nbMines);
		DemineurPainter painter = new DemineurPainter(game, Theme.pour(niveau));
		DemineurController controller = new DemineurController();

		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();
	}

}