package ru.job4j.concurrent.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    public static class Sums {

        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int taskIndex) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            for (int i = 0; i < matrix.length; i++) {
                sums.rowSum += matrix[taskIndex][i];
                sums.colSum += matrix[i][taskIndex];
            }
            return sums;
        });
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (result[i] == null) {
                    result[i] = new Sums();
                }
                if (result[j] == null) {
                    result[j] = new Sums();
                }
                result[j].colSum += matrix[i][j];
                result[i].rowSum += matrix[i][j];
            }
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws InterruptedException, ExecutionException {
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }
        for (Integer key: futures.keySet()) {
            result[key] = futures.get(key).get();
        }
        return result;
    }
}
