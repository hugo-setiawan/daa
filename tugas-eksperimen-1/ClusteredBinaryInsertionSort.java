import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Acknowledgment: pseudocode and algorithm by Shubham Goel & Ravinder Kumar (2018).
 * Java implementation by Hugo Sulaiman Setiawan.
 */
public class ClusteredBinaryInsertionSort {
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

    public static void placeInserter(double arr[], int start, int end) {
        double temp = arr[end];

        for (int k = end; k > start; k--) {
            arr[k] = arr[k-1];
        }

        arr[start] = temp;
    }

    public static int binarySearch(double arr[], int left, int right, double key) {
        if (left == right) {
            if (arr[left] > key) {
                return left;
            } else {
                return left + 1;
            }
        }

        else if (left > right) {
            return left;
        }

        else {
            int middle = (int) Math.floor((left + right) / (double) 2.0);

            if (arr[middle] < key) {
                return binarySearch(arr, middle + 1, right, key);
            } else if (arr[middle] > key) {
                return binarySearch(arr, left, middle - 1, key);
            } else {
                return middle;
            }
        }
    }

    public static void clusteredBinaryInsertionSort(double arr[]) {
        // positionPointer menunjuk kepada lokasi insertion (place) pada iterasi sebelumnya (awalnya 0)
        int positionPointer = 0;

        // currentPointer menunjuk kepada indeks pertama dari bagian array yang belum tersortir
        int currentPointer;

        // place merupakan lokasi hasil binarySearch untuk memasukkan elemen array yang ditunjuk oleh currentPointer
        // place akan menjadi positionPointer untuk iterasi berikutnya
        int place;

        double key;

        for (int i = 1; i <= arr.length - 1; i++) {
            // memindahkan currentPointer ke posisi baru
            currentPointer = i;
            key = arr[currentPointer];

            // Lakukan binarySearch ke cluster kanan atau kiri jika dibagi pada positionPointer
            // Perlu diingat bahwa cluster kanan dan kiri sudah tersortir
            if (key >= arr[positionPointer]) {
                // cek cluster kanan
                place = binarySearch(arr, positionPointer + 1, currentPointer - 1, key);
            } else {
                // cek cluster kiri
                place = binarySearch(arr, 0, positionPointer - 1, key);
            }

            // memindahkan positionPointer ke posisi baru, dan melakukan insertion
            positionPointer = place;
            placeInserter(arr, place, currentPointer);
        }
    }
    public static void main(String[] args) {
        String datasetFilePath = "sorted_sedang.csv"; // UBAH SAYA untuk mengubah dataset yang diuji.

        Runtime runtime = Runtime.getRuntime();

        double[] arr = readDoublesFromCSV(datasetFilePath);

        System.gc();

        long startTime = System.nanoTime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

        clusteredBinaryInsertionSort(arr);

        long endTime = System.nanoTime();
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();

        // printArray(arr);

        System.out.println("Dataset: " + datasetFilePath);
        System.out.println("Execution time in microseconds: " + ((endTime - startTime) / 1000));
        System.out.println("Used memory by algorithm: " + (usedMemoryAfter - usedMemoryBefore));
    }
}
