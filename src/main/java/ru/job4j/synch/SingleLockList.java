package ru.job4j.synch;

import net.jcip.annotations.ThreadSafe;
import java.util.*;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T>, Cloneable {

    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    private synchronized List<T> copy(List<T> list) {
        return new ArrayList<>(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }
}