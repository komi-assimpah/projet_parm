public class ShortToHexa {
    private static char[] caracs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getHexa(short n) {
        String result = "";
        for(int i = 0; i < 16; i += 4) {
            int val = (n >> i) & 0xf;
            result = caracs[val] + result;
        }
        return result;
    }
}
