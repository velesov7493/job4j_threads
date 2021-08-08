package ru.job4j.concurrent;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import static org.hamcrest.core.Is.is;

import static org.junit.Assert.*;

public class SingleLockListTest {

    @Test
    public void whenAdd() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>();
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new LinkedHashSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2)));
    }

    @Test
    public void whenAddRemove() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>();
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.remove(0));
        first.start();
        second.start();
        first.join();
        second.join();
        assertEquals(0, list.size());
    }

    @Test
    public void whenAddAfterIterator() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>();
        Thread first = new Thread(() -> {
            list.add(1);
            list.add(2);
            list.add(3);
        });
        Thread second = new Thread(() -> {
            Iterator<Integer> it = list.iterator();
            int sum = 0;
            while (it.hasNext()) {
                sum += it.next();
            }
            list.add(sum);
        });
        Thread third = new Thread(() -> {
            list.add(7);
            list.add(8);
            list.add(9);
        });
        first.start();
        second.start();
        first.join();
        second.join();
        third.start();
        third.join();
        Set<Integer> result = new LinkedHashSet<>();
        list.iterator().forEachRemaining(result::add);
        assertThat(result, is(Set.of(1, 2, 3, 6, 7, 8, 9)));
    }
}