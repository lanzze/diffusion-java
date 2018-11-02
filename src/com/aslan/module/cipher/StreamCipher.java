package com.aslan.module.cipher;

import java.io.InputStream;
import java.io.OutputStream;

public interface StreamCipher {

    void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo);

    int exec(InputStream input, OutputStream out, int length);
}