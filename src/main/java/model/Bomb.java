package model;

public class Bomb extends Tile {

    private int fuse;
    private int[][] shape;
    private boolean active;
    private boolean blown;

    public Bomb(int l, int c, Sprite[] sprites, int fuse, int[][] shape) {
        super(l,c,false,sprites);
        this.fuse = fuse;
        this.shape = shape;
        this.active = false;
        this.blown = false;
    }

    public int getFuse() {return this.fuse;}
    public void setFuse(int fuse) {this.fuse = fuse;}
}