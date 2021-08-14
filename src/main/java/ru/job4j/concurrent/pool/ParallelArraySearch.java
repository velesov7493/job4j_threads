package ru.job4j.concurrent.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class ParallelArraySearch<T> extends RecursiveTask<List<Integer>> {

    private static final int DEFAULT_ELEMENTS_ON_TASK = 10;

    private final T[] data;
    private final Predicate<T> condition;
    private final int startIndex;
    private final int elementsCount;

    public ParallelArraySearch(T[] aData, Predicate<T> aCondition, int aStartIndex, int aCount) {
        data = aData;
        condition = aCondition;
        startIndex = aStartIndex;
        elementsCount = aCount;
    }

    private ParallelArraySearch(T[] aData, Predicate<T> aCondition, int aStartIndex) {
        data = aData;
        condition = aCondition;
        startIndex = aStartIndex;
        elementsCount = DEFAULT_ELEMENTS_ON_TASK;
    }

    @Override
    protected List<Integer> compute() {
        List<Integer> result = new ArrayList<>();
        if (elementsCount <= DEFAULT_ELEMENTS_ON_TASK) {
            int endIndex = Math.min(startIndex + elementsCount, data.length);
            for (int i = startIndex; i < endIndex; i++) {
                if (condition.test(data[i])) {
                    result.add(i);
                }
            }
            return result;
        } else {
            int taskCount = (int) Math.ceil((double) elementsCount / DEFAULT_ELEMENTS_ON_TASK);
            List<ParallelArraySearch<T>> tasks = new ArrayList<>();
            for (int j = 0; j < taskCount; j++) {
                ParallelArraySearch<T> task = new ParallelArraySearch<>(
                        data, condition,
                        j * DEFAULT_ELEMENTS_ON_TASK
                );
                tasks.add(task);
                task.fork();
            }
            for (ParallelArraySearch<T> task : tasks) {
                result.addAll(task.join());
            }
        }
        return result;
    }
}
