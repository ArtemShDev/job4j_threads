package ru.job4j.sync;

import org.junit.Test;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStoreTest {

    @Test
    public void whenAdd() throws InterruptedException {
        User userFrom = new User(1, 500);
        User userTo = new User(2, 100);
        UserStore userStore = new UserStore();
        Thread threadFrom = new Thread(() -> userStore.add(userFrom));
        Thread threadTo = new Thread(() -> userStore.add(userTo));
        threadFrom.start();
        threadFrom.join();
        threadTo.start();
        threadTo.join();
        assertThat(userStore.getUsers(), is(List.of(userFrom, userTo)));
    }

    @Test
    public void whenUpdate() throws InterruptedException {
        User userFrom = new User(1, 500);
        User userTo = new User(1, 300);
        UserStore userStore = new UserStore();
        Thread threadFrom = new Thread(() -> userStore.add(userFrom));
        Thread threadTo = new Thread(() -> userStore.update(userTo));
        threadFrom.start();
        threadFrom.join();
        threadTo.start();
        threadTo.join();
        assertThat(userStore.getUsers().get(0), is(userTo));
    }

    @Test
    public void whenTransferOK() throws InterruptedException {
        User userFrom = new User(1, 500);
        User userTo = new User(2, 300);
        UserStore userStore = new UserStore();
        Thread threadFrom = new Thread(() -> userStore.add(userFrom));
        Thread threadTo = new Thread(() -> userStore.add(userTo));
        Thread transfer = new Thread(() -> userStore.transfer(1, 2, 150));
        threadFrom.start();
        threadFrom.join();
        threadTo.start();
        threadTo.join();
        transfer.start();
        transfer.join();
        assertThat(userStore.getUsers().get(0).getAmount(), is(350));
        assertThat(userStore.getUsers().get(1).getAmount(), is(450));
    }

    @Test
    public void whenTransferFalse() throws InterruptedException {
        User userFrom = new User(1, 500);
        User userTo = new User(2, 300);
        UserStore userStore = new UserStore();
        Thread threadFrom = new Thread(() -> userStore.add(userFrom));
        Thread threadTo = new Thread(() -> userStore.add(userTo));
        Thread transfer = new Thread(() -> userStore.transfer(1, 2, 550));
        threadFrom.start();
        threadFrom.join();
        threadTo.start();
        threadTo.join();
        transfer.start();
        transfer.join();
        assertThat(userStore.getUsers().get(0).getAmount(), is(500));
        assertThat(userStore.getUsers().get(1).getAmount(), is(300));
    }
}