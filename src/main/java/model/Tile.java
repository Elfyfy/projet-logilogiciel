package model;

public class Tile {

    private int row, column;
    private boolean accessible;
    private boolean unknown;
    private boolean pinned;
    private Sprite[] sprites;

    public Tile(int l, int c, boolean acc, Sprite[] sprites) {
        this.row = l;
        this.column = c;
        this.accessible = acc;
        this.unknown = true;
        this.pinned = false;
        this.sprites = sprites;
    }
}