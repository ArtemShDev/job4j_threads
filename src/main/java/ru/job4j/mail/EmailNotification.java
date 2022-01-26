package ru.job4j.mail;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String userName = user.getUsername();
        String email = user.getEmail();
        String subject = String.format("Notification %s to email %s.",
                userName, email);
        String body = String.format("Add a new event to %s.",
                userName);
        pool.submit(() -> send(subject, body, email));
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

    public void send(String subject, String body, String email) {
    }
}
