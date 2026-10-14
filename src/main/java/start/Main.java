package start;

import engine.GameEngineGraphical;
import model.DemineurGame;
import model.DemineurPainter;
import model.DemineurController;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		DemineurGame game = new DemineurGame(10,10, 2);
		DemineurPainter painter = new DemineurPainter(game);
		DemineurController controller = new DemineurController();

		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();
	}

}