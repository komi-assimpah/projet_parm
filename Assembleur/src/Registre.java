import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Registre extends Parametre {
    public Registre() {
        super(3);
    }

    @Override
    public boolean seTrouveDans(String ligne) {
        Matcher matcher = Pattern.compile("r(\\d*)").matcher(ligne);
        if(!matcher.matches())
            return false;

        int val = parseInt(matcher.group(1));
        return val <= getValeurMax()&&val>=getValeurMin();
    }

   public short getValeur(String line, int lineNum) throws Exception {
        Pattern pattern = Pattern.compile("r(\\d)");
        Matcher matcher = pattern.matcher(line);

        matcher.find();
        int val = parseInt(matcher.group(1));
        if(val > getValeurMax()||val<getValeurMin()) throw new IllegalArgumentException("Le numéro de registre ne respecte pas les intervalles requis !");

        return (short)val;
    }
}
