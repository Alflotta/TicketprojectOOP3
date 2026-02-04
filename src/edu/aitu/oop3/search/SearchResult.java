package edu.aitu.oop3.search;

import java.util.List;
public class SearchResult<T> {

    private final List<T> items;
    private final int total;

    public SearchResult(List<T> items) {
        this.items = items;
        this.total = items.size();
    }
    public List<T> getItems() { return items; }
    public int getTotal() { return total; }
}

