package com.aslan.module.random;

public interface SeedFactory {
    void init();

    void register(SeedSource source);

    int pull(byte[] accept, int offset, int length);
}