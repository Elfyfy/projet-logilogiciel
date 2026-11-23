package model;

public class Mine extends Tile {

    public Mine(int l, int c, Sprite[] tabSprites) {
        // Une case avec une mine est inaccessible.
        super(l,c,true);
        this.setSprites(tabSprites);
    }
}