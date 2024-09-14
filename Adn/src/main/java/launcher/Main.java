package launcher;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Main {
    ArrayList<String> BD = new ArrayList<>();  // Almacenar las secuencias de ADN generadas
    Random rand = new Random();
    static String Ans="";

    public static void main(String[] args) {
        Main main = new Main();
        Scanner sc=new Scanner(System.in);
        System.out.println("Ingrese # secuencias a generar");
        int numSecuencias = sc.nextInt();  // Número de secuencias a generar
        System.out.println("Ingrese la longitud de las secuencias");
        int longitudSecuencia = sc.nextInt();  // Longitud de cada secuencia de ADN
        System.out.println("Ingrese la longitud del motif");
        int longitudMotif = sc.nextInt();  // Longitud del motif

        // Generar el dataset
        main.FillerBD(numSecuencias, longitudSecuencia);

        // Mostrar el dataset en consola
        main.mostrarBD();

        // Buscar el motif más frecuente
        String motif = main.detectarMotif(longitudMotif);  // Buscar motifs de longitud dada
        Ans="Motif más frecuente: " + motif;
        System.out.println("Motif más frecuente: " + motif);

        // Guardar el dataset en el archivo dataset.dat
        main.guardarEnArchivo();
    }

    // Generar una base de ADN de acuerdo a un número aleatorio
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

    // Llenar el ArrayList con secuencias de ADN aleatorias
    public void FillerBD(int numSecuencias, int longitud) {
        for (int i = 0; i < numSecuencias; i++) {
            StringBuilder secuencia = new StringBuilder();
            for (int j = 0; j < longitud; j++) {
                GeneradorBN(rand.nextInt(100), secuencia);
            }
            BD.add(secuencia.toString());
        }
    }

    // Mostrar el contenido de la base de datos (BD) en consola
    public void mostrarBD() {
        System.out.println("Secuencias de ADN generadas:");
        for (String secuencia : BD) {
            System.out.println(secuencia);
        }
    }

    // Detectar el motif más frecuente utilizando un HashMap
    public String detectarMotif(int longitudMotif) {
        HashMap<String, Integer> motifMap = new HashMap<>();

        // Recorrer cada secuencia y extraer motifs
        for (String secuencia : BD) {
            for (int i = 0; i <= secuencia.length() - longitudMotif; i++) {
                String motif = secuencia.substring(i, i + longitudMotif);
                motifMap.put(motif, motifMap.getOrDefault(motif, 0) + 1);
            }
        }

        // Encontrar el motif más frecuente
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

    // Guardar el dataset generado en el archivo dataset.txt
    public void guardarEnArchivo() {
        if (BD.isEmpty()) {
            System.out.println("No hay datos para guardar.");
            return;
        }

        try {
            FileWriter writer = new FileWriter("src/dataset.txt"); // Ruta relativa al directorio del proyecto
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
