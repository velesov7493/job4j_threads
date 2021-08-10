package ru.job4j.concurrent.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

@ThreadSafe
public class SimpleBlockingQueue<T> implements BlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue;
    private final int maxSize;

    public SimpleBlockingQueue(int aMaxSize) {
        queue = new LinkedList<>();
        maxSize = aMaxSize;
    }

    private synchronized void lockByPredicate(Predicate<Queue> condition) {
        while (!Thread.currentThread().isInterrupted() && condition.test(queue)) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void offer(T value) {
        lockByPredicate((q) -> q.size() >= maxSize);
        queue.offer(value);
        notify();
    }

    public synchronized T poll() {
        lockByPredicate(Collection::isEmpty);
        T result;
        result = queue.poll();
        notify();
        return result;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
