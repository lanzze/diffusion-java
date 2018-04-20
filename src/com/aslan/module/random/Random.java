package com.aslan.module.random;

public interface Random {

    void seed(byte[] seed, int offset, int length);

    void next(byte[] accept, int offset, int length);

    byte rand();
}