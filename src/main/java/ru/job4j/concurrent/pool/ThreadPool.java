package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.queue.BlockingQueue;
import ru.job4j.concurrent.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool  extends Thread {

    private final List<Thread> threads;
    private final BlockingQueue<Runnable> tasks;
    private final int maxThreads;

    public ThreadPool() {
        maxThreads = Runtime.getRuntime().availableProcessors();
        threads = new LinkedList<>();
        tasks = new SimpleBlockingQueue<>(20);
        setPriority(Thread.MIN_PRIORITY);
    }

    @Override
    public void run() {
        int i = 0;
        while (!isInterrupted()) {
            Thread entry = threads.get(i);
            if (entry != null && !entry.isAlive()) {
                threads.remove(i);
            }
            if (threads.size() < maxThreads) {
                entry = new Thread(tasks.poll());
                threads.add(entry);
                entry.start();
            }
            i = (i + 1) % threads.size();
        }
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread t : threads) {
            if (t.isAlive()) {
                t.interrupt();
            }
        }
    }
}
