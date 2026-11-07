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

    public int getRow() {return this.row;}
    public void setRow(int l) {this.row = l;}
    public int getColumn() {return this.column;}
    public void setColumn(int c) {this.column = c;}
    public boolean getAccesible() {return this.accessible;}
    public boolean getUnknown() {return this.unknown;}
    public void setUnknown(boolean b) {this.unknown = b;}
    public boolean getPinned() {return this.pinned;}
    public void setPinned(boolean b) {this.pinned = b;}
}