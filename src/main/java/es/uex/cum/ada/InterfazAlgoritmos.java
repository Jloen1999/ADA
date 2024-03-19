package es.uex.cum.ada;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Interfaz que contiene todas las funciones a usar en el programa
 * @author Jose Luis Obiang Ela Nanguang
 * @version 1.0 2024-03-18
 */

public interface InterfazAlgoritmos {
    int[] generarArray(int talla, String caso);
    void mostrarResultados(String[] casos, int[] tallas, String[][][] tiempos);
    int[] bubbleSort(int arr[]);
    int[] selectionSort(int arr[]);
    int[] cocktailSort(int arr[]);
    int[] quickSort(int a[], int izq, int der);
    void intercambiar(int a[], int ind1, int ind2);
    int mediana3(int a[], int izq, int der);
    int[] generateRandomArray(int size);
    int[] generateSortedArray(int size);
    int[] generateReverseSortedArray(int size);
    JTable createTable(String caso, int[] tallas, String[][] tiempos);


}
