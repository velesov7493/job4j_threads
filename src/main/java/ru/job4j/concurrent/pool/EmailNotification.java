package ru.job4j.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private ExecutorService pool;

    public EmailNotification() {
        pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    // Метод-заглушка
    private void send(String subject, String body, String email) {

    }

    public void emailTo(User user) {
        Runnable task = () -> {
            String subject = String.format(
                    "Notification %s to email %s",
                    user.getUserName(), user.getEmail()
            );
            String body = String.format(
                    "Add a new event to %s",
                    user.getUserName()
            );
            send(subject, body, user.getEmail());
        };
        pool.submit(task);
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
