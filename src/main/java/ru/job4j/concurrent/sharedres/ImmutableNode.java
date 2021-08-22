package ru.job4j.concurrent.sharedres;

public class ImmutableNode<T> {

    private final ImmutableNode<T> next;
    private final T value;

    public ImmutableNode(ImmutableNode<T> aNext, T aValue) {
        next = aNext;
        value = aValue;
    }

    public ImmutableNode<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
