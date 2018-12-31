package com.aslan.module.cipher;

import com.aslan.module.core.Usable;

public interface Algorithm extends Usable {

    void init(AlgorithmInfo option);

    void enc(byte[] input, int offset, byte[] key);

    void dec(byte[] input, int offset, byte[] key);

    int identity();

    int version();
}
