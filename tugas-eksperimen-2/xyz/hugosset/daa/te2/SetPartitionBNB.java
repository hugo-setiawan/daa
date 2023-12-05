package xyz.hugosset.daa.te2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SetPartitionBNB {
    // Simulate pass-by-reference behaviour by setting class var
    public static boolean[] bestAssignment;
    public static int bestErr;

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

    public static boolean findPartitionBNB(int[] arr, int n) {
        boolean[] testAssignment = new boolean[n];

        int totalValue = Arrays.stream(arr).sum();
        bestErr = totalValue;

        findPartitionBNBFromIndex(arr, 0, totalValue, totalValue, testAssignment, 0);

        return bestErr == 0;
    }

    public static void findPartitionBNBFromIndex(
        int[] arr, int startIndex, int totalValue, int unassignedValue,
        boolean[] testAssignment, int testValue
    ) {
        if (startIndex >= arr.length) {
            int testErr = Math.abs(2 * testValue - totalValue);

            if (testErr < bestErr) {
                bestErr = testErr;
                bestAssignment = Arrays.copyOf(testAssignment, testAssignment.length);
            }

        } else {
            int testErr = Math.abs(2 * testValue - totalValue);

            if (testErr - unassignedValue < bestErr)  {
                unassignedValue -= arr[startIndex];

                testAssignment[startIndex] = true;
                findPartitionBNBFromIndex(
                    arr, startIndex + 1, totalValue, unassignedValue,
                    testAssignment, testValue + arr[startIndex]
                );

                testAssignment[startIndex] = false;
                findPartitionBNBFromIndex(
                    arr, startIndex + 1, totalValue, unassignedValue,
                    testAssignment, testValue
                );
            }
        }
    }

    public static void main(String[] args) {
        String datasetFilePath = "data_besar.csv"; // UBAH SAYA untuk mengubah dataset yang diuji.

        Runtime runtime = Runtime.getRuntime();

        int[] arr = readIntsFromCSV(datasetFilePath);

        System.gc();

        long startTime = System.nanoTime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

        System.out.println(findPartitionBNB(arr, arr.length));

        long endTime = System.nanoTime();
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Dataset: " + datasetFilePath);
        System.out.println("Execution time in microseconds: " + ((endTime - startTime) / 1000));
        System.out.println("Used memory by algorithm: " + (usedMemoryAfter - usedMemoryBefore));
    }
}
