package ru.job4j.search;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchInArray<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private T obj;

    public SearchInArray(T[] array, int from, int to, T obj) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.obj = obj;
    }

    @Override
    protected Integer compute() {
        if (from == to) {
            if (array[from] == obj) {
                return from;
            }
            return -1;
        }
        int mid = (from + to) / 2;
        SearchInArray<T> leftSort = new SearchInArray<T>(array, from, mid, obj);
        SearchInArray<T> rightSort = new SearchInArray<T>(array, mid + 1, to, obj);
        leftSort.fork();
        rightSort.fork();
        int left = leftSort.join();
        int right = rightSort.join();
        return Math.max(left, right);
    }

    public static void main(String[] args) {
        Integer[] array = {2, 4, 4, 15, 78, 45, 77, 88, 92, 75, 32, 65, 42};
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(forkJoinPool.invoke(new SearchInArray(array, 0, array.length - 1, 92)));
    }
}
