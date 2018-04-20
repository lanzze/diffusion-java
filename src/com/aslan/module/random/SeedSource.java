package com.aslan.module.random;

public interface SeedSource {
    int pull(byte[] accept, int offset, int max);

    int capacity();

    float reliability();
}