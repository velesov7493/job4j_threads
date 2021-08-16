package ru.job4j.concurrent.threads;

public class Wget {

    public static void main(String[] args) {
        Thread progressTask = new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    System.out.print("\rЗагрузка: " + i + "%");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        });
        progressTask.start();
    }
}
