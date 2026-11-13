package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private GamePainter painter;
	private BufferedImage nextImage;
	private BufferedImage currentImage;
	private BufferedImage fond;
	private int width, height;

	public DrawingPanel(GamePainter painter) {
		super();
		this.width = painter.getWidth();
		this.height = painter.getHeight();
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.painter = painter;

		this.nextImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.currentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	public void drawGame() {
		this.painter.draw(this.nextImage);

		BufferedImage temp = this.currentImage;
		this.currentImage = this.nextImage;
		this.nextImage = temp;
		Graphics2D g = this.nextImage.createGraphics();
		g.fillRect(0, 0, this.width, this.height);
		g.dispose();
		this.repaint();
	}

	private BufferedImage creerFond(int w, int h) {
		BufferedImage im = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = im.createGraphics();
		g.setPaint(new GradientPaint(0, 0, new Color(14, 18, 26), w, h, new Color(54, 88, 98)));
		g.fillRect(0, 0, w, h);
		g.setColor(new Color(255, 255, 255, 12));
		for (int x = 0; x < w; x += 30) g.drawLine(x, 0, x, h);
		for (int y = 0; y < h; y += 30) g.drawLine(0, y, w, y);
		g.dispose();
		return im;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		int w = getWidth();
		int h = getHeight();

		if (fond == null || fond.getWidth() != w || fond.getHeight() != h) {
			fond = creerFond(w, h);
		}
		g2.drawImage(fond, 0, 0, null);

		double echelle = Math.max(1.0, Math.min((w - 80.0) / width, (h - 80.0) / height));
		int lw = (int) (width * echelle);
		int lh = (int) (height * echelle);
		int x = (w - lw) / 2;
		int y = (h - lh) / 2;

		painter.dessinerFond(g2, w, h, x, lw);

		g2.setColor(new Color(0, 0, 0, 90));
		g2.fillRect(x + 8, y + 8, lw, lh);

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g2.drawImage(currentImage, x, y, lw, lh, null);

		g2.setColor(new Color(108, 156, 168));
		g2.drawRect(x - 1, y - 1, lw + 1, lh + 1);
	}

}