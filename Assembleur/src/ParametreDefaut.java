public class ParametreDefaut extends  Parametre{
    public ParametreDefaut() {
        super(0);
    }
    @Override
   public short getValeur(String line, int lineNum) throws Exception {
        return 0;
    }

    @Override
    public boolean seTrouveDans(String line) {
        return true;
    }
}
