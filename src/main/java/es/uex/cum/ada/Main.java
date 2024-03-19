package es.uex.cum.ada;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Práctica 1: Análisis experimental de la Eficiencia de algunos Algoritmos de Ordenación
 * <ul>
 *     <li>Estudiar el coste temporal real de algunos algoritmos de ordenación</li>
 *     <li>Los algoritmos ordenaran ascendentemente un vector de enteros de diferente tamaño</li>
 *     <li>Implementar los algoritmos de ordenación Selection sort y Bubble sort</li>
 *     <li>Comparar el coste entre los algoritmos implementados y los algoritmos ya disponibles(Cocktail Sort y Bubble Sort)</li>
 *     <ul>Analizar el comportamiento temporal del algoritmo ante diferentes configuraciones de datos de entrada
 *          <li>Caso Mejor</li>
 *          <li>Caso Peor</li>
 *          <li>Datos de entrada aleatorios</li>
 *     </ul>
 *     <li>Se deben realizar pruebas temporales con tamaños de problema (talla) crecientes.</li>
 *     <li>Realizar varias mediciones para cada talla para reducir el efecto de perturbaciones en el sistema en la toma de tiempos.</li>
 * </ul>
 *
 * @author José Luis Obiang Ela Nanguang
 * @version 1.0 2024-03-18
 * {@link JFrame}, {@link JPanel}, {@link JTextArea}, {@link JTable}, {@link JScrollPane}, {@link GridLayout}, {@link BorderLayout}, {@link Algoritmos}, {@link InterfazAlgoritmos}
 */


public class Main {

    public static void main(String[] args) {
        // Crear una instancia de la clase Algoritmos
        Algoritmos al = new Algoritmos();

        // Definir los tamaños de los arrays a ordenar
        int[] tallas = {2000, 4000, 6000, 8000, 10000, 12000, 14000, 16000, 18000, 20000};

        // Definir el número de repeticiones por cada tamaño
        int repeticiones = 10;

        // Definir los casos
        String[] casos = {"Caso Mejor", "Caso Peor", "Caso Aleatorio"};

        // Array para almacenar los casos, las tallas y tiempos medios de ejecución de cada algoritmo
        String[][][] tiempos = new String[casos.length][tallas.length][al.algoritmos.length];

        // Iterar sobre los casos
        for (int i = 0; i < casos.length; i++) {
            JPanel casoPanel = new JPanel(new BorderLayout());
            String caso = casos[i];
            System.out.println("============================================================ " + caso + " ============================================================");

            // Iterar sobre los tamaños de los arrays
            for (int j = 0; j < tallas.length; j++) {
                // Crear una ventana Swing
                JFrame frame = new JFrame("Arrays - " + caso + " - Talla: " + tallas[j]);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // Crear un panel principal para la ventana
                JPanel panel = new JPanel(new GridLayout(2, 1));
                JTextArea originalTextArea = new JTextArea();
                JTextArea ordenadoTextArea = new JTextArea();

                int talla = tallas[j];
                long[] tiemposTotales = new long[al.algoritmos.length];
                System.out.println("Talla del array: " + talla);

                // Generar el array según el caso
                int[] arrayOriginal = al.generarArray(talla, caso);
                // Mostrar el array original (solo para comprobar que funciona)
                System.out.println("Array original: " + Arrays.toString(arrayOriginal));

                int[] arrayOrdenado = new int[talla];
                // Iterar sobre las repeticiones
                for (int l = 0; l < al.algoritmos.length; l++) {
                    // Medir el tiempo de ejecución de cada algoritmo de ordenación
                    long inicio = System.currentTimeMillis();
                    for (int k = 0; k < repeticiones; k++) {
                        // Ordenar el array con el algoritmo correspondiente
                        arrayOrdenado = switch (l) {
                            case 0 -> al.bubbleSort(Arrays.copyOf(arrayOriginal, arrayOriginal.length));
                            case 1 -> al.selectionSort(Arrays.copyOf(arrayOriginal, arrayOriginal.length));
                            case 2 -> al.quickSort(Arrays.copyOf(arrayOriginal, arrayOriginal.length), 0, arrayOriginal.length - 1);
                            case 3 -> al.cocktailSort(Arrays.copyOf(arrayOriginal, arrayOriginal.length));
                            default -> arrayOriginal;
                        };

                        long fin = System.currentTimeMillis();
                        tiemposTotales[l] += (fin - inicio);
                    }
                }

                // Calcular los tiempos medios
                for (int l = 0; l < al.algoritmos.length; l++) {
                    tiempos[i][j][l] = String.valueOf(tiemposTotales[l] / (double) repeticiones);
                    // Mostrar el array ordenado con el algoritmo correspondiente (solo para comprobar que funciona)
                    System.out.println("Array ordenado con algoritmo " + al.algoritmos[l] + ": " + Arrays.toString(arrayOrdenado));
                }

                // Mostrar el array original y el array ordenado
                originalTextArea.setEditable(false);
                originalTextArea.append("Array Original (" + caso + " - Talla: " + talla + "):\n" + Arrays.toString(arrayOriginal));
                ordenadoTextArea.setEditable(false);
                ordenadoTextArea.append("Array Ordenado (" + caso + " - Talla: " + talla + "):\n" + Arrays.toString(arrayOrdenado));

                panel.add(originalTextArea);
                panel.add(ordenadoTextArea);

                frame.add(panel);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
                frame.setVisible(true);


            }

            System.out.println("========================================================================================================================================\n");
        }

        // Mostrar los resultados finales en una tabla
        al.mostrarResultados(casos, tallas, tiempos);

        JFrame frameFinal = new JFrame("Análisis experimental");
        frameFinal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Crear un panel principal para la ventana
        JPanel panelFinal = new JPanel(new GridLayout(1, casos.length));

        // Crear y agregar tablas para cada caso
        for (int i = 0; i < casos.length; i++) {
            String caso = casos[i];
            JTable table = al.createTable(caso, tallas, tiempos[i]);
            JScrollPane scrollPane = new JScrollPane(table);
            panelFinal.add(scrollPane);
        }

        // Agregar el panel principal a la ventana
        frameFinal.add(panelFinal);

        // Configurar la ventana y hacerla visible
        frameFinal.setSize(800, 600);
        frameFinal.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        frameFinal.setVisible(true);
    }


}
