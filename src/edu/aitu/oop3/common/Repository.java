package edu.aitu.oop3.common;

import java.util.List;

public interface Repository<T> {
    T findById(int id);
    List<T> findAll();
}
