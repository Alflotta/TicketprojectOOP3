package edu.aitu.oop3.search;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Filters {
    public static <T> SearchResult<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> res = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) res.add(item);
        }
        return new SearchResult<>(res);
    }
}
