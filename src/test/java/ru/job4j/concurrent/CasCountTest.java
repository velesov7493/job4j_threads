package ru.job4j.concurrent;

import org.junit.Test;

import static org.junit.Assert.*;

public class CasCountTest {

    @Test
    public void whenIncrement() throws InterruptedException {
        CasCount counter = new CasCount(0);
        Thread first = new Thread(() -> {
            counter.increment();
            counter.increment();
            counter.increment();
            counter.increment();
        });
        Thread second = new Thread(() -> {
            counter.increment();
            counter.increment();
            counter.increment();
            counter.increment();
            counter.increment();
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertEquals(9, counter.get());
    }
}