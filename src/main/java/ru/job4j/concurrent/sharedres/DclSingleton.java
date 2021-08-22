package ru.job4j.concurrent.sharedres;

public class DclSingleton {

    private static volatile DclSingleton inst;

    private DclSingleton() { }

    public static DclSingleton instOf() {
        if (inst == null) {
            synchronized (DclSingleton.class) {
                if (inst == null) {
                    inst = new DclSingleton();
                }
            }
        }
        return inst;
    }
}
