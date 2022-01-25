package ru.job4j.cache;

import org.checkerframework.checker.units.qual.C;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        Base model2 = new Base(2, 1);
        cache.add(model);
        cache.add(model2);
        assertThat(cache.getMemory(), is(List.of(model, model2)));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateFalse() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        Base modelModify = new Base(1, 2);
        modelModify.setName("TEST");
        cache.add(model);
        cache.update(modelModify);
    }

    @Test
    public void whenUpdateTrue() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        Base modelModify = new Base(1, 1);
        modelModify.setName("TEST");
        cache.add(model);
        cache.update(modelModify);
        assertThat(cache.getMemory().get(0).getName(), is("TEST"));
        assertThat(cache.getMemory().get(0).getVersion(), is(2));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        Base model2 = new Base(2, 1);
        cache.add(model);
        cache.add(model2);
        cache.delete(model);
        assertThat(cache.getMemory(), is(List.of(model2)));
    }
}