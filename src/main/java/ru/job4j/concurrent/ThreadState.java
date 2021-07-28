package ru.job4j.concurrent;

public class ThreadState {

    private static void printStats(Thread t1, Thread t2) {
        System.out.print(t1.getState());
        System.out.print(" : ");
        System.out.println(t2.getState());
    }

    public static void main(String[] args) {
        Thread first = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        Thread second = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        printStats(first, second);
        first.start();
        second.start();
        while (
                first.getState() != Thread.State.TERMINATED
                && second.getState() != Thread.State.TERMINATED
        ) {
            printStats(first, second);
        }
        printStats(first, second);
    }
}
