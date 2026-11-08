package model;

public class Empty extends Tile {

    private int nbMine; // Indique le nombre de mines sur les cases voisines.

    public Empty(int l, int c, Sprite[] sprites) {
        super(l,c,true,sprites); // Une case libre est une case accessible.
        this.nbMine = 0;
    }

    // Getter et Setter
    public int getNbMine() {return this.nbMine;}
    public void setNbMine(int n) {this.nbMine = n;}
}