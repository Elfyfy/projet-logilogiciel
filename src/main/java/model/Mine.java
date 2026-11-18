package model;

public class Mine extends Tile {

    public Mine(int l, int c) {
        Sprite[] sprites = new Sprite[1];
        sprites[0] = DemineurPainter.spriteMine;
        super(l,c,false,sprites);
    }
}