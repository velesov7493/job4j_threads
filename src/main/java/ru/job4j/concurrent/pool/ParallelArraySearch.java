package ru.job4j.concurrent.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class ParallelArraySearch<T> extends RecursiveTask<Integer> {

    private static final int DEFAULT_ELEMENTS_ON_TASK = 10;

    private final T[] data;
    private final T model;
    private final int startIndex;
    private final int elementsCount;

    public ParallelArraySearch(T[] aData, T aModel, int aStartIndex, int aCount) {
        data = aData;
        model = aModel;
        startIndex = aStartIndex;
        elementsCount = aCount;
    }

    private ParallelArraySearch(T[] aData, T aModel, int aStartIndex) {
        data = aData;
        model = aModel;
        startIndex = aStartIndex;
        elementsCount = DEFAULT_ELEMENTS_ON_TASK;
    }

    private Integer sequencedSearch() {
        Integer result = null;
        int endIndex = Math.min(startIndex + elementsCount, data.length);
        for (int i = startIndex; i < endIndex; i++) {
            if (model.equals(data[i])) {
                result = i;
                break;
            }
        }
        return result;
    }

    private Integer parallelSearch() {
        Integer result = null;
        int taskCount = (int) Math.ceil((double) elementsCount / DEFAULT_ELEMENTS_ON_TASK);
        List<ParallelArraySearch<T>> tasks = new ArrayList<>();
        for (int j = 0; j < taskCount; j++) {
            ParallelArraySearch<T> task = new ParallelArraySearch<>(
                    data, model,
                    j * DEFAULT_ELEMENTS_ON_TASK
            );
            tasks.add(task);
            task.fork();
        }
        for (ParallelArraySearch<T> task : tasks) {
            Integer tmpResult = task.join();
            if (tmpResult != null && result == null) {
                result = tmpResult;
            }
        }
        return result;
    }

    @Override
    protected Integer compute() {
        return
                elementsCount <= DEFAULT_ELEMENTS_ON_TASK
                ? sequencedSearch()
                : parallelSearch();
    }
}
