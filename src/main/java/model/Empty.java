package model;

public class Empty extends Tile {

    private int nbMine;

    public Empty(int l, int c, Sprite[] sprites) {
        super(l,c,true,sprites);
        this.nbMine = 0;
    }
}