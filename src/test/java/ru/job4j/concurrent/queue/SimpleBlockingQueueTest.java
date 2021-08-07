package ru.job4j.concurrent.queue;

import org.junit.Test;

import static org.junit.Assert.*;

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

}