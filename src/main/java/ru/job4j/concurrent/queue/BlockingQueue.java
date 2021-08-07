package ru.job4j.concurrent.queue;

public interface BlockingQueue<T> {

    void offer(T value);

    T poll();
}
