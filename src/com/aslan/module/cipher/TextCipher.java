package com.aslan.module.cipher;

public interface TextCipher {

    void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo);

    byte[] run(byte[] input, int offset, int length);
}