package model;

public class Empty extends Tile {

    private int nbMine; // Indique le nombre de mines sur les cases voisines.

    public Empty(int l, int c, Sprite[] tabSprites) {
        // Une case libre, ou vide, est accesible
        super(l,c,true);
        this.nbMine = 0;
        this.setSprites(tabSprites);
    }

    // Getter et Setter
    public int getNbMine() {return this.nbMine;}
    public void setNbMine(int n) {this.nbMine = n;}
}