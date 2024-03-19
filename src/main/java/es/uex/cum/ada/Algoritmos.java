package es.uex.cum.ada;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Clase Algoritmos que implementa la interfaz InterfazAlgoritmos
 * @author José Luis Obiang Ela Nanguang
 * @version 1.0 2024-03-18
 * {@link InterfazAlgoritmos}
 */

public class Algoritmos implements InterfazAlgoritmos {
    // Definir los algoritmos de ordenación
    public final String[] algoritmos = {"BubbleSort", "SelectionSort", "QuickSort", "CocktailSort"};

    /**
     * Método para generar el array según el caso
     *
     * @param talla Variable de tipo entero que representa el tamaño del array
     * @param caso  Variable de tipo String que representa el caso a generar
     * @return array
     */
    @Override
    public int[] generarArray(int talla, String caso) {
        int[] array = new int[talla];
        Random random = new Random();

        // Copiar el array aleatorio
        array = switch (caso) {
            case "Caso Mejor" -> Arrays.copyOf(generateSortedArray(talla), talla); // Copiar el array ordenado
            case "Caso Peor" ->
                    Arrays.copyOf(generateReverseSortedArray(talla), talla); // Copiar el array ordenado de manera inversa
            case "Caso Aleatorio" -> Arrays.copyOf(generateRandomArray(talla), talla);
            default -> array;
        };

        return array;
    }

    /**
     * Método para mostrar los resultados finales en una tabla
     *
     * @param casos   Variable de tipo String que representa los casos
     * @param tallas  Variable de tipo entero que representa el tamaño del array
     * @param tiempos Variable de tipo String que representa los tiempos
     */
    @Override
    public void mostrarResultados(String[] casos, int[] tallas, String[][][] tiempos) {
        // Mostrar encabezados de casos
        System.out.print("     ");
        for (String caso : casos) {
            System.out.print("|       " + caso + "      ");
        }
        System.out.println("|");

        // Mostrar separador
        System.out.print("-----");
        for (int i = 0; i < casos.length; i++) {
            System.out.print("|-----------------------");
        }
        System.out.println("|");

        // Mostrar nombres de algoritmos
        System.out.print("      ");
        for (int i = 0; i < casos.length; i++) {
            for (String algoritmo : algoritmos) {
                System.out.print("   " + algoritmo.charAt(0) + " ");
            }
            System.out.print(" |");
        }

        System.out.println();

        // Mostrar tiempos
        for (int j = 0; j < tallas.length; j++) {
            System.out.print("   " + tallas[j] + "   ");
            for (int i = 0; i < casos.length; i++) {
                System.out.print("|");
                for (int k = 0; k < algoritmos.length; k++) {
                    System.out.print(" " + tiempos[i][j][k] + " ");
                }
            }
            System.out.println("|");
        }
    }


    /**
     * Método para ordenar un array usando bubble sort
     *
     * @param arr vector a ordenar
     */
    @Override
    public int[] bubbleSort(int arr[]) {
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
    public int[] selectionSort(int arr[]) {
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
     * @return vector ordenado
     **/
    public int[] quickSort(int a[], int izq, int der) {
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

    /**
     * Metodo para intercambiar dos elementos de un array
     *
     * @param a    vector a ordenar
     * @param ind1 indice del primer elemento
     * @param ind2 indice del segundo elemento
     */
    public void intercambiar(int a[], int ind1, int ind2) {
        int tmp = a[ind1];
        a[ind1] = a[ind2];
        a[ind2] = tmp;
    }

    /**
     * Metodo para calculo de la Mediana de 3, devuelve el valor del pivote
     *
     * @param a   vector a ordenar
     * @param izq indice del limite inferior a ser ordenado
     * @param der indice del limite superior a ser ordenado
     * @return valor del pivote
     */
    public int mediana3(int a[], int izq, int der) {
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
     * @return vector ordenado
     **/
    public int[] cocktailSort(int[] numbers) {
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


    /** Método para generar un array aleatorio de tamaño 'size'
     * @param size Tamaño del array
     * @return array aleatorio
     */
    public int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * Integer.MAX_VALUE); // Números aleatorios entre 0 y el maximo valor del tipo dato entero
        }

        return arr;
    }

    /** Método para generar un array ordenado de tamaño 'size'
     * @param size Tamaño del array
     * @return array ordenado
     */
    public int[] generateSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }

        return arr;
    }

    /** Método para generar un array ordenado de manera inversa de tamaño 'size'
     * @param size Tamaño del array
     * @return array ordenado de manera inversa
     */
    public int[] generateReverseSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }

        return arr;
    }

    /**
     * Método para crear una tabla con los tiempos de ejecución
     * @param caso Nombre del caso
     * @param tallas Tamaños de los arrays
     * @param tiempos Tiempos de ejecución
     * @return tabla con los tiempos de ejecución
     */
    public JTable createTable(String caso, int[] tallas, String[][] tiempos) {
        // Crear un modelo de tabla
        DefaultTableModel model = new DefaultTableModel(tallas.length + 1, algoritmos.length + 1);
        model.setValueAt("Talla", 0, 0); // Encabezado de la primera columna
        model.setValueAt(algoritmos[0], 0, 1); // Encabezado de la primera columna
        model.setValueAt(algoritmos[1], 0, 2); // Encabezado de la primera columna
        model.setValueAt(algoritmos[2], 0, 3); // Encabezado de la primera columna
        model.setValueAt(algoritmos[3], 0, 4); // Encabezado de la primera columna

        // Llenar el modelo de tabla con los tamaños y tiempos
        for (int i = 0; i < tallas.length; i++) {
            model.setValueAt(tallas[i], i + 1, 0); // Tallas en la primera columna
            for (int j = 0; j < algoritmos.length; j++) {
                model.setValueAt(tiempos[i][j], i + 1, j + 1); // Tiempos en las columnas siguientes
            }
        }

        // Crear la tabla con el modelo
        JTable table = new JTable(model);

        // Establecer el nombre del caso como encabezado
        table.getTableHeader().setReorderingAllowed(false); // Deshabilitar el reordenamiento de columnas
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Establecer el tamaño de la fuente
        table.getTableHeader().setBackground(Color.LIGHT_GRAY);
        table.getTableHeader().setForeground(Color.BLACK);

        // Establecer el nombre del caso como encabezado
        table.setTableHeader(null);
        table.setRowHeight(25); // Altura de las filas
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Establecer el tamaño de la fuente

        // Configurar alineación de celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Alinear tallas a la izquierda
        for (int i = 1; i <= algoritmos.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer); // Alinear tiempos al centro
        }

        // Establecer el nombre del caso como título
        table.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), caso));
        return table;
    }
}
