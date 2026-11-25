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
			int niveau = choix.niveau;
			int taille = choix.taille;
			boolean enchainer = true;

			while (enchainer) {
				int[] dim = MenuPrincipal.dimensionsPourTaille(taille);
				int nbMines = MenuPrincipal.calculerNbMines(dim[0], dim[1], niveau);

				DemineurGame game = new DemineurGame(dim[0], dim[1], nbMines);
				game.setNiveauSuivantPossible(!(niveau == 2 && taille == 2));
				DemineurPainter painter = new DemineurPainter(game, Theme.pour(niveau));
				DemineurController controller = new DemineurController();

				new GameEngineGraphical(game, painter, controller).run();

				if (game.getNiveauSuivant()) {
					taille++;
					if (taille > 2) {
						taille = 0;
						niveau++;
					}
				} else {
					enchainer = false; // Échap : retour au menu
				}
			}
		}
		System.exit(0);
	}

}