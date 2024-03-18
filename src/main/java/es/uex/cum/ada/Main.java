package es.uex.cum.ada;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
 * @version 1.0
 * {@link JFrame}
 */

public class Main extends JFrame {
    public static void main(String[] args) {
        Main run = new Main();
        run.setVisible(true);
    }

    public Main() {
        setTitle("Análisis temporal del comportamiento de los algoritmos de ordenación");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);

        //int[] sizes = {2000, 4000, 6000, 8000, 10000, 12000, 14000, 16000, 18000, 20000}; // Tamaños de problema crecientes
        int[] sizes = {5, 12, 14}; // Tamaños de problema crecientes
        int[] arrayOrdenado = new int[sizes.length];

        JPanel panel = new JPanel(new GridLayout(3, 1)); // GridLayout con 3 filas y 1 columna

        // Crear y mostrar tabla para el caso mejor
        DefaultTableModel bestCaseModel = new DefaultTableModel(sizes.length + 1, 6);
        bestCaseModel.setColumnIdentifiers(new String[]{"Tipo de Caso", "Talla", "Bubble Sort", "Selection Sort", "Quick Sort", "Cocktail Sort"});
        for (int i = 0; i < sizes.length; i++) {
            bestCaseModel = tipoCaso(sizes[i], i, arrayOrdenado, bestCaseModel, 1);
        }
        JTable bestCaseTable = new JTable(bestCaseModel);
        bestCaseTable.setValueAt("Caso Mejor", 0, 0);
        JScrollPane bestCaseScrollPane = new JScrollPane(bestCaseTable);
        panel.add(bestCaseScrollPane);


        // Crear y mostrar tabla para el caso peor
        DefaultTableModel worstCaseModel = new DefaultTableModel(sizes.length + 1, 6);
        worstCaseModel.setColumnIdentifiers(new String[]{"Tipo de Caso", "Talla", "Bubble Sort", "Selection Sort", "Quick Sort", "Cocktail Sort"});
        for (int i = 0; i < sizes.length; i++) {
            worstCaseModel = tipoCaso(sizes[i], i, arrayOrdenado, worstCaseModel, 2);
        }
        JTable worstCaseTable = new JTable(worstCaseModel);
        worstCaseTable.setValueAt("Caso Peor", 0, 0);
        JScrollPane worstCaseScrollPane = new JScrollPane(worstCaseTable);
        panel.add(worstCaseScrollPane);


        // Crear y mostrar tabla para el caso medio
        DefaultTableModel avgCaseModel = new DefaultTableModel(sizes.length + 1, 6);
        avgCaseModel.setColumnIdentifiers(new String[]{"Tipo de Caso", "Talla", "Bubble Sort", "Selection Sort", "Quick Sort", "Cocktail Sort"});
        for (int i = 0; i < sizes.length; i++) {
            avgCaseModel = tipoCaso(sizes[i], i, arrayOrdenado, avgCaseModel, 3);
        }
        JTable avgCaseTable = new JTable(avgCaseModel);
        avgCaseTable.setValueAt("Caso Aleatorio/Medio", 0, 0);
        JScrollPane avgCaseScrollPane = new JScrollPane(avgCaseTable);
        panel.add(avgCaseScrollPane);

        add(panel);

    }

    private static DefaultTableModel tipoCaso(int size, int i, int[] arrayOrdenado, DefaultTableModel model, int tipo) {

        int[] data = new int[0];
        switch (tipo) {
            case 1:
                JOptionPane.showMessageDialog(null, "Caso Mejor", "Caso Mejor", JOptionPane.INFORMATION_MESSAGE);
                data = generateSortedArray(size);
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Caso Peor", "Caso Peor", JOptionPane.INFORMATION_MESSAGE);
                data = generateReverseSortedArray(size);
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Caso Aleatorio/Medio", "Caso Aleatorio/Medio", JOptionPane.INFORMATION_MESSAGE);
                data = generateRandomArray(size);
                break;
            default:
                System.out.println("Este caso no existe");
                break;
        }

        mostrarArray(data, "Array Original", size);


        long startTime = 0;
        long bubbleTime = 0;
        for (int j = 0; j < size; j++) {
            startTime = System.currentTimeMillis();
            arrayOrdenado = bubbleSort(Arrays.copyOf(data, data.length)); // Copia del array para mantener la integridad de los datos
            bubbleTime += System.currentTimeMillis() - startTime;
        }

        mostrarArray(arrayOrdenado, "Bubble Sort", size);
        double bubbleTimeDecimal = bubbleTime/(double) size;
        System.out.println((bubbleTime/(double) size) + " ms");

        long selectionTime = 0;
        for (int j = 0; j < size; j++) {
            startTime = System.currentTimeMillis();
            arrayOrdenado = selectionSort(Arrays.copyOf(data, data.length));
            selectionTime += System.currentTimeMillis() - startTime;
        }

        mostrarArray(arrayOrdenado, "Selection Sort", size);
        double selectionTimeDecimal = selectionTime/(double) size;
        System.out.println(selectionTimeDecimal + " ms");

        long quickTime = 0;
        for (int j = 0; j < size; j++) {
            startTime = System.currentTimeMillis();
            arrayOrdenado = quickSort(Arrays.copyOf(data, data.length), 0, data.length - 1);
            quickTime += System.currentTimeMillis() - startTime;
        }

        mostrarArray(arrayOrdenado, "Quick Sort", size);
        double quickTimeDecimal = quickTime/(double) size;
        System.out.println(quickTimeDecimal + " ms");

        long cocktailTime = 0;
        for (int j = 0; i < size; j++) {
            startTime = System.currentTimeMillis();
            arrayOrdenado = cocktailSort(Arrays.copyOf(data, data.length));
            cocktailTime += System.currentTimeMillis() - startTime;
        }

        mostrarArray(arrayOrdenado, "Cocktail Sort", size);
        double cocktailTimeDecimal = cocktailTime/(double) size;
        System.out.println(cocktailTimeDecimal + " ms");


        model.setValueAt(size, i + 1, 1);
        model.setValueAt(bubbleTimeDecimal, i + 1, 2);
        model.setValueAt(selectionTimeDecimal, i + 1, 3);
        model.setValueAt(quickTimeDecimal, i + 1, 4);
        model.setValueAt(cocktailTimeDecimal, i + 1, 5);

        return model;
    }

    /* Método para ordenar un array usando bubble sort
     * @param arr vector a ordenar
     */
    public static int[] bubbleSort(int arr[]) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false; // Para detectar si el array ya está ordenado
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Intercambia arr[j+1] y arr[j]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true; // Se realizó un intercambio
                }
            }
            // Si no se hizo ningún intercambio, el array ya está ordenado
            if (!swapped)
                break;
        }

        return arr;
    }

    /**
     * Algoritmo de ordenacion Selection-Sort.
     * Divide la lista en dos partes: la sublista ordenada y la sublista no ordenada.
     * En cada paso, busca el elemento más pequeño de la sublista no ordenada y lo
     * intercambia con el primer elemento no ordenado. Esto expande la sublista ordenada hacia la derecha.
     *
     * @param arr vector a ordenar
     **/
    public static int[] selectionSort(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap the found minimum element with the first element
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }

        return arr;
    }


    /**
     * Algoritmo de ordenacion quicksort (Hoare -1963-). Utiliza el algoritmo de
     * particion debido a Weiss, con mediana de 3 para el calculo del pivote.
     * Adaptacion de la implementacion de los profesores de EDA del grado de informatica de la UPV
     * quicksort:
     * <a href="https://en.wikipedia.org/wiki/Quicksort">Documentación QuickSort</a>
     *
     * @param a   vector a ordenar
     * @param izq indice del limite inferior a ser ordenado
     * @param der indice del limite superior a ser ordenado
     **/
    public static int[] quickSort(int a[], int izq, int der) {
        if (izq < der) {
            int pivot = mediana3(a, izq, der);
            int i = izq;
            int j = der - 1;
            for (; i < j; ) {
                while (pivot - a[++i] > 0) ;
                while (pivot - a[--j] < 0) ;
                intercambiar(a, i, j);
            }
            intercambiar(a, i, j); // Deshacer el ultimo cambio
            intercambiar(a, i, der - 1); // Restaurar el pivote
            quickSort(a, izq, i - 1); // Ordenar recursivamente los elementos menores
            quickSort(a, i + 1, der); // Ordenar recursivamente los elementos mayores
        }

        return a;

    }

    // Metodo para intercambiar dos elementos de un array
    private static void intercambiar(int a[], int ind1, int ind2) {
        int tmp = a[ind1];
        a[ind1] = a[ind2];
        a[ind2] = tmp;
    }

    /**
     * Metodo para calculo de la Mediana de 3, devuelve el valor del pivote
     */
    private static int mediana3(int a[], int izq, int der) {
        int c = (izq + der) / 2;
        if (a[c] - (a[izq]) < 0)
            intercambiar(a, izq, c);
        if (a[der] - (a[izq]) < 0)
            intercambiar(a, izq, der);
        if (a[der] - (a[c]) < 0)
            intercambiar(a, c, der);
        // (ocultar el pivote en la posicion der-1
        intercambiar(a, c, der - 1);
        return a[der - 1];
    }


//	---------  Cocktail sort -------------------

    /**
     * Cocktail shaker sort (cocktail sort), also known as bidirectional bubble sort
     * <a href="https://en.wikipedia.org/wiki/Cocktail_shaker_sort">Documentación CocktailSort</a>
     * <p>
     * Implementación obtenida de:
     * <a href="https://en.wikibooks.org/wiki/Algorithm_Implementation/Sorting/Cocktail_sort">Algorith_Implementation Sorting CocktailSort</a>
     *
     * @param numbers, recibe el vector a ordenar
     **/
    public static int[] cocktailSort(int[] numbers) {
        boolean swapped = true;
        int i = 0;
        int j = numbers.length - 1;
        while (i < j && swapped) {
            swapped = false;
            for (int k = i; k < j; k++) {
                if (numbers[k] > numbers[k + 1]) {
                    int temp = numbers[k];
                    numbers[k] = numbers[k + 1];
                    numbers[k + 1] = temp;
                    swapped = true;
                }
            }
            j--;
            if (swapped) {
                swapped = false;
                for (int k = j; k > i; k--) {
                    if (numbers[k] < numbers[k - 1]) {
                        int temp = numbers[k];
                        numbers[k] = numbers[k - 1];
                        numbers[k - 1] = temp;
                        swapped = true;
                    }
                }
            }
            i++;
        }

        return numbers;

    }

    /* Método para mostrar los valores de un array en un cuadro de diálogo
     * @param arr vector a mostrar
     * @param titulo título del cuadro de diálogo
     */
    public static void mostrarArray(int[] arr, String titulo, int size) {
        StringBuilder sb = new StringBuilder();
        sb.append(titulo).append(":\n");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
            sb.append((i + 1) % 10 == 0 ? "\n" : " ");
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Valores del Array: Talla->"+size, JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para generar un array aleatorio de tamaño 'size'
    public static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * Integer.MAX_VALUE); // Números aleatorios entre 0 y el maximo valor del tipo dato entero
        }

        return arr;
    }

    // Método para generar un array ordenado de tamaño 'size'
    public static int[] generateSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }

        return arr;
    }

    // Método para generar un array ordenado de manera inversa de tamaño 'size'
    public static int[] generateReverseSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }

        return arr;
    }



    // Método para obtener el color basado en el tiempo de ejecución
    public static Color getColorBasedOnTime(double time) {
        if (time < 100.0) {
            return Color.GREEN;
        } else if (time < 500.0) {
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }

    // Método para convertir un objeto Color a su representación hexadecimal
    public static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

}