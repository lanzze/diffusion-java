package com.aslan.module.cipher;

import com.aslan.module.cipher.keys.SeriesKeyFactory;

public class KeyBuilder {
    static KeyFactory factory = new SeriesKeyFactory();

    public static Key make(CipherInfo option) throws Exception {
        return factory.make(option);
    }
}
