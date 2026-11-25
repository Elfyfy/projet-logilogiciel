package model;

public class Bomb extends Tile {

    private int fuse;
    private int[][] shape;
    private boolean active; 
    private boolean blown;

    public Bomb(int l, int c, Sprite[] tabSprites, int fuse, int[][] shape) {
        super(l,c,false);
        this.fuse = fuse;
        this.shape = shape;
        this.active = false;
        this.blown = false;
        this.setSprites(tabSprites);
    }

    // Methodes
    public void countDown() {
        if (this.fuse == 0) {

        } else {
            this.fuse--;
        }
    }

    // Getter et setter
    public int getFuse() {return this.fuse;}
    public void setFuse(int fuse) {this.fuse = fuse;}
    public int[][] getShape() {return this.shape;}
    public void setShape(int[][] shape) {this.shape = shape;}
    public boolean getActive() {return this.active;}
    public void setActive(boolean b) {this.active = b;}
    public boolean getBlown() {return this.blown;}
    public void setBlown(boolean b) {this.blown = b;}
}