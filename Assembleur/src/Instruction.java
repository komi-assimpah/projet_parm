import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Instruction {
    private String nom;
    private short opcode;
    private List<Parametre> parametres = new ArrayList<>();
    private static final int PROCESSING_OPCODE = 0b100000000;
    private static final int BRANCH_OPCODE = 0b11010000;
    private boolean ordre;

    public Instruction(String nom, int opcode, List parametres) {
        this.nom = nom;
        this.opcode = (short) opcode;
        this.parametres = parametres;
        ordre = false;
    }

    public Instruction(String nom, int opcode, Parametre... parametres) {
        this.nom = nom;
        this.opcode = (short) opcode;
        this.parametres = Arrays.asList(parametres);
        ordre = false;
    }

    public Instruction(String nom, int opcode, boolean ordre, Parametre... parametres) {
        this.nom = nom;
        this.opcode = (short) opcode;
        this.parametres = Arrays.asList(parametres);
        ordre = true;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public short getOpcode() {
        return opcode;
    }

    public void setOpcode(short opcode) {
        this.opcode = opcode;
    }

    public List getParametres() {
        return parametres;
    }

    public void setParametres(List parametres) {
        this.parametres = parametres;
    }

    public Boolean CanProcess(String line) throws Exception {
        line = line.toLowerCase();

        ArrayList<String> insContent = splitInstruction(line);

        if(insContent.size() == 0) throw  new Exception("Error !");

        var headerMatcher = Pattern.compile(nom).matcher(insContent.get(0));
        if(!headerMatcher.matches()) return false;

        insContent.remove(0);

        if(parametres.size() != insContent.size()) return false;

        for(int i = parametres.size() - 1; i >= 0; i--) {
            if(!parametres.get(i).seTrouveDans(insContent.get(i).trim())) return false;
        }

        return true;
    }
    public short GetBits(String fullLine, int lineNum) throws Exception {
        try {
            short result = opcode;

            ArrayList<String> parametersStr = splitInstruction(fullLine);
            parametersStr.remove(0);

            if(ordre){
                for(int i = 0; i < parametersStr.size(); i++) {
                    result = processParameter(i, parametersStr, result, lineNum);
                }
            }
            else {
                for (int i = parametersStr.size() - 1; i >= 0; i--) {
                    result = processParameter(i, parametersStr, result, lineNum);
                }
            }

            return result;
        } catch(Exception e) {
            System.out.println(fullLine);
            System.out.println(nom);
            throw  e;
        }
    }

    private short processParameter(int parameterIndex, ArrayList<String> parameterNames, short response, int lineNum) throws Exception {
        var parameter = parametres.get(parameterIndex);
        response <<= parameter.getTaille();
        short val = parameter.getValeur(parameterNames.get(parameterIndex), lineNum);

        response |= val & parameter.getMasque(); // Here remove the 1 who collapse with header
        return response;
    }

    public ArrayList<String> splitInstruction(String line) throws Exception {
        int crochet = 0;
        ArrayList<String> results = new ArrayList<>();

        var h = line.split("[ \t]")[0];
        line = line.substring(h.length() + 1);
        results.add(h);

        String current = "";

        for(int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if(c == '[') crochet++;
            else if(c == ']') crochet--;

            if(c == ',' && crochet == 0) {
                results.add(current);
                current = "";
            }
            else
                current += c;
        }
        results.add(current);

        if(crochet != 0) throw  new Exception("Error of [");
        return results;
    }

    @Override
    public String toString() {
        return getNom();}

    static final Instruction[] instructions = {
            new Instruction("lsls", 0, new Registre(), new Registre(), new Immediat(5)),
            new Instruction("lsrs", 1, new Registre(), new Registre(), new Immediat(5)),
            new Instruction("asrs", 2, new Registre(), new Registre(), new Immediat(5)),

            new Instruction("adds", 0b1100, new Registre(), new Registre(), new Registre()),
            new Instruction("subs", 0b1101, new Registre(), new Registre(), new Registre()),
            new Instruction("adds", 0b1110, new Registre(), new Registre(), new Immediat(3)),
            new Instruction("subs", 0b1111, new Registre(), new Registre(), new Immediat(3)),
            new Instruction("movs", 4, true, new Registre(), new Immediat(8)),

            new Instruction("ands", PROCESSING_OPCODE | 0, new Registre(), new Registre()),
            new Instruction("eors", PROCESSING_OPCODE | 1, new Registre(), new Registre()),
            new Instruction("lsls", PROCESSING_OPCODE | 2, new Registre(), new Registre()),
            new Instruction("lsrs", PROCESSING_OPCODE | 3, new Registre(), new Registre()),
            new Instruction("asrs", PROCESSING_OPCODE | 4, new Registre(), new Registre()),
            new Instruction("adcs", PROCESSING_OPCODE | 5, new Registre(), new Registre()),
            new Instruction("sbcs", PROCESSING_OPCODE | 6, new Registre(), new Registre()),
            new Instruction("rors", PROCESSING_OPCODE | 7, new Registre(), new Registre()),
            new Instruction("tst", PROCESSING_OPCODE | 8, new Registre(), new Registre()),
            new Instruction("rsbs", PROCESSING_OPCODE | 9, new Registre(), new Registre(), new Immediat(0)),
            new Instruction("cmp", PROCESSING_OPCODE | 10, new Registre(), new Registre()),
            new Instruction("cmn", PROCESSING_OPCODE | 11, new Registre(), new Registre()),
            new Instruction("orrs", PROCESSING_OPCODE | 12, new Registre(), new Registre()),
            new Instruction("muls", PROCESSING_OPCODE | 13, new Registre(), new Registre(), new ParametreDefaut()),
                    new Instruction("bics", PROCESSING_OPCODE | 14, new Registre(), new Registre()),
                    new Instruction("mvns", PROCESSING_OPCODE | 15, new Registre(), new Registre()),

                    new Instruction("str", 0b10010, true, new Registre(), new StackPointeur(8)),
                    new Instruction("ldr", 0b10011, true, new Registre(), new StackPointeur(8)),

                    new Instruction("add", 0b1_0110_0000, new ParametreDefaut(), new Immediat(7)),
                    new Instruction("sub", 0b1_0110_0001, new ParametreDefaut(), new Immediat(7)),

                    new Instruction("beq", BRANCH_OPCODE | 0,Traducteur.getEtiquettes()),
                    new Instruction("bne", BRANCH_OPCODE | 1,Traducteur.getEtiquettes() ),
                    new Instruction("bcs", BRANCH_OPCODE | 2, Traducteur.getEtiquettes()),
                    new Instruction("bcc", BRANCH_OPCODE | 3, Traducteur.getEtiquettes()
                    ),
                    new Instruction("bmi", BRANCH_OPCODE | 4, Traducteur.getEtiquettes()
                    ),
                    new Instruction("bpl", BRANCH_OPCODE | 5, Traducteur.getEtiquettes()
                    ),
                    new Instruction("bvs", BRANCH_OPCODE | 6, Traducteur.getEtiquettes()
                    ),
                    new Instruction("bvc", BRANCH_OPCODE | 7, Traducteur.getEtiquettes()
                    ),
                    new Instruction("bhi", BRANCH_OPCODE | 8, Traducteur.getEtiquettes()
                    ),
                    new Instruction("bls", BRANCH_OPCODE | 9, Traducteur.getEtiquettes()
                    ),
                    new Instruction("bge", BRANCH_OPCODE | 10, Traducteur.getEtiquettes()
                    ),
                    new Instruction("blt", BRANCH_OPCODE | 11, Traducteur.getEtiquettes()
                    ),
                    new Instruction("bgt", BRANCH_OPCODE | 12, Traducteur.getEtiquettes()
                    ),
                    new Instruction("ble", BRANCH_OPCODE | 13, Traducteur.getEtiquettes()
                    ),
                    new Instruction("b", BRANCH_OPCODE | 14, Traducteur.getEtiquettes()
                    ),
                    new Instruction("bal", BRANCH_OPCODE | 14, Traducteur.getEtiquettes())

    };
}
