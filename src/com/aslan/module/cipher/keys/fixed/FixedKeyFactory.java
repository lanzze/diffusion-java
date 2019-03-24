package com.aslan.module.cipher.keys.fixed;

import com.aslan.module.cipher.keys.VersionKeyFactory;
import com.aslan.module.cipher.keys.g3.SG2;

public class FixedKeyFactory extends VersionKeyFactory {
    {
        map.put(20, SG2.class);
        map.put(10, SG1.class);
        map.put(1, FixedKey1.class);
    }
}
