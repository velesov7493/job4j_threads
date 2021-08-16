package ru.job4j.concurrent.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class RowColSumTest {

    @Test
    public void whenSyncSum() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RowColSum.Sums[] sums = RowColSum.sum(matrix);
        assertEquals(12, sums[0].getColSum());
        assertEquals(6, sums[0].getRowSum());
        assertEquals(15, sums[1].getColSum());
        assertEquals(15, sums[1].getRowSum());
        assertEquals(18, sums[2].getColSum());
        assertEquals(24, sums[2].getRowSum());
    }

    @Test
    public void whenAsyncSum() throws InterruptedException, ExecutionException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RowColSum.Sums[] sums = RowColSum.asyncSum(matrix);
        assertEquals(12, sums[0].getColSum());
        assertEquals(6, sums[0].getRowSum());
        assertEquals(15, sums[1].getColSum());
        assertEquals(15, sums[1].getRowSum());
        assertEquals(18, sums[2].getColSum());
        assertEquals(24, sums[2].getRowSum());
    }

}