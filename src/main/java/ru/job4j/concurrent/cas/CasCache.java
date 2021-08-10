package ru.job4j.concurrent.cas;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CasCache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (k, v) -> {
            if (v.getVersion() != model.getVersion()) {
                throw new OptimisticException("Версии объектов не равны!");
            }
            Base result = new Base(model.getId(), v.getVersion() + 1);
            result.setName(model.getName());
            return result;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base getById(int id) {
        return memory.get(id);
    }
}
