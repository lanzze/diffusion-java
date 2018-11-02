package com.aslan.module.cipher.keys.fixed;

import com.aslan.module.cipher.keys.VersionKeyFactory;

public class FixedKeyFactory extends VersionKeyFactory {
    {
        map.put(1, FixedKey1.class);
        map.put(2, FixedKey2.class);
    }
}
