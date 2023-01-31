import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Immediat extends Parametre{
    public Immediat(int taille) {
        super(taille,(short)(Math.pow(2, taille - 1) - 1), (short)(-Math.pow(2, taille - 1)));// l'immédiat est une valeur signée
        if(taille<0) throw new IllegalArgumentException("Les immédiats ont au moins une taille de 0");


    }

    @Override
    public boolean seTrouveDans(String line) {
        Pattern pattern = Pattern.compile("#-?(\\d*)");
        Matcher matcher = pattern.matcher(line);
        if(!matcher.matches())
            return false;
        int val = parseInt(matcher.group(1));
        return val <= getValeurMax() && val >= this.getValeurMin();

    }

    public short getValeur(String line, int lineNum) throws Exception {

        Pattern pattern = Pattern.compile("#-?(\\d*)");
        Matcher matcher = pattern.matcher(line);

        matcher.find();
        int val = parseInt(matcher.group(1));
        if(matcher.group(0).startsWith("#-")) val *= -1;
        if(val > getValeurMax() || val < getValeurMin())
            throw new IllegalArgumentException("La valeur ne respecte pas l'intervalle requis");

        return (short)val;
    }
}
