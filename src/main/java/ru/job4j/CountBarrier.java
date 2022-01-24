package ru.job4j;

public class CountBarrier {

    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            System.out.println("Thread " + Thread.currentThread().getName() + " starts");
            while (count < total) {
                try {
                    System.out.println("Thread " + Thread.currentThread().getName() + " is waiting...");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Thread " + Thread.currentThread().getName() + " ends");
        }
    }
}