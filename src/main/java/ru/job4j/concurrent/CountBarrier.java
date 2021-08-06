package ru.job4j.concurrent;

public class CountBarrier {

    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        count++;
        monitor.notifyAll();
    }

    public void await() {
        while (count < total) {
            try {
                monitor.wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
