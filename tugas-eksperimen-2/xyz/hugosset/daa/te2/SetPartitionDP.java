package xyz.hugosset.daa.te2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SetPartitionDP {
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

    public static boolean findPartitionDP(int[] arr, int n) {
        int sum = 0;
        int i, j;

        // Hitung sum semua elemen
        for (int elem: arr) {
            sum += elem;
        }

        // Jika sum semua elemen set bernilai ganjil, tidak mungkin membagi set
        // ke dalam dua partisi dengan jumlah yang sama
        if (sum % 2 != 0) {
            return false;
        }

        int target = sum / 2;

        // Buat 2D array untuk menyimpan hasil perhitungan DP (bottom-up)
        // Array dimensions: (targetSum + 1) x (n + 1)
        boolean dp[][] = new boolean[target + 1][n + 1];

        // Run a for loop for 0 <= i <= n and set part[0][i] equal to true as zero-sum is always possible
        // Zero sum is always possible with zero or more elements
        for (i = 0; i <= n; i++) {
            dp[0][i] = true;
        }

        // Run a for loop for 1 <= i <= sum/2 and set part[i][0] equal to zero as any sum with zero elements is never possible
        // Any sum with zero elements (except for dp[0][0]) is impossible
        for (i = 1; i <= target; i++) {
            dp[i][0] = false;
        }

        // Fill the partition table in bottom up manner
        for (i = 1; i <= target; i++) {
            for (j = 1; j <= n; j++) {
                dp[i][j] = dp[i][j - 1];

                if (i >= arr[j - 1]) {
                    dp[i][j] = dp[i][j] || dp[i - arr[j - 1]][j - 1];
                }
            }
        }

        return dp[target][n];
    }

    public static void main(String[] args) {
        String datasetFilePath = "data_kecil_bermasalah.csv"; // UBAH SAYA untuk mengubah dataset yang diuji.

        Runtime runtime = Runtime.getRuntime();

        int[] arr = readIntsFromCSV(datasetFilePath);

        System.gc();

        long startTime = System.nanoTime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

        System.out.println(findPartitionDP(arr, arr.length));

        long endTime = System.nanoTime();
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Dataset: " + datasetFilePath);
        System.out.println("Execution time in microseconds: " + ((endTime - startTime) / 1000));
        System.out.println("Used memory by algorithm: " + (usedMemoryAfter - usedMemoryBefore));
    }
}
