package com.aslan.module.cipher;

public interface Padding {

    int compute(int N);

    void padding(byte[] buf, int offset, int count);
}

