package com.aslan.module.cipher;

import com.aslan.module.cipher.algorithms.SeriesAlgorithmFactory;

public class AlgorithmBuilder {

    static AlgorithmFactory factory = new SeriesAlgorithmFactory();

    public static Algorithm make(CipherInfo option) throws Exception {
        return factory.make(option);
    }
}
