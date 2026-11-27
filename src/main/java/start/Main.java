package start;
import engine.GameEngineGraphical;
import model.DemineurGame;
import model.DemineurPainter;
import model.DemineurController;
import model.Theme;
import view.MenuPrincipal;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		boolean retourChoixNiveau = false;
		MenuPrincipal.Parametres choix;
		while ((choix = MenuPrincipal.afficher(retourChoixNiveau)) != null) {
			if (choix.histoire) {
				jouerHistoire();
				retourChoixNiveau = false;
			} else {
				jouerNiveauSeul(choix.niveau, choix.taille);
				retourChoixNiveau = true;}}
		System.exit(0);}
	private static void jouerNiveauSeul(int niveau, int taille) throws InterruptedException {
		int[] dim = MenuPrincipal.dimensionsPourTaille(taille);
		int nbMines = MenuPrincipal.calculerNbMines(dim[0], dim[1], niveau);

		DemineurGame game = new DemineurGame(dim[0], dim[1], nbMines);
		game.setNiveauSeul(true);
		DemineurPainter painter = new DemineurPainter(game, Theme.pour(niveau));
		new GameEngineGraphical(game, painter, new DemineurController()).run();
	}
	private static void jouerHistoire() throws InterruptedException {
		int niveau = 0;
		int taille = 0;
		boolean enchainer = true;
		while (enchainer) {
			int[] dim = MenuPrincipal.dimensionsPourTaille(taille);
			int nbMines = MenuPrincipal.calculerNbMines(dim[0], dim[1], niveau);

			DemineurGame game = new DemineurGame(dim[0], dim[1], nbMines);
			game.setNiveauSuivantPossible(!(niveau == 2 && taille == 2));
			DemineurPainter painter = new DemineurPainter(game, Theme.pour(niveau));
			new GameEngineGraphical(game, painter, new DemineurController()).run();
			if (game.getNiveauSuivant()) {taille++;
				if (taille > 2) {taille = 0; niveau++;}
			} else {
				enchainer = false;
			}
		}
	}
}