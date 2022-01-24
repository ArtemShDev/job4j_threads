package ru.job4j.sync;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStore {

    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        users.put(user.getId(), new User(user.getId(), user.getAmount()));
        return true;
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), new User(user.getId(), user.getAmount())) != null;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), new User(user.getId(), user.getAmount()));
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User userFrom = users.get(fromId);
        if (userFrom.getAmount() >= amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            User userTo = users.get(toId);
            userTo.setAmount(userTo.getAmount() + amount);
            update(userTo);
        }
    }
}
