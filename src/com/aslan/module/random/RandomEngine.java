package com.aslan.module.random;

public interface RandomEngine {

    void seed(byte[] seed, int offset, int length);

    int next(byte[] accept, int offset, int length);

    byte rand();

    /**
     * Return this engine need seed size.
     *
     * @return Need seed size.
     */
    int need();
}