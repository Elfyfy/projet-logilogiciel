package model;

import engine.Cmd;

public class Character {

    private int row, column; // Indique la ligne et la colonne du Personnage.
    private Sprite sprite;
    private Cmd dir; // Indique la direction recente du Personnage.

    public Character(int l, int c) {
        this.row = l;
        this.column = c;
        this.sprite = DemineurPainter.spriteGilet;
        this.dir = Cmd.IDLE; // La direction est initialement indefinie.
    }

    // Getters et Setters
    public int getRow() {return this.row;}
    public void setRow(int l) {this.row = l;}
    public int getColumn() {return this.column;}
    public void setColumn(int c) {this.column = c;}
    public Cmd getDir() {return this.dir;}
    public void setDir(Cmd d) {this.dir = d;}
    public Sprite getSprite() {return this.sprite;}
}