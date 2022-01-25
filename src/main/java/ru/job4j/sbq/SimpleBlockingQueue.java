package ru.job4j.sbq;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == capacity) {
                this.wait();
            }
            queue.offer(value);
            this.notify();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.size() == 0) {
                this.wait();
            }
            T value = queue.poll();
            this.notify();
            return value;
        }
    }

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(5);
        Random random = new Random();
        Runnable producer = () -> {
            while (true) {
                try {
                    sbq.offer(random.nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable consumer = () -> {
            while (true) {
                try {
                    sbq.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}