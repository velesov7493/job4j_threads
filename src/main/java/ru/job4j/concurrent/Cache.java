package ru.job4j.concurrent;

public class Cache {

    private static Cache cache;

    synchronized public static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
