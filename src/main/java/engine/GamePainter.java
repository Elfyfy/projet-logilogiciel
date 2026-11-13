package engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 * 
 * represente la maniere de dessiner sur un JPanel
 * 
 */
public interface GamePainter {

	/**
	 * methode dessiner a completer. Elle construit une image correspondant au
	 * jeu. Game est un attribut de l'afficheur
	 * 
	 * @param image
	 *            image sur laquelle dessiner
	 */
	public abstract void draw(BufferedImage image);

	public abstract int getWidth();

	public abstract int getHeight();

	public default void dessinerFond(Graphics2D g, int largeurEcran, int hauteurEcran, int xJeu, int largeurJeu) {
	}
	
}