package launcher;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Main {
    ArrayList<String> BD = new ArrayList<>();  // Store the DNA sequences generated
    Random rand = new Random();
    static String Ans="";

    public static void main(String[] args) {
        Main main = new Main();
        Scanner sc=new Scanner(System.in);
        System.out.println("Ingrese # secuencias a generar");
        int numSecuencias = sc.nextInt();  // Number of sequences to generate
        System.out.println("Ingrese la longitud de las secuencias");
        int longitudSecuencia = sc.nextInt();  // Length of each DNA sequence
        System.out.println("Ingrese la longitud del motif");
        int longitudMotif = sc.nextInt();  // Length of motif

        // Generate the dataset
        main.FillerBD(numSecuencias, longitudSecuencia);

        // Show dataset on console
        main.mostrarBD();

        // Search the most frecuent motif
        String motif = main.detectarMotif(longitudMotif);  // Search motifs of given lenght 
        Ans="Motif más frecuente: " + motif;
        System.out.println("Motif más frecuente: " + motif);

        //Save the dataset in the file dataset.txt
        main.guardarEnArchivo();
    }

    // Generate a base of DNA according to a random number
    public void GeneradorBN(int value, StringBuilder secuencia) {
        if (value < 25)
            secuencia.append("A");
        else if (value < 50)
            secuencia.append("C");
        else if (value < 75)
            secuencia.append("G");
        else
            secuencia.append("T");
    }

    // Fill the ArrayList with random DNA sequences
    public void FillerBD(int numSecuencias, int longitud) {
        for (int i = 0; i < numSecuencias; i++) {
            StringBuilder secuencia = new StringBuilder();
            for (int j = 0; j < longitud; j++) {
                GeneradorBN(rand.nextInt(100), secuencia);
            }
            BD.add(secuencia.toString());
        }
    }

    // Show the Daset in console
    public void mostrarBD() {
        System.out.println("Secuencias de ADN generadas:");
        for (String secuencia : BD) {
            System.out.println(secuencia);
        }
    }

    // Detect the motif more frequent using HashMap
    public String detectarMotif(int longitudMotif) {
        HashMap<String, Integer> motifMap = new HashMap<>();

        // Loop through each sequence and extract motifs
        for (String secuencia : BD) {
            for (int i = 0; i <= secuencia.length() - longitudMotif; i++) {
                String motif = secuencia.substring(i, i + longitudMotif);
                motifMap.put(motif, motifMap.getOrDefault(motif, 0) + 1);
            }
        }

        // Find the more frequent motif
        String motifFrecuente = "";
        int maxFrecuencia = 0;
        for (String key : motifMap.keySet()) {
            if (motifMap.get(key) > maxFrecuencia) {
                motifFrecuente = key;
                maxFrecuencia = motifMap.get(key);
            }
        }
        return motifFrecuente;
    }

    // Save the dataset in the file dataset.txt
    public void guardarEnArchivo() {
        if (BD.isEmpty()) {
            System.out.println("No hay datos para guardar.");
            return;
        }

        try {
            FileWriter writer = new FileWriter("src/dataset.txt"); // Alternative route to the directory of the work
            for (String secuencia : BD) {
                writer.write(secuencia + "\n");
            }
            writer.write(Ans + "\n");
            writer.close();
            System.out.println("Dataset guardado exitosamente en 'src/dataset.txt'");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}
