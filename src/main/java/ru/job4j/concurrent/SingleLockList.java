package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> aList) {
        list = copy(aList);
    }

    public SingleLockList() {
        list = new ArrayList<>();
    }

    private List<T> copy(List<T> list) {
        return new ArrayList<>(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    public synchronized void remove(int index) {
        list.remove(index);
    }

    public synchronized int size() {
        return list.size();
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }
}
