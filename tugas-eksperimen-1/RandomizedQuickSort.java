import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/*
 * Acknowledgment: pseudocode taken from Slides DAA-C by Ammar Fathin Sabili (2023).
 * Java implementation by Hugo Sulaiman Setiawan.
 */
public class RandomizedQuickSort {
    public static double[] readDoublesFromCSV(String fileName) {
        ArrayList<Double> doubles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                doubles.add(Double.parseDouble(line.strip()));
            }
        } catch (IOException e) {
            System.err.println("ERROR: Invalid CSV format!");
            e.printStackTrace();
        }

        return doubles.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
    }

    public static void printArray(double arr[]){
        for(int i=0; i < arr.length; i++){
            System.out.print(arr[i]+" ");
        }

        System.out.println();
    }

    public static void swap(double arr[], int left, int right) {
        double temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    public static int randomizedPartition(double arr[], int left, int right) {
        Random random = new Random();
        int randomNumber = random.nextInt(right);

        swap(arr, randomNumber, right);

        double pivot = arr[right];

        int lastFilled = left - 1;

        for (int i = left; i < right; i++) {
            if (arr[i] < pivot) {
                lastFilled++;
                swap(arr, lastFilled, i);
            }
        }

        lastFilled++;

        swap(arr, lastFilled, right);

        return lastFilled;
    }

    public static void randomizedQuickSort(double arr[], int left, int right) {
        if (left < right) {
            int finalPivotPos = randomizedPartition(arr, left, right);
            randomizedQuickSort(arr, left, finalPivotPos - 1);
            randomizedQuickSort(arr, finalPivotPos + 1, right);
        }
    }

    public static void main(String[] args) {
        String datasetFilePath = "reversed_besar.csv"; // UBAH SAYA untuk mengubah dataset yang diuji.

        Runtime runtime = Runtime.getRuntime();

        double[] arr = readDoublesFromCSV(datasetFilePath);

        System.gc();

        long startTime = System.nanoTime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

        randomizedQuickSort(arr, 0, arr.length - 1);

        long endTime = System.nanoTime();
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();

        // printArray(arr);

        System.out.println("Dataset: " + datasetFilePath);
        System.out.println("Execution time in microseconds: " + ((endTime - startTime) / 1000));
        System.out.println("Used memory by algorithm: " + (usedMemoryAfter - usedMemoryBefore));

    }

}
