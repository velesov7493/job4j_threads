package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.queue.BlockingQueue;
import ru.job4j.concurrent.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final BlockingQueue<Runnable> tasks;

    private class PoolThread extends Thread {

        @Override
        public void run() {
            while (!isInterrupted()) {
                Runnable task = tasks.poll();
                task.run();
            }
        }
    }

    public ThreadPool(int maxTasks) {
        int maxThreads = Runtime.getRuntime().availableProcessors();
        tasks = new SimpleBlockingQueue<>(maxTasks);
        for (int i = 0; i < maxThreads; i++) {
            PoolThread t = new PoolThread();
            threads.add(t);
            t.start();
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
