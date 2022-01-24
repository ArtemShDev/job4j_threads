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
    private int capacity = 5;

    public void offer(T value) {
        synchronized (this) {
            System.out.println("Thread producer " + Thread.currentThread().getName() + " starts");
            while (queue.size() == capacity) {
                try {
                    System.out.println("Thread producer " + Thread.currentThread().getName() + " is waiting ...");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(value);
            this.notify();
            System.out.println("Thread producer " + Thread.currentThread().getName() + " ends");
        }
    }

    public T poll() {
        synchronized (this) {
            System.out.println("Thread consumer " + Thread.currentThread().getName() + " starts");
            while (queue.size() == 0) {
                try {
                    System.out.println("Thread consumer " + Thread.currentThread().getName() + " is waiting ...");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T value = queue.poll();
            this.notify();
            System.out.println("Thread consumer " + Thread.currentThread().getName() + " ends");
            return value;
        }
    }

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>();
        Random random = new Random();
        Runnable producer = () -> {
            while (true) {
                sbq.offer(random.nextInt(5));
            }
        };
        Runnable consumer = () -> {
            while (true) {
                sbq.poll();
            }
        };
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}