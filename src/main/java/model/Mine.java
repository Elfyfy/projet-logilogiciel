package model;

public class Mine extends Tile {

    public Mine(int l, int c) {
        // Une case avec une mine est inaccessible.
        super(l,c,true);
        Sprite[] tabSprite = new Sprite[1];
        tabSprite[0] = DemineurPainter.spriteMine;
        this.setSprites(tabSprite);
    }
}