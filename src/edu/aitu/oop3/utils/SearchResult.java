package edu.aitu.oop3.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SearchResult<T> {
    private final List<T> items;

    public SearchResult(List<T> items) {
        this.items = (items == null) ? new ArrayList<>() : new ArrayList<>(items);
    }

    // Read-only view (қауіпсіз)
    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int count() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public T firstOrNull() {
        return items.isEmpty() ? null : items.get(0);
    }

    // Java 8 compatible filtering + null-safe
    public SearchResult<T> filter(Predicate<T> predicate) {
        if (predicate == null) return new SearchResult<>(items);

        List<T> filtered = items.stream()
                .filter(predicate)
                .collect(Collectors.toList());

        return new SearchResult<>(filtered);
    }
}
