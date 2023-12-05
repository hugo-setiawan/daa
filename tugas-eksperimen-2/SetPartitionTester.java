import xyz.hugosset.daa.te2.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SetPartitionTester {
    public static final String[] DATASET_FILENAMES = {"data_kecil.csv", "data_sedang.csv", "data_besar.csv"};

    public static int[] readIntsFromCSV(String fileName) {
        ArrayList<Integer> ints = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                ints.add(Integer.parseInt(line.strip()));
            }
        } catch (IOException e) {
            System.err.println("ERROR: Invalid CSV format!");
            e.printStackTrace();
        }

        return ints.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        int[] arr;

        long startTime, endTime;
        long usedMemoryBefore, usedMemoryAfter;
        for (String datasetFile: DATASET_FILENAMES) {
            System.out.println();
            System.out.println();

            System.out.println("========== Dataset: " + datasetFile);

            arr = readIntsFromCSV(datasetFile);

            System.out.println("=== DP ===");
            System.gc();
            System.gc();

            startTime = System.nanoTime();
            usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

            System.out.println(SetPartitionDP.findPartitionDP(arr, arr.length));

            endTime = System.nanoTime();
            usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();

            System.out.println("Execution time in microseconds: " + ((endTime - startTime) / 1000));
            System.out.println("Used memory by algorithm: " + (usedMemoryAfter - usedMemoryBefore));

            ///
            System.out.println();
            ///
            
            System.out.println("=== BnB ===");
            System.gc();
            System.gc();

            startTime = System.nanoTime();
            usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

            System.out.println(SetPartitionBNB.findPartitionBNB(arr, arr.length));

            endTime = System.nanoTime();
            usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();

            System.out.println("Execution time in microseconds: " + ((endTime - startTime) / 1000));
            System.out.println("Used memory by algorithm: " + (usedMemoryAfter - usedMemoryBefore));

        }


    }
}
