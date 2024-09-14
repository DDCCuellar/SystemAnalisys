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
    static String Ans = "";
    static int motifFrecuencia = 0;  // Store the frequency of the motif

    public static void main(String[] args) {
        Main main = new Main();
        Scanner sc = new Scanner(System.in);

        // Request sequence number
        System.out.println("Enter the number of sequences: ");
        int numSecuencias = sc.nextInt();  // Number of sequences to generate
        if (numSecuencias < 1000 || numSecuencias > 2000000) {
            System.out.println("the number of sequences must be integers between 1000 to 2000000.");
            return;  // Terminate the program if the condition is not met
        }

        // Request length of sequences
        System.out.println("Enter the size of the sequences: ");
        int longitudSecuencia = sc.nextInt();  // Length of each DNA sequence
        if (longitudSecuencia < 5 || longitudSecuencia > 100) {
            System.out.println("the size of sequences must be integers between 5 to 100");
            return;  // Terminate the program if the condition is not met
        }

        // Request motif length
        System.out.println("Enter the size of the motif: ");
        int longitudMotif = sc.nextInt();  // Length of motif
        if (longitudMotif < 4 || longitudMotif > 10) {
            System.out.println("the size of motif must be integers between 4 to 10");
            return;  // Terminate the program if the condition is not met
        }

        // Start measuring time
        long startTime = System.currentTimeMillis();

        // Generate the dataset
        main.FillerBD(numSecuencias, longitudSecuencia);

        // Show dataset on console
        main.mostrarBD();

        // Search the most frequent motif
        String motif = main.detectarMotif(longitudMotif);  // Search motifs of given length 
        Ans = "Most frequent motif: " + motif;
        System.out.println(Ans);
        System.out.println("Frequency of the motif: " + motifFrecuencia);  // Show motif frequency

        // End measuring time
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;  // Calculate the elapsed time
        System.out.println("Time taken to find the motif: " + elapsedTime + " milliseconds");

        // Save the dataset in the file dataset.txt
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

    // Show the dataset in console
    public void mostrarBD() {
        System.out.println("DNA sequences generated: ");
        for (String secuencia : BD) {
            System.out.println(secuencia);
        }
    }

    // Detect the most frequent motif using HashMap
    public String detectarMotif(int longitudMotif) {
        HashMap<String, Integer> motifMap = new HashMap<>();
        // Loop through each sequence and extract motifs
        for (String secuencia : BD) {
            for (int i = 0; i <= secuencia.length() - longitudMotif; i++) {
                String motif = secuencia.substring(i, i + longitudMotif);
                motifMap.put(motif, motifMap.getOrDefault(motif, 0) + 1);
            }
        }

        // Find the most frequent motif
        String motifFrecuente = "";
        motifFrecuencia = 0;
        for (String key : motifMap.keySet()) {
            if (motifMap.get(key) > motifFrecuencia) {
                motifFrecuente = key;
                motifFrecuencia = motifMap.get(key);  // Store the frequency of the motif
            }
        }
        return motifFrecuente;
    }

    // Save the dataset in the file dataset.txt
    public void guardarEnArchivo() {
        if (BD.isEmpty()) {
            System.out.println("There is no data to save.");
            return;
        }

        try {
            FileWriter writer = new FileWriter("src/dataset.txt"); // Alternative route to the directory of the work
            for (String secuencia : BD) {
                writer.write(secuencia + "\n");
            }
            writer.write(Ans + "\n");
            writer.write("Frequency of the motif: " + motifFrecuencia + "\n");
            writer.close();
            System.out.println("Data set successfully saved in 'src/dataset.txt'");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
        
