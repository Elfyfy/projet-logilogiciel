package model;

public class Wall extends Tile {

    public Wall(int l, int c, Sprite[]tabSprites) {
        // Une case mur est inaccessible.
        super(l,c,false);
        this.setSprites(tabSprites);
    }
}