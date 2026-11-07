package model;

import engine.Cmd;

public class Character {

    private int row, column;
    private Sprite[] sprites;
    private Cmd dir;

    public Character(int l, int c, Sprite[] sprites) {
        this.row = l;
        this.column = c;
        this.sprites = sprites;
        this.dir = Cmd.IDLE;
    }

    public int getRow() {return this.row;}
    public void setRow(int l) {this.row = l;}
    public int getColumn() {return this.column;}
    public void setColumn(int c) {this.column = c;}
}