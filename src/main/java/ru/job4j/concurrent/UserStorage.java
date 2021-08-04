package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> users;
    private final AtomicInteger generator;

    public UserStorage(int startId) {
        generator = new AtomicInteger(startId);
        users = new HashMap<>();
    }

    public synchronized boolean add(User user) {
        int newId = generator.incrementAndGet();
        boolean result = !users.containsKey(newId);
        if (result) {
            user.setId(newId);
            users.put(newId, user);
        }
        return result;
    }

    public synchronized boolean update(User user) {
        int id = user.getId();
        boolean result = users.containsKey(id);
        if (result) {
            users.put(id, user);
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        int id = user.getId();
        boolean result = users.containsKey(id);
        if (result) {
            users.remove(id);
        }
        return result;
    }

    public synchronized User findById(int id) {
        User u = users.get(id);
        return new User(u.getId(), u.getAmount());
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (!users.containsKey(fromId) || !users.containsKey(toId)) {
            return false;
        }
        User fromUser = users.get(fromId);
        User toUser = users.get(toId);
        boolean result = fromUser.getAmount() >= amount;
        if (result) {
            fromUser.setAmount(fromUser.getAmount() - amount);
            toUser.setAmount(toUser.getAmount() + amount);
        }
        return result;
    }
}
