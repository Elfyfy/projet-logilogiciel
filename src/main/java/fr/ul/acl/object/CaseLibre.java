package fr.ul.acl.object;

public class CaseLibre extends Case {
    private int nMines;

    public CaseLibre(int x, int y,String affichage) {
        super(true,x,y,affichage);
        nMines = 0;
    }

    // Getter et Setter :
    public int getNMines() {
        return nMines;
    }
    public void setNMines(int n) {
        nMines = n;
    }
}