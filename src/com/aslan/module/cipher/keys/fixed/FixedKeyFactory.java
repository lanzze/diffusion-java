package com.aslan.module.cipher.keys.fixed;

import com.aslan.module.cipher.keys.VersionKeyFactory;

public class FixedKeyFactory extends VersionKeyFactory {
    {
        map.put(20, DCA_C2_FixedKey.class);
        map.put(10, DCA_C1_FixedKey.class);
        map.put(1, FixedKey1.class);
    }
}
