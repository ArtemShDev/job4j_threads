package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);
    int lastCount;

    public void increment() {
        do {
            lastCount = count.get();
        } while (!count.compareAndSet(lastCount, lastCount + 1));
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CASCount casCount = new CASCount();
        Runnable runnable = () ->
        {
            for (int i = 0; i < 5; i++) {
                casCount.increment();
            }
        };
        Thread threadInc1 = new Thread(runnable);
        Thread threadInc2 = new Thread(runnable);
        Thread threadGet = new Thread(() ->
        System.out.println(casCount.get())
        );
        threadInc1.start();
        threadInc2.start();
        threadInc1.join();
        threadInc2.join();
        threadGet.start();
        threadGet.join();
    }
}