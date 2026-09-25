package fr.ul.acl.object;

public class Case {
    private boolean decouvert;
    private boolean accessible;
    private int coordX;
    private int coordY;
    private String sprite;

    // Constructeurs de la classe
    public Case(boolean acces,int x,int y,String affichage) {
        decouvert = false;
        accessible = acces;
        coordX = x;
        coordY = y;
        sprite = affichage;
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
    public String getSprite() {
        return sprite;
    }

    // Setter des attributs
    public void setDecouvert() {
        decouvert = true; // On ne modifiera l attribut decouvert seulement lorsque le joueur decouvre une case;
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
    public void setSprite(String affichage) {
        sprite = affichage;
    }
}