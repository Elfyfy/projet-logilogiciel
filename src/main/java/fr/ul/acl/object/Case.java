package fr.ul.acl.object;

public class Case {
    private boolean decouvert;
    private boolean accessible;
    private int coordX;
    private int coordY;

    // Constructeurs de la classe
    public Case(boolean acces,int x,int y) {
        decouvert = false;
        accessible = acces;
        coordX = x;
        coordY = y;
    }

    // Getter des attributs
    public boolean getDecouvert() {
        return decouvert;
    }
    public boolean getAccessible() {
        return accessible;
    }
    public int getCoordX() {
        return coordX;
    }
    public int getCoordY() {
        return coordY;
    }

    // Setter des attributs
    public void setDecouvert() {
        decouvert = true;
    }
    public void setAccessible(boolean acces) {
        accessible = acces;
    }
    public void setCoordX(int x) {
        coordX = x;
    }
    public void setCoordY(int y) {
        coordY = y;
    }
}