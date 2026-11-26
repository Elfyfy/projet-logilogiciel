package model;

public class CaseBombe extends Case {
    
    private int delai;
    private int[][] zone;
    private boolean active; 
    private boolean blown;

    public CaseBombe(int x,int y,String affichage,int delai,int[][] zone) {
        super(true,x,y,affichage);
        this.delai = delai;
        this.zone = zone;
        this.active = false;
        this.blown = false;
    }

    // Methodes
    public void countDown() {
        if (this.delai == 0) {
            this.active = true;
        } else {
            this.delai--;
        }
    }

    public void explose() {
       int n = this.zone.length;
       int x=this.getCoordX(), y=this.getCoordY();
       for (int i = 0; i < n; i++) {
            int dx=this.zone[i][0], dy = this.zone[i][1];
            Character hero = this.getGame().getHero();
            if (x+dx == hero.getRow() && y+dy == hero.getColumn()) {
                this.getGame().declencherDefaite(x+dx,y+dy);
            }
       }
       this.setBlown(true);
    }

    // Getter et setter
    public int getDelai() {return this.delai;}
    public void setDelai(int dt) {this.delai = dt;}
    public int[][] getZone() {return this.zone;}
    public void setZone(int[][] zone) {this.zone = zone;}
    public boolean getActive() {return this.active;}
    public void setActive(boolean b) {this.active = b;}
    public boolean getBlown() {return this.blown;}
    public void setBlown(boolean b) {this.blown = b;}
}
