package ru.job4j.concurrent.queue;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        final Thread producer = new Thread(() -> {
            for (int index = 0; index != 3; index++) {
                queue.offer(index);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        final Thread consumer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Integer val = queue.poll();
                if (val == null) {
                    continue;
                }
                System.out.println(val);
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
    }
}
