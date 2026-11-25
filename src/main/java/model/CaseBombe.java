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
