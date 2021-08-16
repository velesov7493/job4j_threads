package ru.job4j.concurrent.threads;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] indicator = {'-', '\\', '|', '/'};
        try {
            int i = 0;
            while (!Thread.currentThread().isInterrupted() && i <= 100) {
                int j = i % 4;
                System.out.print("\rЗагрузка: " + indicator[j] + "[...]");
                Thread.sleep(200);
                i++;
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
