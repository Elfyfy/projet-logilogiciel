package model;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Sprite {
    
    private BufferedImage image;

    public Sprite(String chemin,int taille) {
        this.loadImage(chemin);
        this.redimension(taille);
    }

    public void loadImage(String chemin) {
		try {this.image = ImageIO.read(getClass().getResource(chemin));}
        catch (Exception e) {
			System.err.println("Impossible de charger " + chemin);
            this.image = null;
		}
	}

    public void redimension(int taille) {
		if (!(this.image == null)) {
            BufferedImage res = new BufferedImage(taille, taille, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = res.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.drawImage(this.image, 0, 0, taille, taille, null);
            g.dispose();
            this.image = res;
        }

    // Getter
    public BufferedImage getImage() {return this.image;}   
}