package com.aslan.module.utils;

public interface Filter<T, V> {

    V accept(T element);
}
