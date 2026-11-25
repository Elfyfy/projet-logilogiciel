package model;

import engine.Cmd;

public class Character {

    private int row, column; // Indique la ligne et la colonne du Personnage.
    private Sprite sprite;
    private Cmd dir; // Indique la derniere direction du Personnage.

    private DemineurGame game; // Jeu auquel appartient le personnage

    public Character(int l, int c,DemineurGame game) {
        this.row = l;
        this.column = c;
        this.sprite = DemineurPainter.spriteGilet;
        this.dir = Cmd.IDLE; // La direction est initialement indefinie.
        this.game = game;
    }

    // Methode
    public void move(Cmd direction) {
        this.setDir(direction);
        int newRow=this.row, newColumn=this.column;
        switch (this.dir) {
            case UP :
                if (newRow > 0) {newRow--;}
                else {return;}
            case DOWN :
                if (newRow < this.game.getLignes()) {newRow--;}
                else {return;}
            case LEFT :
                if (newColumn > 0) {newColumn--;}
                else {return;}
            case RIGHT :
                if (newColumn < this.game.getColonnes()) {newColumn--;}
                else {return;}
            default :
                break;
        } Case newCase = this.game.getCase(newRow,newColumn);
        if (newCase.getAccessible() && newCase.getDrapeau()) {
            if (newCase.getDecouvert()) {
                this.setRow(newRow);
                this.setColumn(newColumn);
            } else {
                newCase.setDecouvert();
                this.setRow(newRow);
                this.setColumn(newColumn);
            }
            newCase.setDecouvert();
            this.setRow(newRow); this.setColumn(newColumn);
        }
    }

    // Getters et Setters
    public int getRow() {return this.row;}
    public void setRow(int l) {this.row = l;}
    public int getColumn() {return this.column;}
    public void setColumn(int c) {this.column = c;}
    public Cmd getDir() {return this.dir;}
    public void setDir(Cmd d) {this.dir = d;}
    public Sprite getSprites() {return this.sprite;}
    public void setSprites(Sprite sprite) {this.sprite = sprite;}

    public DemineurGame getGame() {return this.game;}
}