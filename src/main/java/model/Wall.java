package model;

public class Wall extends Tile {

    public Wall(int l, int c) {
        super(l,c,false,null); // Un mur est une case inaccessible.
    }
}