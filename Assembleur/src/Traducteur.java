import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Traducteur {
    public static Etiquettes getEtiquettes() {
        return etiquettes;
    }

    private static Etiquettes etiquettes=new Etiquettes();
    private List<Instruction>instructions= Arrays.asList(Instruction.instructions);
    private boolean estUneDirective(String ligne) {
        if(ligne.trim().charAt(0) == '.' && !contientUneEtiquette(ligne)) return true;
        return false;
    }
    private boolean estUneEtiquette(String ligne) {
        ligne = ligne.trim();
        return ligne.charAt(ligne.length() - 1) == ':';
    }
    private boolean contientUneEtiquette(String ligne) {
        ligne = ligne.trim();
        return ligne.indexOf(':') != -1;
    }
    private String getNomEtiquette(String ligne) {
        ligne = ligne.trim();
        return ligne.split(":")[0];
    }

    private String suppressionComms(String ligne) {
        var splitted = ligne.split("@");
        if(splitted.length == 1) return ligne;
        else return  splitted[0].trim();
    }

    private void preprocess(List<String> lignes) throws Exception {
        int i = 0;

        try {
            while (i < lignes.size()) {
                String ligne = lignes.get(i);
                ligne = ligne.toLowerCase();
                ligne = suppressionComms(ligne);
                ligne = ligne.trim();

                if (ligne.equals("")) {
                    lignes.remove(i);
                    continue;
                } else if (estUneDirective(ligne)) {
                    lignes.remove(i);
                    continue;
                } else if (contientUneEtiquette(ligne)) {
                    String labelName = getNomEtiquette(ligne);
                    Etiquette label = new Etiquette(labelName, i);
                    // adding label
                    ajouterEtiquette(label);

                    if (estUneEtiquette(ligne)) {
                        lignes.remove(i);
                        continue;
                    } else {
                        ligne = ligne.split(":")[1];
                    }
                }

                ligne = ligne.trim();
                lignes.set(i, ligne);
                i++;
            }
        } catch (Exception e) {
            System.out.println("Error : " + lignes.get(i));
            throw  e;
        }
    }

    private List<String> getLignesFichier(File file) throws Exception {
        Scanner scanner = new Scanner(file);
        List<String> lignes = new ArrayList<>();
        while(scanner.hasNextLine())
            lignes.add(scanner.nextLine());
        return lignes;
    }

    public String process(File file) throws Exception {
        List<String> lignes = getLignesFichier(file);
        return processlignes(lignes);
    }

   

    private String processlignes(List<String> lignes) throws Exception {
        preprocess(lignes);

        String result = "v2.0 raw\n";

        for(int i = 0; i < lignes.size(); i++) {
            String ligne = lignes.get(i);
            short code = getligneCode(ligne, i);

            String hex = ShortToHexa.getHexa(code);

            result += hex + " ";
        }
        if(result.length() == 0) throw  new Exception("File is empty !");
        result = result.substring(0, result.length() - 1);

        return result;
    }


    private void ajouterEtiquette(Etiquette label) throws Exception {
        if(etiquettes.get().contains(label))
            throw new Exception("Label allready exist");
        etiquettes.get().add(label);
    }

    private short getligneCode(String ligne, int ligneNum) throws Exception {
        ligne = ligne.toLowerCase();

        try {
            return getInstructionCode(ligne.trim(), ligneNum);
        } catch (Exception e) {
            throw e;
            //throw new Exception("There is an error ligne " + ligneNum + " in \"" + ligne + "\".\n" + e.toString());
        }
    }

    short getInstructionCode(String ligne, int ligneNum) throws Exception {
        Instruction ins = null;
        ligne = ligne.trim();
        ligne = ligne.toLowerCase();

        for(Instruction i : instructions) {
            if(i.CanProcess(ligne))
            {
                ins = i;
                break;
            }
        }

        if(ins == null)
            throw new Exception("Error in the programme ! : (" + ligne + ").");

        return ins.GetBits(ligne, ligneNum);
    }
}
