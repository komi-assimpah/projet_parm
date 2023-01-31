import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
        String entree="assets/tests/calculator.s";
        String sortie="assets/tests/sortie1.txt";
        File fichier=new File(entree);
        Traducteur inter = new Traducteur();

        String outputText = inter.process(fichier);
        System.out.println(outputText);

        File outputFile = new File(sortie);
        outputFile.createNewFile();
        FileWriter writer = new FileWriter(sortie);
        writer.write(outputText);
        writer.close();



    }
}