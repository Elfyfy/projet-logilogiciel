package start;

import engine.GameEngineGraphical;
import model.DemineurGame;
import model.DemineurPainter;
import model.DemineurController;
import model.Theme;
import view.MenuPrincipal;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		MenuPrincipal.Parametres choix;
		while ((choix = MenuPrincipal.afficher()) != null) {
			DemineurGame game = new DemineurGame(choix.lignes, choix.colonnes, choix.nbMines);
			DemineurPainter painter = new DemineurPainter(game, Theme.pour(choix.niveau));
			DemineurController controller = new DemineurController();

			GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
			engine.run();
		}
		System.exit(0);
	}

} 