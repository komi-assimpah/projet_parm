import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Etiquettes extends Parametre{
    public List<Etiquette> get() {
        return etiquettes;
    }

    private List<Etiquette>etiquettes;
    public Etiquettes() {
        super(8);
        etiquettes=new ArrayList<>();
    }

    @Override
    public boolean seTrouveDans(String ligne) {
        if(etiquettes ==null) throw new IllegalStateException("Aucune étiquette n'est présente !");
        String etiquette = ligne.trim();
        return etiquettes.stream().filter(l -> l.getNom().equals(etiquette)).count() > 0;
    }

    @Override
    public short getValeur(String ligne, int ligneNum) throws Exception {
        if(etiquettes ==null) throw new IllegalStateException("Aucune étiquette n'est présente !");
        String etiquette = ligne.trim();

        Optional<Etiquette> resultat = etiquettes.stream().filter(l -> l.equals(etiquette)).findAny();
        if(resultat.isEmpty()) throw  new IllegalStateException("L'étiquette n'est pas dans le fichier !");

        int val = resultat.get().getLigne()- ligneNum;
        if(val > getValeurMax()) throw  new Exception("La valeur ne respecte pas l'intervalle requis !");

        return (short)(val - 3);
    }
}
