package model;

public class Wall extends Tile {

    public Wall(int l, int c, Sprite[] sprites) {
        super(l,c,false,sprites); // Un mur est une case inaccessible.
    }
}