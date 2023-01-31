public abstract class Parametre {
    public Parametre(int taille, short valeurMax, short valeurMin) {
        this.taille = taille;
        this.valeurMax = valeurMax;
        this.valeurMin = valeurMin;
    }

    public int getTaille() {
        return taille;
    }

    public short getValeurMax() {
        return valeurMax;
    }

    public short getValeurMin() {
        return valeurMin;
    }

    public short getValeur(String ligne,int num) throws Exception{
        return valeur;
    }

    public short getMasque() {
        return masque;
    }

    private  int taille;
    private short valeurMax;
    private short valeurMin;
    private short valeur;
    private short masque;
    public abstract boolean seTrouveDans(String line) throws Exception;


    public Parametre(int taille) {
        this.taille = taille;
        this.valeurMax=(short) (Math.pow(2,taille)-1);
        this.valeurMin=0;
        masque=0;
        for (int i = 0; i < taille; i++) { //mettre tous les bits du masque à 1
            masque<<=1 ;
            masque|=1;

        }

    }
}
