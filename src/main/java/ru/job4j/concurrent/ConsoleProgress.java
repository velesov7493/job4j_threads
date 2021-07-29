package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] indicator = {'-', '\\', '|', '/'};
        try {
            for (int i = 0; i <= 100; i++) {
                int j = i % 4;
                System.out.print("\rЗагрузка: " + indicator[j] + "[...]");
                Thread.sleep(200);
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            System.out.print("\rЗагрузка - [готово]");
        } catch (InterruptedException ex) {
            System.out.print("\rЗагрузка - [отменено]");
        }

    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            System.out.println("Процесс прерван!");
        }
        progress.interrupt();
    }
}
