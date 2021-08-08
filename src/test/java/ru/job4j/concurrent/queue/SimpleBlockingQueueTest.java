package ru.job4j.concurrent.queue;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class SimpleBlockingQueueTest {

    private class ProducerThread extends Thread {

        private BlockingQueue<Integer> queue;

        public ProducerThread(BlockingQueue<Integer> aQueue) {
            super();
            setName("producer-thread");
            queue = aQueue;
        }

        @Override
        public void run() {
            queue.offer(128);
            queue.offer(64);
            queue.offer(64);
            queue.offer(4);
            queue.offer(0);
            queue.offer(1);
        }
    }

    private class ConsumerThread extends Thread {

        private BlockingQueue<Integer> queue;

        public ConsumerThread(BlockingQueue<Integer> aQueue) {
            super();
            setName("consumer-thread");
            queue = aQueue;
        }

        @Override
        public void run() {
            int status = -1;
            while (!(interrupted() || status == 0)) {
                status = queue.poll();
            }
        }
    }

    @Test
    public void whenProducerStartsFirst() throws InterruptedException {
        BlockingQueue<Integer> numbers = new SimpleBlockingQueue<>(10);
        ProducerThread producer = new ProducerThread(numbers);
        ConsumerThread consumer = new ConsumerThread(numbers);
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        int number = numbers.poll();
        assertEquals(1, number);
    }

    @Test
    public void whenProducerStartsLast() throws InterruptedException {
        BlockingQueue<Integer> numbers = new SimpleBlockingQueue<>(5);
        ProducerThread producer = new ProducerThread(numbers);
        ConsumerThread consumer = new ConsumerThread(numbers);
        consumer.start();
        producer.start();
        producer.join();
        consumer.join();
        int number = numbers.poll();
        assertEquals(1, number);
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(6);
        Thread producer = new Thread(() -> IntStream.range(0, 5).forEach(queue::offer));
        producer.start();
        Thread consumer = new Thread(() -> {
            while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                buffer.add(queue.poll());
            }
        });
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}