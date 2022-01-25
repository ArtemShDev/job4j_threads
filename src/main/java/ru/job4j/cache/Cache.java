package ru.job4j.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        BiFunction<Integer, Base, Base> biFunction = (i, m) -> {
            Base stored = memory.get(i);
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            model.setVersion(stored.getVersion() + 1);
            return model;
        };
        return memory.computeIfPresent(model.getId(), biFunction) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public synchronized List<Base> getMemory() {
        return memory.values().stream().toList();
    }
}