public class Etiquette {
    private String nom;
    private int ligne;

    Etiquette(String nom, int ligne) {
        this.nom = nom;
        this.ligne = ligne;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public boolean equals(Object obj) {
        return nom.equals(obj);
    }

    public int getLigne() {
        return ligne;
    }

}
