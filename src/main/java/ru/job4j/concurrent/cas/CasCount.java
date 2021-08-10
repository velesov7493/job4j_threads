package ru.job4j.concurrent.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CasCount {

    private final AtomicReference<Integer> count;

    public CasCount(int aStartValue) {
        count = new AtomicReference<>(aStartValue);
    }

    public void increment() {
        Integer value;
        do {
            value = count.get();
        } while (!count.compareAndSet(value, value + 1));
    }

    public int get() {
        return count.get();
    }
}
