package com.aslan.module.cipher;

import com.aslan.module.core.Usable;

public interface Key extends Usable {

    /**
     * @param cipherInfo
     * @param algorithmInfo
     */
    void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo);

    byte[] update();

    int identity();

    int version();
}