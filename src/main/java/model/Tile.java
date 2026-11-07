package model;

public class Tile {

    private int row, column; // Indique la ligne et la colonne de la case.
    private final boolean accessible; // Indique si la case est accessible.
    private boolean unknown; // Indique si la case est inconnue.
    private boolean pinned; // Indique si la case possede un drapeau.
    private Sprite[] sprites;

    public Tile(int l, int c, boolean acc, Sprite[] sprites) {
        this.row = l;
        this.column = c;
        this.accessible = acc;
        this.unknown = true; // Une case est initialement inconnue.
        this.pinned = false; // Une case est initialement sans drapeau.
        this.sprites = sprites;
    }

    // Getters et Setters
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