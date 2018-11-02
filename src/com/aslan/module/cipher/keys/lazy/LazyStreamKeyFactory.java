package com.aslan.module.cipher.keys.lazy;

import com.aslan.module.cipher.keys.VersionKeyFactory;

public class LazyStreamKeyFactory extends VersionKeyFactory {

    {
        map.put(1, LazyStreamKey1.class);
        map.put(2, LazyStreamKey2.class);
    }

}
