package engine;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * moteur de game generique.
 * On lui passe un game et un afficheur et il permet d'executer un game.
 */
public class GameEngineGraphical {

	/**
	 * le game a executer
	 */
	private Game game;

	/**
	 * l'afficheur a utiliser pour le rendu
	 */
	private GamePainter gamePainter;

	/**
	 * le controlleur a utiliser pour recuperer les commandes
	 */
	private GameController gameController;

	/**
	 * l'interface graphique
	 */
	private GraphicalInterface gui;

	/**
	 * true : plein ecran / false : fenetre normale
	 */
	private boolean pleinEcran = true;

	/**
	 * construit un moteur
	 * 
	 * @param game
	 *            game a lancer
	 * @param gamePainter
	 *            afficheur a utiliser
	 * @param gameController
	 *            controlleur a utiliser
	 *            
	 */
	public GameEngineGraphical(Game game, GamePainter gamePainter, GameController gameController) {
		// creation du game
		this.game = game;
		this.gamePainter = gamePainter;
		this.gameController = gameController;
	}

	/**
	 * construit un moteur en precisant le mode d'affichage
	 *
	 * @param pleinEcran
	 *            true pour le plein ecran, false pour une fenetre
	 */
	public GameEngineGraphical(Game game, GamePainter gamePainter, GameController gameController, boolean pleinEcran) {
		this(game, gamePainter, gameController);
		this.pleinEcran = pleinEcran;
	}

	/**
	 * permet de lancer le game
	 */
	public void run() throws InterruptedException {

		// creation de l'interface graphique
		this.gui = new GraphicalInterface(this.gamePainter, this.gameController, this.pleinEcran);

		// boucle de game
		while (!this.game.isFinished() && !this.gui.isClosed()) {
			// demande controle utilisateur
			Cmd c = this.gameController.getCommand();
			// fait evoluer le game
			this.game.evolve(c);
			// affiche le game
			this.gui.paint();
			// met en attente
			Thread.sleep(100);
		}

		// fin de partie (ou fenetre fermee) -> on ferme la fenetre de jeu, Main reaffiche le menu
		this.gui.close();
	}

}
