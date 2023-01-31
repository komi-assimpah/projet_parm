import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class StackPointeur extends Parametre{
    public StackPointeur(int taille) {
        super(taille);
    }

    @Override
    public boolean seTrouveDans(String ligne) {
        ligne = ligne.replace('[', ' ').replace(']', ' ');
        Matcher matcher1 = Pattern.compile("[ \t]*sp[ \t]*,[ \t]*#(\\d*)[ \t]*").matcher(ligne);
        boolean matche = matcher1.matches();
        int val = 0;
        if(matche) {
            val = parseInt(matcher1.group(1));
        }

        var matcher2 = Pattern.compile("[ \t]*sp[ \t]*").matcher(ligne);

        return (matche && val <= getValeurMax()&&val>=getValeurMin()) || matcher2.matches();
    }

    @Override
    public short getValeur(String ligne, int num) throws Exception {
        Matcher matcher = Pattern.compile("#(\\d*)").matcher(ligne);
        if(matcher.find()) {
            int val = parseInt(matcher.group(1));
            if(val > getValeurMax()||val<getValeurMin()) throw new IllegalArgumentException("L'argument ne respecte pas les intervalles requis ! ");
            return (short)val;
        } else return 0;
    }
}
