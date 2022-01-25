package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {
    @Test
    public void whenIncrement10PerThreadThan20() throws InterruptedException {
        CASCount casCount = new CASCount();
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                casCount.increment();
            }
        };
        Thread threadInc1 = new Thread(runnable);
        Thread threadInc2 = new Thread(runnable);
        threadInc1.start();
        threadInc2.start();
        threadInc1.join();
        threadInc2.join();
        assertThat(casCount.get(), is(20));
    }
}