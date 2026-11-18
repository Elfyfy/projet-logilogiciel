package model;

import java.awt.image.BufferedImage;

public class Sprite {
    
    private BufferedImage image;

    public Sprite(BufferedImage img) {
        this.image = img;
    }

    private void loadImage(String chemin) {
		try {this.image = ImageIO.read(getClass().getResource(chemin));}
        catch (Exception e) {
			System.err.println("Impossible de charger " + chemin);
            this.image = null;
		}
	}


    // Getter
    public BufferedImage getImage() {return this.image;}   
}