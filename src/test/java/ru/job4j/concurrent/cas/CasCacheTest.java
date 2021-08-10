package ru.job4j.concurrent.cas;

import org.junit.Test;

import static org.junit.Assert.*;

public class CasCacheTest {

    @Test
    public void whenAddSuccess() {
        CasCache cache = new CasCache();
        assertTrue(cache.add(new Base(1, 0)));
    }

    @Test
    public void whenAddFail() {
        CasCache cache = new CasCache();
        Base value = new Base(1, 0);
        cache.add(value);
        assertFalse(cache.add(value));
    }

    @Test
    public void whenUpdateSuccess() {
        CasCache cache = new CasCache();
        Base value = new Base(1, 1);
        cache.add(value);
        assertTrue(cache.update(value));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateFail() {
        CasCache cache = new CasCache();
        Base oldValue = new Base(1, 0);
        Base newValue = new Base(1, 1);
        cache.add(oldValue);
        cache.update(newValue);
    }
}